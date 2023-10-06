package com.lahusa.superior_ballistics.armor.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.ShakoArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShakoArmorModel extends GeoModel<ShakoArmorItem> {

    @Override
    public Identifier getModelResource(ShakoArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/shako.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShakoArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/shako_layer_1.png");
    }

    @Override
    public Identifier getAnimationResource(ShakoArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/shako.animation.json");
    }
}
