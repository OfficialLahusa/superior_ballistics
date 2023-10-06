package com.lahusa.superior_ballistics.block.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.block.entity.GunpowderKegBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GunpowderKegBlockModel extends GeoModel<GunpowderKegBlockEntity> {
    @Override
    public Identifier getModelResource(GunpowderKegBlockEntity object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/gunpowder_keg.geo.json");
    }

    @Override
    public Identifier getTextureResource(GunpowderKegBlockEntity object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/block/gunpowder_keg.png");
    }

    @Override
    public Identifier getAnimationResource(GunpowderKegBlockEntity animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/gunpowder_keg.animation.json");
    }
}
