package com.marcel.gameoflife.ais;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

/**
 * Created by marcel on 23.06.16.
 */
public class EntityAISimpleEatGrass extends EntityAIBase {
    private EntityLiving grassEater;
    int eatTimer;
    int timerDefault;
    int noEatSurvive;

    public EntityAISimpleEatGrass(EntityLiving grassEater, int timer){
        this.grassEater = grassEater;
        this.timerDefault = timer;
        this.noEatSurvive = 3;
        this.setMutexBits(8);
    }

    @Override
    public boolean shouldExecute() {
        if(this.eatTimer <= 0){
            this.eatTimer = this.timerDefault;
            return true;
        }

        return false;
    }

    @Override
    public void startExecuting(){
        BlockPos pos = new BlockPos(grassEater.getPosition()).down();

        if(this.grassEater.worldObj.getBlockState(pos).getBlock() == Blocks.GRASS){
            this.grassEater.worldObj.playEvent(2001, pos, Block.getIdFromBlock(Blocks.GRASS));
            this.grassEater.worldObj.setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
        }
        else{
            this.noEatSurvive--;

            if(this.noEatSurvive == 0){
                this.grassEater.setHealth(0);
            }
        }

    }

    @Override
    public void updateTask(){
        this.eatTimer--;
    }


}
