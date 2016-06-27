package com.marcel.gameoflife.ais;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by kipu5728 on 6/22/16.
 */
public class CrazySheepAI extends EntityAIBase {
    private Entity entity;
    private Entity interactor;

    public CrazySheepAI(EntityLivingBase entity){
        this.entity = entity;
    }

    public CrazySheepAI(EntityLivingBase entity, EntityLivingBase interactor){
        this.entity = entity;
        this.interactor = interactor;
    }

    @Override
    public boolean shouldExecute() {
        return true;
    }

    @Override public boolean continueExecuting(){
//        entity.dropItem(new ItemCoal(),10);

        return true;
    }

    @Override
    public void startExecuting(){
//        System.out.println(entity.getName()+" start executing!");
    }

}
