package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.TricorneArmorItem;
import com.lahusa.superior_ballistics.item.model.TricorneItemModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class TricorneItemRenderer extends GeoItemRenderer<TricorneArmorItem> {
    public TricorneItemRenderer(){
        super(new TricorneItemModel());
    }
}
