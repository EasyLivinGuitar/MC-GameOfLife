package com.marcel.gameoflife.gui.hudelements;

import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kipu5728 on 6/29/16.
 */
public class PopulationStats {
    private Minecraft minecraft;

    private Map<String, Integer> currentStats;
    private Map<String, Integer> maxStats;

    public PopulationStats(){
        currentStats = new ConcurrentHashMap<String, Integer>();
        maxStats = new HashMap<String, Integer>();
        this.minecraft = Minecraft.getMinecraft();
    }

    public void drawStats(Map<String, Integer> stats) {
        int distance = 10;

        this.currentStats = stats;
        this.setMaxStats();

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

    private void setMaxStats(){
        for(Map.Entry<String, Integer> entry: currentStats.entrySet()){
            if(maxStats.getOrDefault(entry.getKey(),0) < entry.getValue()){
                maxStats.put(entry.getKey(),entry.getValue());
            }
        }
    }

    public int getMaxStats(String entity){
        return maxStats.getOrDefault(entity, 0);
    }

    public void reset(){
        maxStats.clear();
    }


}
