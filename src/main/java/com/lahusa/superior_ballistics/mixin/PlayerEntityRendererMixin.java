package com.lahusa.superior_ballistics.mixin;

import com.lahusa.superior_ballistics.item.FlintlockMusketItem;
import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(at= @At("HEAD"), method = "getArmPose", cancellable = true)
    private static void handleArmPoses(AbstractClientPlayerEntity abstractClientPlayerEntity, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack stackInHand = abstractClientPlayerEntity.getStackInHand(hand);

        if((stackInHand.getItem() == SuperiorBallisticsMod.FLINTLOCK_MUSKET_ITEM || stackInHand.getItem() == SuperiorBallisticsMod.FLINTLOCK_BLUNDERBUSS_ITEM) && FlintlockMusketItem.isCharged(stackInHand)) {
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
