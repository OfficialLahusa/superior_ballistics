package com.lahusa.superior_ballistics.entity;

import com.lahusa.superior_ballistics.net.EntitySpawnPacket;
import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class CannonBallEntity extends ThrownItemEntity {

    public CannonBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public CannonBallEntity(World world, LivingEntity owner) {
        super(SuperiorBallisticsMod.CANNONBALL_ENTITY_TYPE, owner, world);
    }
    public CannonBallEntity(World world, double x, double y, double z) {
        super(SuperiorBallisticsMod.CANNONBALL_ENTITY_TYPE, x, y, z, world);
    }

    public void tick() {
        super.tick();
        if(world.isClient) spawnTrailParticles();
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    // Called on entity hit
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)13); // deals damage

        if (entity instanceof LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 2))); // applies a status effect
        }
    }

    // Called on block collision
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {

            Vec3d hitPos = hitResult.getPos();

            this.world.createExplosion(this.getOwner(), hitPos.x, hitPos.y, hitPos.z, 5.0f, false, Explosion.DestructionType.DESTROY);

            // Spawn impact particles
            ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.BLACKSTONE.getDefaultState()),
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

    private void spawnTrailParticles() {
        world.addParticle(ParticleTypes.FLAME, getX(), getY(), getZ(), 0, 0, 0);
        world.addParticle(ParticleTypes.LARGE_SMOKE, getX(), getY(), getZ(), 0, 0, 0);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, SuperiorBallisticsMod.PacketID);
    }
}
