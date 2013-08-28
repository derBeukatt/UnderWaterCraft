package org.derbeukatt.underwatercraft;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.derbeukatt.underwatercraft.blocks.Blocks;
import org.derbeukatt.underwatercraft.gui.CraftingHandler;
import org.derbeukatt.underwatercraft.items.Items;
import org.derbeukatt.underwatercraft.network.PacketHandler;
import org.derbeukatt.underwatercraft.proxies.CommonProxy;
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

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, certificateFingerprint = ModInfo.MOD_FINGERPRINT)
@NetworkMod(channels = {ModInfo.MOD_CHANNELS}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class UnderWaterCraft{

    @Instance(ModInfo.MOD_ID)
    public static UnderWaterCraft instance;

    @SidedProxy(clientSide = "org.derbeukatt.underwatercraft.proxies.ClientProxy", serverSide = "org.derbeukatt.underwatercraft.proxies.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void invalidFingerPrint(FMLFingerprintViolationEvent event) {
    	Logger log = Logger.getLogger(ModInfo.MOD_ID);
    	log.setParent(FMLLog.getLogger());
    	log.log(Level.SEVERE, ModInfo.MOD_FINGERPRINT_MSG);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        Items.init();
        Blocks.init();
        GameRegistry.registerCraftingHandler(new CraftingHandler());
        proxy.initRenderers();
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        Items.addNames();
        Items.registerRecipes();
        Blocks.addNames();
    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent event)
    {
    }
}
