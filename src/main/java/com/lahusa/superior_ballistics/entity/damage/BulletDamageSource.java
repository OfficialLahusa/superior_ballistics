package com.lahusa.superior_ballistics.entity.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.ProjectileDamageSource;
import org.jetbrains.annotations.Nullable;

public class BulletDamageSource extends ProjectileDamageSource {
    public BulletDamageSource(String name, Entity projectile, @Nullable Entity attacker) {
        super(name, projectile, attacker);
        this.setBypassesArmor();
        this.setUnblockable();
    }

}
