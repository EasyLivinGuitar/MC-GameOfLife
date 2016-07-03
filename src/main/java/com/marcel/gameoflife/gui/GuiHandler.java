package com.marcel.gameoflife.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by kipu5728 on 7/3/16.
 */
public class GuiHandler implements IGuiHandler {
    private static final int GAME_OVER_GUI_ID = 666;

    public GuiHandler(){}

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        System.out.println("GET SERVER GUI");
        /*if(ID == GAME_OVER_GUI_ID){
            return new GameOverGui();
        }*/

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        System.out.println("GET CLIENT GUI");
        if(ID == GAME_OVER_GUI_ID){
            return new GameOverGui();
        }

        return null;
    }
}
