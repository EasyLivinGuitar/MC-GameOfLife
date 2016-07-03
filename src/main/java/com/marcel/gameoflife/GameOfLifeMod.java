package com.marcel.gameoflife;

import com.marcel.gameoflife.events.EventHandler;
import com.marcel.gameoflife.gui.GuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by kipu5728 on 6/21/16.
 */

@Mod(modid = GameOfLifeMod.MODID, version = GameOfLifeMod.VERSION, clientSideOnly = true)

public class GameOfLifeMod {
    public static final String MODID = "GameOfLife";
    public static final String VERSION = "1.0";

    @Mod.Instance(value = GameOfLifeMod.MODID)
    public static GameOfLifeMod instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        System.out.println(instance.MODID);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(GameOfLifeMod.instance, new GuiHandler());
    }

}
