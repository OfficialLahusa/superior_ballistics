package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.GunpowderKegBlockItem;
import com.lahusa.superior_ballistics.item.model.CannonBlockItemModel;
import com.lahusa.superior_ballistics.item.model.GunpowderKegBlockItemModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GunpowderKegItemRenderer extends GeoItemRenderer<GunpowderKegBlockItem> {
    public GunpowderKegItemRenderer() {
        super(new GunpowderKegBlockItemModel());
    }
}
