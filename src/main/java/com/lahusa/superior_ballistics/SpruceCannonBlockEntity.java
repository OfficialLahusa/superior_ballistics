package com.lahusa.superior_ballistics;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpruceCannonBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

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
    public static final short IRON_SHOT = 1;

    // Lit phase
    public static final short MAX_LIT_TICKS = 40;

    // Firing phase
    public static final double BARREL_LENGTH = 13.0/16.0;

    private short loadingStage = 0;
    private short powderAmount = 0;
    private boolean isShotLoaded = false;
    private short shotType = NO_SHOT;
    private short litTicks = 0;


    public SpruceCannonBlockEntity(BlockPos pos, BlockState state) {
        super(SuperiorBallisticsMod.SPRUCE_CANNON_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, SpruceCannonBlockEntity blockEntity) {
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
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 2.0f, 1.2f);

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
                double angle = world.getBlockState(pos).get(SpruceCannonBlock.ANGLE) * 22.5;
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

                // Spawn particles
                ((ServerWorld)world).spawnParticles(
                        ParticleTypes.POOF,
                        pos.getX() + pivot.x + 1.2 * BARREL_LENGTH * dir.x,
                        pos.getY() + pivot.y + 1.2 * BARREL_LENGTH * dir.y,
                        pos.getZ() + pivot.z + 1.2 * BARREL_LENGTH * dir.z,
                        75, 0.3, 0.3, 0.3, 0.1
                );
            }

            loadingStage = CLEANUP_STAGE;
        }
    }

    public void addPowder() {
        if(!canLoadPowder()) return;

        if(loadingStage == POWDER_LOADING_STAGE) {
            ++powderAmount;
            markDirty();
        }
    }

    public boolean loadShot(short shotTypeToLoad)
    {
        if(!isShotLoaded) {
            shotType = shotTypeToLoad;
            isShotLoaded = true;
            markDirty();
            return true;
        }
        else {
            return false;
        }
    }

    public void push() {
        if(loadingStage == POWDER_LOADING_STAGE && powderAmount > 0) {
            loadingStage = SHOT_LOADING_STAGE;
            markDirty();
        }
        else if(loadingStage == SHOT_LOADING_STAGE && isShotLoaded && shotType != NO_SHOT) {
            loadingStage = READY_STAGE;
            markDirty();
        }
    }

    public void light() {
        if(loadingStage == READY_STAGE) {
            loadingStage = LIT_STAGE;
            markDirty();
        }
    }

    public void clean() {
        if(loadingStage == CLEANUP_STAGE) {
            reset();
        }
    }

    public void reset() {
        loadingStage = POWDER_LOADING_STAGE;
        powderAmount = 0;
        isShotLoaded = false;
        shotType = NO_SHOT;
        litTicks = 0;
        markDirty();
    }

    public boolean canLoadPowder() {
        return powderAmount < MAX_POWDER;
    }

    public String getShotName() {
        switch(shotType) {
            case IRON_SHOT:
                return "Iron Shot";
            case NO_SHOT:
            default:
                return "None";
        }
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

        super.writeNbt(tag);

        return tag;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

        loadingStage = tag.getShort("loadingState");
        powderAmount = (short)Math.min(tag.getShort("powderAmount"), MAX_POWDER);
        isShotLoaded = tag.getBoolean("isShotLoaded");
        shotType = tag.getShort("shotType");
        litTicks = tag.getShort("litTicks");
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
