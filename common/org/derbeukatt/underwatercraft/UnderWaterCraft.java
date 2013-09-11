package org.derbeukatt.underwatercraft;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

import org.derbeukatt.underwatercraft.client.gui.CraftingHandler;
import org.derbeukatt.underwatercraft.client.gui.GuiHandler;
import org.derbeukatt.underwatercraft.common.CommonProxy;
import org.derbeukatt.underwatercraft.common.blocks.Blocks;
import org.derbeukatt.underwatercraft.common.fluids.Fluids;
import org.derbeukatt.underwatercraft.common.items.Items;
import org.derbeukatt.underwatercraft.network.PacketHandler;
import org.derbeukatt.underwatercraft.util.BucketHandler;
import org.derbeukatt.underwatercraft.util.ClientTickHandler;
import org.derbeukatt.underwatercraft.util.ConfigHandler;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, certificateFingerprint = ModInfo.MOD_FINGERPRINT)
@NetworkMod(channels = { ModInfo.MOD_CHANNELS }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class UnderWaterCraft {

	@Instance(ModInfo.MOD_ID)
	public static UnderWaterCraft instance;

	@SidedProxy(clientSide = "org.derbeukatt.underwatercraft.client.ClientProxy", serverSide = "org.derbeukatt.underwatercraft.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void invalidFingerPrint(final FMLFingerprintViolationEvent event) {
		final Logger log = Logger.getLogger(ModInfo.MOD_ID);
		log.setParent(FMLLog.getLogger());
		log.log(Level.SEVERE, ModInfo.MOD_FINGERPRINT_MSG);
	}

	@EventHandler
	public void load(final FMLInitializationEvent event) {
		Items.addNames();
		Items.registerFluidContainers();
		Blocks.addNames();

		Items.registerRecipes();

		Blocks.registerTileEntities();

		new GuiHandler();
	}

	@EventHandler
	public void modsLoaded(final FMLPostInitializationEvent event) {
	}

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		Fluids.init();
		Blocks.init();
		Items.init();
		GameRegistry.registerCraftingHandler(new CraftingHandler());
		proxy.initRenderers();

		BucketHandler.INSTANCE.buckets.put(Blocks.blubber,
				Items.itemBlubberBucket);

		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

		MinecraftForge.EVENT_BUS.register(this);

		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
	}

	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void textureHook(final TextureStitchEvent.Post event) {
		if (event.map.textureType == 0) {
			Fluids.fluidBlubber.setIcons(
					Blocks.blubber.getBlockTextureFromSide(1),
					Blocks.blubber.getBlockTextureFromSide(2));
		}
	}
}
