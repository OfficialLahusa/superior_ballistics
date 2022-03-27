package com.lahusa.superior_ballistics;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SpruceCannonBlockEntity extends BlockEntity {
    public SpruceCannonBlockEntity(BlockPos pos, BlockState state) {
        super(SuperiorBallisticsMod.SPRUCE_CANNON_BLOCK_ENTITY, pos, state);
    }
}
