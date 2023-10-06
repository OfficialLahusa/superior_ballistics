package com.lahusa.superior_ballistics.armor.renderer;

import com.lahusa.superior_ballistics.item.BicorneFrontArmorItem;
import com.lahusa.superior_ballistics.armor.model.BicorneFrontArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BicorneFrontArmorRenderer extends GeoArmorRenderer<BicorneFrontArmorItem> {
    public BicorneFrontArmorRenderer(){
        super(new BicorneFrontArmorModel());

        //this.headBone = "bipedHead";
    }
}
