package com.lahusa.superior_ballistics.item;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.entity.StoneBulletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


import java.util.function.Predicate;

public class FlintlockPistolItem extends RangedWeaponItem {

    public static final float REQUIRED_PULL_PROGRESS = 0.5f;
    protected static final float soundPitch = 1.4f;
    protected static final float soundVolume = 1.0F;


    public FlintlockPistolItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            boolean creativeMode = playerEntity.getAbilities().creativeMode;

            // Get the ammo stack, that should be used
            ItemStack itemStack = playerEntity.getArrowType(stack);
            boolean hasGunpowder = playerEntity.getInventory().contains(Items.GUNPOWDER.getDefaultStack());

            // Only execute, is there is ammo and gunpowder or user is in creative mode
            if ((!itemStack.isEmpty() && hasGunpowder) || creativeMode) {
                // If there is no ammo, default to stone bullet
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(Items.ARROW);
                }

                // Calculate pull progress
                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float pullProgress = getPullProgress(i);

                // Only execute, if pull progress is high enough
                if (pullProgress >= REQUIRED_PULL_PROGRESS) {
                    // Shoot
                    shoot(world, user);

                    // Subtract ammo, if found
                    if (!playerEntity.getAbilities().creativeMode) {
                        PlayerInventory inventory = playerEntity.getInventory();
                        itemStack.decrement(1);

                        // Remove bullet
                        if (itemStack.isEmpty()) {
                            inventory.removeOne(itemStack);
                        }

                        // Remove first gunpowder
                        for (int j = 0; j < inventory.size(); ++j) {
                            ItemStack currentStack = inventory.getStack(j);
                            if (currentStack.isOf(Items.GUNPOWDER)) {
                                currentStack.decrement(1);
                                break;
                            }
                        }
                    }

                    // Damage Pistol
                    if(!playerEntity.isCreative() && !world.isClient) stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                }
            }
        }
    }

    protected void fireProjectile(World world, LivingEntity shooter)
    {
        // Only execute on server
        if (!world.isClient) {
            StoneBulletEntity stoneBulletEntity = new StoneBulletEntity(world, shooter, SuperiorBallisticsMod.CONFIG.getPistolShotDamage(), null);
            stoneBulletEntity.setItem(new ItemStack(SuperiorBallisticsMod.STONE_BULLET_ITEM));
            stoneBulletEntity.setVelocity(
                    shooter, shooter.getPitch(), shooter.getYaw(), 0.0F,
                    SuperiorBallisticsMod.CONFIG.getPistolShotSpeed(),
                    SuperiorBallisticsMod.CONFIG.getPistolShotDivergence());
            world.spawnEntity(stoneBulletEntity);
        }
    }

    protected void shoot(World world, LivingEntity shooter) {
        // Shoot projectile
        fireProjectile(world, shooter);

        // Play firing sound
        world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, FlintlockPistolItem.soundVolume, FlintlockPistolItem.soundPitch);

        // Spawn particles
        Vec3d lookDir = shooter.getRotationVector();
        if (!shooter.getEntityWorld().isClient) {
            ((ServerWorld) shooter.getEntityWorld()).spawnParticles(ParticleTypes.POOF,
                    shooter.getX() + lookDir.getX(),
                    shooter.getY() + 1.4 + lookDir.getY(),
                    shooter.getZ() + lookDir.getZ(),
                    15,
                    0.2,
                    0.2,
                    0.2,
                    0.01);
        }

        // Increment statistics
        if(shooter instanceof PlayerEntity playerEntity) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl = !user.getArrowType(itemStack).isEmpty();
        if (!user.getAbilities().creativeMode && !bl) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    public static final Predicate<ItemStack> FLINTLOCK_MUSKET_PROJECTILES = (stack) -> stack.isIn(FlintlockMusketItem.AMMUNITION_TAG);

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return FLINTLOCK_MUSKET_PROJECTILES;
    }

    @Override
    public int getRange() {
        return 12;
    }
}
