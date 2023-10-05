package com.lahusa.superior_ballistics.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ibm.icu.impl.locale.XCldrStub;
import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.entity.StoneBulletEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.tag.*;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


import java.util.function.Predicate;

public class FlintlockMusketItem extends RangedWeaponItem {

    public static final float REQUIRED_PULL_PROGRESS = 1.0f;
    protected boolean loaded = false;
    protected static final float soundPitch = 1.0f;
    protected static final float soundVolume = 1.0F;

    public static final TagKey<Item> AMMUNITION_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(SuperiorBallisticsMod.MODID, "pistol_ammunition"));
    public final Multimap<EntityAttribute, EntityAttributeModifier> bayonetModifiers;

    public FlintlockMusketItem(boolean hasBayonet, Settings settings) {
        super(settings);

        // Add modifiers for bayonet
        if(hasBayonet) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> attributeBuilder = ImmutableMultimap.builder();
            attributeBuilder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Weapon modifier", 6, EntityAttributeModifier.Operation.ADDITION));
            attributeBuilder.put(EntityAttributes.GENERIC_ATTACK_SPEED,  new EntityAttributeModifier("Weapon modifier", -2.8f, EntityAttributeModifier.Operation.ADDITION));
            bayonetModifiers = attributeBuilder.build();
        }
        else {
            bayonetModifiers = null;
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND && bayonetModifiers != null)
            return bayonetModifiers;
        else
            return super.getAttributeModifiers(slot);
    }

    public static boolean isCharged(ItemStack stack) {
        NbtCompound compoundTag = stack.getNbt();
        return compoundTag != null && compoundTag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        NbtCompound compoundTag = stack.getNbt();
        if (compoundTag != null) {
            compoundTag.putBoolean("Charged", charged);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (isCharged(itemStack)) {
            // Shoot projectile
            shoot(world, user);

            // Reset charge
            setCharged(itemStack, false);

            // Damage Musket
            if(!user.isCreative() && !world.isClient) itemStack.damage(1, user, (p) -> p.sendToolBreakStatus(p.getActiveHand()));

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
        if (user instanceof PlayerEntity playerEntity) {
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
                if (pullProgress >= REQUIRED_PULL_PROGRESS) {
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

    protected void fireProjectile(World world, LivingEntity shooter, int damage, float speed, float divergence)
    {
        // Only execute on server
        if (!world.isClient) {
            StoneBulletEntity stoneBulletEntity = new StoneBulletEntity(world, shooter, damage, StatusEffects.SLOWNESS);
            stoneBulletEntity.setItem(new ItemStack(SuperiorBallisticsMod.STONE_BULLET_ITEM));
            stoneBulletEntity.setVelocity(shooter, shooter.getPitch(), shooter.getYaw(), 0.0F, speed, divergence);
            world.spawnEntity(stoneBulletEntity);
        }
    }

    protected void shoot(World world, LivingEntity shooter) {
        // Shoot projectile
        fireProjectile(world, shooter,
                SuperiorBallisticsMod.CONFIG.getMusketShotDamage(),
                SuperiorBallisticsMod.CONFIG.getMusketShotSpeed(),
                SuperiorBallisticsMod.CONFIG.getMusketShotDivergence());

        // Play firing sound
        world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, FlintlockMusketItem.soundVolume, FlintlockMusketItem.soundPitch);

        // Spawn smoke particles
        Vec3d lookDir = shooter.getRotationVector();
        if (!world.isClient) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.POOF,
                    shooter.getX() + lookDir.getX(), shooter.getY() + 1.4 + lookDir.getY(), shooter.getZ() + lookDir.getZ(),
                    50,0.2,0.2,0.2,0.01);
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

    public static final Predicate<ItemStack> FLINTLOCK_MUSKET_PROJECTILES = (stack) -> stack.isIn(AMMUNITION_TAG);

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return FLINTLOCK_MUSKET_PROJECTILES;
    }

    @Override
    public int getRange() {
        return 1;
    }
}
