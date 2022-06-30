package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.CannonBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CannonBlockItemModel extends AnimatedGeoModel<CannonBlockItem> {
    @Override
    public Identifier getModelLocation(CannonBlockItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/cannon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CannonBlockItem object) {
        return new Identifier("minecraft", "textures/block/spruce_planks.png");
    }

    @Override
    public Identifier getAnimationFileLocation(CannonBlockItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/cannon.animation.json");
    }
}
