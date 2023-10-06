package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.PithHelmetArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PithHelmetItemModel extends GeoModel<PithHelmetArmorItem> {

    @Override
    public Identifier getModelResource(PithHelmetArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/pith_helmet.geo.json");
    }

    @Override
    public Identifier getTextureResource(PithHelmetArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/pith_helmet_layer_1.png");
    }

    @Override
    public Identifier getAnimationResource(PithHelmetArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/pith_helmet.animation.json");
    }
}
