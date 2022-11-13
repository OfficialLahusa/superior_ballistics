package com.lahusa.superior_ballistics.block.renderer;

import com.lahusa.superior_ballistics.block.entity.GunpowderKegBlockEntity;
import com.lahusa.superior_ballistics.block.model.GunpowderKegModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class GunpowderKegRenderer extends GeoBlockRenderer<GunpowderKegBlockEntity> {
    public GunpowderKegRenderer() {
        super(new GunpowderKegModel());
    }
}
