package org.derbeukatt.underwatercraft.common.entities;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

import org.derbeukatt.underwatercraft.common.entities.hostile.EntityOctopus;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Entities {

	public static void init() {
		final int orangeColor = (255 << 16) + (200 << 8);
		final int redColor = (255 << 16);
		//
		// EntityRegistry.registerModEntity(EntityOctopus.class,
		// "EntityOctopus",
		// 0, UnderWaterCraft.instance, 80, 3, true);
		//
		// final int id = getUniqueID();
		//
		// EntityList.IDtoClassMapping.put(id, EntityOctopus.class);
		// EntityList.entityEggs.put(id, new EntityEggInfo(id, redColor,
		// orangeColor));

		EntityRegistry.registerGlobalEntityID(EntityOctopus.class,
				"EntityOctopus", EntityRegistry.findGlobalUniqueEntityId(),
				redColor, orangeColor);
		System.out.println("AHHHH OCTOPUS");
		EntityRegistry.addSpawn(EntityOctopus.class, 8, 2, 5,
				EnumCreatureType.waterCreature, BiomeGenBase.ocean,
				BiomeGenBase.swampland, BiomeGenBase.plains);

		LanguageRegistry.instance().addStringLocalization(
				"entity.UnderWaterCraft.EntityOctopus.name", "Entity Octopus");

	}

}
