package com.lahusa.superior_ballistics.config;

import com.google.gson.GsonBuilder;
import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;

public final class ModConfig {
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), SuperiorBallisticsMod.MODID + ".json");

    // Cannon properties
    private boolean cannonOverchargingAllowed = true;
    private short cannonMaxLitTicks = 40;
    private float cannonShotSpeed = 1.0f;
    private float cannonShotDivergence = 3.0f;
    private float cannonGrapeShotDivergence = 4.5f;

    // Pistol properties
    private float pistolShotSpeed = 3.0f;
    private float pistolShotDivergence = 5.2f;
    private int pistolShotDamage = 8;

    // Musket properties
    private float musketShotSpeed = 3.0f;
    private float musketShotDivergence = 3.0f;
    private int musketShotDamage = 13;
    private int musketBayonetDamage = 6;
    private float musketBayonetSpeed = 1.2f;

    // Blunderbuss properties
    private int blunderbussShotCount = 8;
    private float blunderbussShotSpeed = 1.6f;
    private float blunderbussShotDivergence = 10.0f;
    private int blunderbussShotDamage = 3;

    public ModConfig() {}

    public static ModConfig load() {
        // Create new config, in case none exists
        ModConfig config = new ModConfig();

        // Load config file, if it exists
        if(CONFIG_FILE.exists()) {
            try {
                Reader reader = Files.newBufferedReader(CONFIG_FILE.toPath());
                config = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, ModConfig.class);
            } catch (IOException e) {
                SuperiorBallisticsMod.LOGGER.error("Error while loading config file. Reverting to default.", e);
            }
        }

        // Validate fields
        config.validate();
        // Save validated config to ensure a complete file exists
        save(config);

        return config;
    }

    public static void save(ModConfig config) {
        config.validate();

        try {
            Writer writer = Files.newBufferedWriter(CONFIG_FILE.toPath());
            new GsonBuilder().setPrettyPrinting().create().toJson(config, writer);

            writer.close();
        } catch (IOException e) {
            SuperiorBallisticsMod.LOGGER.error("Error while saving config file.", e);
        }
    }

    public void validate() {
        // Enforce validity of all fields by feeding the values through the setters, which ensure validity
        // Cannon properties
        setCannonOverchargingAllowed(this.cannonOverchargingAllowed);
        setCannonMaxLitTicks(this.cannonMaxLitTicks);
        setCannonShotSpeed(this.cannonShotSpeed);
        setCannonShotDivergence(this.cannonShotDivergence);
        setCannonGrapeShotDivergence(this.cannonGrapeShotDivergence);

        // Pistol properties
        setPistolShotSpeed(this.pistolShotSpeed);
        setPistolShotDivergence(this.pistolShotDivergence);
        setPistolShotDamage(this.pistolShotDamage);

        // Musket properties
        setMusketShotSpeed(this.musketShotSpeed);
        setMusketShotDivergence(this.musketShotDivergence);
        setMusketShotDamage(this.musketShotDamage);
        setMusketBayonetDamage(this.musketBayonetDamage);
        setMusketBayonetSpeed(this.musketBayonetSpeed);

        // Blunderbuss properties
        setBlunderbussShotCount(this.blunderbussShotCount);
        setBlunderbussShotSpeed(this.blunderbussShotSpeed);
        setBlunderbussShotDivergence(this.blunderbussShotDivergence);
        setBlunderbussShotDamage(this.blunderbussShotDamage);
    }

    // Cannon properties
    public boolean isCannonOverchargingAllowed() {
        return cannonOverchargingAllowed;
    }

    public void setCannonOverchargingAllowed(boolean cannonOverchargingAllowed) {
        this.cannonOverchargingAllowed = cannonOverchargingAllowed;
    }

    public short getCannonMaxLitTicks() {
        return cannonMaxLitTicks;
    }

    public void setCannonMaxLitTicks(short cannonMaxLitTicks) {
        this.cannonMaxLitTicks = (short)Math.max(1, cannonMaxLitTicks);
    }

    public float getCannonShotSpeed() {
        return cannonShotSpeed;
    }

    public void setCannonShotSpeed(float cannonShotSpeed) {
        this.cannonShotSpeed = Math.max(0.1f, cannonShotSpeed);
    }

    public float getCannonShotDivergence() {
        return cannonShotDivergence;
    }

    public void setCannonShotDivergence(float cannonShotDivergence) {
        this.cannonShotDivergence = Math.max(0.f, cannonShotDivergence);
    }

    public float getCannonGrapeShotDivergence() {
        return cannonGrapeShotDivergence;
    }

    public void setCannonGrapeShotDivergence(float cannonGrapeShotDivergence) {
        this.cannonGrapeShotDivergence = Math.max(0.f, cannonGrapeShotDivergence);
    }

    // Pistol properties
    public float getPistolShotSpeed() {
        return pistolShotSpeed;
    }

    public void setPistolShotSpeed(float pistolShotSpeed) {
        this.pistolShotSpeed = Math.max(0.1f, pistolShotSpeed);
    }

    public float getPistolShotDivergence() {
        return pistolShotDivergence;
    }

    public void setPistolShotDivergence(float pistolShotDivergence) {
        this.pistolShotDivergence = Math.max(0.f, pistolShotDivergence);
    }

    public int getPistolShotDamage() {
        return pistolShotDamage;
    }

    public void setPistolShotDamage(int pistolShotDamage) {
        this.pistolShotDamage = Math.max(1, pistolShotDamage);
    }

    // Musket properties
    public float getMusketShotSpeed() {
        return musketShotSpeed;
    }

    public void setMusketShotSpeed(float musketShotSpeed) {
        this.musketShotSpeed = Math.max(0.1f, musketShotSpeed);
    }

    public float getMusketShotDivergence() {
        return musketShotDivergence;
    }

    public void setMusketShotDivergence(float musketShotDivergence) {
        this.musketShotDivergence = Math.max(0.f, musketShotDivergence);
    }

    public int getMusketShotDamage() {
        return musketShotDamage;
    }

    public void setMusketShotDamage(int musketShotDamage) {
        this.musketShotDamage = Math.max(1, musketShotDamage);
    }

    public int getMusketBayonetDamage() {
        return musketBayonetDamage;
    }

    public void setMusketBayonetDamage(int musketBayonetDamage) {
        this.musketBayonetDamage = Math.max(1, musketBayonetDamage);
    }

    public float getMusketBayonetSpeed() {
        return musketBayonetSpeed;
    }

    public void setMusketBayonetSpeed(float musketBayonetSpeed) {
        this.musketBayonetSpeed = Math.max(0.1f, musketBayonetSpeed);
    }

    // Blunderbuss properties
    public int getBlunderbussShotCount() {
        return blunderbussShotCount;
    }

    public void setBlunderbussShotCount(int blunderbussShotCount) {
        this.blunderbussShotCount = clamp(blunderbussShotCount, 1, 25);
    }

    public float getBlunderbussShotSpeed() {
        return blunderbussShotSpeed;
    }

    public void setBlunderbussShotSpeed(float blunderbussShotSpeed) {
        this.blunderbussShotSpeed = Math.max(0.1f, blunderbussShotSpeed);
    }

    public float getBlunderbussShotDivergence() {
        return blunderbussShotDivergence;
    }

    public void setBlunderbussShotDivergence(float blunderbussShotDivergence) {
        this.blunderbussShotDivergence = Math.max(0.f, blunderbussShotDivergence);
    }

    public int getBlunderbussShotDamage() {
        return blunderbussShotDamage;
    }

    public void setBlunderbussShotDamage(int blunderbussShotDamage) {
        this.blunderbussShotDamage = Math.max(1, blunderbussShotDamage);
    }

    // Utility Functions
    private int clamp(int value, int min, int max) {
        return Math.min(max, Math.max(value, min));
    }

    private float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }

    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(value, min));
    }
}
