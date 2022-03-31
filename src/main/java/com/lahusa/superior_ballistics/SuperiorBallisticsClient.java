package com.lahusa.superior_ballistics;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.CloudParticle;
import net.minecraft.client.particle.ExplosionSmokeParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class SuperiorBallisticsClient implements ClientModInitializer {

    public static final Identifier PacketID = new Identifier(SuperiorBallisticsMod.MODID, "spawn_packet");

    @Override
    public void onInitializeClient() {
        // EntityRenderer
        EntityRendererRegistry.INSTANCE.register(SuperiorBallisticsMod.STONE_BULLET_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SuperiorBallisticsMod.CANNONBALL_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        receiveEntityPacket();

        // Particles
        ParticleFactoryRegistry.getInstance().register(SuperiorBallisticsMod.CANNON_MUZZLE_FIRE, CannonMuzzleFireParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SuperiorBallisticsMod.CANNON_MUZZLE_SMOKE_TRAIL, CannonMuzzleSmokeTrailParticle.Factory::new);
    }

    public void receiveEntityPacket() {
        ClientSidePacketRegistry.INSTANCE.register(PacketID, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }
}
