package com.lahusa.superior_ballistics.mixin;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    private ShaderEffect postShader = null;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;updateWorldIcon()V"))
    public void render(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if(client.getCameraEntity() instanceof PlayerEntity playerEntity && playerEntity.getMainHandStack().isOf(SuperiorBallisticsMod.WET_SPONGE_ON_A_STICK_ITEM)
                && playerEntity.getMainHandStack().getName().asString().equals("ShaderTest") && getPostShader() != null) {
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.enableTexture();
            RenderSystem.resetTextureMatrix();
            getPostShader().render(client.getTickDelta());
        }
    }

    private ShaderEffect getPostShader() {
        MinecraftClient client = MinecraftClient.getInstance();

        if(postShader == null) {
            try {
                postShader = new ShaderEffect(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), new Identifier("shaders/post/sobel.json"));
                postShader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            } catch (IOException e) {
                postShader = null;
            }
        }

        return postShader;
    }
}
