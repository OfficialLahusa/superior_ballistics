package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.ShakoArmorItem;
import com.lahusa.superior_ballistics.item.model.ShakoItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ShakoItemRenderer extends GeoItemRenderer<ShakoArmorItem> {
    public ShakoItemRenderer(){
        super(new ShakoItemModel());
    }
}
