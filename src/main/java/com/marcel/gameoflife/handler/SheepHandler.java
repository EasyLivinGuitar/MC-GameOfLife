package com.marcel.gameoflife.handler;

import com.marcel.gameoflife.ais.EntityAIRandomWalking;
import com.marcel.gameoflife.ais.EntityAISimpleEatGrass;
import net.minecraft.entity.passive.EntitySheep;

/**
 * Created by kipu5728 on 6/28/16.
 */
public class SheepHandler {
    public void initAI(EntitySheep sheep){
        sheep.tasks.taskEntries.clear();
        sheep.targetTasks.taskEntries.clear();

        sheep.tasks.addTask(0, new EntityAISimpleEatGrass(sheep, 50));
        sheep.tasks.addTask(1, new EntityAIRandomWalking(sheep));
    }
}
