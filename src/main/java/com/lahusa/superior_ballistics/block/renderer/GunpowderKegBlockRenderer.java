package com.lahusa.superior_ballistics.block.renderer;

import com.lahusa.superior_ballistics.block.entity.GunpowderKegBlockEntity;
import com.lahusa.superior_ballistics.block.model.GunpowderKegBlockModel;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GunpowderKegBlockRenderer extends GeoBlockRenderer<GunpowderKegBlockEntity> {
    public GunpowderKegBlockRenderer() {
        super(new GunpowderKegBlockModel());
    }

    @Override
    public void preRender(MatrixStack poseStack, GunpowderKegBlockEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        AnimationProcessor<GunpowderKegBlockEntity> animProc = getGeoModel().getAnimationProcessor();
        CoreGeoBone powder = animProc.getBone("Powder_Level");
        CoreGeoBone lid = animProc.getBone("Lid");
        short powderAmount = animatable.getPowderAmount();
        powder.setPosY(-13+13*((float)powderAmount / GunpowderKegBlockEntity.MAX_POWDER_AMOUNT));

        // Toggle Lid and Powder Visibility
        boolean hideLid = !animatable.getClosed();
        boolean hidePowder = animatable.getClosed() || powderAmount == 0;
        lid.setHidden(hideLid);
        powder.setHidden(hidePowder);
    }

    @Override
    public void postRender(MatrixStack poseStack, GunpowderKegBlockEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        AnimationProcessor<GunpowderKegBlockEntity> animProc = getGeoModel().getAnimationProcessor();
        CoreGeoBone powder = animProc.getBone("Powder_Level");
        CoreGeoBone lid = animProc.getBone("Lid");

        // Un-hide bones, so they're visible for other instances that re-use this model without manually unhiding
        lid.setHidden(false);
        powder.setHidden(false);
    }
}
