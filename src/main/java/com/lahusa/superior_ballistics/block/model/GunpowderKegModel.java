package com.lahusa.superior_ballistics.block.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.block.entity.GunpowderKegBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GunpowderKegModel extends AnimatedGeoModel<GunpowderKegBlockEntity> {
    @Override
    public Identifier getModelLocation(GunpowderKegBlockEntity object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/gunpowder_keg.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GunpowderKegBlockEntity object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/block/gunpowder_keg.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GunpowderKegBlockEntity animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/gunpowder_keg.animation.json");
    }
}
