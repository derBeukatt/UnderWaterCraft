package org.derbeukatt.underwatercraft.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketHandler {

	public static BucketHandler INSTANCE = new BucketHandler();
	public Map<Block, Item> buckets = new HashMap<Block, Item>();

	private BucketHandler() {
	}

	private ItemStack fillCustomBucket(final World world,
			final MovingObjectPosition pos) {

		final int blockID = world
				.getBlockId(pos.blockX, pos.blockY, pos.blockZ);

		final Item bucket = this.buckets.get(Block.blocksList[blockID]);
		if ((bucket != null)
				&& (world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0)) {
			world.setBlock(pos.blockX, pos.blockY, pos.blockZ, 0);
			return new ItemStack(bucket);
		} else {
			return null;
		}

	}

	@ForgeSubscribe
	public void onBucketFill(final FillBucketEvent event) {

		final ItemStack result = this.fillCustomBucket(event.world,
				event.target);

		if (result == null) {
			return;
		}

		event.result = result;
		event.setResult(Result.ALLOW);
	}

}
