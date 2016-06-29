package com.marcel.gameoflife.hudelements;

import net.minecraft.client.Minecraft;

import java.util.Map;

/**
 * Created by kipu5728 on 6/29/16.
 */
public class PopulationStats {
    private Minecraft minecraft;

    public PopulationStats(){
        this.minecraft = Minecraft.getMinecraft();
    }

    public void drawStats(Map<String, Integer> stats) {
        int distance = 10;

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            minecraft.fontRendererObj.drawStringWithShadow(entry.getKey()+": "+entry.getValue(),
                    10,
                    10 + distance,
                    0xFFFFFF);
            distance += distance;
        }
    }
}
