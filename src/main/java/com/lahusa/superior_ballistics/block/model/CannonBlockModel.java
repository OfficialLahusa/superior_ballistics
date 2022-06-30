package com.lahusa.superior_ballistics.block.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.block.entity.CannonBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CannonBlockModel extends AnimatedGeoModel<CannonBlockEntity> {
    @Override
    public Identifier getModelLocation(CannonBlockEntity object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/cannon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CannonBlockEntity object) {
        return new Identifier("minecraft", "textures/block/spruce_planks.png");
    }

    @Override
    public Identifier getAnimationFileLocation(CannonBlockEntity animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/cannon.animation.json");
    }
}
