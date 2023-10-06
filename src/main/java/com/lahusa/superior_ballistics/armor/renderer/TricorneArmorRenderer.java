package com.lahusa.superior_ballistics.armor.renderer;

import com.lahusa.superior_ballistics.item.TricorneArmorItem;
import com.lahusa.superior_ballistics.armor.model.TricorneArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TricorneArmorRenderer extends GeoArmorRenderer<TricorneArmorItem> {
    public TricorneArmorRenderer(){
        super(new TricorneArmorModel());

        //this.headBone = "bipedHead";
    }
}
