package com.lahusa.superior_ballistics.entity;

import com.lahusa.superior_ballistics.entity.damage.CustomDamageTypes;
import com.lahusa.superior_ballistics.mixin.LivingEntityAccessor;
import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StoneBulletEntity extends ThrownItemEntity {

    public static final TagKey<Block> STONE_BULLET_BREAKABLE_TAG = TagKey.of(RegistryKeys.BLOCK, new Identifier(SuperiorBallisticsMod.MODID, "stone_bullet_breakable"));
    public static final TagKey<Block> FLOWER_POT_TAG = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "flower_pots"));

    private final int damage;
    private final StatusEffect statusEffect;

    public StoneBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.damage = 13;
        this.statusEffect = null;
    }

    public StoneBulletEntity(World world, LivingEntity owner, int damage, @Nullable StatusEffect statusEffect) {
        super(SuperiorBallisticsMod.STONE_BULLET_ENTITY_TYPE, owner, world);
        this.damage = damage;
        this.statusEffect = statusEffect;
    }

    public StoneBulletEntity(World world, double x, double y, double z, int damage, @Nullable StatusEffect statusEffect) {
        super(SuperiorBallisticsMod.STONE_BULLET_ENTITY_TYPE, x, y, z, world);
        this.damage = damage;
        this.statusEffect = statusEffect;
    }

    @Override
    protected Item getDefaultItem() {
        return SuperiorBallisticsMod.STONE_BULLET_ITEM;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        Entity owner = this.getOwner();

        // Randomize damage source text to produce a variety of death messages
        RegistryKey<DamageType> damageTypeKey = switch(random.nextInt(20)) {
            case 17, 18: yield CustomDamageTypes.SHOT__UNCOMMON_DAMAGE_TYPE;
            case 19: yield CustomDamageTypes.SHOT__RARE_DAMAGE_TYPE;
            default: yield CustomDamageTypes.SHOT_DAMAGE_TYPE;
        };
        DamageSource source = CustomDamageTypes.of(this.getWorld(), damageTypeKey);


        if(entity instanceof LivingEntity livingEntity) {
            // Reset damage cooldown
            ((LivingEntityAccessor)livingEntity).setLastDamageTaken(Float.MIN_VALUE);

            // Is target holding sword and shot by a ServerPlayer
            if(!getWorld().isClient && livingEntity.getMainHandStack().getItem() instanceof SwordItem && owner instanceof ServerPlayerEntity player) {
                SuperiorBallisticsMod.SWORD_USER_SHOT_CRITERION.trigger(player);
            }
        }

        // Play hit ding sound
        if(owner instanceof ServerPlayerEntity ownerPlayer && entity instanceof ServerPlayerEntity targetPlayer
                && !ownerPlayer.equals(targetPlayer) && !targetPlayer.isCreative()) {
            ownerPlayer.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
        }

        entity.damage(source, (float)damage); // deals damage

        if (statusEffect != null && entity instanceof LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(statusEffect, 20 * 3, 1))); // applies a status effect
        }
    }

    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!getWorld().isClient) {
            // Determine hit block and break it, if it's contained in the tags
            Vec3d hitPos = hitResult.getPos().add(getVelocity().normalize().multiply(0.5));
            BlockPos hitBlockPos = new BlockPos((int) hitPos.x, (int) hitPos.y, (int) hitPos.z);

            Block hitBlock = getWorld().getBlockState(hitBlockPos).getBlock();
            RegistryEntry.Reference<Block> hitBlockRegEntry = hitBlock.getRegistryEntry();
            if(hitBlockRegEntry.isIn(STONE_BULLET_BREAKABLE_TAG) || hitBlockRegEntry.isIn(FLOWER_POT_TAG))
            {
                getWorld().breakBlock(hitBlockPos, false, this.getOwner(), 400);
            }

            // Spawn impact particles
            ((ServerWorld)getWorld()).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.STONE.getDefaultState()),
                    hitResult.getPos().getX(),
                    hitResult.getPos().getY(),
                    hitResult.getPos().getZ(),
                    12,
                    0.2,
                    0.2,
                    0.2,
                    0.01);

            // Remove entity
            getWorld().sendEntityStatus(this, (byte)3); // particle?
            this.remove(RemovalReason.KILLED);
        }
    }
}
