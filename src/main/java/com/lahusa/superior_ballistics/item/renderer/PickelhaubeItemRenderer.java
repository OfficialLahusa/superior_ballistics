package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.PickelhaubeArmorItem;
import com.lahusa.superior_ballistics.item.model.PickelhaubeItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PickelhaubeItemRenderer extends GeoItemRenderer<PickelhaubeArmorItem> {
    public PickelhaubeItemRenderer(){
        super(new PickelhaubeItemModel());
    }
}
