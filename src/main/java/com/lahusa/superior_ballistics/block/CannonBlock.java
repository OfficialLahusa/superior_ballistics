package com.lahusa.superior_ballistics.block;

import com.lahusa.superior_ballistics.block.entity.CannonBlockEntity;
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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class CannonBlock extends BlockWithEntity {
    public static final DirectionProperty FACING;

    static {
        FACING = Properties.HORIZONTAL_FACING;
    }

    protected final Identifier plankVariant;
    protected final Identifier logVariant;

    public CannonBlock(Identifier plankVariant, Identifier logVariant, Settings settings) {
        super(settings);
        setDefaultState(
                this.getStateManager().getDefaultState()
                        .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
        );
        this.plankVariant = plankVariant;
        this.logVariant = logVariant;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        ItemStack heldStack = player.getInventory().getMainHandStack();
        CannonBlockEntity blockEntity = (CannonBlockEntity) world.getBlockEntity(pos);

        // Adjust angle with empty hand
        if(heldStack.isEmpty()) {
            // Calculate and set new angle
            short oldAngle = blockEntity.getAngle();
            short delta = (short)(player.isSneaking() ? -1 : 1);
            short newAngle = (short)Math.max(0, Math.min(CannonBlockEntity.MAX_ANGLE, oldAngle + delta));

            // Check if angle changed
            if(newAngle != oldAngle) {
                // Play lever click sound
                player.playSound(SoundEvents.BLOCK_LEVER_CLICK, 1.f, 0.6f);

                // Set new angle
                blockEntity.setAngle(newAngle);
            }
        }
        else if(blockEntity != null) {
            // Loading stages:
            switch(blockEntity.getLoadingStage()) {
                case CannonBlockEntity.POWDER_LOADING_STAGE -> {
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
                case CannonBlockEntity.SHOT_LOADING_STAGE -> {
                    // If cannon isn't loaded yet
                    if(!blockEntity.isShotLoaded()) {
                        if(heldStack.isOf(SuperiorBallisticsMod.IRON_CANNONBALL)) {
                            // Load shot
                            if(!world.isClient) blockEntity.loadShot(CannonBlockEntity.IRON_CANNONBALL, player);

                            // Play sound
                            player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.75f, 1.4f);
                        }
                        else if(heldStack.isOf(SuperiorBallisticsMod.IRON_GRAPESHOT)) {
                            // Load shot
                            if(!world.isClient) blockEntity.loadShot(CannonBlockEntity.IRON_GRAPESHOT, player);

                            // Play sound
                            player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.75f, 1.4f);
                        }
                        else if(heldStack.isOf(Items.ENDER_PEARL)) {
                            // Load shot
                            if(!world.isClient) blockEntity.loadShot(CannonBlockEntity.ENDER_PEARL_SHOT, player);

                            // Play sound
                            player.playSound(SoundEvents.ENTITY_ENDER_EYE_LAUNCH, 0.75f, 1.4f);
                        }
                        else if(heldStack.isOf(Items.PAPER)) {
                            // Load shot
                            if(!world.isClient) blockEntity.loadShot(CannonBlockEntity.BLANK_SHOT, player);

                            // Play sound
                            player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, 0.75f, 1.0f);
                        }

                        // Remove shot from hand
                        if(!player.isCreative()) heldStack.decrement(1);
                    }
                    // If cannon is loaded and piston loader is held
                    else if(heldStack.isOf(SuperiorBallisticsMod.PISTON_LOADER_ITEM)) {
                        // Push
                        if(!world.isClient) blockEntity.push(player);

                        // Play sound
                        world.playSound(player, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.NEUTRAL, 1.0f, 1.0f);

                        // Damage piston
                        if(!player.isCreative() && !world.isClient) heldStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                    }
                }
                case CannonBlockEntity.READY_STAGE -> {
                    if(heldStack.isOf(Items.FLINT_AND_STEEL)) {
                        // Light cannon
                        if(!world.isClient) blockEntity.light(player);

                        // Play sound
                        world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f);

                        // Damage flint and steel
                        if(!player.isCreative() && !world.isClient) heldStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                    }
                    else if(heldStack.isOf(SuperiorBallisticsMod.CREATIVE_CANNON_MODULE) && !blockEntity.isCreative()) {
                        // Make cannon creative
                        if(!world.isClient) blockEntity.setCreative(true);

                        // Play sound
                        world.playSound(player, pos, SoundEvents.BLOCK_NOTE_BLOCK_PLING, SoundCategory.NEUTRAL, 1.0f, 1.0f);

                        // Show message
                        if(world.isClient) player.sendMessage(new TranslatableText("item.superior_ballistics.creative_cannon_module.applied_message").formatted(Formatting.LIGHT_PURPLE), false);

                        // Remove one from hand
                        if(!player.isCreative()) heldStack.decrement(1);
                    }
                }
                case CannonBlockEntity.CLEANUP_STAGE -> {
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
            CannonBlockEntity blockEntity = ((CannonBlockEntity)world.getBlockEntity(pos));

            // Get Redstone Power (direct or from behind)
            Direction facing = state.get(FACING);
            boolean isPowered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.add(-facing.getOffsetX(), 0, -facing.getOffsetZ()));

            if (blockEntity != null && isPowered && blockEntity.getLoadingStage() == CannonBlockEntity.READY_STAGE) {
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
        return new CannonBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, SuperiorBallisticsMod.CANNON_BLOCK_ENTITY, CannonBlockEntity::tick);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HORIZONTAL_FACING);
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
