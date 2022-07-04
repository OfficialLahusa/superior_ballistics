package com.lahusa.superior_ballistics.armor.renderer;

import com.lahusa.superior_ballistics.item.PithHelmetArmorItem;
import com.lahusa.superior_ballistics.armor.model.PithHelmetArmorModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PithHelmetArmorRenderer extends GeoArmorRenderer<PithHelmetArmorItem> {
    public PithHelmetArmorRenderer(){
        super(new PithHelmetArmorModel());
        this.headBone = "bipedHead";
    }
}
