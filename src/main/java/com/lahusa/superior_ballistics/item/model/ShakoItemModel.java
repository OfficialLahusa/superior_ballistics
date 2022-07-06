package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.ShakoArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShakoItemModel extends AnimatedGeoModel<ShakoArmorItem> {

    @Override
    public Identifier getModelLocation(ShakoArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/shako.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ShakoArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/shako_layer_1.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ShakoArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/shako.animation.json");
    }
}
