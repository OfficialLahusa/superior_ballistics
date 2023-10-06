package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.TricorneArmorItem;
import com.lahusa.superior_ballistics.item.model.TricorneItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TricorneItemRenderer extends GeoItemRenderer<TricorneArmorItem> {
    public TricorneItemRenderer(){
        super(new TricorneItemModel());
    }
}
