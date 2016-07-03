package com.marcel.gameoflife.hudelements;

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

    public RuntimeTimer(){
        format = new SimpleDateFormat("mm:ss");
    }

    public void start(){
        startTime = System.currentTimeMillis();
        System.out.println(Minecraft.getMinecraft().displayWidth);
    }

    public void draw(){
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
                format.format(System.currentTimeMillis()-this.startTime),
                resolution.getScaledWidth() / 2 - 10,
                20,
                0xFFFFFF);


    }

}
