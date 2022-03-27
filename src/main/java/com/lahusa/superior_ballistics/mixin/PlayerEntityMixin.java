package com.lahusa.superior_ballistics.mixin;

import com.lahusa.superior_ballistics.SpruceCannonBlock;
import com.lahusa.superior_ballistics.SpruceCannonBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
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
            BlockPos pos = blockHit.getBlockPos();
            Block block = client.world.getBlockState(pos).getBlock();
            if(block instanceof SpruceCannonBlock) {
                SpruceCannonBlockEntity blockEntity = (SpruceCannonBlockEntity) client.world.getBlockEntity(pos);

                client.inGameHud.setOverlayMessage(new LiteralText("[Cannon Loading Stage: Insert Powder (" + blockEntity.getPowderAmount() + "/5)]"), false);

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

