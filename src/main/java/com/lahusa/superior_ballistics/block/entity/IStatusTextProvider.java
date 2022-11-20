package com.lahusa.superior_ballistics.block.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public interface IStatusTextProvider {
    Text getStatusText(PlayerEntity player);
}
