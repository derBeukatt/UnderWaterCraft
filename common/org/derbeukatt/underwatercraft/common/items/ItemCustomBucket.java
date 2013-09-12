package org.derbeukatt.underwatercraft.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomBucket extends ItemBucket {

	private String iconName;

	public ItemCustomBucket(final int id, final int blockId) {
		super(id, blockId);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setContainerItem(Item.bucketEmpty);
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
		return EnumAction.drink;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
		return 32;
	}

	@Override
	public ItemStack onEaten(final ItemStack item, final World world,
			final EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--item.stackSize;
		}

		if (!world.isRemote) {
			player.addPotionEffect(new PotionEffect(Potion.confusion.getId(),
					300, 1));
			player.addPotionEffect(new PotionEffect(Potion.resistance.getId(),
					300, 0));
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(),
					300, 0));
		}

		return item.stackSize <= 0 ? new ItemStack(Item.bucketEmpty) : item;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack par1ItemStack,
			final World par2World, final EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IconRegister register) {
		this.itemIcon = register.registerIcon(new ResourceLocation(
				ItemInfo.TEXTURE_LOCATION, this.iconName).toString());
	}

	@Override
	public Item setUnlocalizedName(final String name) {
		this.iconName = name;
		return super.setUnlocalizedName(name);
	}
}
