package com.lahusa.superior_ballistics.armor.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.PickelhaubeArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PickelhaubeArmorModel extends AnimatedGeoModel<PickelhaubeArmorItem> {
    @Override
    public Identifier getModelLocation(PickelhaubeArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/pickelhaube.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PickelhaubeArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/pickelhaube_layer_1.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PickelhaubeArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/pickelhaube.animation.json");
    }
}
