package com.lahusa.superior_ballistics.mixin;

import com.lahusa.superior_ballistics.SpruceCannonBlock;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    private boolean isShowingStatusText = false;

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void tick(CallbackInfo info) {
        // Check for cannon using raycast
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;
        if(hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            Block block = client.world.getBlockState(blockHit.getBlockPos()).getBlock();
            if(block instanceof SpruceCannonBlock) {
                //((PlayerEntity)(Object)this).sendMessage(new LiteralText("[Loading Stage: Insert Powder]"), true);
                client.inGameHud.setTitleTicks(0, 0, 0);
                client.inGameHud.setOverlayMessage(new LiteralText("[Loading Stage: Insert Powder]"), false);
                isShowingStatusText = true;
            }
            else if(isShowingStatusText) {
                client.inGameHud.setOverlayMessage(new LiteralText(""), false);
                isShowingStatusText = false;
            }
        }
        else if(isShowingStatusText){
            client.inGameHud.setOverlayMessage(new LiteralText(""), false);
            isShowingStatusText = false;
        }
    }
}

