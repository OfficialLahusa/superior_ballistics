package com.lahusa.superior_ballistics.mixin;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {
    // Initialized with dummy value
    @Shadow @Final
    Map<Item, CauldronBehavior> WATER_CAULDRON_BEHAVIOR = null;

    @SuppressWarnings("ConstantConditions")
    @Inject(at = @At("TAIL"), method = "registerBehavior()V")
    private static void registerSpongeOnAStickBehavior(CallbackInfo ci)
    {
        WATER_CAULDRON_BEHAVIOR.put(SuperiorBallisticsMod.SPONGE_ON_A_STICK_ITEM, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                // Wet sponge and damage it
                Item item = stack.getItem();
                ItemStack newItemStack = new ItemStack(SuperiorBallisticsMod.WET_SPONGE_ON_A_STICK_ITEM);
                newItemStack.setDamage(stack.getDamage());
                player.setStackInHand(hand, newItemStack);
                if(!player.isCreative()) newItemStack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));

                // Increment stats
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));

                // Decrease fluid level
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);

                // Play sound and play event
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }

            return ActionResult.success(world.isClient);
        });
    }
}