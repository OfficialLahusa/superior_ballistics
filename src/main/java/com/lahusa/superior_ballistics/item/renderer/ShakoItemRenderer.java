package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.ShakoArmorItem;
import com.lahusa.superior_ballistics.item.model.ShakoItemModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ShakoItemRenderer extends GeoItemRenderer<ShakoArmorItem> {
    public ShakoItemRenderer(){
        super(new ShakoItemModel());
    }
}
