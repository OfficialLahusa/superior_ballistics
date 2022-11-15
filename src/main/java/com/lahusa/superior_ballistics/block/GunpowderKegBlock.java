package com.lahusa.superior_ballistics.block;

import com.lahusa.superior_ballistics.block.entity.GunpowderKegBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GunpowderKegBlock extends BlockWithEntity {
    public GunpowderKegBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GunpowderKegBlockEntity(pos, state);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.89f, 0.9375f);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        GunpowderKegBlockEntity blockEntity = (GunpowderKegBlockEntity)world.getBlockEntity(pos);
        ItemStack heldStack = player.getMainHandStack();
        PlayerInventory inventory = player.getInventory();

        if(blockEntity == null) return ActionResult.FAIL;

        short powderAmount = blockEntity.getPowderAmount();
        boolean closed = blockEntity.getClosed();

        // Open/Close barrel
        if(heldStack.isOf(Items.STICK)) {
            // Toggle lid
            blockEntity.setClosed(!closed);

            // Play sound
            world.playSound(null, pos, closed ? SoundEvents.BLOCK_BARREL_OPEN : SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS, 1.f, 1.0f);

            // Return early
            return ActionResult.SUCCESS;
        }

        if(!closed) {
            // Extract powder
            if(!player.isSneaking() && powderAmount > 0) {
                // Reduce powder stored
                blockEntity.setPowderAmount(--powderAmount);

                // Play sound
                world.playSound(null, pos, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1.f, 1.4f);

                // Extract powder to existing stack
                if(heldStack.isOf(Items.GUNPOWDER) && heldStack.getCount() > 0 && heldStack.getCount() < 64) {
                    heldStack.increment(1);

                }
                // Extract powder to new stack
                else {
                    if(heldStack.getCount() >= 64) {
                        inventory.insertStack(new ItemStack(Items.GUNPOWDER, 1));
                    }
                    else if(heldStack.isEmpty()) {
                        inventory.insertStack(inventory.selectedSlot, new ItemStack(Items.GUNPOWDER, 1));
                    }
                }


            }
            // Insert powder
            else if(player.isSneaking() && heldStack.isOf(Items.GUNPOWDER) && heldStack.getCount() > 0 && powderAmount < GunpowderKegBlockEntity.MAX_POWDER_AMOUNT) {
                // Increase powder stored
                blockEntity.setPowderAmount(++powderAmount);

                // Play sound
                world.playSound(null, pos, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1.f, 1.4f);

                heldStack.decrement(1);
            }
        }

        return ActionResult.SUCCESS;
    }
}
