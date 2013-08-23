package org.derbeukatt.underwatercraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.derbeukatt.underwatercraft.items.Items;

import cpw.mods.fml.common.ICraftingHandler;

/**
 * Created with IntelliJ IDEA.
 * User: philipp
 * Date: 08.08.13
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class CraftingHandler implements ICraftingHandler {
    @Override
    public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
        for(int i = 0; i < craftMatrix.getSizeInventory(); i++ )
        {
            if(craftMatrix.getStackInSlot(i) != null)
            {
                ItemStack j = craftMatrix.getStackInSlot(i);
                if(j.getItem() != null && j.getItem() == Items.itemWhetstone){
                    ItemStack k = new ItemStack(Items.itemWhetstone, 2, (j.getItemDamage() + 1));
                    if(k.getItemDamage() < k.getMaxDamage()){
                        craftMatrix.setInventorySlotContents(i, k);
                    }else{
                        //ItemStack l = new ItemStack(Block.blockClay, 0);
                        craftMatrix.setInventorySlotContents(i, j);
                    }
                }

                else if(j.getItem() != null && j.getItem() == Items.itemScalingKnife){
                    ItemStack k = new ItemStack(Items.itemScalingKnife, 2, (j.getItemDamage() + 1));
                    if(k.getItemDamage() < k.getMaxDamage()){
                        craftMatrix.setInventorySlotContents(i, k);
                    }else{
                        //ItemStack l = new ItemStack(Block.blockClay, 0);
                        craftMatrix.setInventorySlotContents(i, j);
                    }
                }
            }
        }
    }

    @Override
    public void onSmelting(EntityPlayer player, ItemStack item) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
