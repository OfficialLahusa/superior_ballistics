package com.lahusa.superior_ballistics.mixin;

import com.lahusa.superior_ballistics.block.AnimatedCannonBlock;
import com.lahusa.superior_ballistics.block.entity.AnimatedCannonBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
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

        // If looking at block
        if(client.world != null && hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos pos = blockHit.getBlockPos();
            Block block = client.world.getBlockState(pos).getBlock();


            // If looking at cannon
            if(block instanceof AnimatedCannonBlock) {
                AnimatedCannonBlockEntity blockEntity = (AnimatedCannonBlockEntity)client.world.getBlockEntity(pos);

                if(blockEntity != null) {
                    // Build overlay message
                    MutableText text = new LiteralText("[").formatted(Formatting.GRAY).append(new LiteralText("Cannon Loading Stage: ").formatted(Formatting.BLACK));

                    switch (blockEntity.getLoadingStage()) {
                        case AnimatedCannonBlockEntity.POWDER_LOADING_STAGE -> {
                            text.append(new LiteralText("Insert Powder ").formatted(Formatting.DARK_GREEN));
                            text.append(new LiteralText("(" + blockEntity.getPowderAmount() + "/" + AnimatedCannonBlockEntity.MAX_POWDER + ")")
                                    .formatted(blockEntity.getPowderAmount() > AnimatedCannonBlockEntity.MAX_POWDER ? Formatting.RED : Formatting.GOLD)
                            );
                        }
                        case AnimatedCannonBlockEntity.SHOT_LOADING_STAGE -> {
                            text.append(new LiteralText("Insert Shot ").formatted(Formatting.DARK_GREEN));
                            text.append(new LiteralText("(" + blockEntity.getShotName() + ")").formatted(Formatting.GOLD));
                        }
                        case AnimatedCannonBlockEntity.READY_STAGE -> {
                            text.append(new LiteralText("Ready to light ").formatted(Formatting.DARK_GREEN));
                            text.append(new LiteralText("(Flint and Steel / Redstone)").formatted(Formatting.GOLD));
                        }
                        case AnimatedCannonBlockEntity.LIT_STAGE -> text.append(new LiteralText("FIRING").formatted(Formatting.RED));
                        case AnimatedCannonBlockEntity.CLEANUP_STAGE -> text.append(new LiteralText("Cleanup").formatted(Formatting.DARK_GREEN));
                        default -> text.append(new LiteralText("INVALID").formatted(Formatting.RED));
                    }
                    text.append(new LiteralText("]").formatted(Formatting.GRAY));

                    client.inGameHud.setOverlayMessage(text, false);
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
}

