package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.client.gui.elements.GuiBottle;
import org.derbeukatt.underwatercraft.client.gui.elements.GuiElement;
import org.derbeukatt.underwatercraft.client.gui.elements.GuiGauge;
import org.derbeukatt.underwatercraft.common.containers.ContainerMixer;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;
import org.derbeukatt.underwatercraft.network.PacketHandler;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMixer extends Gui {

	private final GuiElement blubberGauge;
	private final GuiElement bottle;

	private final InventoryPlayer inventory;

	private final TileEntityMixer mixer;

	public GuiMixer(final InventoryPlayer inventory, final TileEntityMixer te) {
		super(new ContainerMixer(inventory, te), te);

		this.mixer = te;
		this.inventory = inventory;

		this.xSize = 176;
		this.ySize = 166;

		this.blubberGauge = new GuiGauge(8, 19, 16, 58, te.getInputTank());

		this.texture = new ResourceLocation("underwatercraft",
				"textures/gui/mixer_gui.png");

		this.bottle = new GuiBottle(67, 18, 42, 60);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int x,
			final int y) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.renderEngine.func_110577_a(this.texture);

		/* Draw main gui */
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);

		/* Display blubber */
		this.blubberGauge.drawBackground(this, this.mc.renderEngine, 176, 0);

		this.bottle.drawBackground(this, this.mc.renderEngine, 176, 58);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int x, final int y) {
		this.fontRenderer.drawString("Mixer", 8, 6, 0x404040);
		this.bottle.drawForeground(this, this.fontRenderer, x, y);
		this.blubberGauge.drawForeground(this, this.fontRenderer, x, y);
	}

	@Override
	public void drawScreen(final int x, final int y, final float f) {
		super.drawScreen(x, y, f);
		this.blubberGauge.drawScreen(this, x, y);
	}

	@Override
	protected void mouseClicked(final int x, final int y, final int button) {
		super.mouseClicked(x, y, button);
		if (button == 1) {
			if (this.bottle.mouseActionInElement(this, x, y)) {
				if (this.mixer.hasBottleFluid) {
					final ItemStack itemStack = this.inventory.getItemStack();
					if (itemStack != null) {
						if (itemStack.itemID == Item.dyePowder.itemID) {
							if (!this.mixer.dyes.containsKey(itemStack
									.getItemDamage())) {
								this.mixer.dyes.put(itemStack.getItemDamage(),
										new ItemStack(itemStack.itemID, 1,
												itemStack.getItemDamage()));
								itemStack.stackSize--;
								PacketHandler.sendAddedDye(itemStack,
										this.mixer.xCoord, this.mixer.yCoord,
										this.mixer.zCoord);
							}
						}
					}
				}
			}
		} else if (button == 0) {
			if (this.bottle.mouseActionInElement(this, x, y)) {
				if ((this.mixer.dyes.size() == 0) && !this.mixer.hasBottleFluid) {
					final FluidStack amount = this.mixer
							.drain(ForgeDirection.UNKNOWN,
									FluidRegistry
											.getFluidStack(
													"blubber",
													FluidContainerRegistry.BUCKET_VOLUME),
									true);
					if (amount != null) {
						if (amount.amount == FluidContainerRegistry.BUCKET_VOLUME) {
							this.mixer.hasBottleFluid = true;
							PacketHandler.sendHasBottleFluid(
									this.mixer.hasBottleFluid, this.mixer
											.getInputTank().getFluidAmount(),
									this.mixer.xCoord, this.mixer.yCoord,
									this.mixer.zCoord);
						}
					}
				}
			}
		}
	}
}
