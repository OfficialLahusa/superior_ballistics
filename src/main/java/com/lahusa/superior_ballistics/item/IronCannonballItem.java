package com.lahusa.superior_ballistics.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class IronCannonballItem extends Item {

    public IronCannonballItem(Settings settings) {
        super(settings);
    }
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.superior_ballistics.cannonball.tooltip").formatted(Formatting.GRAY));
    }
}
