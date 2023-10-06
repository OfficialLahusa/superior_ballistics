package com.lahusa.superior_ballistics.armor.renderer;

import com.lahusa.superior_ballistics.item.ShakoArmorItem;
import com.lahusa.superior_ballistics.armor.model.ShakoArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ShakoArmorRenderer extends GeoArmorRenderer<ShakoArmorItem> {
    public ShakoArmorRenderer(){
        super(new ShakoArmorModel());

        //this.headBone = "bipedHead";
    }
}
