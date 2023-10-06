package com.lahusa.superior_ballistics.block.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.block.entity.CannonBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CannonBlockModel extends GeoModel<CannonBlockEntity> {
    @Override
    public Identifier getModelResource(CannonBlockEntity object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/cannon.geo.json");
    }

    @Override
    public Identifier getTextureResource(CannonBlockEntity object) {
        return new Identifier("minecraft", "textures/block/spruce_planks.png");
    }

    @Override
    public Identifier getAnimationResource(CannonBlockEntity animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/cannon.animation.json");
    }
}
