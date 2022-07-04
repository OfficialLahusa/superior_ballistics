package com.lahusa.superior_ballistics.armor.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.BicorneFrontArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BicorneFrontArmorModel extends AnimatedGeoModel<BicorneFrontArmorItem> {

    @Override
    public Identifier getModelLocation(BicorneFrontArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/bicorne_front.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BicorneFrontArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/bicorne_layer_1.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BicorneFrontArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/bicorne_front.animation.json");
    }
}
