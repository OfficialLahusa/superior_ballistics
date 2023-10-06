package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.BicorneFrontArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BicorneFrontItemModel extends GeoModel<BicorneFrontArmorItem> {

    @Override
    public Identifier getModelResource(BicorneFrontArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/bicorne_front.geo.json");
    }

    @Override
    public Identifier getTextureResource(BicorneFrontArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/bicorne_layer_1.png");
    }

    @Override
    public Identifier getAnimationResource(BicorneFrontArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/bicorne_front.animation.json");
    }
}
