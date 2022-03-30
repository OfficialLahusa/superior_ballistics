package com.lahusa.superior_ballistics;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class CannonMuzzleSmokeTrailParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    protected CannonMuzzleSmokeTrailParticle(ClientWorld world, double x, double y, double z, double d, double e, double f, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.gravityStrength = -0.05F;
        this.field_28786 = 0.9F;
        this.spriteProvider = spriteProvider;
        this.velocityX = d + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
        this.velocityY = e + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
        this.velocityZ = f + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
        float g = this.random.nextFloat() * 0.3F + 0.7F;
        this.colorRed = g;
        this.colorGreen = g;
        this.colorBlue = g;
        this.scale = 0.1F * (this.random.nextFloat() * this.random.nextFloat() * 6.0F + 1.0F);
        this.maxAge = (int)(3.5*((16.0D / ((double)this.random.nextFloat() * 0.8D + 0.2D)) + 2.0));
        this.setSpriteForAge(spriteProvider);
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.velocityY -= 0.04D * (double)this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if (this.field_28787 && this.y == this.prevPosY) {
                this.velocityX *= 1.1D;
                this.velocityZ *= 1.1D;
            }

            this.velocityX *= (double)this.field_28786;
            this.velocityY *= (double)this.field_28786;
            this.velocityZ *= (double)this.field_28786;
            if (this.onGround) {
                this.velocityX *= 0.699999988079071D;
                this.velocityZ *= 0.699999988079071D;
            }

        }

        this.setSpriteForAge(this.spriteProvider);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new CannonMuzzleSmokeTrailParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
