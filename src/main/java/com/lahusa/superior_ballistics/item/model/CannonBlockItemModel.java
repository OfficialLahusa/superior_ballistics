package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.item.CannonBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CannonBlockItemModel extends GeoModel<CannonBlockItem> {
    @Override
    public Identifier getModelResource(CannonBlockItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/cannon.geo.json");
    }

    @Override
    public Identifier getTextureResource(CannonBlockItem object) {
        return new Identifier("minecraft", "textures/block/spruce_planks.png");
    }

    @Override
    public Identifier getAnimationResource(CannonBlockItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/cannon.animation.json");
    }
}
