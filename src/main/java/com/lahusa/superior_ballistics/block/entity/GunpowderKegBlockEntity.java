package com.lahusa.superior_ballistics.block.entity;

import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GunpowderKegBlockEntity extends BlockEntity implements GeoBlockEntity, IStatusTextProvider {

    private short powderAmount = 0;
    private boolean closed = false;
    public static final short MAX_POWDER_AMOUNT = 128;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public GunpowderKegBlockEntity(BlockPos pos, BlockState state) {
        super(SuperiorBallisticsMod.GUNPOWDER_KEG_BLOCK_ENTITY, pos, state);
    }
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.gunpowder_keg.idle"));
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
    public Text getStatusText(PlayerEntity player) {
        MutableText text = Text.literal("[").formatted(Formatting.GRAY);
        text.append(Text.translatable("superior_ballistics.gunpowder_keg.powder_amount_status_text").formatted(Formatting.DARK_GREEN));
        text.append(Text.literal(" (" + getPowderAmount() + "/" + GunpowderKegBlockEntity.MAX_POWDER_AMOUNT + ")").formatted(Formatting.GOLD));
        text.append(Text.literal("]").formatted(Formatting.GRAY));
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
