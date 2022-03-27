package com.lahusa.superior_ballistics;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class SpruceCannonBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

    private short loadingState = 0;
    private short powderAmount = 0;
    private boolean isShotLoaded = false;
    private short shotType = 0;
    public static final short MAX_POWDER = 5;

    public SpruceCannonBlockEntity(BlockPos pos, BlockState state) {
        super(SuperiorBallisticsMod.SPRUCE_CANNON_BLOCK_ENTITY, pos, state);
    }

    public void addPowder() {
        if(!canLoadPowder()) return;

        if(loadingState == 0) {
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

    public void reset() {
        loadingState = 0;
        powderAmount = 0;
        isShotLoaded = false;
        shotType = 0;
        markDirty();
    }

    public boolean canLoadPowder() {
        return powderAmount < MAX_POWDER;
    }

    public short getLoadingState() {
        return loadingState;
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

        tag.putShort("loadingState", loadingState);
        tag.putShort("powderAmount", powderAmount);
        tag.putBoolean("isShotLoaded", isShotLoaded);
        tag.putShort("shotType", shotType);

        super.writeNbt(tag);

        return tag;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

        loadingState = tag.getShort("loadingState");
        powderAmount = (short)Math.min(tag.getShort("powderAmount"), MAX_POWDER);
        isShotLoaded = tag.getBoolean("isShotLoaded");
        shotType = tag.getShort("shotType");
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
