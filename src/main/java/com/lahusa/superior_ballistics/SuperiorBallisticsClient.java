package com.lahusa.superior_ballistics;

import com.lahusa.superior_ballistics.block.renderer.CannonBlockRenderer;
import com.lahusa.superior_ballistics.item.renderer.CannonBlockItemRenderer;
import com.lahusa.superior_ballistics.item.renderer.PickelhaubeArmorRenderer;
import com.lahusa.superior_ballistics.item.renderer.TschakoArmorRenderer;
import com.lahusa.superior_ballistics.net.EntitySpawnPacket;
import com.lahusa.superior_ballistics.particle.CannonMuzzleFireParticle;
import com.lahusa.superior_ballistics.particle.CannonMuzzleSmokeTrailParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

import java.util.UUID;

public class SuperiorBallisticsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // EntityRenderers
        EntityRendererRegistry.INSTANCE.register(SuperiorBallisticsMod.STONE_BULLET_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SuperiorBallisticsMod.CANNONBALL_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        receiveEntityPacket();

        // BlockEntityRenderers
        BlockEntityRendererRegistry.register(SuperiorBallisticsMod.CANNON_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new CannonBlockRenderer());

        // ItemRenderers
        GeoItemRenderer.registerItemRenderer(SuperiorBallisticsMod.OAK_CANNON_ITEM, new CannonBlockItemRenderer());
        GeoItemRenderer.registerItemRenderer(SuperiorBallisticsMod.SPRUCE_CANNON_ITEM, new CannonBlockItemRenderer());
        GeoItemRenderer.registerItemRenderer(SuperiorBallisticsMod.BIRCH_CANNON_ITEM, new CannonBlockItemRenderer());
        GeoItemRenderer.registerItemRenderer(SuperiorBallisticsMod.JUNGLE_CANNON_ITEM, new CannonBlockItemRenderer());
        GeoItemRenderer.registerItemRenderer(SuperiorBallisticsMod.ACACIA_CANNON_ITEM, new CannonBlockItemRenderer());
        GeoItemRenderer.registerItemRenderer(SuperiorBallisticsMod.DARK_OAK_CANNON_ITEM, new CannonBlockItemRenderer());
        GeoItemRenderer.registerItemRenderer(SuperiorBallisticsMod.CRIMSON_CANNON_ITEM, new CannonBlockItemRenderer());
        GeoItemRenderer.registerItemRenderer(SuperiorBallisticsMod.WARPED_CANNON_ITEM, new CannonBlockItemRenderer());

        // ArmorRenderers
        GeoArmorRenderer.registerArmorRenderer(new PickelhaubeArmorRenderer(), SuperiorBallisticsMod.PICKELHAUBE_HELMET);
        GeoArmorRenderer.registerArmorRenderer(new TschakoArmorRenderer(), SuperiorBallisticsMod.TSCHAKO_HELMET);

        // Particles
        ParticleFactoryRegistry.getInstance().register(SuperiorBallisticsMod.CANNON_MUZZLE_FIRE, CannonMuzzleFireParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SuperiorBallisticsMod.CANNON_MUZZLE_SMOKE_TRAIL, CannonMuzzleSmokeTrailParticle.Factory::new);
    }

    public void receiveEntityPacket() {
        ClientSidePacketRegistry.INSTANCE.register(SuperiorBallisticsMod.PacketID, (ctx, byteBuf) -> {
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
