package com.lahusa.superior_ballistics.item.renderer;

import com.lahusa.superior_ballistics.item.PickelhaubeArmorItem;
import com.lahusa.superior_ballistics.item.TschakoArmorItem;
import com.lahusa.superior_ballistics.item.model.PickelhaubeItemModel;
import com.lahusa.superior_ballistics.item.model.TschakoArmorModel;
import com.lahusa.superior_ballistics.item.model.TschakoItemModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class TschakoItemRenderer extends GeoItemRenderer<TschakoArmorItem> {
    public TschakoItemRenderer(){
        super(new TschakoItemModel());
    }
}
