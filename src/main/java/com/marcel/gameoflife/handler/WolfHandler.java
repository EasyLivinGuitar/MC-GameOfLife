package com.marcel.gameoflife.handler;

import com.marcel.gameoflife.ais.EntityAILeaveSpawnArea;
import com.marcel.gameoflife.ais.EntityAISheepEating;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;

/**
 * Created by kipu5728 on 7/1/16.
 */
public class WolfHandler {
    public void initAI(EntityWolf wolf){
        /*wolf.tasks.taskEntries.clear();*/
        wolf.targetTasks.taskEntries.clear();

        wolf.tasks.addTask(0, new EntityAILeaveSpawnArea(wolf));
        wolf.targetTasks.addTask(1, new EntityAISheepEating<EntitySheep>(wolf,EntitySheep.class, false));

        wolf.setDropItemsWhenDead(false);
    }
}
