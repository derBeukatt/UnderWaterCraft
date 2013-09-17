package org.derbeukatt.underwatercraft.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.fluids.Fluids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluidRainbowBlubber extends BlockFluidClassic {

	private Icon flowIcon;
	private Icon stillIcon;

	public BlockFluidRainbowBlubber(final int id) {
		super(id, Fluids.rainbowBlubber, Material.water);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setUnlocalizedName(BlockInfo.RAINBOW_BLUBBER_UNLOCALIZED_NAME);
		Fluids.rainbowBlubber.setBlockID(id);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(final int side, final int meta) {
		return (side != 0) && (side != 1) ? this.flowIcon : this.stillIcon;
	}

	@Override
	public void onEntityCollidedWithBlock(final World world, final int x,
			final int y, final int z, final Entity entity) {
		if (!world.isRemote) {
			if (entity != null) {
				if (entity instanceof EntityPlayer) {
					final EntityPlayer player = (EntityPlayer) entity;
					player.addPotionEffect(new PotionEffect(Potion.confusion
							.getId(), 100, 2, true));
					player.addPotionEffect(new PotionEffect(Potion.resistance
							.getId(), 100, 1, true));
					player.addPotionEffect(new PotionEffect(Potion.moveSpeed
							.getId(), 100, 1, true));
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IconRegister register) {
		this.flowIcon = register
				.registerIcon(new ResourceLocation(BlockInfo.TEXTURE_LOCATION,
						BlockInfo.RAINBOW_BLUBBER_FLOW_ICON).toString());
		this.stillIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION,
				BlockInfo.RAINBOW_BLUBBER_STILL_ICON).toString());
	}

}
