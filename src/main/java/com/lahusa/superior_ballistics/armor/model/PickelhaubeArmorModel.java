package com.lahusa.superior_ballistics.armor.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.PickelhaubeArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PickelhaubeArmorModel extends GeoModel<PickelhaubeArmorItem> {
    @Override
    public Identifier getModelResource(PickelhaubeArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/pickelhaube.geo.json");
    }

    @Override
    public Identifier getTextureResource(PickelhaubeArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/pickelhaube_layer_1.png");
    }

    @Override
    public Identifier getAnimationResource(PickelhaubeArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/pickelhaube.animation.json");
    }
}
