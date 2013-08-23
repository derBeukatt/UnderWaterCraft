package org.derbeukatt.underwatercraft;

import org.derbeukatt.underwatercraft.gui.CraftingHandler;
import org.derbeukatt.underwatercraft.items.Items;
import org.derbeukatt.underwatercraft.network.PacketHandler;
import org.derbeukatt.underwatercraft.proxies.CommonProxy;
import org.derbeukatt.underwatercraft.util.ConfigHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created with IntelliJ IDEA.
 * User: philipp
 * Date: 04.08.13
 * Time: 19:06
 * To change this template use File | Settings | File Templates.
 */
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION)
@NetworkMod(channels = {ModInfo.MOD_CHANNELS}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class UnderWaterCraft{

    @Instance(ModInfo.MOD_ID)
    public static UnderWaterCraft instance;

    @SidedProxy(clientSide = "org.derbeukatt.underwatercraft.proxies.ClientProxy", serverSide = "org.derbeukatt.underwatercraft.proxies.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        Items.init();
        GameRegistry.registerCraftingHandler(new CraftingHandler());
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        Items.addNames();
        Items.registerRecipes();
    }

    @EventHandler
    public void modLoaded(FMLPostInitializationEvent event)
    {
    }
}
