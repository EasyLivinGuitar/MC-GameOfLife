package com.marcel.gameoflife.gui.hudelements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by kipu5728 on 7/3/16.
 */
public class RuntimeTimer {
    private DateFormat format;
    private long startTime;
    private long currentTime;

    private boolean started;

    public RuntimeTimer(){
        format = new SimpleDateFormat("mm:ss");
        started = false;
    }

    public void start(){
        startTime = System.currentTimeMillis();
        started = true;
    }

    public void draw(){
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        if(started){
            currentTime = System.currentTimeMillis();
        }

        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
                format.format(this.currentTime - this.startTime),
                resolution.getScaledWidth() / 2 - 10,
                20,
                0xFFFFFF);


    }

    public void stop(){
        this.started = false;
    }

    public String getCurrentTimeString(){
        return format.format(this.currentTime-this.startTime);
    }

}
