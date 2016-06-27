package com.marcel.gameoflife.ais;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * Created by marcel on 23.06.16.
 */
public class EntityAISimpleEatGrass extends EntityAIBase {
    private EntityLiving grassEater;
    int eatTimer;
    int timerDefault;

    public EntityAISimpleEatGrass(EntityLiving grassEater, int timer){
        this.grassEater = grassEater;
        this.timerDefault = timer;
//        this.setMutexBits(8);
    }

    @Override
    public boolean shouldExecute() {
        if(this.eatTimer == 0){
            this.eatTimer = this.timerDefault;
            return true;
        }
        else{
            this.eatTimer--;
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
        /*Random random = new Random(12314);

        if(random.nextInt()%10 < 3){
            this.grassEater.worldObj.setBlockState(pos, Blocks.LAVA.getDefaultState());
        }
        else{

            this.grassEater.worldObj.setBlockState(pos, Blocks.GRASS.canGrow());
        }*/


    }


}
