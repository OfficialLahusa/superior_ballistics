package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.BicorneSideArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BicorneSideArmorModel extends AnimatedGeoModel<BicorneSideArmorItem> {

    @Override
    public Identifier getModelLocation(BicorneSideArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/bicorne_side.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BicorneSideArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/bicorne_layer_1.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BicorneSideArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/bicorne_side.animation.json");
    }
}
