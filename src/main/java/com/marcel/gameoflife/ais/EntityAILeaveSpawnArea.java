package com.marcel.gameoflife.ais;

import com.marcel.gameoflife.config.ModConfig;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by kipu5728 on 7/1/16.
 */
public class EntityAILeaveSpawnArea extends EntityAIBase {
    private EntityLiving leaver;

    public EntityAILeaveSpawnArea(EntityLiving entity){
        this.leaver = entity;
    }

    @Override
    public boolean shouldExecute() {
/*        if(this.leaver.getPositionVector() == ModConfig.WOLF_SPAWN_POS ||
                this.leaver.getPositionVector() == ModConfig.SHEEP_SPAWN_POS){
            return true;
        }*/

        if(this.leaver.getPositionVector().xCoord > ModConfig.WOLF_SPAWN_POS.xCoord - 2 &&
                this.leaver.getPositionVector().xCoord < ModConfig.WOLF_SPAWN_POS.xCoord + 2){
            if(this.leaver.getPositionVector().zCoord > ModConfig.WOLF_SPAWN_POS.zCoord - 2 &&
                    this.leaver.getPositionVector().zCoord < ModConfig.WOLF_SPAWN_POS.zCoord + 2){
                if(leaver.getNavigator().noPath())
                    return true;
            }
        }

        return false;
    }

    @Override
    public void updateTask(){
        if(shouldExecute()){
            int randx = leaver.getRNG().nextInt((int) (ModConfig.ARENA_GRASS_END_POS.xCoord - ModConfig.ARENA_GRASS_START_POS.xCoord));
            randx += ModConfig.ARENA_GRASS_START_POS.xCoord;

            int randz = leaver.getRNG().nextInt((int) (ModConfig.ARENA_GRASS_END_POS.zCoord -  ModConfig.ARENA_GRASS_START_POS.zCoord));
            randz += ModConfig.ARENA_GRASS_START_POS.zCoord;

            this.leaver.getNavigator().tryMoveToXYZ(randx, leaver.posY, randz, 1.5);
        }
    }
}
