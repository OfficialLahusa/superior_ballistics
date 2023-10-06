package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.block.CannonBlock;
import com.lahusa.superior_ballistics.item.CannonBlockItem;
import com.lahusa.superior_ballistics.item.model.CannonBlockItemModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class CannonBlockItemRenderer extends GeoItemRenderer<CannonBlockItem> {

    protected final Identifier ANVIL_TEXTURE = new Identifier("minecraft", "textures/block/anvil.png");
    protected final Identifier BLACKSTONE_TOP_TEXTURE = new Identifier("minecraft", "textures/block/blackstone_top.png");

    private float currentPartialTicks;
    private VertexConsumerProvider bufferSource;

    public CannonBlockItemRenderer() {
        super(new CannonBlockItemModel());
    }

    @Override
    public void preRender(MatrixStack poseStack, CannonBlockItem animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        this.bufferSource = bufferSource;

        // Rotate cannon barrel
        short angle = 0;
        float rotX = (angle * 22.5f) / 180.0f * (float)Math.PI;
        getGeoModel().getAnimationProcessor().getBone("Cannon").setRotX(rotX);
    }

    @Override
    public void postRender(MatrixStack poseStack, CannonBlockItem animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        this.currentPartialTicks = partialTick;
    }

    private Identifier getBlockTexture(Identifier block) {
        return new Identifier(block.getNamespace(), "textures/block/" + block.getPath() + ".png");
    }

    protected Identifier getTextureForBone(String boneName, CannonBlockItem currentItem) {
        CannonBlock cannonBlock = (CannonBlock)currentItem.getBlock();
        return switch (boneName) {
            case "Cannon", "Edges_Right", "Edges_Left" -> ANVIL_TEXTURE;
            case "Axis_BR", "Axis_BL", "Axis_FR", "Axis_FL" -> BLACKSTONE_TOP_TEXTURE;
            case "Log_BR", "Log_BL", "Log_FR", "Log_FL" -> getBlockTexture(cannonBlock.getLogVariant());
            default -> getBlockTexture(cannonBlock.getPlankVariant());
        };
    }

    @Override
    public void renderRecursively(MatrixStack poseStack, CannonBlockItem animatable, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(animatable == null) return;

        Identifier tfb = this.getTextureForBone(bone.getName(), animatable);
        boolean customTextureMarker = tfb != null;
        Identifier currentTexture = getTextureLocation(animatable);
        if (customTextureMarker) {
            currentTexture = tfb;
            RenderLayer rt = this.getRenderType(animatable, currentTexture, this.bufferSource, this.currentPartialTicks);
            buffer = this.bufferSource.getBuffer(rt);

        }
        else {
            RenderLayer rt = this.getRenderType(animatable, currentTexture, this.bufferSource, this.currentPartialTicks);
            buffer = this.bufferSource.getBuffer(rt);
        }

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
