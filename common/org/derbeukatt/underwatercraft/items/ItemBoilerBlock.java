package org.derbeukatt.underwatercraft.items;

import net.minecraft.item.ItemBlock;

public class ItemBoilerBlock extends ItemBlock {

	public ItemBoilerBlock(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}

}
