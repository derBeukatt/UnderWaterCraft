package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.common.containers.ContainerMixer;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;
import org.derbeukatt.underwatercraft.network.PacketHandler;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMixer extends GuiContainer {

	protected static final ResourceLocation BLOCK_TEXTURE = TextureMap.field_110575_b;

	private static final GuiBottle BOTTLE;

	protected static final ResourceLocation TEXTURE = new ResourceLocation(
			"underwatercraft", "textures/gui/mixer_gui.png");
	static {
		BOTTLE = new GuiBottle(67, 18, 42, 60);
	}

	private final InventoryPlayer inventory;

	private final TileEntityMixer mixer;

	public GuiMixer(final InventoryPlayer inventory, final TileEntityMixer te) {
		super(new ContainerMixer(inventory, te));

		this.mixer = te;
		this.inventory = inventory;

		this.xSize = 176;
		this.ySize = 166;
	}

	private void displayGauge(final int line, final int col, int squaled,
			final FluidStack liquid) {
		if (liquid == null) {
			return;
		}
		int start = 0;

		Icon liquidIcon = null;
		final Fluid fluid = liquid.getFluid();
		if ((fluid != null) && (fluid.getIcon() != null)) {
			liquidIcon = fluid.getIcon();
		}
		this.mc.renderEngine.func_110577_a(BLOCK_TEXTURE);

		if (liquidIcon != null) {
			while (true) {
				int x;

				if (squaled > 16) {
					x = 16;
					squaled -= 16;
				} else {
					x = squaled;
					squaled = 0;
				}

				this.drawTexturedModelRectFromIcon(this.guiLeft + col,
						(this.guiTop + line + 58) - x - start, liquidIcon, 16,
						16 - (16 - x));
				start = start + 16;

				if ((x == 0) || (squaled == 0)) {
					break;
				}
			}
		}

		this.mc.renderEngine.func_110577_a(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft + col, this.guiTop + line, 176,
				0, 16, 58);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int x,
			final int y) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.renderEngine.func_110577_a(TEXTURE);

		/* Draw main gui */
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);

		// /* Draw boiling animation */
		// if (this.boiler.isBoiling()) {
		// this.drawTexturedModalRect(this.guiLeft + 53, this.guiTop + 60,
		// 176, 0, 14, 14);
		// }

		/* Display water */
		this.displayGauge(19, 8, this.mixer.getScaledWaterAmount(58),
				this.mixer.getInputFluid());

		GuiMixer.BOTTLE.draw(this, this.mc.renderEngine, 176, 58);

		// /* Display blubber */
		// this.displayGauge(19, 131, this.mixer.getScaledBlubberAmount(58),
		// this.mixer.getOutputFluid());

		// final int scaledProgress = this.boiler.getCookProgressScaled(24);
		// this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 41, 176,
		// 14, scaledProgress + 1, 16);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int x, final int y) {
		this.fontRenderer.drawString("Mixer", 8, 6, 0x404040);
	}

	public int getLeft() {
		return this.guiLeft;
	}

	public TileEntityMixer getMixer() {
		return this.mixer;
	}

	public int getTop() {
		return this.guiTop;
	}

	@Override
	protected void mouseClicked(final int x, final int y, final int button) {
		super.mouseClicked(x, y, button);
		if (button == 1) {
			if (GuiMixer.BOTTLE.mouseClicked(this, x, y)) {
				final ItemStack itemStack = this.inventory.getItemStack();
				if (itemStack != null) {
					if (itemStack.itemID == Item.dyePowder.itemID) {
						if (!this.mixer.dyes.contains(itemStack)) {
							this.mixer.dyes.add(itemStack);
							PacketHandler.sendAddedDye(itemStack,
									this.mixer.xCoord, this.mixer.yCoord,
									this.mixer.zCoord);
						}
					}
				}
			}
		} else if (button == 0) {
			if (this.mixer.dyes.size() == 16) {
				if (GuiMixer.BOTTLE.mouseClicked(this, x, y)) {
					System.out.println("would do stuff here");
				}
			}
		}
	}
}
