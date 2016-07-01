package com.marcel.gameoflife.logic;

import com.marcel.gameoflife.config.ModConfig;
import com.marcel.gameoflife.misc.predicates.PassThrough;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by kipu5728 on 7/1/16.
 */
public class Game {
    public static void reset(World world, EntityPlayer player){
        /*player.cameraYaw = -90;
        player.cameraPitch = 81;*/

        player.setPositionAndUpdate(ModConfig.HOME_POS.xCoord,
                ModConfig.HOME_POS.yCoord,
                ModConfig.HOME_POS.zCoord);

        if(!world.isRemote)
            world.setWorldTime(1000);

        for(EntityLiving entity: world.getEntities(EntityLiving.class, new PassThrough<Entity>())){
            entity.setHealth(0);
        }

        world.unloadEntities(world.<Entity>getEntities(EntityLiving.class, new PassThrough<Entity>()));

        for(int i = (int) ModConfig.WALL_START_POS.xCoord; i <= ModConfig.WALL_END_POS.xCoord; i++){
            for(int j = (int) ModConfig.WALL_START_POS.yCoord; j <= ModConfig.WALL_END_POS.yCoord; j++){
                world.setBlockState(new BlockPos(i, j, ModConfig.WALL_START_POS.zCoord),
                        Blocks.GOLD_BLOCK.getDefaultState());
            }
        }

        for(int i = (int) ModConfig.ARENA_GRASS_START_POS.xCoord; i <= ModConfig.ARENA_GRASS_END_POS.xCoord; i++){
            for(int j = (int) ModConfig.ARENA_GRASS_START_POS.zCoord; j<= ModConfig.ARENA_GRASS_END_POS.zCoord; j++){
                world.setBlockState(new BlockPos(i, ModConfig.ARENA_GRASS_END_POS.yCoord, j),
                        Blocks.GRASS.getDefaultState());
            }
        }
    }

}
