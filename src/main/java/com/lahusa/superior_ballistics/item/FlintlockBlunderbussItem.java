package com.lahusa.superior_ballistics.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlintlockBlunderbussItem extends FlintlockMusketItem {

    public static final float REQUIRED_PULL_PROGRESS = 1.0f;
    protected static final float speed = 1.6f;
    protected static final float divergence = 10.0f;
    protected static final int shotDamage = 2000;
    protected static final int shotCount = 8;
    protected static final float soundPitch = 0.8f;
    protected static final float soundVolume = 1.1F;

    public FlintlockBlunderbussItem(Settings settings) {
        super(settings);
    }

    @Override
    protected void shoot(World world, LivingEntity shooter) {
        for(int i = 0; i < shotCount; i++)
        {
            // Shoot projectile
            fireProjectile(world, shooter, FlintlockBlunderbussItem.shotDamage, FlintlockBlunderbussItem.speed, FlintlockBlunderbussItem.divergence);
        }

        // Play firing sound
        world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, FlintlockBlunderbussItem.soundVolume, FlintlockBlunderbussItem.soundPitch);

        // Spawn smoke particles
        Vec3d lookDir = shooter.getRotationVector();
        if (!world.isClient) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.POOF,
                    shooter.getX() + lookDir.getX(), shooter.getY() + 1.4 + lookDir.getY(), shooter.getZ() + lookDir.getZ(),
                    70,0.2,0.2,0.2,0.01);
        }

        // Increment statistics
        if(shooter instanceof PlayerEntity playerEntity) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            boolean creativeMode = playerEntity.getAbilities().creativeMode;

            // Get the ammo stack, that should be used
            ItemStack ammoStack = playerEntity.getArrowType(stack);
            boolean hasAmmo = playerEntity.getArrowType(stack).getCount() >= shotCount;
            boolean hasGunpowder = playerEntity.getInventory().contains(Items.GUNPOWDER.getDefaultStack());

            // Only execute, is there is ammo and gunpowder or user is in creative mode
            if ((hasAmmo && hasGunpowder) || creativeMode) {
                // If there is no ammo, default to stone bullet
                if (ammoStack.isEmpty()) {
                    ammoStack = new ItemStack(Items.ARROW);
                }

                // Calculate pull progress
                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float pullProgress = getPullProgress(i);

                // Only execute, if pull progress is high enough
                if (pullProgress >= REQUIRED_PULL_PROGRESS) {
                    setCharged(stack, true);
                    subtractAmmunition(playerEntity, ammoStack);
                }
            }
        }
    }

    @Override
    public void subtractAmmunition(PlayerEntity player, ItemStack ammoStack) {
        // Subtract ammo, if found
        if (!player.getAbilities().creativeMode) {
            PlayerInventory inventory = player.getInventory();
            ammoStack.decrement(shotCount);

            // Remove bullet
            if (ammoStack.isEmpty()) {
                inventory.removeOne(ammoStack);
            }
            // Remove first gunpowder
            for(int j = 0; j < inventory.size(); ++j) {
                ItemStack currentStack = inventory.getStack(j);
                if(currentStack.isOf(Items.GUNPOWDER)) {
                    currentStack.decrement(1);
                    break;
                }
            }
        }
    }
}
