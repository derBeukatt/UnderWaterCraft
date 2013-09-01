package org.derbeukatt.underwatercraft.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Items {

    public static Item itemScale;
    public static Item itemScaleHard;
    public static Item itemScalePlate;
    public static Item itemWhetstone;
    public static Item itemScalingKnife;

    public static void init()
    {
        LanguageRegistry.instance().addStringLocalization("itemGroup.tabUnderWaterCraft", "UnderWaterCraft");

        itemScale = new ItemScale(ItemInfo.SCALE_ID);
        itemScaleHard = new ItemScaleHard(ItemInfo.HARD_SCALE_ID);
        itemScalePlate = new ItemScalePlate(ItemInfo.SCALE_PLATE_ID);

        itemWhetstone = new ItemWhetstone(ItemInfo.WHETSTONE_ID);
        itemScalingKnife = new ItemScalingKnife(ItemInfo.SCALING_KNIFE_ID);
    }

    public static void addNames()
    {
        LanguageRegistry.addName(itemScale, ItemInfo.SCALE_NAME);
        LanguageRegistry.addName(itemScaleHard, ItemInfo.HARD_SCALE_NAME);
        LanguageRegistry.addName(itemScalePlate, ItemInfo.SCALE_PLATE_NAME);
        LanguageRegistry.addName(itemWhetstone, ItemInfo.WHETSTONE_NAME);
        LanguageRegistry.addName(itemScalingKnife, ItemInfo.SCALING_KNIFE_NAME);
    }

    public static void registerRecipes()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(itemScale),
                new Object[]{
                        new ItemStack(itemScalingKnife, 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(Item.fishRaw)
                });
        GameRegistry.addRecipe(new ItemStack(itemScaleHard),
                new Object[]{ "ss",
                              "ss",

                              's', itemScale
                });

        GameRegistry.addRecipe(new ItemStack(itemScalePlate),
                new Object[]{   "ss",

                                's', itemScaleHard
                });

        GameRegistry.addRecipe(new ItemStack(itemWhetstone),
                new Object[]{ "sss",
                              "iii",
                              "ccc",

                              's', Block.sand,
                              'i', Item.ingotIron,
                              'c', Block.stone
                });

        GameRegistry.addRecipe(new ItemStack(itemScalingKnife),
                new Object[]{ "i ",
                              "bw",

                              'i', new ItemStack(itemScalingKnife, 1 , OreDictionary.WILDCARD_VALUE),
                              'b', Item.bucketWater,
                              'w', new ItemStack(itemWhetstone, 1, OreDictionary.WILDCARD_VALUE)
                });

        GameRegistry.addRecipe(new ItemStack(itemScalingKnife, 1, 64),
                new Object[]{ "f  ",
                              " i ",
                              "  s",

                              'f', Item.flint,
                              'i', Item.ingotIron,
                              's', Item.stick
                });
    }

}
