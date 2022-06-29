package com.lahusa.superior_ballistics.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CannonMuzzleFireParticle extends SpriteBillboardParticle {
    CannonMuzzleFireParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
        this.field_28786 = 0.96F;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.x += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.y += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.z += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.maxAge = (int)(((8.0D / (Math.random() * 0.8D + 0.2D)) + 4.0) / 3.0);
        this.scale = 0.25f;
        this.gravityStrength = 2.0f;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            if((double)this.age / (double) this.maxAge > 0.3) {
                this.velocityY -= 0.04D * (double)this.gravityStrength;
            }
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
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    public float getSize(float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        //return this.scale * (1.0F - f * f * 0.5F);
        //return this.scale * (1.0f - (float)Math.pow(f, 4));
        //return this.scale * (1.0f - (float)Math.pow(f, 3) + 0.2f * (float)Math.pow(f, 4));
        f = 1.0f-f;
        return this.scale * (f < 0.5f ? 8.0f * f * f * f * f : 1.0f - (float)Math.pow(-2.0f * f + 2.0f, 4.0f) / 2.0f);
    }

    public int getBrightness(float tint) {
        float f = ((float)this.age + tint) / (float)this.maxAge;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getBrightness(tint);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @Environment(EnvType.CLIENT)
    public static class SmallFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public SmallFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            CannonMuzzleFireParticle flameParticle = new CannonMuzzleFireParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
            flameParticle.setSprite(this.spriteProvider);
            flameParticle.scale(0.5F);
            return flameParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            CannonMuzzleFireParticle flameParticle = new CannonMuzzleFireParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
            flameParticle.setSprite(this.spriteProvider);
            return flameParticle;
        }
    }
}
