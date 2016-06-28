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
    private int noEatSurvive;

    private long startTime;
    private long eatTime;


    public EntityAISimpleEatGrass(EntityLiving grassEater, long millis){
        this.grassEater = grassEater;
        this.noEatSurvive = 3;
        this.setMutexBits(8);

        this.startTime = System.currentTimeMillis();
        this.eatTime = millis;
    }

    @Override
    public boolean shouldExecute() {
        if(System.currentTimeMillis()-this.startTime >= this.eatTime){
            this.startTime = System.currentTimeMillis();
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
        /*else{
            this.noEatSurvive--;

            if(this.noEatSurvive == 0){
                this.grassEater.setHealth(0);
            }
        }*/

    }

    @Override
    public void updateTask(){
        if(shouldExecute()){
            startExecuting();
        }
    }


}
