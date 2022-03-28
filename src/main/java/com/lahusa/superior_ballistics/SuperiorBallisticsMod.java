package com.lahusa.superior_ballistics;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SuperiorBallisticsMod implements ModInitializer {

	public static final String MODID = "superior_ballistics";

	// Blocks
	public static final Block SPRUCE_CANNON_BLOCK = new CannonBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));

	// BlockEntities
	public static BlockEntityType<CannonBlockEntity> SPRUCE_CANNON_BLOCK_ENTITY;

	// Items
	public static final Item SPRUCE_CANNON_ITEM = new BlockItem(SPRUCE_CANNON_BLOCK, new FabricItemSettings().group(ItemGroup.COMBAT));
	public static final Item STONE_BULLETS_ITEM = new StoneBulletsItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(16));
	public static final Item STONE_BULLET_ITEM = new StoneBulletItem(new FabricItemSettings());
	public static final Item FLINTLOCK_PISTOL_ITEM = new FlintlockPistolItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(450));
	public static final Item FLINTLOCK_MUSKET_ITEM = new FlintlockMusketItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(750));
	public static final Item GUNPOWDER_POUCH_ITEM = new GunpowderPouchItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(512));
	public static final Item SPONGE_ON_A_STICK_ITEM = new SpongeOnAStickItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(120));
	public static final Item WET_SPONGE_ON_A_STICK_ITEM = new WetSpongeOnAStickItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(120));
	public static final Item PISTON_LOADER_ITEM = new PistonLoaderItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(120));

	// Entities
	public static final EntityType<StoneBulletEntity> STONE_BULLET_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MODID, "stone_bullet"),
			FabricEntityTypeBuilder.<StoneBulletEntity>create(SpawnGroup.MISC, StoneBulletEntity::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build()
	);

	@Override
	public void onInitialize() {
		System.out.println("Superior Ballistics Init: Starting");

		// Blocks
		Registry.register(Registry.BLOCK, new Identifier(MODID, "spruce_cannon"), SPRUCE_CANNON_BLOCK);

		// BlockEntities
		SPRUCE_CANNON_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "spruce_cannon_block_entity"), FabricBlockEntityTypeBuilder.create(CannonBlockEntity::new, SPRUCE_CANNON_BLOCK).build(null));

		// Items
		Registry.register(Registry.ITEM, new Identifier(MODID, "spruce_cannon"), SPRUCE_CANNON_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "stone_bullets"), STONE_BULLETS_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "stone_bullet"), STONE_BULLET_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "flintlock_pistol"), FLINTLOCK_PISTOL_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "flintlock_musket"), FLINTLOCK_MUSKET_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "gunpowder_pouch"), GUNPOWDER_POUCH_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "sponge_on_a_stick"), SPONGE_ON_A_STICK_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "wet_sponge_on_a_stick"), WET_SPONGE_ON_A_STICK_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "piston_loader"), PISTON_LOADER_ITEM);

		System.out.println("Superior Ballistics Init: Done");
	}
}
