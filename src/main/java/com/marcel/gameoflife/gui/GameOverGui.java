package com.marcel.gameoflife.gui;

import com.marcel.gameoflife.events.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

/**
 * Created by kipu5728 on 7/3/16.
 */
public class GameOverGui extends GuiScreen implements GuiYesNoCallback {
    private ScaledResolution resolution;
    private FontRenderer fontRenderer;

    private String DIE_MESSAGE = "GAME OVER";

    public GameOverGui(){
        System.out.println("CONSTRUCTOR");
    }

    @Override
    public void initGui(){
        System.out.println("INIT");
        this.mc = Minecraft.getMinecraft();
        this.fontRenderer = this.mc.fontRendererObj;
        this.resolution = new ScaledResolution(this.mc);

        EventHandler.GUI_OPENED = true;

//        this.setWorldAndResolution(Minecraft.getMinecraft(),resolution.getScaledWidth(), resolution.getScaledHeight());
    }

    @Override
    public void drawScreen(int mousex, int mousey, float partialTicks){

        this.drawDefaultBackground();

        /**Die message**/
        this.drawString(fontRenderer,
                DIE_MESSAGE,
                resolution.getScaledWidth()/2 -fontRenderer.getStringWidth(DIE_MESSAGE)/2,
                resolution.getScaledHeight()/2 - 60,
                0xe60000);

        /**Description**/
        this.drawString(fontRenderer,
                "Survived time:",
                resolution.getScaledWidth()/2 - fontRenderer.getStringWidth("Survived time:")/2,
                resolution.getScaledHeight()/2 - 10,
                0xFFFFFF);

        /**Survived time**/
        this.drawString(fontRenderer, EventHandler.TIMER.getCurrentTimeString(),
                resolution.getScaledWidth()/2 - fontRenderer.getStringWidth(EventHandler.TIMER.getCurrentTimeString())/2,
                resolution.getScaledHeight()/2, 0xFFFFFF);

        /*super.drawScreen(mousex, mousey, partialTicks);*/
    }

    @Override
    public boolean doesGuiPauseGame(){
        return true;
    }

    @Override
    protected void keyTyped(char typedChar, int keycode){
        try {
            super.keyTyped(typedChar, keycode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(keycode == 1){
            EventHandler.GUI_OPENED = false;
        }
    }

}
