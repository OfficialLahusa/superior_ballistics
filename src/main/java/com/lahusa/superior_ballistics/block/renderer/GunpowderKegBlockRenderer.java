package com.lahusa.superior_ballistics.block.renderer;

import com.lahusa.superior_ballistics.block.entity.CannonBlockEntity;
import com.lahusa.superior_ballistics.block.entity.GunpowderKegBlockEntity;
import com.lahusa.superior_ballistics.block.model.GunpowderKegBlockModel;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class GunpowderKegBlockRenderer extends GeoBlockRenderer<GunpowderKegBlockEntity> {
    public GunpowderKegBlockRenderer() {
        super(new GunpowderKegBlockModel());
    }

    @Override
    public void renderEarly(
            GunpowderKegBlockEntity animatable, MatrixStack stackIn, float partialTicks,
            VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderEarly(animatable, stackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        AnimationProcessor animProc = getGeoModelProvider().getAnimationProcessor();
        IBone powder = animProc.getBone("Powder_Level");
        IBone lid = animProc.getBone("Lid");
        powder.setPositionY(-13+13*((float)animatable.getPowderAmount() / GunpowderKegBlockEntity.MAX_POWDER_AMOUNT));

        // Toggle Lid and Powder Visibility
        lid.setScaleX(animatable.getClosed() ? 1.f : 0.f);
        lid.setScaleY(animatable.getClosed() ? 1.f : 0.f);
        lid.setScaleZ(animatable.getClosed() ? 1.f : 0.f);
        powder.setScaleX(animatable.getClosed() ? 0.f : 1.f);
        powder.setScaleY(animatable.getClosed() ? 0.f : 1.f);
        powder.setScaleZ(animatable.getClosed() ? 0.f : 1.f);
    }
}
