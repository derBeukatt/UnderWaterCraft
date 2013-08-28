package org.derbeukatt.underwatercraft.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.derbeukatt.underwatercraft.gui.UnderWaterCraftTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemScalingKnife extends Item {

    public ItemScalingKnife(int id) {
        super(id);
        setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
        setUnlocalizedName(ItemInfo.SCALING_KNIFE_UNLOCALIZED_NAME);
        setHasSubtypes(true);
        setMaxDamage(64);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void addInformation(ItemStack itemStack,	EntityPlayer player, List list, boolean showAdvancedToolTip) {
    	list.add((itemStack.getMaxDamage() - itemStack.getItemDamage()) + " uses left.");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {
        itemIcon = register.registerIcon(new ResourceLocation(ItemInfo.TEXTURE_LOCATION, ItemInfo.SCALING_KNIFE_ICON).toString());
    }
}
