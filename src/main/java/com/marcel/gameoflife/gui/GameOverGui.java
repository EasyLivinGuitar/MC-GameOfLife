package com.marcel.gameoflife.gui;

import com.marcel.gameoflife.events.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Created by kipu5728 on 7/3/16.
 */
public class GameOverGui extends GuiScreen {
    private ScaledResolution resolution;
    private FontRenderer fontRenderer;

    private String DIE_MESSAGE = "You died!";

    @Override
    public void initGui(){
        resolution = new ScaledResolution(Minecraft.getMinecraft());
        fontRenderer = Minecraft.getMinecraft().fontRendererObj;
//        this.setWorldAndResolution(Minecraft.getMinecraft(), resolution.getScaledWidth(), resolution.getScaledWidth());
    }

    @Override
    public void drawScreen(int mousex, int mousey, float partialTicks){
        this.drawDefaultBackground();

        /**Die message**/
        this.drawString(fontRenderer,
                DIE_MESSAGE,
                resolution.getScaledWidth()/2 -fontRenderer.getStringWidth(DIE_MESSAGE)/2,
                resolution.getScaledHeight()/2 - 20,
                0xe60000);

        /**Description**/
        this.drawString(fontRenderer,
                "Survived time:",
                resolution.getScaledWidth()/2 - fontRenderer.getStringWidth("Survived time:")/2,
                resolution.getScaledHeight()/2 + 10,
                0xFFFFFF);

        /**Survived time**/
        this.drawString(fontRenderer, EventHandler.TIMER.getCurrentTimeString(),
                resolution.getScaledWidth()/2 - fontRenderer.getStringWidth(EventHandler.TIMER.getCurrentTimeString())/2
                , resolution.getScaledHeight()/2 + 20, 0xFFFFFF);

        super.drawScreen(mousex, mousey, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return true;
    }

}