package com.lahusa.superior_ballistics.armor.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.TricorneArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TricorneArmorModel extends GeoModel<TricorneArmorItem> {

    @Override
    public Identifier getModelResource(TricorneArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/tricorne.geo.json");
    }

    @Override
    public Identifier getTextureResource(TricorneArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/tricorne_layer_1.png");
    }

    @Override
    public Identifier getAnimationResource(TricorneArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/tricorne.animation.json");
    }
}
