package com.lahusa.superior_ballistics.item;

import com.lahusa.superior_ballistics.item.renderer.CannonBlockItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.BlockItem;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CannonBlockItem extends BlockItem implements GeoItem, GeoAnimatable {

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public CannonBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.animated_cannon.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private CannonBlockItemRenderer renderer;
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if(this.renderer == null) this.renderer = new CannonBlockItemRenderer();
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }
}
