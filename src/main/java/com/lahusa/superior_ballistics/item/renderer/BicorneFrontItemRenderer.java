package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.BicorneFrontArmorItem;
import com.lahusa.superior_ballistics.item.model.BicorneFrontItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BicorneFrontItemRenderer extends GeoItemRenderer<BicorneFrontArmorItem> {
    public BicorneFrontItemRenderer(){
        super(new BicorneFrontItemModel());
    }
}
