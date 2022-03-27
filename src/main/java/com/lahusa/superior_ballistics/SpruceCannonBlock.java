package com.lahusa.superior_ballistics;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SpruceCannonBlock extends HorizontalFacingBlock implements BlockEntityProvider {

    public static final IntProperty ANGLE = IntProperty.of("angle", 0, 3);

    public SpruceCannonBlock(Settings settings) {
        super(settings);
        setDefaultState(
                this.getStateManager().getDefaultState()
                        .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                        .with(ANGLE, 1)
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpruceCannonBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        ItemStack heldStack = player.getInventory().getMainHandStack();

        if(heldStack.isEmpty()) {
            // Calculate and set new angle
            int oldAngle = state.get(ANGLE).intValue();
            int delta = player.isSneaking() ? -1 : 1;
            int newAngle = Math.max(0, Math.min(3, oldAngle + delta));

            // Check if angle changed
            if(newAngle != oldAngle) {
                // Play lever click sound
                player.playSound(SoundEvents.BLOCK_LEVER_CLICK, 1.f, 0.6f);

                // Set new block state
                world.setBlockState(pos, state.with(ANGLE, newAngle));
            }
        }
        else {
            player.playSound(SoundEvents.BLOCK_SAND_HIT, 1.f, 1.0f);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HORIZONTAL_FACING);
        stateManager.add(ANGLE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0625f, 0.0625f, 0.0625f, 0.9375f, 0.5f, 0.9375f);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing());
    }
}
