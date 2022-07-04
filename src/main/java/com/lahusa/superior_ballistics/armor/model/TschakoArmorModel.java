package com.lahusa.superior_ballistics.armor.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.TschakoArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TschakoArmorModel extends AnimatedGeoModel<TschakoArmorItem> {

    @Override
    public Identifier getModelLocation(TschakoArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/tschako.geo.json");
    }

    @Override
    public Identifier getTextureLocation(TschakoArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/tschako_layer_1.png");
    }

    @Override
    public Identifier getAnimationFileLocation(TschakoArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/tschako.animation.json");
    }
}
