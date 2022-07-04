package com.lahusa.superior_ballistics.armor.renderer;

import com.lahusa.superior_ballistics.item.TschakoArmorItem;
import com.lahusa.superior_ballistics.armor.model.TschakoArmorModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class TschakoArmorRenderer extends GeoArmorRenderer<TschakoArmorItem> {
    public TschakoArmorRenderer(){
        super(new TschakoArmorModel());
        this.headBone = "bipedHead";
    }
}
