package com.marcel.gameoflife.events;

import com.google.common.base.Predicate;
import com.marcel.gameoflife.ais.EntityAIRandomWalking;
import com.marcel.gameoflife.ais.EntityAISimpleEatGrass;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;


/**
 * Created by kipu5728 on 6/22/16.
 */
public class EventHandler {
    private static World world;
    private static EntityPlayer player;
    private static KeyBinding BUTTON_SHEEP;
    private static KeyBinding BUTTON_WOLF;

    @SubscribeEvent
    public void logIn(PlayerEvent.PlayerLoggedInEvent event){
        if(world == null){
            world = event.player.worldObj;
        }

        if(player == null){
            player = event.player;
        }

        EntityPlayer player = event.player;
        player.addChatComponentMessage(new TextComponentString(player.getDisplayNameString()+" logged in!"));

        List<EntitySheep> sheeps =  player.worldObj.getEntities(EntitySheep.class, new Predicate<EntitySheep>() {
            @Override
            public boolean apply(@Nullable EntitySheep input) {
                return true;
            }
        });

        for(EntitySheep sheep: sheeps){
            sheep.tasks.taskEntries.clear();
            sheep.targetTasks.taskEntries.clear();
            sheep.tasks.addTask(0, new EntityAISimpleEatGrass(sheep, 100));
            sheep.tasks.addTask(1, new EntityAIRandomWalking(sheep));
//            sheep.targetTasks.addTask(1, new EntityAIRandomWalking(sheep));
        }

        Vec3d pos = new Vec3d(200, 4, 200);

        player.setPositionAndUpdate(pos.xCoord, pos.yCoord, pos.zCoord);

        /*for(int i = 0; i < height; i++){
            for(int j=0; j<length; j++){
                for(int k = 0; k < width; k++){
                    pos.add(i, j, k);
                    player.worldObj.setBlockState(pos, Blocks.SANDSTONE.getDefaultState());
                }
            }
        }*/



        BUTTON_SHEEP = new KeyBinding("key.spawn.sheep", Keyboard.KEY_L, "key.categories.misc");
        BUTTON_WOLF = new KeyBinding("key.spawn.wolf", Keyboard.KEY_K, "key.categories.misc");
//        player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, 2.0f, true);
    }

    @SubscribeEvent
    public void pickUp(PlayerEvent.ItemPickupEvent event){
        EntityPlayer player = event.player;

        player.addChatComponentMessage(new TextComponentString(player.getDisplayNameString()+" picked up: "+event.pickedUp.getDisplayName().getFormattedText()));
    }

    @SubscribeEvent
    public void breakBlock(BlockEvent.BreakEvent event){
        EntityPlayer player = event.getPlayer();

        player.addChatComponentMessage(new TextComponentString(player.getDisplayNameString()+ " broke block at "+event.getPos().toString()));
    }

    @SubscribeEvent
    public void placeBlock(BlockEvent.PlaceEvent event){
        EntityPlayer player = event.getPlayer();

        player.addChatComponentMessage(new TextComponentString(player.getDisplayNameString()+" placed block at "+event.getPos()));
    }

    @SubscribeEvent
    public void spawn(LivingSpawnEvent event){
        if(event.getEntity() instanceof EntitySheep){
            ((EntitySheep) event.getEntity()).tasks.taskEntries.clear();
            ((EntitySheep) event.getEntity()).targetTasks.taskEntries.clear();

            ((EntitySheep) event.getEntity()).tasks.addTask(0,
                    new EntityAISimpleEatGrass((EntityLiving) event.getEntity(), 100));
            ((EntitySheep) event.getEntity()).tasks.addTask(1, new EntityAIRandomWalking((EntityCreature) event.getEntity()));
//            ((EntitySheep) event.getEntity()).targetTasks.addTask(2, new EntityAIRandomWalking((EntityCreature) event.getEntity()));
        }

        if (event.getEntity() instanceof EntityMob){
            ((EntityLiving)event.getEntity()).setHealth(0);
        }
    }

    @SubscribeEvent
    public void join(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof EntitySheep){
            ((EntitySheep) event.getEntity()).tasks.taskEntries.clear();
            ((EntitySheep) event.getEntity()).targetTasks.taskEntries.clear();

            ((EntitySheep) event.getEntity()).tasks.addTask(0,
                    new EntityAISimpleEatGrass((EntityLiving) event.getEntity(), 100));
            ((EntitySheep) event.getEntity()).tasks.addTask(1, new EntityAIRandomWalking((EntityCreature) event.getEntity()));
//            ((EntitySheep) event.getEntity()).targetTasks.addTask(2, new EntityAIRandomWalking((EntityCreature) event.getEntity()));
        }
    }

    @SubscribeEvent
    public void jump(LivingEvent.LivingJumpEvent event){
        System.out.println("JUMP");
    }

    @SubscribeEvent
    public void update(LivingEvent.LivingUpdateEvent event){
        if(!(event.getEntity() instanceof EntitySheep) && !(event.getEntity() instanceof EntityWolf))
        if(event.getEntity() instanceof EntityLiving){
            ((EntityLiving)event.getEntity()).setHealth(0);
        }

        /*if(new KeyBinding("key.spawn", Keyboard.KEY_L, "key.spawn.gameoflife").isPressed()){
            System.out.println("Key Pressed.");
            if(world != null){
                System.out.println("Spawn Sheep");
                world.setSpawnPoint(world.playerEntities.get(0).playerLocation.east());
                world.spawnEntityInWorld(new EntitySheep(world));
            }
        }*/
    }

    @SubscribeEvent
    public void initEntity(EntityEvent.EntityConstructing event){
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void keyPressed(InputEvent.KeyInputEvent event){
        if(BUTTON_SHEEP.isPressed()){

            Entity sheep = new EntitySheep(world);
            sheep.setPosition(player.posX, player.posY, player.posZ+1);
            world.spawnEntityInWorld(sheep);
        }

        if(BUTTON_WOLF.isPressed()){
            EntityWolf wolf = new EntityWolf(world);
            wolf.setPosition(player.posX, player.posY, player.posZ+1);
            world.spawnEntityInWorld(wolf);
        }
    }



}
