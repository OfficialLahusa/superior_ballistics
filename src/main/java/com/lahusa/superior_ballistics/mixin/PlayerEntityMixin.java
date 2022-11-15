package com.lahusa.superior_ballistics.mixin;

import com.lahusa.superior_ballistics.block.CannonBlock;
import com.lahusa.superior_ballistics.block.GunpowderKegBlock;
import com.lahusa.superior_ballistics.block.entity.CannonBlockEntity;
import com.lahusa.superior_ballistics.block.entity.GunpowderKegBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow protected abstract void attackLivingEntity(LivingEntity target);

    private boolean isShowingStatusText = false;

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void tick(CallbackInfo info) {
        // Check for cannon using raycast
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        // If looking at block
        if(client.world != null && hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos pos = blockHit.getBlockPos();
            Block block = client.world.getBlockState(pos).getBlock();


            // If looking at cannon
            if(block instanceof CannonBlock) {
                CannonBlockEntity blockEntity = (CannonBlockEntity)client.world.getBlockEntity(pos);

                if(blockEntity != null) {
                    client.inGameHud.setOverlayMessage(blockEntity.getStatusText(), false);
                    isShowingStatusText = true;
                }
            }
            else if(block instanceof GunpowderKegBlock) {
                GunpowderKegBlockEntity blockEntity = (GunpowderKegBlockEntity)client.world.getBlockEntity(pos);

                if(blockEntity != null) {
                    client.inGameHud.setOverlayMessage(blockEntity.getStatusText(), false);
                    isShowingStatusText = true;
                }
            }
            else if(isShowingStatusText) {
                // Reset overlay message
                client.inGameHud.setOverlayMessage(new LiteralText(""), false);
                isShowingStatusText = false;
            }
        }
        else if(isShowingStatusText){
            // Reset overlay message
            client.inGameHud.setOverlayMessage(new LiteralText(""), false);
            isShowingStatusText = false;
        }
    }

    @Inject(at = @At("HEAD"), method = "shouldCancelInteraction()Z", cancellable = true)
    private void overwriteSneakInteractionCancelling(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        ItemStack heldStack = ((PlayerEntity)(Object)this).getMainHandStack();
        boolean allowedItemHeld = heldStack.isOf(Items.GUNPOWDER);
        if(allowedItemHeld) callbackInfoReturnable.setReturnValue(false);
    }
}

