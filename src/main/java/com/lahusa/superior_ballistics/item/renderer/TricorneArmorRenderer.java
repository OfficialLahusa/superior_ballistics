package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.TricorneArmorItem;
import com.lahusa.superior_ballistics.item.model.TricorneArmorModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class TricorneArmorRenderer extends GeoArmorRenderer<TricorneArmorItem> {
    public TricorneArmorRenderer(){
        super(new TricorneArmorModel());
        this.headBone = "bipedHead";
    }
}
