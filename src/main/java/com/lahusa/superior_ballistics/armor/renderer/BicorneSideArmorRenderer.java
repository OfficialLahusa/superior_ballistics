package com.lahusa.superior_ballistics.armor.renderer;

import com.lahusa.superior_ballistics.item.BicorneSideArmorItem;
import com.lahusa.superior_ballistics.armor.model.BicorneSideArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BicorneSideArmorRenderer extends GeoArmorRenderer<BicorneSideArmorItem> {
    public BicorneSideArmorRenderer(){
        super(new BicorneSideArmorModel());

        //this.headBone = "bipedHead";
    }
}
