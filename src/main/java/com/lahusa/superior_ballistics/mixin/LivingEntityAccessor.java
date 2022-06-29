package com.lahusa.superior_ballistics.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor
{
    @Accessor("lastDamageTaken")
    void setLastDamageTaken(float value);
}
