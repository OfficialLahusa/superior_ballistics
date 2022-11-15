package com.lahusa.superior_ballistics.block.entity;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import com.lahusa.superior_ballistics.block.GunpowderKegBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GunpowderKegBlockEntity extends BlockEntity implements IAnimatable {

    private short powderAmount = 0;
    private boolean closed = false;
    public static final short MAX_POWDER_AMOUNT = 128;
    private final AnimationFactory factory = new AnimationFactory(this);
    public GunpowderKegBlockEntity(BlockPos pos, BlockState state) {
        super(SuperiorBallisticsMod.GUNPOWDER_KEG_ENTITY, pos, state);
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gunpowder_keg.idle", true));
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

    public Text getStatusText() {
        MutableText text = new LiteralText("[").formatted(Formatting.GRAY);
        text.append(new TranslatableText("superior_ballistics.gunpowder_keg.powder_amount_status_text").formatted(Formatting.DARK_GREEN));
        text.append(new LiteralText(" (" + getPowderAmount() + "/" + GunpowderKegBlockEntity.MAX_POWDER_AMOUNT + ")").formatted(Formatting.GOLD));
        text.append(new LiteralText("]").formatted(Formatting.GRAY));
        return text;
    }

    public short getPowderAmount() {
        return powderAmount;
    }

    public void setPowderAmount(short amount) {
        powderAmount = (short) Math.max(0,Math.min(amount, MAX_POWDER_AMOUNT));
        sync();
    }

    public boolean getClosed() {
        return closed;
    }

    public void setClosed(boolean value) {
        closed = value;
        sync();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putShort("powderAmount", powderAmount);
        nbt.putBoolean("closed", closed);

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        closed = nbt.getBoolean("closed");
        powderAmount = nbt.getShort("powderAmount");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    private void sync() {
        World world = getWorld();
        if(world != null) getWorld().updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
    }

}
