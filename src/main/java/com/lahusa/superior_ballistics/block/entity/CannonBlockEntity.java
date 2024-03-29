package com.lahusa.superior_ballistics.block.entity;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.entity.CannonBallEntity;
import com.lahusa.superior_ballistics.entity.StoneBulletEntity;
import com.lahusa.superior_ballistics.util.CannonDistanceLookup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class CannonBlockEntity extends BlockEntity implements GeoBlockEntity, IStatusTextProvider {

    // Loading stages
    public static final short POWDER_LOADING_STAGE = 0;
    public static final short SHOT_LOADING_STAGE = 1;
    public static final short READY_STAGE = 2;
    public static final short LIT_STAGE = 3;
    public static final short CLEANUP_STAGE = 4;

    // Powder
    public static final short MAX_POWDER = 5;
    public static final short MAX_POWDER_OVERLOADING = 8;

    // Shot types
    public static final short NO_SHOT = 0;
    public static final short IRON_CANNONBALL = 1;
    public static final short IRON_GRAPESHOT = 2;
    public static final short ENDER_PEARL_SHOT = 3;
    public static final short BLANK_SHOT = 4;

    // Firing phase
    private static final double BARREL_LENGTH = 13.0/16.0;
    private static final double MUZZLE_PARTICLE_OFFSET = 4.5/16.0;
    private static final float FIRING_SOUND_VOLUME = 4.0f;
    private static final float FIRING_SOUND_FAR_VOLUME_COMPENSATION_RATE = 0.062f;
    private static final float FIRING_SOUND_PITCH = 1.1f;
    private static final float FIRING_SOUND_FAR_PITCH = 0.48f;
    private static final float FIRING_SOUND_FAR_RANGE = 380.0f;

    // Barrel angle
    public static final short MAX_ANGLE = 15;
    public static final Item DETAIL_ITEM = Items.CLOCK;

    private short angle = 4;
    private short loadingStage = 0;
    private short powderAmount = 0;
    private boolean isShotLoaded = false;
    private short shotType = NO_SHOT;
    private short litTicks = 0;
    private boolean isCreative = false;
    private UUID lastUserUUID = null;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public CannonBlockEntity(BlockPos pos, BlockState state) {
        super(SuperiorBallisticsMod.CANNON_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, CannonBlockEntity blockEntity) {
        if(blockEntity.getLoadingStage() == LIT_STAGE) {
            ++blockEntity.litTicks;
        }

        if(blockEntity.litTicks >= SuperiorBallisticsMod.CONFIG.getCannonMaxLitTicks()) {
            blockEntity.fire();
        }
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.animated_cannon.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public Text getStatusText(PlayerEntity player) {
        MutableText text = Text.literal("[").formatted(Formatting.GRAY);

        // Detail info item held
        if(player.getMainHandStack().isOf(Items.CLOCK) || player.getOffHandStack().isOf(Items.CLOCK)) {
            boolean isOverloaded = getPowderAmount() > MAX_POWDER;
            int rangeEstimate = (int)Math.round(CannonDistanceLookup.getShotRangeEstimate(isOverloaded ? MAX_POWDER : getPowderAmount(), getAngle()));

            text.append(Text.translatable("superior_ballistics.cannon.trajectory").append(Text.literal(": ")).formatted(Formatting.BLACK));
            text.append(Text.translatable("superior_ballistics.cannon.launch_angle").append(Text.literal(": ")).formatted(Formatting.DARK_GREEN));
            text.append(Text.literal("(" + getAngleDegrees() + "°) ").formatted(Formatting.GOLD));
            text.append(Text.translatable("superior_ballistics.cannon.range").append(Text.literal(": ")).formatted(Formatting.DARK_GREEN));
            text.append(Text.literal("(" + rangeEstimate + " ").append(Text.translatable("superior_ballistics.cannon.distance_unit")).append(Text.literal(")")).formatted(Formatting.GOLD));
        }
        // Generic info
        else {
            text.append(Text.translatable("superior_ballistics.cannon.loading_stage").formatted(Formatting.BLACK));
            switch (getLoadingStage()) {
                case CannonBlockEntity.POWDER_LOADING_STAGE -> {
                    text.append(Text.translatable("superior_ballistics.cannon.insert_powder").formatted(Formatting.DARK_GREEN));
                    text.append(Text.literal(" (" + getPowderAmount() + "/" + CannonBlockEntity.MAX_POWDER + ")")
                            .formatted(getPowderAmount() > CannonBlockEntity.MAX_POWDER ? Formatting.RED : Formatting.GOLD)
                    );
                }
                case CannonBlockEntity.SHOT_LOADING_STAGE -> {
                    text.append(Text.translatable("superior_ballistics.cannon.insert_shot").formatted(Formatting.DARK_GREEN));
                    text.append(Text.literal(" (").append(getShotName()).append(Text.literal(")")).formatted(Formatting.GOLD));
                }
                case CannonBlockEntity.READY_STAGE -> {
                    // Show different text when cannon is creative
                    if(isCreative()) {
                        text.append(Text.translatable("superior_ballistics.cannon.ready_to_light_creative").formatted(Formatting.LIGHT_PURPLE));

                    }
                    else {
                        text.append(Text.translatable("superior_ballistics.cannon.ready_to_light").formatted(Formatting.DARK_GREEN));
                    }

                    text.append(Text.literal(" (").append(Text.translatable("item.minecraft.flint_and_steel"))
                            .append(Text.literal(" / ")).append(Text.translatable("itemGroup.redstone")).append(Text.literal(")"))
                            .formatted(Formatting.GOLD));
                }
                case CannonBlockEntity.LIT_STAGE -> text.append(Text.translatable("superior_ballistics.cannon.firing").formatted(Formatting.RED));
                case CannonBlockEntity.CLEANUP_STAGE -> text.append(Text.translatable("superior_ballistics.cannon.cleanup").formatted(Formatting.DARK_GREEN));
                default -> text.append(Text.literal("INVALID").formatted(Formatting.RED));
            }
        }

        text.append(Text.literal("]").formatted(Formatting.GRAY));

        return text;
    }

    private void fire() {
        if(world == null) return;

        if(loadingStage == LIT_STAGE) {
            // Summon particles
            if(!world.isClient) {
                // Play sound
                playFiringSound();

                // Determine barrel pivot point
                Vec3d pivot = new Vec3d(0.5, 7.5/16.0, 0.5);
                final double offset = 1.0/32.0;
                switch (world.getBlockState(pos).get(Properties.HORIZONTAL_FACING)) {
                    case NORTH -> pivot = pivot.add(0.0, 0.0,  offset);
                    case SOUTH -> pivot = pivot.add(0.0, 0.0, -offset);
                    case EAST  -> pivot = pivot.add(-offset, 0.0, 0.0);
                    case WEST  -> pivot = pivot.add (offset, 0.0, 0.0);
                    default -> throw new IllegalStateException("Unexpected value: " + world.getBlockState(pos).get(Properties.HORIZONTAL_FACING));
                }

                // Determine barrel direction
                Vec3d dir = getBarrelDirection();

                Vec3d muzzleParticlePos = new Vec3d(
                        pos.getX() + pivot.x + (BARREL_LENGTH + MUZZLE_PARTICLE_OFFSET) * dir.x,
                        pos.getY() + pivot.y + (BARREL_LENGTH + MUZZLE_PARTICLE_OFFSET) * dir.y,
                        pos.getZ() + pivot.z + (BARREL_LENGTH + MUZZLE_PARTICLE_OFFSET) * dir.z
                );

                // Spawn particles
                // Explosion Emitter
                ((ServerWorld)world).spawnParticles(
                        ParticleTypes.EXPLOSION,
                        muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z,
                        1, 0.1, 0.1, 0.1, 0.08
                );

                // Muzzle area smoke
                ((ServerWorld)world).spawnParticles(
                        SuperiorBallisticsMod.CANNON_MUZZLE_SMOKE_TRAIL,
                        muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z,
                        150, 0.3, 0.3, 0.3, 0.08
                );

                // Trail smoke
                for(int i = 0; i < 250; i++) {
                    final double scaleFac = 0.1;
                    double a = world.random.nextGaussian();
                    double b = world.random.nextGaussian();
                    double c = world.random.nextGaussian();
                    Vec3d particleDir = new Vec3d(dir.x + scaleFac * a, dir.y + scaleFac * b, dir.z + scaleFac * c);

                    float speed = getProjectileSpeedFactor() * 0.25f * (float) Math.abs(world.random.nextGaussian());

                    ((ServerWorld)world).spawnParticles(
                            SuperiorBallisticsMod.CANNON_MUZZLE_SMOKE_TRAIL,
                            muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z,
                            0, particleDir.x, particleDir.y, particleDir.z, speed
                    );
                }

                // Fire
                for(int i = 0; i < 150; i++) {
                    final double scaleFac = 0.05;
                    double a = world.random.nextGaussian();
                    double b = world.random.nextGaussian();
                    double c = world.random.nextGaussian();
                    Vec3d particleDir = new Vec3d(dir.x + scaleFac * a, dir.y + scaleFac * b, dir.z + scaleFac * c);

                    float speed = getProjectileSpeedFactor() * 0.175f * (float) Math.abs(world.random.nextGaussian());

                    ((ServerWorld)world).spawnParticles(
                            SuperiorBallisticsMod.CANNON_MUZZLE_FIRE,
                            muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z,
                            0, particleDir.x, particleDir.y, particleDir.z, speed
                    );
                }

                // Fire shot
                if(lastUserUUID != null) {
                    PlayerEntity player = world.getPlayerByUuid(lastUserUUID);

                    if(player == null) return;

                    // Check for powder overload
                    if(powderAmount > MAX_POWDER && shotType != BLANK_SHOT && SuperiorBallisticsMod.CONFIG.isCannonOverchargingAllowed()) {
                        // Hard remove cannon, so it cannot survive the explosion
                        world.removeBlock(pos, false);

                        // Blow up cannon
                        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), Math.min(0.6f * powderAmount, 10.0f), World.ExplosionSourceType.BLOCK);

                        // Trigger overcharge advancement criterion
                        SuperiorBallisticsMod.CANNON_OVERCHARGE_CRITERION.trigger((ServerPlayerEntity) player);
                    }
                    // Normal powder amount
                    else {
                        switch(shotType) {
                            case IRON_CANNONBALL -> {
                                CannonBallEntity cannonBallEntity = new CannonBallEntity(world, muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z);
                                cannonBallEntity.setItem(new ItemStack(SuperiorBallisticsMod.IRON_CANNONBALL));
                                cannonBallEntity.setVelocity(
                                        player, getProjectilePitch(), getProjectileYaw(), 0.0F,
                                        getProjectileSpeedFactor() * SuperiorBallisticsMod.CONFIG.getCannonShotSpeed(),
                                        SuperiorBallisticsMod.CONFIG.getCannonShotDivergence());
                                world.spawnEntity(cannonBallEntity);
                            }
                            case ENDER_PEARL_SHOT -> {
                                EnderPearlEntity enderPearlEntity = new EnderPearlEntity(world, player);
                                enderPearlEntity.setPos(muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z);
                                enderPearlEntity.setItem(new ItemStack(Items.ENDER_PEARL));
                                enderPearlEntity.setVelocity(player, getProjectilePitch(), getProjectileYaw(), 0.0F,
                                        getProjectileSpeedFactor() * SuperiorBallisticsMod.CONFIG.getCannonShotSpeed(),
                                        SuperiorBallisticsMod.CONFIG.getCannonShotDivergence());
                                world.spawnEntity(enderPearlEntity);

                                // Trigger advancement
                                SuperiorBallisticsMod.ENDER_PEARL_CANNON_SHOT.trigger((ServerPlayerEntity) player);
                            }
                            case IRON_GRAPESHOT -> {
                                for(int i = 0; i < 8; i++) {
                                    StoneBulletEntity grapeshotEntity = new StoneBulletEntity(world, muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z, 13, StatusEffects.SLOWNESS);
                                    grapeshotEntity.setItem(new ItemStack(SuperiorBallisticsMod.IRON_SINGLE_GRAPESHOT));
                                    grapeshotEntity.setVelocity(player, getProjectilePitch(), getProjectileYaw(), 0.0F,
                                            getProjectileSpeedFactor() * SuperiorBallisticsMod.CONFIG.getCannonShotSpeed(),
                                            SuperiorBallisticsMod.CONFIG.getCannonGrapeShotDivergence());
                                    world.spawnEntity(grapeshotEntity);
                                }
                            }
                        }
                    }
                }
            }

            if(!isCreative) {
                loadingStage = CLEANUP_STAGE;
            }
            else {
                loadingStage = READY_STAGE;
                litTicks = 0;
            }
        }
    }

    private void playFiringSound() {
        if(world == null || world.isClient) return;
        ServerWorld serverWorld = ((ServerWorld)world);

        // Reduce close/far sound shift dist to 80%, so the change feels smoother
        double closeSoundDist = 0.8 * 16.0 * FIRING_SOUND_VOLUME;

        // Send sound packet to each player
        for(ServerPlayerEntity player : serverWorld.getPlayers()) {
            double playerDist = Math.sqrt(pos.getSquaredDistanceFromCenter(player.getX(), player.getY(), player.getZ()));

            // Player in close sound range
            if(playerDist <= closeSoundDist ) {
                player.networkHandler.sendPacket(
                        new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(SuperiorBallisticsMod.CANNON_SHOT_SOUND_EVENT),
                                SoundCategory.NEUTRAL, pos.getX(), pos.getY(), pos.getZ(), FIRING_SOUND_VOLUME, FIRING_SOUND_PITCH, 0)
                );
            }
            // Player in far sound range (apply range compensation and change pitch)
            else if(playerDist <= FIRING_SOUND_FAR_RANGE) {
                float rangeCompensation = FIRING_SOUND_FAR_VOLUME_COMPENSATION_RATE * (float) (playerDist - closeSoundDist);
                player.networkHandler.sendPacket(
                        new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(SuperiorBallisticsMod.CANNON_SHOT_SOUND_EVENT),
                                SoundCategory.NEUTRAL, pos.getX(), pos.getY(), pos.getZ(), FIRING_SOUND_VOLUME + rangeCompensation, FIRING_SOUND_FAR_PITCH, 0)
                );
            }
        }

    }

    public void addPowder(@Nullable PlayerEntity player) {
        if(!canLoadPowder()) return;

        if(loadingStage == POWDER_LOADING_STAGE) {
            ++powderAmount;
            markDirty();
            sync();

            if(player != null) {
                updateLastUserUUID(player);
            }
        }
    }

    public void loadShot(short shotTypeToLoad, @Nullable PlayerEntity player) {
        if(!isShotLoaded) {
            shotType = shotTypeToLoad;
            isShotLoaded = true;
            markDirty();
            sync();

            if(player != null) {
                updateLastUserUUID(player);
            }
        }
    }

    public void push(@Nullable PlayerEntity player) {
        if(loadingStage == POWDER_LOADING_STAGE && powderAmount > 0) {
            loadingStage = SHOT_LOADING_STAGE;
            markDirty();
            sync();

            if(player != null) {
                updateLastUserUUID(player);
            }
        }
        else if(loadingStage == SHOT_LOADING_STAGE && isShotLoaded && shotType != NO_SHOT) {
            loadingStage = READY_STAGE;
            markDirty();
            sync();

            if(player != null) {
                updateLastUserUUID(player);
            }
        }
    }

    public void light(@Nullable PlayerEntity player) {
        if(loadingStage == READY_STAGE) {
            loadingStage = LIT_STAGE;
            markDirty();
            sync();

            if(player != null) {
                updateLastUserUUID(player);
            }
        }
    }

    public void clean() {
        if(loadingStage == CLEANUP_STAGE) {
            if(world != null) {
                // Summon particles
                if(!world.isClient) {
                    // Determine barrel pivot point
                    Vec3d pivot = new Vec3d(0.5, 7.5/16.0, 0.5);
                    final double offset = 1.0/32.0;
                    switch (world.getBlockState(pos).get(Properties.HORIZONTAL_FACING)) {
                        case NORTH -> pivot = pivot.add(0.0, 0.0,  offset);
                        case SOUTH -> pivot = pivot.add(0.0, 0.0, -offset);
                        case EAST  -> pivot = pivot.add(-offset, 0.0, 0.0);
                        case WEST  -> pivot = pivot.add (offset, 0.0, 0.0);
                        default -> throw new IllegalStateException("Unexpected value: " + world.getBlockState(pos).get(Properties.HORIZONTAL_FACING));
                    }

                    // Determine barrel direction
                    Vec3d dir = getBarrelDirection();

                    // Spawn particles
                    // Muzzle area smoke
                    ((ServerWorld)world).spawnParticles(
                            ParticleTypes.SMOKE,
                            pos.getX() + pivot.x + 1.15 * BARREL_LENGTH * dir.x,
                            pos.getY() + pivot.y + 1.15 * BARREL_LENGTH * dir.y,
                            pos.getZ() + pivot.z + 1.15 * BARREL_LENGTH * dir.z,
                            world.random.nextInt(3) + 2, 0.05, 0.05, 0.05, 0.06
                    );

                    // Splash
                    ((ServerWorld)world).spawnParticles(
                            ParticleTypes.SPLASH,
                            pos.getX() + pivot.x + 1.05 * BARREL_LENGTH * dir.x,
                            pos.getY() + pivot.y + 1.05 * BARREL_LENGTH * dir.y,
                            pos.getZ() + pivot.z + 1.05 * BARREL_LENGTH * dir.z,
                            world.random.nextInt(4)+3, 0.01, 0.01, 0.01, 0.05
                    );

                    // Drip
                    if(world.random.nextInt(3) == 2) {
                        ((ServerWorld)world).spawnParticles(
                                ParticleTypes.DRIPPING_WATER,
                                pos.getX() + pivot.x + 1.05 * BARREL_LENGTH * dir.x,
                                pos.getY() + pivot.y + 1.05 * BARREL_LENGTH * dir.y,
                                pos.getZ() + pivot.z + 1.05 * BARREL_LENGTH * dir.z,
                                1, 0.01, 0.01, 0.01, 1
                        );
                    }
                }
            }

            reset();
        }
    }

    public void reset() {
        loadingStage = POWDER_LOADING_STAGE;
        powderAmount = 0;
        isShotLoaded = false;
        shotType = NO_SHOT;
        litTicks = 0;
        lastUserUUID = null;
        markDirty();
        sync();
    }

    public void setCreative(boolean creative) {
        isCreative = creative;
        markDirty();
        sync();
    }

    private Vec3d getBarrelDirection() {
        double angleDegrees = getAngleDegrees();
        double slopeHorizontal = Math.cos(Math.toRadians(angleDegrees));
        double slopeVertical = Math.sin(Math.toRadians(angleDegrees));
        Vec3d dir = new Vec3d(0.0, slopeVertical, 0.0);

        if(world == null) return dir;

        switch (world.getBlockState(pos).get(Properties.HORIZONTAL_FACING)) {
            case NORTH -> dir = dir.add(0.0, 0.0, -slopeHorizontal);
            case SOUTH -> dir = dir.add(0.0, 0.0,  slopeHorizontal);
            case EAST  -> dir = dir.add( slopeHorizontal, 0.0, 0.0);
            case WEST  -> dir = dir.add(-slopeHorizontal, 0.0, 0.0);
            default -> throw new IllegalStateException("Unexpected value: " + world.getBlockState(pos).get(Properties.HORIZONTAL_FACING));
        }
        return dir.normalize();
    }

    private void updateLastUserUUID(PlayerEntity player) {
        lastUserUUID = player.getUuid();
        markDirty();
        sync();
    }

    public boolean canLoadPowder() {
        return powderAmount < MAX_POWDER || SuperiorBallisticsMod.CONFIG.isCannonOverchargingAllowed() && powderAmount < MAX_POWDER_OVERLOADING;
    }

    private float getProjectileSpeedFactor() {
        return powderAmount;
    }

    private float getProjectileYaw() {
        if(world == null) return 0.0f;

        BlockState state = world.getBlockState(pos);
        float yaw = 0.0f;
        switch (state.get(Properties.HORIZONTAL_FACING)) {
            case NORTH -> yaw = 180.0f;
            case EAST -> yaw = -90.0f;
            case WEST -> yaw = 90.0f;
        }

        return yaw;
    }

    private float getProjectilePitch() {
        return -getAngleDegrees();
    }

    public float getAngleDegrees() {
        return 90.0f * angle / (MAX_ANGLE + 1);
    }

    public Text getShotName() {
        return switch (shotType) {
            case IRON_CANNONBALL -> Text.translatable("item.superior_ballistics.iron_cannonball");
            case IRON_GRAPESHOT -> Text.translatable("item.superior_ballistics.iron_grapeshot");
            case ENDER_PEARL_SHOT -> Text.translatable("item.minecraft.ender_pearl");
            case BLANK_SHOT -> Text.translatable("superior_ballistics.cannon.blank_shot");
            default -> Text.translatable("superior_ballistics.cannon.empty");
        };
    }

    public void setAngle(short angle) {
        this.angle = angle;
    }

    public short getAngle() {
        return angle;
    }

    public short getLoadingStage() {
        return loadingStage;
    }

    public short getPowderAmount() {
        return powderAmount;
    }

    public boolean isShotLoaded() {
        return isShotLoaded;
    }

    public short getShotType() {
        return shotType;
    }

    public boolean isCreative() {
        return isCreative;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        tag.putShort("angle", angle);
        tag.putShort("loadingState", loadingStage);
        tag.putShort("powderAmount", powderAmount);
        tag.putBoolean("isShotLoaded", isShotLoaded);
        tag.putShort("shotType", shotType);
        tag.putShort("litTicks", litTicks);
        tag.putBoolean("isCreative", isCreative);
        if(lastUserUUID != null) tag.putUuid("lastUserUUID", lastUserUUID);

        super.writeNbt(tag);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

        angle = tag.getShort("angle");
        loadingStage = tag.getShort("loadingState");
        powderAmount = (short)Math.min(tag.getShort("powderAmount"), Short.MAX_VALUE);
        isShotLoaded = tag.getBoolean("isShotLoaded");
        shotType = tag.getShort("shotType");
        litTicks = tag.getShort("litTicks");
        isCreative = tag.getBoolean("isCreative");
        if(tag.contains("lastUserUUID")) lastUserUUID = tag.getUuid("lastUserUUID");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    private void sync() {
        World world = getWorld();
        if(world != null) getWorld().updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
    }
}
