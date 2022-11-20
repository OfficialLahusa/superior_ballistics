package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.GunpowderKegBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GunpowderKegBlockItemModel extends AnimatedGeoModel<GunpowderKegBlockItem> {
    @Override
    public Identifier getModelLocation(GunpowderKegBlockItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/gunpowder_keg.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GunpowderKegBlockItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/block/gunpowder_keg.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GunpowderKegBlockItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/gunpowder_keg.animation.json");
    }
}
