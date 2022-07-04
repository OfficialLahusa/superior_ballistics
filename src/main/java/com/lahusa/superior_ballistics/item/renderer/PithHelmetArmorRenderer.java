package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.PithHelmetArmorItem;
import com.lahusa.superior_ballistics.item.TricorneArmorItem;
import com.lahusa.superior_ballistics.item.model.PithHelmetArmorModel;
import com.lahusa.superior_ballistics.item.model.TricorneArmorModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PithHelmetArmorRenderer extends GeoArmorRenderer<PithHelmetArmorItem> {
    public PithHelmetArmorRenderer(){
        super(new PithHelmetArmorModel());
        this.headBone = "bipedHead";
    }
}
