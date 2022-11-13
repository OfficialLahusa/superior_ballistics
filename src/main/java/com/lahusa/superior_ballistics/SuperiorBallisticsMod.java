package com.lahusa.superior_ballistics;

import com.lahusa.superior_ballistics.advancement.criterion.CannonOverchargeCriterion;
import com.lahusa.superior_ballistics.block.CannonBlock;
import com.lahusa.superior_ballistics.block.entity.CannonBlockEntity;
import com.lahusa.superior_ballistics.entity.CannonBallEntity;
import com.lahusa.superior_ballistics.entity.StoneBulletEntity;
import com.lahusa.superior_ballistics.item.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import software.bernie.example.GeckoLibMod;

public class SuperiorBallisticsMod implements ModInitializer {

	public static final String MODID = "superior_ballistics";

	// Blocks
	public static final float CANNON_STRENGTH = 2.0f;
	public static final float CANNON_HARDNESS = 14.0f;
	protected static final FabricBlockSettings cannonSettings = FabricBlockSettings.of(Material.WOOD).strength(CANNON_STRENGTH).hardness(CANNON_HARDNESS).requiresTool();

	public static final Block OAK_CANNON_BLOCK 		= new CannonBlock(new Identifier("minecraft", "oak_planks"), 		new Identifier("minecraft", "oak_log"), 		cannonSettings);
	public static final Block SPRUCE_CANNON_BLOCK 	= new CannonBlock(new Identifier("minecraft", "spruce_planks"), 		new Identifier("minecraft", "spruce_log"), 	cannonSettings);
	public static final Block BIRCH_CANNON_BLOCK 	= new CannonBlock(new Identifier("minecraft", "birch_planks"), 		new Identifier("minecraft", "birch_log"), 	cannonSettings);
	public static final Block JUNGLE_CANNON_BLOCK 	= new CannonBlock(new Identifier("minecraft", "jungle_planks"), 		new Identifier("minecraft", "jungle_log"), 	cannonSettings);
	public static final Block ACACIA_CANNON_BLOCK 	= new CannonBlock(new Identifier("minecraft", "acacia_planks"), 		new Identifier("minecraft", "acacia_log"), 	cannonSettings);
	public static final Block DARK_OAK_CANNON_BLOCK = new CannonBlock(new Identifier("minecraft", "dark_oak_planks"), 	new Identifier("minecraft", "dark_oak_log"), cannonSettings);
	public static final Block CRIMSON_CANNON_BLOCK 	= new CannonBlock(new Identifier("minecraft", "crimson_planks"), 	new Identifier(MODID, "crimson_stem"), 				cannonSettings);
	public static final Block WARPED_CANNON_BLOCK 	= new CannonBlock(new Identifier("minecraft", "warped_planks"), 		new Identifier(MODID, "warped_stem"), 					cannonSettings);

	// BlockEntities
	public static BlockEntityType<CannonBlockEntity> CANNON_BLOCK_ENTITY;

	// Items
	public static final Item OAK_CANNON_ITEM 		= new CannonBlockItem(OAK_CANNON_BLOCK, 		new FabricItemSettings().group(ItemGroup.COMBAT));
	public static final Item SPRUCE_CANNON_ITEM 	= new CannonBlockItem(SPRUCE_CANNON_BLOCK, 		new FabricItemSettings().group(ItemGroup.COMBAT));
	public static final Item BIRCH_CANNON_ITEM 		= new CannonBlockItem(BIRCH_CANNON_BLOCK, 		new FabricItemSettings().group(ItemGroup.COMBAT));
	public static final Item JUNGLE_CANNON_ITEM 	= new CannonBlockItem(JUNGLE_CANNON_BLOCK, 		new FabricItemSettings().group(ItemGroup.COMBAT));
	public static final Item ACACIA_CANNON_ITEM 	= new CannonBlockItem(ACACIA_CANNON_BLOCK, 		new FabricItemSettings().group(ItemGroup.COMBAT));
	public static final Item DARK_OAK_CANNON_ITEM 	= new CannonBlockItem(DARK_OAK_CANNON_BLOCK, 	new FabricItemSettings().group(ItemGroup.COMBAT));
	public static final Item CRIMSON_CANNON_ITEM 	= new CannonBlockItem(CRIMSON_CANNON_BLOCK, 	new FabricItemSettings().group(ItemGroup.COMBAT));
	public static final Item WARPED_CANNON_ITEM 	= new CannonBlockItem(WARPED_CANNON_BLOCK, 		new FabricItemSettings().group(ItemGroup.COMBAT));

