package com.marcel.gameoflife.gui;

import com.marcel.gameoflife.events.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

/**
 * Created by kipu5728 on 7/4/16.
 */
public class InsertNameGui extends GuiScreen {
    private FontRenderer fontRenderer;
    private GuiTextField textBox;
    private ScaledResolution resolution;

    @Override
    public void initGui(){
        this.mc = Minecraft.getMinecraft();
        this.fontRenderer = this.mc.fontRendererObj;
        this.resolution = new ScaledResolution(this.mc);
        this.textBox = new GuiTextField(100,
                this.fontRendererObj,
                this.resolution.getScaledWidth()/2 - 100,
                this.resolution.getScaledHeight()/2 -10,
                200,
                20);

        this.textBox.setMaxStringLength(20);
        this.textBox.setText("StupidPlayerName");
        this.textBox.setFocused(true);

        EventHandler.GUI_OPENED = true;

    }

    @Override
    public void keyTyped(char key, int keycode){
        try {
            if(key == 1){
                EventHandler.GUI_OPENED = false;
            }

            super.keyTyped(key, keycode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.textBox.textboxKeyTyped(key, keycode);

        if(keycode == 28){
            EventHandler.PLAYER.setCustomNameTag(this.textBox.getText());
            try {
                EventHandler.GUI_OPENED = false;
                super.keyTyped(key, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void updateScreen(){
        super.updateScreen();
        this.textBox.updateCursorCounter();
    }


    @Override
    public void drawScreen(int x, int y, float ticks){
        this.drawDefaultBackground();
        this.drawString(this.fontRenderer, "Insert name:",
                resolution.getScaledWidth()/2 -100,
                resolution.getScaledHeight()/2 - 20,
                0xFFFFFF);
        this.textBox.drawTextBox();
    }

    @Override
    public void mouseClicked(int x, int y, int btn){
        try {
            super.mouseClicked(x, y, btn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.textBox.mouseClicked(x, y, btn);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return true;
    }
}
