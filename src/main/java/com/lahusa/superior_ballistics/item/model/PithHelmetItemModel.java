package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.PithHelmetArmorItem;
import com.lahusa.superior_ballistics.item.TricorneArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PithHelmetItemModel extends AnimatedGeoModel<PithHelmetArmorItem> {

    @Override
    public Identifier getModelLocation(PithHelmetArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/pith_helmet.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PithHelmetArmorItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "textures/models/armor/pith_helmet_layer_1.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PithHelmetArmorItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/pith_helmet.animation.json");
    }
}
