package com.lahusa.superior_ballistics.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class CreativeCannonModuleItem extends Item {

    public CreativeCannonModuleItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(
                new TranslatableText("item.superior_ballistics.creative_cannon_module.tooltip.phase_hint",
                        new TranslatableText("superior_ballistics.cannon.ready_to_light").formatted(Formatting.GREEN)
                ).formatted(Formatting.GRAY)
        );
        tooltip.add(new TranslatableText("item.superior_ballistics.creative_cannon_module.tooltip.description").formatted(Formatting.GRAY));
    }
}