	public static final Item STONE_BULLETS_ITEM 		= new StoneBulletsItem(new FabricItemSettings().group(ItemGroup.MISC));
	public static final Item STONE_BULLET_ITEM 			= new StoneBulletItem(new FabricItemSettings());
	public static final Item FLINTLOCK_PISTOL_ITEM 		= new FlintlockPistolItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(225));
	public static final Item FLINTLOCK_MUSKET_ITEM 		= new FlintlockMusketItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(600));
	public static final Item FLINTLOCK_BLUNDERBUSS_ITEM = new FlintlockBlunderbussItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(480));
	public static final Item SPONGE_ON_A_STICK_ITEM 	= new SpongeOnAStickItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(120));
	public static final Item WET_SPONGE_ON_A_STICK_ITEM = new WetSpongeOnAStickItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(120));
	public static final Item PISTON_LOADER_ITEM 		= new PistonLoaderItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(120));
	public static final Item IRON_CANNONBALL 			= new IronCannonballItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(16));
	public static final Item IRON_GRAPESHOT 			= new IronGrapeshotItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(16));
	public static final Item IRON_SINGLE_GRAPESHOT 		= new IronSingleGrapeshotItem(new FabricItemSettings());
	public static final Item CREATIVE_CANNON_MODULE		= new CreativeCannonModuleItem(new FabricItemSettings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT));

	//Armor
	public static final ArmorMaterial UNIFORM_ARMOR_MATERIAL = new UniformArmorMaterial();
	public static final Item PICKELHAUBE_HELMET 	= new PickelhaubeArmorItem(UNIFORM_ARMOR_MATERIAL, 		EquipmentSlot.HEAD,new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
	public static final Item SHAKO_HELMET 			= new ShakoArmorItem(UNIFORM_ARMOR_MATERIAL, 			EquipmentSlot.HEAD,new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
	public static final Item TRICORNE_HELMET 		= new TricorneArmorItem(UNIFORM_ARMOR_MATERIAL, 		EquipmentSlot.HEAD,new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
	public static final Item BICORNE_FRONT_HELMET 	= new BicorneFrontArmorItem(UNIFORM_ARMOR_MATERIAL, 	EquipmentSlot.HEAD,new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
	public static final Item BICORNE_SIDE_HELMET 	= new BicorneSideArmorItem(UNIFORM_ARMOR_MATERIAL, 		EquipmentSlot.HEAD,new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
	public static final Item PITH_HELMET 			= new PithHelmetArmorItem(UNIFORM_ARMOR_MATERIAL, 		EquipmentSlot.HEAD,new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));

	// Entities
	public static final EntityType<StoneBulletEntity> STONE_BULLET_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MODID, "stone_bullet"),
			FabricEntityTypeBuilder.<StoneBulletEntity>create(SpawnGroup.MISC, StoneBulletEntity::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
					.trackRangeBlocks(4).trackedUpdateRate(10)
					.build()
	);
	public static final EntityType<CannonBallEntity> CANNONBALL_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MODID, "cannonball"),
			FabricEntityTypeBuilder.<CannonBallEntity>create(SpawnGroup.MISC, CannonBallEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.trackRangeBlocks(4).trackedUpdateRate(10)
					.build()
	);

	// Entity spawn packet
	public static final Identifier PacketID = new Identifier(SuperiorBallisticsMod.MODID, "spawn_packet");

	// Particles
	public static final DefaultParticleType CANNON_MUZZLE_FIRE = FabricParticleTypes.simple();
	public static final DefaultParticleType CANNON_MUZZLE_SMOKE_TRAIL = FabricParticleTypes.simple();

	// TODO: Advancement criteria
	public static final CannonOverchargeCriterion CANNON_OVERCHARGE_CRITERION = Criteria.register(new CannonOverchargeCriterion());

	static {
		GeckoLibMod.DISABLE_IN_DEV = true;
	}

	@Override
	public void onInitialize() {
		System.out.println("Superior Ballistics Init: Starting");

		// Blocks
		Registry.register(Registry.BLOCK, new Identifier(MODID, "oak_cannon"), 		OAK_CANNON_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "spruce_cannon"), 		SPRUCE_CANNON_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "birch_cannon"), 		BIRCH_CANNON_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "jungle_cannon"), 		JUNGLE_CANNON_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "acacia_cannon"), 		ACACIA_CANNON_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "dark_oak_cannon"), 	DARK_OAK_CANNON_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "crimson_cannon"), 	CRIMSON_CANNON_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "warped_cannon"), 		WARPED_CANNON_BLOCK);

		// BlockEntities
		CANNON_BLOCK_ENTITY = Registry.register(
				Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "cannon_block_entity"),
				FabricBlockEntityTypeBuilder.create(
						CannonBlockEntity::new, OAK_CANNON_BLOCK, SPRUCE_CANNON_BLOCK, BIRCH_CANNON_BLOCK, JUNGLE_CANNON_BLOCK, ACACIA_CANNON_BLOCK, DARK_OAK_CANNON_BLOCK, CRIMSON_CANNON_BLOCK, WARPED_CANNON_BLOCK)
						.build(null)
		);

		// Items
		Registry.register(Registry.ITEM, new Identifier(MODID, "oak_cannon"), 		OAK_CANNON_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "spruce_cannon"), 	SPRUCE_CANNON_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "birch_cannon"),	BIRCH_CANNON_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "jungle_cannon"), 	JUNGLE_CANNON_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "acacia_cannon"), 	ACACIA_CANNON_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "dark_oak_cannon"), DARK_OAK_CANNON_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "crimson_cannon"), 	CRIMSON_CANNON_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "warped_cannon"), 	WARPED_CANNON_ITEM);

		Registry.register(Registry.ITEM, new Identifier(MODID, "stone_bullets"), 			STONE_BULLETS_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "stone_bullet"), 			STONE_BULLET_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "flintlock_pistol"), 		FLINTLOCK_PISTOL_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "flintlock_musket"), 		FLINTLOCK_MUSKET_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "flintlock_blunderbuss"), 	FLINTLOCK_BLUNDERBUSS_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "sponge_on_a_stick"), 		SPONGE_ON_A_STICK_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "wet_sponge_on_a_stick"), 	WET_SPONGE_ON_A_STICK_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "piston_loader"), 			PISTON_LOADER_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "iron_cannonball"), 		IRON_CANNONBALL);
		Registry.register(Registry.ITEM, new Identifier(MODID, "iron_grapeshot"), 			IRON_GRAPESHOT);
		Registry.register(Registry.ITEM, new Identifier(MODID, "iron_single_grapeshot"), 	IRON_SINGLE_GRAPESHOT);
		Registry.register(Registry.ITEM, new Identifier(MODID, "creative_cannon_module"),	CREATIVE_CANNON_MODULE);

		// Armor
		Registry.register(Registry.ITEM, new Identifier(MODID, "pickelhaube_helmet"), 			PICKELHAUBE_HELMET);
		Registry.register(Registry.ITEM, new Identifier(MODID, "shako_helmet"), 				SHAKO_HELMET);
		Registry.register(Registry.ITEM, new Identifier(MODID, "tricorne_helmet"), 			TRICORNE_HELMET);
		Registry.register(Registry.ITEM, new Identifier(MODID, "bicorne_front_helmet"), 		BICORNE_FRONT_HELMET);
		Registry.register(Registry.ITEM, new Identifier(MODID, "bicorne_side_helmet"), 		BICORNE_SIDE_HELMET);
		Registry.register(Registry.ITEM, new Identifier(MODID, "pith_helmet"), 				PITH_HELMET);

		// Particles
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MODID, "cannon_muzzle_fire"), 		CANNON_MUZZLE_FIRE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MODID, "cannon_muzzle_smoke_trail"), 	CANNON_MUZZLE_SMOKE_TRAIL);

		System.out.println("Superior Ballistics Init: Done");
	}
}
