package com.lahusa.superior_ballistics.item.model;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.block.entity.AnimatedCannonBlockEntity;
import com.lahusa.superior_ballistics.item.AnimatedCannonBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedCannonBlockItemModel extends AnimatedGeoModel<AnimatedCannonBlockItem> {
    @Override
    public Identifier getModelLocation(AnimatedCannonBlockItem object) {
        return new Identifier(SuperiorBallisticsMod.MODID, "geo/animated_cannon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(AnimatedCannonBlockItem object) {
        return new Identifier("minecraft", "textures/block/spruce_planks.png");
    }

    @Override
    public Identifier getAnimationFileLocation(AnimatedCannonBlockItem animatable) {
        return new Identifier(SuperiorBallisticsMod.MODID, "animations/animated_cannon.animation.json");
    }
}
