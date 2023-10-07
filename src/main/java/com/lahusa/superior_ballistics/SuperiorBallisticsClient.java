package com.lahusa.superior_ballistics;

import com.lahusa.superior_ballistics.block.renderer.CannonBlockRenderer;
import com.lahusa.superior_ballistics.block.renderer.GunpowderKegBlockRenderer;
import com.lahusa.superior_ballistics.item.FlintlockBlunderbussItem;
import com.lahusa.superior_ballistics.item.FlintlockMusketItem;
import com.lahusa.superior_ballistics.item.FlintlockPistolItem;
import com.lahusa.superior_ballistics.particle.CannonMuzzleFireParticle;
import com.lahusa.superior_ballistics.particle.CannonMuzzleSmokeTrailParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class SuperiorBallisticsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // EntityRenderers
        EntityRendererRegistry.INSTANCE.register(SuperiorBallisticsMod.STONE_BULLET_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SuperiorBallisticsMod.CANNONBALL_ENTITY_TYPE, FlyingItemEntityRenderer::new);

        // BlockEntityRenderers
        BlockEntityRendererRegistry.register(SuperiorBallisticsMod.CANNON_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new CannonBlockRenderer());
        BlockEntityRendererRegistry.register(SuperiorBallisticsMod.GUNPOWDER_KEG_BLOCK_ENTITY, (BlockEntityRendererFactory.Context renderDispatcherIn) -> new GunpowderKegBlockRenderer());

        // ModelPredicateProviders
        ClampedModelPredicateProvider firstPersonModelPredicateProvider = (itemStack, clientWorld, livingEntity, seed) -> (MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) ? 1.0f : 0.0f;

        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_PISTOL_ITEM, new Identifier("first_person"), firstPersonModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_MUSKET_ITEM, new Identifier("first_person"), firstPersonModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_MUSKET_BAYONET_ITEM, new Identifier("first_person"), firstPersonModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_BLUNDERBUSS_ITEM, new Identifier("first_person"), firstPersonModelPredicateProvider);

        ClampedModelPredicateProvider usingItemModelPredicateProvider = (itemStack, clientWorld, livingEntity, seed) -> ((livingEntity != null && livingEntity.isUsingItem()) ? 1.0F : 0.0F);

        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_PISTOL_ITEM, new Identifier("using_item"), usingItemModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_MUSKET_ITEM, new Identifier("using_item"), usingItemModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_MUSKET_BAYONET_ITEM, new Identifier("using_item"), usingItemModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_BLUNDERBUSS_ITEM, new Identifier("using_item"), usingItemModelPredicateProvider);

        ClampedModelPredicateProvider musketPullModelPredicateProvider = (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) return 0.0F;
            float pullProgress = FlintlockMusketItem.getPullProgress(itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft());
            return (livingEntity.getActiveItem() == itemStack && pullProgress >= FlintlockMusketItem.REQUIRED_PULL_PROGRESS) ? 1.0f : 0.0f;
        };

        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_MUSKET_ITEM, new Identifier("pull"), musketPullModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_MUSKET_BAYONET_ITEM, new Identifier("pull"), musketPullModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_PISTOL_ITEM, new Identifier("pull"),
                (itemStack, clientWorld, livingEntity, seed) -> {
                    if (livingEntity == null) return 0.0F;
                    float pullProgress = FlintlockPistolItem.getPullProgress(itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft());
                    return (livingEntity.getActiveItem() == itemStack && pullProgress >= FlintlockPistolItem.REQUIRED_PULL_PROGRESS) ? 1.0f : 0.0f;
                }
        );
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_BLUNDERBUSS_ITEM, new Identifier("pull"),
                (itemStack, clientWorld, livingEntity, seed) -> {
                    if (livingEntity == null) return 0.0F;
                    float pullProgress = FlintlockBlunderbussItem.getPullProgress(itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft());
                    return (livingEntity.getActiveItem() == itemStack && pullProgress >= FlintlockBlunderbussItem.REQUIRED_PULL_PROGRESS) ? 1.0f : 0.0f;
                }
        );

        ClampedModelPredicateProvider chargedModelPredicateProvider = (itemStack, clientWorld, livingEntity, seed) -> (FlintlockMusketItem.isCharged(itemStack)) ? 1.0f : 0.0f;

        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_MUSKET_ITEM, new Identifier("charged"), chargedModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_MUSKET_BAYONET_ITEM, new Identifier("charged"), chargedModelPredicateProvider);
        ModelPredicateProviderRegistry.register(SuperiorBallisticsMod.FLINTLOCK_BLUNDERBUSS_ITEM, new Identifier("charged"), chargedModelPredicateProvider);

        // Particles
        ParticleFactoryRegistry.getInstance().register(SuperiorBallisticsMod.CANNON_MUZZLE_FIRE, CannonMuzzleFireParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SuperiorBallisticsMod.CANNON_MUZZLE_SMOKE_TRAIL, CannonMuzzleSmokeTrailParticle.Factory::new);

        // Item tooltips
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if(stack.isOf(Items.CLOCK)) {
                lines.add(Text.translatable("item.superior_ballistics.clock.tooltip.line1").formatted(Formatting.GRAY));
                lines.add(Text.translatable("item.superior_ballistics.clock.tooltip.line2").formatted(Formatting.GRAY));
            }
        });
    }
}
