package com.marcel.gameoflife.hudelements;

import net.minecraft.client.Minecraft;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kipu5728 on 6/29/16.
 */
public class PopulationStats {
    private Minecraft minecraft;

    private Map<String, Integer> currentStats;

    public PopulationStats(){
        currentStats = new ConcurrentHashMap<String, Integer>();
        this.minecraft = Minecraft.getMinecraft();
    }

    public void drawStats(Map<String, Integer> stats) {
        int distance = 10;

        currentStats = stats;

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            minecraft.fontRendererObj.drawStringWithShadow(entry.getKey()+": "+entry.getValue(),
                    10,
                    10 + distance,
                    0xFFFFFF);
            distance += distance;
        }
    }

    public int getStat(String entityName){
        Integer stat = currentStats.get(entityName);

        if(stat != null){
            return stat;
        }
        else{
            return 0;
        }


    }
}
