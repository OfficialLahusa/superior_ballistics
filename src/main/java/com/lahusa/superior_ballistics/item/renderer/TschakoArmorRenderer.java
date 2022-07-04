package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.TschakoArmorItem;
import com.lahusa.superior_ballistics.item.model.TschakoArmorModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class TschakoArmorRenderer extends GeoArmorRenderer<TschakoArmorItem> {
    public TschakoArmorRenderer(){
        super(new TschakoArmorModel());
        this.headBone = "bipedHead";
    }
}
