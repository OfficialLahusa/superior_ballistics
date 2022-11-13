package com.lahusa.superior_ballistics.block.entity;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GunpowderKegBlockEntity extends BlockEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public GunpowderKegBlockEntity(BlockPos pos, BlockState state) {
        super(SuperiorBallisticsMod.GUNPOWDER_KEG_ENTITY, pos, state);
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gunpowder_keg.gunpowder_amount", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
         return this.factory;
    }

}
