package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.PithHelmetArmorItem;
import com.lahusa.superior_ballistics.item.model.PithHelmetItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PithHelmetItemRenderer extends GeoItemRenderer<PithHelmetArmorItem> {
    public PithHelmetItemRenderer(){
        super(new PithHelmetItemModel());
    }
}
