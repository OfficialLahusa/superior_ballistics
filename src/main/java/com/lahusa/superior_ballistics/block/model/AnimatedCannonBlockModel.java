package com.lahusa.superior_ballistics.block.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.block.entity.AnimatedCannonBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedCannonBlockModel extends AnimatedGeoModel<AnimatedCannonBlockEntity> {
    @Override
    public Identifier getModelLocation(AnimatedCannonBlockEntity object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/animated_cannon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(AnimatedCannonBlockEntity object) {
        return new Identifier("minecraft", "textures/block/spruce_planks.png");
    }

    @Override
    public Identifier getAnimationFileLocation(AnimatedCannonBlockEntity animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/animated_cannon.animation.json");
    }
}
