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

    private long startTime;
    private long eatTime;

    private long lastEaten;


    public EntityAISimpleEatGrass(final EntityLiving grassEater, long millis){
        this.grassEater = grassEater;
        this.setMutexBits(8);

        this.startTime = System.currentTimeMillis();
        this.lastEaten = this.startTime;
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

            this.lastEaten = System.currentTimeMillis();

        }
    }

    @Override
    public void updateTask(){
        if(shouldExecute()){
            startExecuting();
        }

        if(System.currentTimeMillis() - this.lastEaten > 8000){
            this.grassEater.setHealth(0);
        }
    }


}
