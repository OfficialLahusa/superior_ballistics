package com.lahusa.superior_ballistics.entity.damage;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CustomDamageTypes {
    public static final RegistryKey<DamageType> SHOT_DAMAGE_TYPE = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE, new Identifier(SuperiorBallisticsMod.MODID, "shot_type"));
    public static final RegistryKey<DamageType> SHOT__UNCOMMON_DAMAGE_TYPE = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE, new Identifier(SuperiorBallisticsMod.MODID, "shot_uncommon_type"));
    public static final RegistryKey<DamageType> SHOT__RARE_DAMAGE_TYPE = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE, new Identifier(SuperiorBallisticsMod.MODID, "shot_rare_type"));

    public static DamageSource of(World world, RegistryKey<DamageType> key, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), attacker);
    }
}
