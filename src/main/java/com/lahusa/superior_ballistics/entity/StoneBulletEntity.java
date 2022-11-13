package com.lahusa.superior_ballistics.entity;

import com.lahusa.superior_ballistics.entity.damage.BulletDamageSource;
import com.lahusa.superior_ballistics.mixin.LivingEntityAccessor;
import com.lahusa.superior_ballistics.net.EntitySpawnPacket;
import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.particle.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StoneBulletEntity extends ThrownItemEntity {

    public static final TagKey<Block> STONE_BULLET_BREAKABLE_TAG = TagKey.of(Registry.BLOCK_KEY, new Identifier(SuperiorBallisticsMod.MODID, "stone_bullet_breakable"));
    public static final TagKey<Block> FLOWER_POT_TAG = TagKey.of(Registry.BLOCK_KEY, new Identifier("minecraft", "flower_pots"));

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

        DamageSource source =  (new BulletDamageSource("thrown", this, this.getOwner())).setProjectile();

        // Reset damage cooldown
        if(entity instanceof LivingEntity livingEntity) {
            ((LivingEntityAccessor)livingEntity).setLastDamageTaken(Float.MIN_VALUE);
        }

        entity.damage(source, (float)damage); // deals damage


        if (statusEffect != null && entity instanceof LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(statusEffect, 20 * 3, 1))); // applies a status effect
        }
    }

    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            // Determine hit block and break it, if it's contained in the tags
            BlockPos hitBlockPos = new BlockPos(hitResult.getPos().add(getVelocity().normalize().multiply(0.5)));

            Block hitBlock = this.world.getBlockState(hitBlockPos).getBlock();
            RegistryEntry.Reference<Block> hitBlockRegEntry = hitBlock.getRegistryEntry();
            if(hitBlockRegEntry.isIn(STONE_BULLET_BREAKABLE_TAG) || hitBlockRegEntry.isIn(FLOWER_POT_TAG))
            {
                this.world.breakBlock(hitBlockPos, false, this.getOwner(), 400);
            }

            // Spawn impact particles
            ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.STONE.getDefaultState()),
                    hitResult.getPos().getX(),
                    hitResult.getPos().getY(),
                    hitResult.getPos().getZ(),
                    12,
                    0.2,
                    0.2,
                    0.2,
                    0.01);

            // Remove entity
            this.world.sendEntityStatus(this, (byte)3); // particle?
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public Packet createSpawnPacket() {
        return EntitySpawnPacket.create(this, SuperiorBallisticsMod.PacketID);
    }
}
