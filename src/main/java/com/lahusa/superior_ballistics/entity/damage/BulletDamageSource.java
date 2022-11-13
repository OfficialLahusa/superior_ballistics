package com.lahusa.superior_ballistics.entity.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;

public class BulletDamageSource extends ProjectileDamageSource {
    public BulletDamageSource(String name, Entity projectile, @Nullable Entity attacker) {
        super(name, projectile, attacker);
        this.setBypassesArmor();
        this.setUnblockable();
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        Entity attacker = getAttacker();
        Text text = attacker == null ? this.source.getDisplayName() : attacker.getDisplayName();
        ItemStack itemStack = attacker instanceof LivingEntity ? ((LivingEntity)attacker).getMainHandStack() : ItemStack.EMPTY;
        String string = "death.attack." + this.name;
        String string2 = string + ".item";
        return !itemStack.isEmpty() && itemStack.hasCustomName() ? new TranslatableText(string2, entity.getDisplayName(), text, itemStack.toHoverableText()) : new TranslatableText(string, entity.getDisplayName(), text);
    }
}
