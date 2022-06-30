package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.block.AnimatedCannonBlock;
import com.lahusa.superior_ballistics.block.entity.AnimatedCannonBlockEntity;
import com.lahusa.superior_ballistics.block.model.AnimatedCannonBlockModel;
import com.lahusa.superior_ballistics.item.AnimatedCannonBlockItem;
import com.lahusa.superior_ballistics.item.model.AnimatedCannonBlockItemModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AnimatedCannonBlockItemRenderer extends GeoItemRenderer<AnimatedCannonBlockItem> {

    protected final Identifier ANVIL_TEXTURE = new Identifier("minecraft", "textures/block/anvil.png");
    protected final Identifier BLACKSTONE_TOP_TEXTURE = new Identifier("minecraft", "textures/block/blackstone_top.png");

    private AnimatedCannonBlockItem currentEntityBeingRendered;
    private float currentPartialTicks;
    private VertexConsumerProvider renderTypeBuffer;

    public AnimatedCannonBlockItemRenderer() {
        super(new AnimatedCannonBlockItemModel());
    }

    @Override
    public void renderEarly(
            AnimatedCannonBlockItem animatable, MatrixStack stackIn, float partialTicks,
            VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderEarly(animatable, stackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        this.renderTypeBuffer = renderTypeBuffer;
        
        // Rotate cannon barrel
        short angle = 0;
        float rotX = (angle * 22.5f) / 180.0f * (float)Math.PI;
        getGeoModelProvider().getAnimationProcessor().getBone("Cannon").setRotationX(rotX);
    }

    @Override
    public void renderLate(AnimatedCannonBlockItem animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer,
                           VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue,
                           float partialTicks) {
        super.renderLate(animatable, stackIn, ticks, renderTypeBuffer, bufferIn, packedLightIn, packedOverlayIn, red,
                green, blue, partialTicks);
        this.currentEntityBeingRendered = animatable;
        this.currentPartialTicks = partialTicks;
    }

    private Identifier getBlockTexture(Identifier block) {
        return new Identifier(block.getNamespace(), "textures/block/" + block.getPath() + ".png");
    }

    protected Identifier getTextureForBone(String boneName, AnimatedCannonBlockItem currentItem) {
        AnimatedCannonBlock animatedCannonBlock = (AnimatedCannonBlock)currentItem.getBlock();
        return switch (boneName) {
            case "Cannon", "Edges_Right", "Edges_Left" -> ANVIL_TEXTURE;
            case "Axis_BR", "Axis_BL", "Axis_FR", "Axis_FL" -> BLACKSTONE_TOP_TEXTURE;
            case "Log_BR", "Log_BL", "Log_FR", "Log_FL" -> getBlockTexture(animatedCannonBlock.getLogVariant());
            default -> getBlockTexture(animatedCannonBlock.getPlankVariant());
        };
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn,
                                  int packedOverlayIn, float red, float green, float blue, float alpha) {
        Identifier tfb = this.getTextureForBone(bone.getName(), this.currentEntityBeingRendered);
        boolean customTextureMarker = tfb != null;
        Identifier currentTexture = getTextureLocation(this.currentEntityBeingRendered);
        if (customTextureMarker) {
            currentTexture = tfb;
            RenderLayer rt = this.getRenderType(this.currentEntityBeingRendered, this.currentPartialTicks,
                    stack, this.renderTypeBuffer, bufferIn, packedLightIn, currentTexture);
            bufferIn = this.renderTypeBuffer.getBuffer(rt);

        }
        else {
            RenderLayer rt = this.getRenderType(this.currentEntityBeingRendered, this.currentPartialTicks,
                    stack, this.renderTypeBuffer, bufferIn, packedLightIn, currentTexture);
            bufferIn = this.renderTypeBuffer.getBuffer(rt);
        }

        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
