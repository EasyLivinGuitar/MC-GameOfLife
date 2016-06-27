package com.marcel.gameoflife.ais;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.Vec3d;

/**
 * Created by marcel on 24.06.16.
 */
public class EntityAIRandomWalking extends EntityAIBase {
    private EntityCreature walker;
    private Vec3d randomPos;
    private boolean debug;

    public EntityAIRandomWalking(EntityCreature walker){
        this.walker = walker;
        this.debug = false;
//        this.setMutexBits(8);
    }

    @Override
    public boolean shouldExecute() {
        /*if(debug){
            System.out.println("EXECUTION?");
        }*/

        if(!this.walker.getNavigator().noPath()){
            if(debug){
                System.out.println("No execution!");
            }

            return false;
        }
        else{
            Vec3d pos = walker.getPositionVector();
            int randx = walker.getRNG().nextInt() % 20;
            int randz = walker.getRNG().nextInt() % 20;

            this.randomPos = new Vec3d(pos.xCoord+randx, pos.yCoord, pos.zCoord+randz);

            if(debug)
                System.out.println("Execute");

            return true;
        }
    }

    @Override
    public void startExecuting(){
        if(debug)
        System.out.println("START EXECUTION");

        boolean state = this.walker.getNavigator().tryMoveToXYZ(randomPos.xCoord, randomPos.yCoord, randomPos.zCoord,2.0);

        if(!state){
            if(debug)
            System.out.println("NO PATH!");
            this.walker.getNavigator().setPath(
                   walker.getNavigator().getPathToXYZ(
                           randomPos.xCoord, randomPos.yCoord, randomPos.zCoord),
                   2.0);
        }
    }

    @Override
    public void updateTask(){
        Entity player = this.walker.worldObj.getClosestPlayerToEntity(this.walker, 10.0);

        if(player != null){
            if(this.walker.getDistanceToEntity(player) < 5.0){
//                System.out.println(this.walker.getDistanceToEntity(player));
                debug = true;
            }
            else{
                debug = false;
            }
        }

    }
}
