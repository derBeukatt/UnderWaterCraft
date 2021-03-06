package org.derbeukatt.underwatercraft.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.derbeukatt.underwatercraft.common.blocks.BlockInfo;
import org.derbeukatt.underwatercraft.common.items.armor.ItemScaleBoots;
import org.derbeukatt.underwatercraft.common.items.armor.ItemScaleHarness;
import org.derbeukatt.underwatercraft.common.items.armor.ItemScaleHat;
import org.derbeukatt.underwatercraft.common.items.armor.ItemScaleLeggings;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Items {

	public static Item itemBlubberBottle;
	public static Item itemBlubberBucket;
	public static Item itemRainbowBlubberBottle;
	public static Item itemRainbowBlubberBucket;
	public static Item itemScale;
	public static Item itemScaleBoots;
	public static Item itemScaleHard;
	public static Item itemScaleHarness;
	public static Item itemScaleHat;
	public static Item itemScaleLeggings;
	public static Item itemScalePlate;
	public static Item itemScalingKnife;
	public static Item itemWhetstone;

	public static void addNames() {
		LanguageRegistry.addName(itemScale, ItemInfo.SCALE_NAME);
		LanguageRegistry.addName(itemScaleHard, ItemInfo.HARD_SCALE_NAME);
		LanguageRegistry.addName(itemScalePlate, ItemInfo.SCALE_PLATE_NAME);
		LanguageRegistry.addName(itemWhetstone, ItemInfo.WHETSTONE_NAME);
		LanguageRegistry.addName(itemScalingKnife, ItemInfo.SCALING_KNIFE_NAME);
		LanguageRegistry.addName(itemBlubberBucket,
				ItemInfo.BLUBBER_BUCKET_NAME);
		LanguageRegistry.addName(itemRainbowBlubberBucket,
				ItemInfo.RAINBOW_BLUBBER_BUCKET_NAME);
		LanguageRegistry.addName(itemBlubberBottle,
				ItemInfo.BLUBBER_BOTTLE_NAME);
		LanguageRegistry.addName(itemRainbowBlubberBottle,
				ItemInfo.RAINBOW_BLUBBER_BOTTLE_NAME);
		LanguageRegistry.addName(itemScaleHat, ItemInfo.SCALE_HAT_NAME);
		LanguageRegistry.addName(itemScaleHarness, ItemInfo.SCALE_HARNESS_NAME);
		LanguageRegistry.addName(itemScaleLeggings,
				ItemInfo.SCALE_LEGGINGS_NAME);
		LanguageRegistry.addName(itemScaleBoots, ItemInfo.SCALE_BOOTS_NAME);
	}

	public static void init() {
		itemScale = new ItemScale(ItemInfo.SCALE_ID);
		itemScaleHard = new ItemScaleHard(ItemInfo.HARD_SCALE_ID);
		itemScalePlate = new ItemScalePlate(ItemInfo.SCALE_PLATE_ID);

		itemWhetstone = new ItemWhetstone(ItemInfo.WHETSTONE_ID);
		itemScalingKnife = new ItemScalingKnife(ItemInfo.SCALING_KNIFE_ID);

		itemBlubberBucket = new ItemCustomBucket(ItemInfo.BLUBBER_BUCKET_ID,
				BlockInfo.BLUBBER_ID).setUnlocalizedName(
				ItemInfo.BLUBBER_BUCKET_UNLOCALIZED_NAME).setContainerItem(
				Item.bucketEmpty);
		itemRainbowBlubberBucket = new ItemCustomBucket(
				ItemInfo.RAINBOW_BLUBBER_BUCKET_ID,
				BlockInfo.RAINBOW_BLUBBER_ID).setUnlocalizedName(
				ItemInfo.RAINBOW_BLUBBER_BUCKET_UNLOCALIZED_NAME)
				.setContainerItem(Item.bucketEmpty);

		itemBlubberBottle = new ItemCustomBottle(ItemInfo.BLUBBER_BOTTLE_ID,
				BlockInfo.BLUBBER_ID).setUnlocalizedName(
				ItemInfo.BLUBBER_BOTTLE_UNLOCALIZED_NAME).setContainerItem(
				Item.glassBottle);
		itemRainbowBlubberBottle = new ItemCustomBottle(
				ItemInfo.RAINBOW_BLUBBER_BOTTLE_ID,
				BlockInfo.RAINBOW_BLUBBER_ID).setUnlocalizedName(
				ItemInfo.RAINBOW_BLUBBER_BOTTLE_UNLOCALIZED_NAME)
				.setContainerItem(Item.glassBottle);

		itemScaleHat = new ItemScaleHat(ItemInfo.SCALE_HAT_ID);
		itemScaleHarness = new ItemScaleHarness(ItemInfo.SCALE_HARNESS_ID);
		itemScaleLeggings = new ItemScaleLeggings(ItemInfo.SCALE_LEGGINGS_ID);
		itemScaleBoots = new ItemScaleBoots(ItemInfo.SCALE_BOOTS_ID);
	}

	public static void registerFluidContainers() {
		FluidContainerRegistry.registerFluidContainer(
				FluidRegistry.getFluidStack("blubber",
						FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(
						itemBlubberBucket), new ItemStack(Item.bucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry
				.getFluidStack("rainbowblubber",
						FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(
				itemRainbowBlubberBucket), new ItemStack(Item.bucketEmpty));

		FluidContainerRegistry.registerFluidContainer(
				FluidRegistry.getFluidStack("blubber",
						FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(
						itemBlubberBottle), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry
				.getFluidStack("rainbowblubber",
						FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(
				itemRainbowBlubberBottle), new ItemStack(Item.glassBottle));
	}

	public static void registerRecipes() {
		GameRegistry.addShapelessRecipe(new ItemStack(itemScale),
				new Object[] {
						new ItemStack(itemScalingKnife, 1,
								OreDictionary.WILDCARD_VALUE),
						new ItemStack(Item.fishRaw) });
		GameRegistry.addRecipe(new ItemStack(itemScaleHard), new Object[] {
				"ss", "ss",

				's', itemScale });

		GameRegistry.addRecipe(new ItemStack(itemScalePlate), new Object[] {
				"ss",

				's', itemScaleHard });

		GameRegistry.addRecipe(new ItemStack(itemWhetstone), new Object[] {
				"sss", "iii", "ccc",

				's', Block.sand, 'i', Item.ingotIron, 'c', Block.stone });

		GameRegistry.addRecipe(new ItemStack(itemScalingKnife),
				new Object[] {
						"i ",
						"bw",

						'i',
						new ItemStack(itemScalingKnife, 1,
								OreDictionary.WILDCARD_VALUE),
						'b',
						Item.bucketWater,
						'w',
						new ItemStack(itemWhetstone, 1,
								OreDictionary.WILDCARD_VALUE) });

		GameRegistry.addRecipe(new ItemStack(itemScalingKnife, 1, 64),
				new Object[] { "f  ", " i ", "  s",

				'f', Item.flint, 'i', Item.ingotIron, 's', Item.stick });
	}

}
