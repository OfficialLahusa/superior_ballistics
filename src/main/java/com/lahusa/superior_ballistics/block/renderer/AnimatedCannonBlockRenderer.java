package com.lahusa.superior_ballistics.block.renderer;

import com.lahusa.superior_ballistics.block.entity.AnimatedCannonBlockEntity;
import com.lahusa.superior_ballistics.block.model.AnimatedCannonBlockModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class AnimatedCannonBlockRenderer extends GeoBlockRenderer<AnimatedCannonBlockEntity> {

    public AnimatedCannonBlockRenderer() {
        super(new AnimatedCannonBlockModel());
    }
}
