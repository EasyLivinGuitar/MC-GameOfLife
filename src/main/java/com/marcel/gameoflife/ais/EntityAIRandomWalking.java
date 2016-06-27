package com.marcel.gameoflife.ais;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

/**
 * Created by marcel on 24.06.16.
 */
public class EntityAIRandomWalking extends EntityAIBase {
    private EntityCreature walker;
    private Vec3d randomPos;

    private int runRange = 20;
    private boolean debug;

    public EntityAIRandomWalking(EntityCreature walker){
        this.walker = walker;
        this.debug = false;
    }

    @Override
    public boolean shouldExecute() {
        if(!this.walker.getNavigator().noPath()){
            if(debug){
                System.out.println("No execution!");
            }

            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void startExecuting(){
        if(debug)
            System.out.println("START EXECUTION");
    }

    @Override
    public void updateTask(){
        Entity player = this.walker.worldObj.getClosestPlayerToEntity(this.walker, 10.0);

        if(player != null){
            if(this.walker.getDistanceToEntity(player) < 5.0){
                debug = true;
            }
            else{
                debug = false;
            }
        }

        if(shouldExecute()){
            this.startWalking();
        }

    }

    public void startWalking(){
        Vec3d pos = walker.getPositionVector();
        int randx = walker.getRNG().nextInt() % 20;
        int randz = walker.getRNG().nextInt() % 20;

        int chose = walker.getRNG().nextInt() % 2;

        if(chose == 0){
            randx *= (-1);
            randz *= (-1);
        }

        this.randomPos = new Vec3d(pos.xCoord+randx, pos.yCoord, pos.zCoord+randz);

        boolean state = this.walker.getNavigator().tryMoveToXYZ(randomPos.xCoord, randomPos.yCoord, randomPos.zCoord,2.0);

        if(!state){
            if(debug)
                System.out.println("NO PATH!");

        }
    }
}
