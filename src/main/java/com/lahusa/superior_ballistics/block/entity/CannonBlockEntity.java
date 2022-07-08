package com.lahusa.superior_ballistics.block.entity;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.entity.CannonBallEntity;
import com.lahusa.superior_ballistics.entity.StoneBulletEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class CannonBlockEntity extends BlockEntity implements IAnimatable {

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

    // Lit phase
    public static final short MAX_LIT_TICKS = 40;

    // Firing phase
    private static final double BARREL_LENGTH = 13.0/16.0;
    private static final double MUZZLE_PARTICLE_OFFSET = 4.5/16.0;
    private static final float FIRING_SOUND_VOLUME = 2.0f;
    private static final float FIRING_SOUND_PITCH = 1.2f;
    private static final float SHOT_SPEED = 2.0f;
    private static final float SHOT_DIVERGENCE = 1.2f;
    private static final float GRAPESHOT_DIVERGENCE = 3.0f;

    // Barrel angle
    public static final short MAX_ANGLE = 14;

    private short angle = 4;
    private short loadingStage = 0;
    private short powderAmount = 0;
    private boolean isShotLoaded = false;
    private short shotType = NO_SHOT;
    private short litTicks = 0;
    private boolean isCreative = false;
    private UUID lastUserUUID = null;

    private AnimationFactory factory = new AnimationFactory(this);


    public CannonBlockEntity(BlockPos pos, BlockState state) {
        super(SuperiorBallisticsMod.CANNON_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, CannonBlockEntity blockEntity) {
        if(blockEntity.getLoadingStage() == LIT_STAGE) {
            ++blockEntity.litTicks;
        }

        if(blockEntity.litTicks >= MAX_LIT_TICKS) {
            blockEntity.fire();
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.animated_cannon.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public Text getStatusText() {
        MutableText text = new LiteralText("[").formatted(Formatting.GRAY)
                .append(new TranslatableText("superior_ballistics.cannon.loading_stage").formatted(Formatting.BLACK));

        switch (getLoadingStage()) {
            case CannonBlockEntity.POWDER_LOADING_STAGE -> {
                text.append(new TranslatableText("superior_ballistics.cannon.insert_powder").formatted(Formatting.DARK_GREEN));
                text.append(new LiteralText(" (" + getPowderAmount() + "/" + CannonBlockEntity.MAX_POWDER + ")")
                        .formatted(getPowderAmount() > CannonBlockEntity.MAX_POWDER ? Formatting.RED : Formatting.GOLD)
                );
            }
            case CannonBlockEntity.SHOT_LOADING_STAGE -> {
                text.append(new TranslatableText("superior_ballistics.cannon.insert_shot").formatted(Formatting.DARK_GREEN));
                text.append(new LiteralText(" (").append(getShotName()).append(new LiteralText(")")).formatted(Formatting.GOLD));
            }
            case CannonBlockEntity.READY_STAGE -> {
                // Show different text when cannon is creative
                if(!isCreative()) {
                    text.append(new TranslatableText("superior_ballistics.cannon.ready_to_light").formatted(Formatting.DARK_GREEN));
                }
                else {
                    text.append(new TranslatableText("superior_ballistics.cannon.ready_to_light_creative").formatted(Formatting.LIGHT_PURPLE));
                }

                text.append(new LiteralText(" (").append(new TranslatableText("item.minecraft.flint_and_steel"))
                        .append(new LiteralText(" / ")).append(new TranslatableText("itemGroup.redstone")).append(new LiteralText(")"))
                        .formatted(Formatting.GOLD));
            }
            case CannonBlockEntity.LIT_STAGE -> text.append(new TranslatableText("superior_ballistics.cannon.firing").formatted(Formatting.RED));
            case CannonBlockEntity.CLEANUP_STAGE -> text.append(new TranslatableText("superior_ballistics.cannon.cleanup").formatted(Formatting.DARK_GREEN));
            default -> text.append(new LiteralText("INVALID").formatted(Formatting.RED));
        }
        text.append(new LiteralText("]").formatted(Formatting.GRAY));

        return text;
    }

    private void fire() {
        if(world == null) return;

        if(loadingStage == LIT_STAGE) {
            // Play sound
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, FIRING_SOUND_VOLUME, FIRING_SOUND_PITCH);

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

                    float speed = getProjectileSpeedFactor() * 0.5f * (float) Math.abs(world.random.nextGaussian());

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

                    float speed = getProjectileSpeedFactor() * 0.35f * (float) Math.abs(world.random.nextGaussian());

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
                    if(powderAmount > MAX_POWDER && shotType != BLANK_SHOT) {
                        // Blow up cannon
                        world.createExplosion(player, pos.getX(), pos.getY(), pos.getZ(), Math.min(0.7f * powderAmount, 20.0f), true, Explosion.DestructionType.DESTROY);
                    }
                    // Normal powder amount
                    else {
                        switch(shotType) {
                            case IRON_CANNONBALL -> {
                                CannonBallEntity cannonBallEntity = new CannonBallEntity(world, muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z);
                                cannonBallEntity.setItem(new ItemStack(SuperiorBallisticsMod.IRON_CANNONBALL));
                                cannonBallEntity.setVelocity(player, getProjectilePitch(), getProjectileYaw(), 0.0F, getProjectileSpeedFactor() * SHOT_SPEED, SHOT_DIVERGENCE);
                                world.spawnEntity(cannonBallEntity);
                            }
                            case ENDER_PEARL_SHOT -> {
                                EnderPearlEntity enderPearlEntity = new EnderPearlEntity(world, player);
                                enderPearlEntity.setPos(muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z);
                                enderPearlEntity.setItem(new ItemStack(Items.ENDER_PEARL));
                                enderPearlEntity.setVelocity(player, getProjectilePitch(), getProjectileYaw(), 0.0F, getProjectileSpeedFactor() * SHOT_SPEED, SHOT_DIVERGENCE);
                                world.spawnEntity(enderPearlEntity);
                            }
                            case IRON_GRAPESHOT -> {
                                for(int i = 0; i < 8; i++) {
                                    StoneBulletEntity grapeshotEntity = new StoneBulletEntity(world, muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z, 13, StatusEffects.SLOWNESS);
                                    grapeshotEntity.setItem(new ItemStack(SuperiorBallisticsMod.IRON_SINGLE_GRAPESHOT));
                                    grapeshotEntity.setVelocity(player, getProjectilePitch(), getProjectileYaw(), 0.0F, getProjectileSpeedFactor() * SHOT_SPEED, GRAPESHOT_DIVERGENCE);
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

    public boolean loadShot(short shotTypeToLoad, @Nullable PlayerEntity player) {
        if(!isShotLoaded) {
            shotType = shotTypeToLoad;
            isShotLoaded = true;
            markDirty();
            sync();

            if(player != null) {
                updateLastUserUUID(player);
            }

            return true;
        }
        return false;
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
        return powderAmount < MAX_POWDER_OVERLOADING;
    }

    private float getProjectileSpeedFactor() {
        return 0.5f * powderAmount;
    }

    private float getProjectileYaw() {
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
            case IRON_CANNONBALL -> new TranslatableText("item.superior_ballistics.iron_cannonball");
            case IRON_GRAPESHOT -> new TranslatableText("item.superior_ballistics.iron_grapeshot");
            case ENDER_PEARL_SHOT -> new TranslatableText("item.minecraft.ender_pearl");
            case BLANK_SHOT -> new TranslatableText("superior_ballistics.cannon.blank_shot");
            default -> new TranslatableText("superior_ballistics.cannon.empty");
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
        getWorld().updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
    }
}
