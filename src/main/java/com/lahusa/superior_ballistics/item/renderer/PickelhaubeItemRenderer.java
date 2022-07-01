package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.PickelhaubeArmorItem;
import com.lahusa.superior_ballistics.item.model.PickelhaubeItemModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class PickelhaubeItemRenderer extends GeoItemRenderer<PickelhaubeArmorItem> {
    public PickelhaubeItemRenderer(){
        super(new PickelhaubeItemModel());
    }
}
