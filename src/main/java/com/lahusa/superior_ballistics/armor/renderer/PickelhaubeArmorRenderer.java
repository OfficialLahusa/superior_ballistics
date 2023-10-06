package com.lahusa.superior_ballistics.armor.renderer;

import com.lahusa.superior_ballistics.item.PickelhaubeArmorItem;
import com.lahusa.superior_ballistics.armor.model.PickelhaubeArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PickelhaubeArmorRenderer extends GeoArmorRenderer<PickelhaubeArmorItem> {
    public PickelhaubeArmorRenderer(){
        super(new PickelhaubeArmorModel());

        //this.headBone = "bipedHead";
    }
}
