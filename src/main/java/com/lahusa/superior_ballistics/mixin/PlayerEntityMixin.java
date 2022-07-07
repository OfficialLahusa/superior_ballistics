package com.lahusa.superior_ballistics.mixin;

import com.lahusa.superior_ballistics.block.CannonBlock;
import com.lahusa.superior_ballistics.block.entity.CannonBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
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
            if(block instanceof CannonBlock) {
                CannonBlockEntity blockEntity = (CannonBlockEntity)client.world.getBlockEntity(pos);

                if(blockEntity != null) {
                    // Build status text
                    MutableText text = new LiteralText("[").formatted(Formatting.GRAY)
                            .append(new TranslatableText("superior_ballistics.cannon.loading_stage").formatted(Formatting.BLACK));

                    switch (blockEntity.getLoadingStage()) {
                        case CannonBlockEntity.POWDER_LOADING_STAGE -> {
                            text.append(new TranslatableText("superior_ballistics.cannon.insert_powder").formatted(Formatting.DARK_GREEN));
                            text.append(new LiteralText(" (" + blockEntity.getPowderAmount() + "/" + CannonBlockEntity.MAX_POWDER + ")")
                                    .formatted(blockEntity.getPowderAmount() > CannonBlockEntity.MAX_POWDER ? Formatting.RED : Formatting.GOLD)
                            );
                        }
                        case CannonBlockEntity.SHOT_LOADING_STAGE -> {
                            text.append(new TranslatableText("superior_ballistics.cannon.insert_shot").formatted(Formatting.DARK_GREEN));
                            text.append(new LiteralText(" (").append(blockEntity.getShotName()).append(new LiteralText(")")).formatted(Formatting.GOLD));
                        }
                        case CannonBlockEntity.READY_STAGE -> {
                            // Show different text when cannon is creative
                            if(!blockEntity.isCreative()) {
                                text.append(new TranslatableText("superior_ballistics.cannon.ready_to_light").formatted(Formatting.DARK_GREEN));
                            }
                            else {
                                text.append(new TranslatableText("superior_ballistics.cannon.ready_to_light_creative").formatted(Formatting.LIGHT_PURPLE));
                            }

                            text.append(new LiteralText(" (").append(new TranslatableText("item.minecraft.flint_and_steel"))
                                    .append(new LiteralText(" / ")).append(new TranslatableText("itemGroup.redstone")).append(new LiteralText(")"))
                                    .formatted(Formatting.GOLD));
                        }
                        case CannonBlockEntity.LIT_STAGE -> text.append(new TranslatableText("superior_ballistics.cannon.firing").formatted(Formatting.RED));
                        case CannonBlockEntity.CLEANUP_STAGE -> text.append(new TranslatableText("superior_ballistics.cannon.cleanup").formatted(Formatting.DARK_GREEN));
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

