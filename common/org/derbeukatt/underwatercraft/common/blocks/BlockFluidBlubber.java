package org.derbeukatt.underwatercraft.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.fluids.Fluids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluidBlubber extends BlockFluidClassic {

	private Icon flowIcon;
	private Icon stillIcon;

	public BlockFluidBlubber(final int id) {
		super(id, Fluids.blubber, Material.water);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setUnlocalizedName(BlockInfo.BLUBBER_UNLOCALIZED_NAME);
		Fluids.blubber.setBlockID(id);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(final int side, final int meta) {
		return (side != 0) && (side != 1) ? this.flowIcon : this.stillIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IconRegister register) {
		this.flowIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.BLUBBER_FLOW_ICON)
				.toString());
		this.stillIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.BLUBBER_STILL_ICON)
				.toString());
	}

}
