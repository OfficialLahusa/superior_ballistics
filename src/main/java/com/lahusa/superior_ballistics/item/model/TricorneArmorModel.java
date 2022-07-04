package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.TricorneArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TricorneArmorModel extends AnimatedGeoModel<TricorneArmorItem> {

    @Override
    public Identifier getModelLocation(TricorneArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/tricorne.geo.json");
    }

    @Override
    public Identifier getTextureLocation(TricorneArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/tricorne_layer_1.png");
    }

    @Override
    public Identifier getAnimationFileLocation(TricorneArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/tricorne.animation.json");
    }
}
