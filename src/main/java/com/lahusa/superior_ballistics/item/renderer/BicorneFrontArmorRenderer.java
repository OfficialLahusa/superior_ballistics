package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.BicorneFrontArmorItem;
import com.lahusa.superior_ballistics.item.model.BicorneFrontArmorModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BicorneFrontArmorRenderer extends GeoArmorRenderer<BicorneFrontArmorItem> {
    public BicorneFrontArmorRenderer(){
        super(new BicorneFrontArmorModel());
        this.headBone = "bipedHead";
    }
}
