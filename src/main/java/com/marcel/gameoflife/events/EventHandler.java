package com.marcel.gameoflife.events;

import com.marcel.gameoflife.config.ModConfig;
import com.marcel.gameoflife.handler.SheepHandler;
import com.marcel.gameoflife.hudelements.PopulationStats;
import com.marcel.gameoflife.logic.Game;
import com.marcel.gameoflife.misc.predicates.PassThrough;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by kipu5728 on 6/22/16.
 */

public class EventHandler {
    private static World WORLD;
    private static EntityPlayer PLAYER;

    private SheepHandler SHEEP_HANDLER;

    private static ModConfig CONFIG;

    private PopulationStats STATS;
    private Map<String, Integer> currentStats;

    private List<Entity> spawnQueue;

    private int sheepCounter = 0;
    private int wolfCounter = 0;

    private boolean killAll;

    public EventHandler(){
        CONFIG = new ModConfig();
        SHEEP_HANDLER = new SheepHandler();

        spawnQueue = new ArrayList<Entity>();
        killAll = false;
    }

    @SubscribeEvent
    public void init(GuiScreenEvent.InitGuiEvent event){
        STATS = new PopulationStats();
        currentStats = new ConcurrentHashMap<String, Integer>();
    }

    @SubscribeEvent
    public void logIn(PlayerEvent.PlayerLoggedInEvent event){
        if(WORLD == null){
            WORLD = event.player.worldObj;
        }

        if(PLAYER == null){
            PLAYER = event.player;
        }

        WORLD.setAllowedSpawnTypes(false, false);

        List<EntitySheep> sheeps =  WORLD.getEntities(EntitySheep.class, new PassThrough<EntitySheep>());

        for(EntitySheep sheep: sheeps){
            SHEEP_HANDLER.initAI(sheep);
        }

        Game.reset(WORLD, PLAYER);
        killAll = true;

//        PLAYER.setPositionAndUpdate(CONFIG.HOME_POS.xCoord, CONFIG.HOME_POS.yCoord, CONFIG.HOME_POS.zCoord);
    }

    @SubscribeEvent
    public void pickUp(PlayerEvent.ItemPickupEvent event){
//        PLAYER.addChatComponentMessage(new TextComponentString(PLAYER.getDisplayNameString()+" picked up: "+event.pickedUp.getDisplayName().getFormattedText()));
    }

    @SubscribeEvent
    public void breakBlock(BlockEvent.BreakEvent event){
//        PLAYER.addChatComponentMessage(new TextComponentString(PLAYER.getDisplayNameString()+ " broke block at "+event.getPos().toString()));
    }

    @SubscribeEvent
    public void placeBlock(BlockEvent.PlaceEvent event){
//        PLAYER.addChatComponentMessage(new TextComponentString(PLAYER.getDisplayNameString()+" placed block at "+event.getPos()));
    }

    @SubscribeEvent
    public void spawn(LivingSpawnEvent event){
        if(event.getEntity() instanceof EntitySheep){
            SHEEP_HANDLER.initAI((EntitySheep) event.getEntity());
        }
    }

    @SubscribeEvent
    public void join(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof EntitySheep){
            SHEEP_HANDLER.initAI((EntitySheep) event.getEntity());
        }
    }

    @SubscribeEvent
    public void jump(LivingEvent.LivingJumpEvent event){
//        System.out.println("JUMP");
    }

    @SubscribeEvent
    public void update(LivingEvent.LivingUpdateEvent event){
        if(WORLD!=null)
        if(event.getEntity() instanceof EntityLiving) {
            if(!killAll) {
                if(!(event.getEntity().getClass().getSimpleName().equals("EntitySheep"))
                        && !(event.getEntity().getClass().getSimpleName().equals("EntityWolf"))) {
//                    ((EntityLiving)event.getEntity()).setHealth(0);
                    WORLD.removeEntity(event.getEntity());
                }
            }
            else{
                event.getEntityLiving().setHealth(0);
//                WORLD.removeEntity(event.getEntity());
            }

            if(event.getEntity().posX < CONFIG.ARENA_START_POS.xCoord ||
                    event.getEntity().posX > CONFIG.ARENA_END_POS.xCoord){
                event.getEntityLiving().setHealth(0);
            }

            if(event.getEntity().posZ < CONFIG.ARENA_START_POS.zCoord
                    || event.getEntity().posZ > CONFIG.ARENA_END_POS.zCoord){
                event.getEntityLiving().setHealth(0);
            }

        }

        if(STATS.getStat("EntitySheep") == 0 && STATS.getStat("EntityWolf") == 0){
            killAll = false;
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event){
        if(currentStats != null){
            STATS.drawStats(currentStats);
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event){
        if(WORLD != null) {
            for (Class entityType : CONFIG.DISPLAY_ENTITY_POPULATION) {
                try{
                    if(!WORLD.isRemote){
                        currentStats.put(entityType.getSimpleName(), Minecraft.getMinecraft().theWorld.countEntities(entityType));
                    }


                }
                catch (Exception e){
                    System.out.println("ERROR: Unable to get stats.");
                }
            }

            if(!spawnQueue.isEmpty()){
                try{
                    if(!WORLD.isRemote)
                        WORLD.spawnEntityInWorld(spawnQueue.get(0));
                }
                catch (Exception e){
                    System.out.println("ERROR: Unable to spawn.");
                }


                spawnQueue.remove(0);
            }
        }
    }

    private int countEntities(Class type){
        int counter = 0;
        for(Entity entity: WORLD.loadedEntityList){
            if(entity.getClass().getSimpleName().equals(type.getSimpleName())){
                counter++;
            }
        }

        return counter;
    }

    @SubscribeEvent
    public void initEntity(EntityEvent.EntityConstructing event){
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void keyPressed(InputEvent.KeyInputEvent event){
        if(CONFIG.SHEEP_SPAWN_BUTTON.isPressed()){

            EntitySheep sheep = new EntitySheep(WORLD);

            /*sheep.setPosition(PLAYER.getLookVec().xCoord + PLAYER.posX,
                    PLAYER.getLookVec().yCoord + PLAYER.posY + 1,
                    PLAYER.getLookVec().zCoord + PLAYER.posZ);*/

            sheep.setPosition(CONFIG.SHEEP_SPAWN_POS.xCoord,
                    CONFIG.SHEEP_SPAWN_POS.yCoord,
                    CONFIG.SHEEP_SPAWN_POS.zCoord);
            sheep.setFleeceColor(EnumDyeColor.LIGHT_BLUE);

            spawnQueue.add(sheep);
        }

        if(CONFIG.WOLF_SPAWN_BUTTON.isPressed()){
            EntityWolf wolf = new EntityWolf(WORLD);

            /*wolf.setPosition(PLAYER.getLookVec().xCoord + PLAYER.posX,
                    PLAYER.getLookVec().yCoord + PLAYER.posY + 1,
                    PLAYER.getLookVec().zCoord + PLAYER.posZ);*/

            wolf.setPosition(CONFIG.WOLF_SPAWN_POS.xCoord,
                    CONFIG.WOLF_SPAWN_POS.yCoord,
                    CONFIG.WOLF_SPAWN_POS.zCoord);

            spawnQueue.add(wolf);
        }

        if(CONFIG.RESET_BUTTON.isPressed()){
            Game.reset(WORLD, PLAYER);
            killAll = true;
        }
    }




}
