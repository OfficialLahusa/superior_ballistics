package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.BicorneSideArmorItem;
import com.lahusa.superior_ballistics.item.model.BicorneSideItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BicorneSideItemRenderer extends GeoItemRenderer<BicorneSideArmorItem> {
    public BicorneSideItemRenderer(){
        super(new BicorneSideItemModel());
    }
}
