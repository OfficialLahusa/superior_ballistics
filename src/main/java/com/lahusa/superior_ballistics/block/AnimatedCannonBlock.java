package com.lahusa.superior_ballistics.block;

import com.lahusa.superior_ballistics.block.entity.AnimatedCannonBlockEntity;
import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
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
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class AnimatedCannonBlock extends BlockWithEntity {

    public static final IntProperty ANGLE = IntProperty.of("angle", 0, 3);
    public static final DirectionProperty FACING;

    static {
        FACING = Properties.HORIZONTAL_FACING;
    }

    protected final Identifier plankVariant;
    protected final Identifier logVariant;

    public AnimatedCannonBlock(Identifier plankVariant, Identifier logVariant, Settings settings) {
        super(settings);
        setDefaultState(
                this.getStateManager().getDefaultState()
                        .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                        .with(ANGLE, 1)
        );
        this.plankVariant = plankVariant;
        this.logVariant = logVariant;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        ItemStack heldStack = player.getInventory().getMainHandStack();
        AnimatedCannonBlockEntity blockEntity = (AnimatedCannonBlockEntity) world.getBlockEntity(pos);

        if(heldStack.isEmpty()) {
            // Calculate and set new angle
            int oldAngle = state.get(ANGLE);
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
        else if(blockEntity != null) {
            // Loading stages:
            switch(blockEntity.getLoadingStage()) {
                case AnimatedCannonBlockEntity.POWDER_LOADING_STAGE -> {
                    if(heldStack.isOf(Items.GUNPOWDER) && blockEntity.canLoadPowder()) {
                        // Load powder
                        if(!world.isClient) blockEntity.addPowder(player);

                        // Play sound
                        player.playSound(SoundEvents.BLOCK_SAND_BREAK, 1.f, 1.4f);

                        // Remove gunpowder from hand
                        if(!player.isCreative()) heldStack.decrement(1);
                    }
                    else if(heldStack.isOf(SuperiorBallisticsMod.PISTON_LOADER_ITEM)) {
                        // Push powder
                        if(!world.isClient) blockEntity.push(player);

                        // Play sound
                        world.playSound(player, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.NEUTRAL, 1.0f, 1.0f);

                        // Damage piston
                        if(!player.isCreative() && !world.isClient) heldStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                    }
                }
                case AnimatedCannonBlockEntity.SHOT_LOADING_STAGE -> {
                    if(heldStack.isOf(SuperiorBallisticsMod.IRON_CANNONBALL) && !blockEntity.isShotLoaded()) {
                        // Load shot
                        if(!world.isClient) blockEntity.loadShot((short) 1, player);

                        // Play sound
                        player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.75f, 1.4f);

                        // Remove shot from hand
                        if(!player.isCreative()) heldStack.decrement(1);
                    }
                    else if(heldStack.isOf(SuperiorBallisticsMod.IRON_GRAPESHOT) && !blockEntity.isShotLoaded()) {
                        // Load shot
                        if(!world.isClient) blockEntity.loadShot((short) 2, player);

                        // Play sound
                        player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.75f, 1.4f);

                        // Remove shot from hand
                        if(!player.isCreative()) heldStack.decrement(1);
                    }
                    else if(heldStack.isOf(Items.PAPER) && !blockEntity.isShotLoaded()) {
                        // Load shot
                        if(!world.isClient) blockEntity.loadShot((short) 3, player);

                        // Play sound
                        player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, 0.75f, 1.0f);

                        // Remove shot from hand
                        if(!player.isCreative()) heldStack.decrement(1);
                    }
                    else if(heldStack.isOf(SuperiorBallisticsMod.PISTON_LOADER_ITEM)) {
                        // Push
                        if(!world.isClient) blockEntity.push(player);

                        // Play sound
                        world.playSound(player, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.NEUTRAL, 1.0f, 1.0f);

                        // Damage piston
                        if(!player.isCreative() && !world.isClient) heldStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                    }
                }
                case AnimatedCannonBlockEntity.READY_STAGE -> {
                    if(heldStack.isOf(Items.FLINT_AND_STEEL)) {
                        // Light cannon
                        if(!world.isClient) blockEntity.light(player);

                        // Play sound
                        world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f);

                        // Damage flint and steel
                        if(!player.isCreative() && !world.isClient) heldStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                    }
                }
                case AnimatedCannonBlockEntity.CLEANUP_STAGE -> {
                    if(heldStack.isOf(SuperiorBallisticsMod.WET_SPONGE_ON_A_STICK_ITEM)) {
                        // Clean cannon
                        if(!world.isClient) blockEntity.clean();

                        // Play sound
                        world.playSound(player, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);

                        // Damage and dry sponge
                        if(!player.isCreative()) {
                            ItemStack newItemStack = new ItemStack(SuperiorBallisticsMod.SPONGE_ON_A_STICK_ITEM);
                            newItemStack.setDamage(heldStack.getDamage());
                            if(!player.isCreative() && !world.isClient) newItemStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                            player.setStackInHand(Hand.MAIN_HAND, newItemStack);
                        }
                    }
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    // Fire cannon when powered by redstone
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if(!world.isClient) {
            AnimatedCannonBlockEntity blockEntity = ((AnimatedCannonBlockEntity)world.getBlockEntity(pos));

            // Get Redstone Power (direct or from behind)
            Direction facing = state.get(FACING);
            boolean isPowered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.add(-facing.getOffsetX(), 0, -facing.getOffsetZ()));

            if (blockEntity != null && isPowered && blockEntity.getLoadingStage() == AnimatedCannonBlockEntity.READY_STAGE) {
                // Light cannon
                blockEntity.light(null);

                // Play sound
                world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
        }
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AnimatedCannonBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, SuperiorBallisticsMod.ANIMATED_CANNON_BLOCK_ENTITY, AnimatedCannonBlockEntity::tick);
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
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing());
    }

    public Identifier getPlankVariant() {
        return plankVariant;
    }

    public Identifier getLogVariant() {
        return logVariant;
    }
}
