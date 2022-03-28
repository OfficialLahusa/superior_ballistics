package com.lahusa.superior_ballistics;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class CannonBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final IntProperty ANGLE = IntProperty.of("angle", 0, 3);
    public static final DirectionProperty FACING;

    static {
        FACING = Properties.HORIZONTAL_FACING;
    }

    public CannonBlock(Settings settings) {
        super(settings);
        setDefaultState(
                this.getStateManager().getDefaultState()
                        .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                        .with(ANGLE, 1)
        );
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CannonBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        ItemStack heldStack = player.getInventory().getMainHandStack();
        CannonBlockEntity blockEntity = (CannonBlockEntity) world.getBlockEntity(pos);

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
        // Powder loading stage
        else if(blockEntity.getLoadingStage() == CannonBlockEntity.POWDER_LOADING_STAGE) {
            if(heldStack.isOf(Items.GUNPOWDER) && blockEntity.canLoadPowder()) {
                // Play sound and load powder
                player.playSound(SoundEvents.BLOCK_SAND_BREAK, 1.f, 1.4f);
                blockEntity.addPowder();

                // Remove gunpowder from hand
                if(!player.isCreative()) heldStack.decrement(1);
            }
            else if(heldStack.isOf(SuperiorBallisticsMod.PISTON_LOADER_ITEM)) {
                blockEntity.push();
                world.playSound(player, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if(!player.isCreative() && !world.isClient) heldStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
            }
        }
        // Shot loading stage
        else if(blockEntity.getLoadingStage() == CannonBlockEntity.SHOT_LOADING_STAGE) {
            if(heldStack.isOf(Items.IRON_BLOCK) && !blockEntity.isShotLoaded()) {
                // Play sound and load powder
                player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.f, 1.4f);
                blockEntity.loadShot((short) 1);

                // Remove shot from hand
                if(!player.isCreative()) heldStack.decrement(1);
            }
            else if(heldStack.isOf(SuperiorBallisticsMod.PISTON_LOADER_ITEM)) {
                blockEntity.push();
                world.playSound(player, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if(!player.isCreative() && !world.isClient) heldStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
            }
        }
        // Ready stage
        else if(blockEntity.getLoadingStage() == CannonBlockEntity.READY_STAGE) {
            if(heldStack.isOf(Items.FLINT_AND_STEEL)) {
                blockEntity.light();
                world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if(!player.isCreative() && !world.isClient) heldStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
            }
        }
        // Cleanup stage
        else if(blockEntity.getLoadingStage() == CannonBlockEntity.CLEANUP_STAGE) {
            if(heldStack.isOf(SuperiorBallisticsMod.WET_SPONGE_ON_A_STICK_ITEM)) {
                //Replace held item
                if(!player.isCreative()) {
                    ItemStack newItemStack = new ItemStack(SuperiorBallisticsMod.SPONGE_ON_A_STICK_ITEM);
                    newItemStack.setDamage(heldStack.getDamage());
                    if(!player.isCreative() && !world.isClient) newItemStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                    player.setStackInHand(Hand.MAIN_HAND, newItemStack);
                }
                blockEntity.clean();
                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
        }

        return ActionResult.SUCCESS;
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if(!world.isClient) {
            CannonBlockEntity blockEntity = ((CannonBlockEntity)world.getBlockEntity(pos));

            // Get Redstone Power (direct or from behind)
            Direction facing = state.get(FACING);
            boolean isPowered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.add(-facing.getOffsetX(), 0, -facing.getOffsetZ()));

            if (blockEntity != null && isPowered && blockEntity.getLoadingStage() == CannonBlockEntity.READY_STAGE) {
                blockEntity.light();
                world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                blockEntity.sync();
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, SuperiorBallisticsMod.CANNON_BLOCK_ENTITY, CannonBlockEntity::tick);
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
