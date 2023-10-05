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

    private boolean canOverchargeCannons = true;

    public ModConfig() {}

    public static ModConfig load() {
        ModConfig config = new ModConfig();

        // create new config file, if none exists
        if(!CONFIG_FILE.exists()) {
            save(config);
        }

        try {
            Reader reader = Files.newBufferedReader(CONFIG_FILE.toPath());
            config = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, ModConfig.class);
        } catch (IOException e) {
            SuperiorBallisticsMod.LOGGER.error("Error while loading config file. Reverting to default.", e);
        }

        return config;
    }

    public static void save(ModConfig config) {
        try {
            Writer writer = Files.newBufferedWriter(CONFIG_FILE.toPath());
            new GsonBuilder().setPrettyPrinting().create().toJson(config, writer);

            writer.close();
        } catch (IOException e) {
            SuperiorBallisticsMod.LOGGER.error("Error while saving config file.", e);
        }
    }

    public boolean getCanOverchargeCannons() {
        return canOverchargeCannons;
    }

    public void setCanOverchargeCannons(boolean canOverchargeCannons) {
        this.canOverchargeCannons = canOverchargeCannons;
    }
}
