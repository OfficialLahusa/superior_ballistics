package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.BicorneSideArmorItem;
import com.lahusa.superior_ballistics.item.model.BicorneSideArmorModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BicorneSideArmorRenderer extends GeoArmorRenderer<BicorneSideArmorItem> {
    public BicorneSideArmorRenderer(){
        super(new BicorneSideArmorModel());
        this.headBone = "bipedHead";
    }
}
