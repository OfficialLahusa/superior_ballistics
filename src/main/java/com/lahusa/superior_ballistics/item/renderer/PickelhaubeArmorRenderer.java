package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.PickelhaubeArmorItem;
import com.lahusa.superior_ballistics.item.model.PickelhaubeArmorModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PickelhaubeArmorRenderer extends GeoArmorRenderer<PickelhaubeArmorItem> {
    public PickelhaubeArmorRenderer(){
        super(new PickelhaubeArmorModel());

        this.headBone = "bipedHead";
    }
}
