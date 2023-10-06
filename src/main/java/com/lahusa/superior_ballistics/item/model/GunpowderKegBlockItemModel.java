package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.GunpowderKegBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GunpowderKegBlockItemModel extends GeoModel<GunpowderKegBlockItem> {
    @Override
    public Identifier getModelResource(GunpowderKegBlockItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/gunpowder_keg.geo.json");
    }

    @Override
    public Identifier getTextureResource(GunpowderKegBlockItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/block/gunpowder_keg.png");
    }

    @Override
    public Identifier getAnimationResource(GunpowderKegBlockItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/gunpowder_keg.animation.json");
    }
}
