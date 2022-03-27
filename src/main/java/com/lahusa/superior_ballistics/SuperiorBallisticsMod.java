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
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

public class SuperiorBallisticsMod implements ModInitializer {

	public static final String MODID = "superior_ballistics";

	// Blocks
	public static final Block SPRUCE_CANNON_BLOCK = new SpruceCannonBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));

	// BlockEntities
	public static BlockEntityType<SpruceCannonBlockEntity> SPRUCE_CANNON_BLOCK_ENTITY;

	// Items
	public static final Item SPRUCE_CANNON_ITEM = new BlockItem(SPRUCE_CANNON_BLOCK, new FabricItemSettings().group(ItemGroup.COMBAT));
	public static final Item STONE_BULLETS_ITEM = new StoneBulletsItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(16));
	public static final Item STONE_BULLET_ITEM = new StoneBulletItem(new FabricItemSettings());
	public static final Item FLINTLOCK_PISTOL_ITEM = new FlintlockPistolItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(450));
	public static final Item FLINTLOCK_MUSKET_ITEM = new FlintlockMusketItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(750));
	public static final Item GUNPOWDER_POUCH_ITEM = new GunpowderPouchItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(512));

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
		SPRUCE_CANNON_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "spruce_cannon_block_entity"), FabricBlockEntityTypeBuilder.create(SpruceCannonBlockEntity::new, SPRUCE_CANNON_BLOCK).build(null));

		// Items
		Registry.register(Registry.ITEM, new Identifier(MODID, "spruce_cannon"), SPRUCE_CANNON_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "stone_bullets"), STONE_BULLETS_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "stone_bullet"), STONE_BULLET_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "flintlock_pistol"), FLINTLOCK_PISTOL_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "flintlock_musket"), FLINTLOCK_MUSKET_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "gunpowder_pouch"), GUNPOWDER_POUCH_ITEM);

		System.out.println("Superior Ballistics Init: Done");
	}
}
