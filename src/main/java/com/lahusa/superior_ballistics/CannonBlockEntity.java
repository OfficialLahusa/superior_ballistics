package com.lahusa.superior_ballistics;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CannonBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

    // Loading stages
    public static final short POWDER_LOADING_STAGE = 0;
    public static final short SHOT_LOADING_STAGE = 1;
    public static final short READY_STAGE = 2;
    public static final short LIT_STAGE = 3;
    public static final short CLEANUP_STAGE = 4;

    // Powder
    public static final short MAX_POWDER = 5;

    // Shot types
    public static final short NO_SHOT = 0;
    public static final short IRON_CANNONBALL = 1;
    public static final short IRON_GRAPESHOT = 2;
    public static final short BLANK_SHOT = 3;

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

    private short loadingStage = 0;
    private short powderAmount = 0;
    private boolean isShotLoaded = false;
    private short shotType = NO_SHOT;
    private short litTicks = 0;
    private UUID lastUserUUID = null;


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
                double angle = world.getBlockState(pos).get(CannonBlock.ANGLE) * 22.5;
                double slopeHorizontal = Math.cos(Math.toRadians(angle));
                double slopeVertical = Math.sin(Math.toRadians(angle));
                Vec3d dir = new Vec3d(0.0, slopeVertical, 0.0);
                switch (world.getBlockState(pos).get(Properties.HORIZONTAL_FACING)) {
                    case NORTH -> dir = dir.add(0.0, 0.0, -slopeHorizontal);
                    case SOUTH -> dir = dir.add(0.0, 0.0,  slopeHorizontal);
                    case EAST  -> dir = dir.add( slopeHorizontal, 0.0, 0.0);
                    case WEST  -> dir = dir.add(-slopeHorizontal, 0.0, 0.0);
                    default -> throw new IllegalStateException("Unexpected value: " + world.getBlockState(pos).get(Properties.HORIZONTAL_FACING));
                }
                dir = dir.normalize();

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
                                cannonBallEntity.setProperties(player, getProjectilePitch(), getProjectileYaw(), 0.0F, getProjectileSpeedFactor() * SHOT_SPEED, SHOT_DIVERGENCE);
                                world.spawnEntity(cannonBallEntity);
                            }
                            case IRON_GRAPESHOT -> {
                                for(int i = 0; i < 8; i++) {
                                    StoneBulletEntity grapeshotEntity = new StoneBulletEntity(world, muzzleParticlePos.x, muzzleParticlePos.y, muzzleParticlePos.z, 13, StatusEffects.SLOWNESS);
                                    grapeshotEntity.setItem(new ItemStack(SuperiorBallisticsMod.IRON_SINGLE_GRAPESHOT));
                                    grapeshotEntity.setProperties(player, getProjectilePitch(), getProjectileYaw(), 0.0F, getProjectileSpeedFactor() * SHOT_SPEED, GRAPESHOT_DIVERGENCE);
                                    world.spawnEntity(grapeshotEntity);
                                }
                            }
                        }
                    }
                }
            }

            loadingStage = CLEANUP_STAGE;
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
                    double angle = world.getBlockState(pos).get(CannonBlock.ANGLE) * 22.5;
                    double slopeHorizontal = Math.cos(Math.toRadians(angle));
                    double slopeVertical = Math.sin(Math.toRadians(angle));
                    Vec3d dir = new Vec3d(0.0, slopeVertical, 0.0);
                    switch (world.getBlockState(pos).get(Properties.HORIZONTAL_FACING)) {
                        case NORTH -> dir = dir.add(0.0, 0.0, -slopeHorizontal);
                        case SOUTH -> dir = dir.add(0.0, 0.0,  slopeHorizontal);
                        case EAST  -> dir = dir.add( slopeHorizontal, 0.0, 0.0);
                        case WEST  -> dir = dir.add(-slopeHorizontal, 0.0, 0.0);
                        default -> throw new IllegalStateException("Unexpected value: " + world.getBlockState(pos).get(Properties.HORIZONTAL_FACING));
                    }
                    dir = dir.normalize();

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

    private void updateLastUserUUID(PlayerEntity player) {
        lastUserUUID = player.getUuid();
        markDirty();
        sync();
    }

    public boolean canLoadPowder() {
        return powderAmount < Short.MAX_VALUE;
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
        BlockState state = world.getBlockState(pos);

        return -22.5f * state.get(CannonBlock.ANGLE);
    }

    public String getShotName() {
        return switch (shotType) {
            case IRON_CANNONBALL -> "Iron Shot";
            case IRON_GRAPESHOT -> "Iron Grapeshot";
            case BLANK_SHOT -> "Blank Shot";
            default -> "None";
        };
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

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {

        tag.putShort("loadingState", loadingStage);
        tag.putShort("powderAmount", powderAmount);
        tag.putBoolean("isShotLoaded", isShotLoaded);
        tag.putShort("shotType", shotType);
        tag.putShort("litTicks", litTicks);
        if(lastUserUUID != null) tag.putUuid("lastUserUUID", lastUserUUID);

        super.writeNbt(tag);

        return tag;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

        loadingStage = tag.getShort("loadingState");
        powderAmount = (short)Math.min(tag.getShort("powderAmount"), Short.MAX_VALUE);
        isShotLoaded = tag.getBoolean("isShotLoaded");
        shotType = tag.getShort("shotType");
        litTicks = tag.getShort("litTicks");
        if(tag.contains("lastUserUUID")) lastUserUUID = tag.getUuid("lastUserUUID");
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        readNbt(tag);
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        return writeNbt(tag);
    }
}
