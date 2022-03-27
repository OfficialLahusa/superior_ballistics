package com.lahusa.superior_ballistics;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.tag.*;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


import java.util.function.Predicate;

public class FlintlockMusketItem extends RangedWeaponItem {

    private boolean loaded = false;

    private float speed = 3.0f;
    private float divergence = 0.35f;
    private float soundPitch = 1.0f;

    public FlintlockMusketItem(Settings settings) {
        super(settings);
    }

    public static boolean isCharged(ItemStack stack) {
        NbtCompound compoundTag = stack.getNbt();
        return compoundTag != null && compoundTag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        NbtCompound compoundTag = stack.getNbt();
        compoundTag.putBoolean("Charged", charged);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (isCharged(itemStack)) {
            shoot(world, user, soundPitch, speed, divergence);
            setCharged(itemStack, false);
            return TypedActionResult.consume(itemStack);
        } else if (!user.getArrowType(itemStack).isEmpty() || user.getAbilities().creativeMode) {
            if (!isCharged(itemStack)) {
                this.loaded = false;
                user.setCurrentHand(hand);
            }
            return TypedActionResult.consume(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            boolean creativeMode = playerEntity.getAbilities().creativeMode;
            // Get the ammo stack, that should be used
            ItemStack ammoStack = playerEntity.getArrowType(stack);
            boolean hasAmmo = !playerEntity.getArrowType(stack).isEmpty();
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
                if (pullProgress >= 1.0f) {
                    setCharged(stack, true);
                    subtractAmmunition(playerEntity, ammoStack);
                }
            }
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient) {
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            float pullProgress = getPullProgress(i);
            if (pullProgress < 1.0f) {
                this.loaded = false;
            }
            if (pullProgress >= 1.0F && !this.loaded) {
                this.loaded = true;
                world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_LEVER_CLICK, (user instanceof PlayerEntity)? SoundCategory.PLAYERS:SoundCategory.HOSTILE,1.0f,0.6f);
            }
        }

    }

    public void subtractAmmunition(PlayerEntity player, ItemStack ammoStack) {
        // Subtract ammo, if found
        if (!player.getAbilities().creativeMode) {
            PlayerInventory inventory = player.getInventory();
            ammoStack.decrement(1);
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

    public void shoot(World world, LivingEntity shooter, float soundPitch, float speed, float divergence) {
        // Shoot projectile
        if (!world.isClient) {
            StoneBulletEntity snowballEntity = new StoneBulletEntity(world, shooter);
            snowballEntity.setItem(new ItemStack(SuperiorBallisticsMod.STONE_BULLET_ITEM));
            snowballEntity.setProperties(shooter, shooter.getPitch(), shooter.getYaw(), 0.0F, speed, divergence);
            world.spawnEntity(snowballEntity);
        }

        // Play firing sound
        world.playSound((PlayerEntity)null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, soundPitch);

        // Spawn smoke particles
        Vec3d lookDir = shooter.getRotationVector();
        if (!world.isClient) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.POOF,
                    shooter.getX() + lookDir.getX(), shooter.getY() + 1.4 + lookDir.getY(), shooter.getZ() + lookDir.getZ(),
                    50,0.2,0.2,0.2,0.01);
        }

        // Increment statistics
        if(shooter instanceof PlayerEntity) {
            ((PlayerEntity)shooter).incrementStat(Stats.USED.getOrCreateStat(this));
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

    public static final Predicate<ItemStack> FLINTLOCK_PISTOL_PROJECTILES = (stack) -> {
        return stack.isIn(ServerTagManagerHolder.getTagManager().getTag(Registry.ITEM_KEY,new Identifier("superior_ballistics", "pistol_ammunition"), id -> new RuntimeException("Could not load tag: " + id.toString())));
    };

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return FLINTLOCK_PISTOL_PROJECTILES;
    }

    @Override
    public int getRange() {
        return 1;
    }
}
