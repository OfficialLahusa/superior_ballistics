package com.lahusa.superior_ballistics;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

public class StoneBulletEntity extends ThrownItemEntity {

    public StoneBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public StoneBulletEntity(World world, LivingEntity owner) {
        super(SuperiorBallisticsMod.STONE_BULLET_ENTITY_TYPE, owner, world); // null will be changed later
    }

    public StoneBulletEntity(World world, double x, double y, double z) {
        super(SuperiorBallisticsMod.STONE_BULLET_ENTITY_TYPE, x, y, z, world); // null will be changed later
    }

    @Override
    protected Item getDefaultItem() {
        return SuperiorBallisticsMod.STONE_BULLET_ITEM;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)13); // deals damage

        if (entity instanceof LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 2))); // applies a status effect
        }
    }

    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            // Determine hit block and break it, if it's contained in the tags
            BlockPos hitBlock = new BlockPos(hitResult.getPos().add(getVelocity().normalize().multiply(0.5)));
            Tag<Block> breakableTag = MinecraftClient.getInstance().getNetworkHandler().getTagManager().getOrCreateTagGroup(Registry.BLOCK_KEY).getTag(new Identifier(SuperiorBallisticsMod.MODID, "stone_bullet_breakable"));
            Tag<Block> flowerPotTag = MinecraftClient.getInstance().getNetworkHandler().getTagManager().getOrCreateTagGroup(Registry.BLOCK_KEY).getTag(new Identifier("minecraft", "flower_pots"));
            if(breakableTag.contains(this.world.getBlockState(hitBlock).getBlock()) || flowerPotTag.contains(this.world.getBlockState(hitBlock).getBlock()))
            {
                this.world.breakBlock(hitBlock, false, this.getOwner(), 400);
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
        return EntitySpawnPacket.create(this, SuperiorBallisticsClient.PacketID);
    }
}
