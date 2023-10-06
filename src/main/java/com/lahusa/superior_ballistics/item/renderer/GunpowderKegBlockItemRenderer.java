package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.GunpowderKegBlockItem;
import com.lahusa.superior_ballistics.item.model.GunpowderKegBlockItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GunpowderKegBlockItemRenderer extends GeoItemRenderer<GunpowderKegBlockItem> {
    public GunpowderKegBlockItemRenderer() {
        super(new GunpowderKegBlockItemModel());
    }
}
