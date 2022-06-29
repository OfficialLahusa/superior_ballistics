package com.lahusa.superior_ballistics.block.renderer;

import com.lahusa.superior_ballistics.block.AnimatedCannonBlock;
import com.lahusa.superior_ballistics.block.entity.AnimatedCannonBlockEntity;
import com.lahusa.superior_ballistics.block.model.AnimatedCannonBlockModel;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class AnimatedCannonBlockRenderer extends GeoBlockRenderer<AnimatedCannonBlockEntity> {

    public AnimatedCannonBlockRenderer() {
        super(new AnimatedCannonBlockModel());
    }

    @Override
    public void renderEarly(
            AnimatedCannonBlockEntity animatable, MatrixStack stackIn, float partialTicks,
            VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderEarly(animatable, stackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        short angle = animatable.getCachedState().get(AnimatedCannonBlock.ANGLE).shortValue();
        float rotX = (angle * 22.5f) / 180.0f * (float)Math.PI;
        getGeoModelProvider().getAnimationProcessor().getBone("Cannon").setRotationX(rotX);
    }
}
