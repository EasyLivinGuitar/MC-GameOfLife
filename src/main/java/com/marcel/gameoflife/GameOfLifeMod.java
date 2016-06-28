package com.marcel.gameoflife;

import com.marcel.gameoflife.events.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Created by kipu5728 on 6/21/16.
 */

@Mod(modid = GameOfLifeMod.MODID, version = GameOfLifeMod.VERSION)
public class GameOfLifeMod {
    public static final String MODID = "GameOfLife";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

}
