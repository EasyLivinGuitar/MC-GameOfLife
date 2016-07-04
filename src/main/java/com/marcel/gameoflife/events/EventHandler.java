package com.marcel.gameoflife.events;

import com.marcel.gameoflife.GameOfLifeMod;
import com.marcel.gameoflife.config.ModConfig;
import com.marcel.gameoflife.gui.hudelements.PopulationStats;
import com.marcel.gameoflife.gui.hudelements.RuntimeTimer;
import com.marcel.gameoflife.handler.SheepHandler;
import com.marcel.gameoflife.handler.WolfHandler;
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
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
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
    public static EntityPlayer PLAYER;

    private SheepHandler SHEEP_HANDLER;
    private WolfHandler WOLF_HANDLER;

    private static ModConfig CONFIG;
    private static Game GAME;

    public static PopulationStats STATS;
    public static RuntimeTimer TIMER;

    private Map<String, Integer> currentStats;

    private List<Entity> spawnQueue;
    private List<Integer> renderQueue;

    private boolean killAll;
    public static boolean GUI_OPENED;


    public EventHandler(){
        CONFIG = new ModConfig();
        SHEEP_HANDLER = new SheepHandler();
        WOLF_HANDLER = new WolfHandler();
        GAME = new Game();
        TIMER = new RuntimeTimer();

        spawnQueue = new ArrayList<Entity>();
        renderQueue = new ArrayList<Integer>();
        killAll = false;
        GUI_OPENED = false;
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

        GAME.reset(WORLD, PLAYER, STATS);
        killAll = true;

        PLAYER.setCustomNameTag("Player"+String.format("%03d",PLAYER.getRNG().nextInt(1000)));
    }

    @SubscribeEvent
    public void spawn(LivingSpawnEvent event){
        if(event.getEntity() instanceof EntitySheep){
            SHEEP_HANDLER.initAI((EntitySheep) event.getEntity());
        }

        if(event.getEntity() instanceof  EntityWolf){
            WOLF_HANDLER.initAI((EntityWolf) event.getEntity());
        }
    }

    @SubscribeEvent
    public void join(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof EntitySheep){
            SHEEP_HANDLER.initAI((EntitySheep) event.getEntity());
        }

        if(event.getEntity() instanceof  EntityWolf){
            WOLF_HANDLER.initAI((EntityWolf) event.getEntity());
        }
    }

    @SubscribeEvent
    public void drop(LivingDropsEvent event){
        event.getDrops().clear();
    }

    @SubscribeEvent
    public void update(LivingEvent.LivingUpdateEvent event){
        if(WORLD!=null)
        if(event.getEntity() instanceof EntityLiving) {
            if(!killAll) {
                if(!(event.getEntity().getClass().getSimpleName().equals("EntitySheep"))
                        && !(event.getEntity().getClass().getSimpleName().equals("EntityWolf"))) {
                    WORLD.removeEntity(event.getEntity());
                }
            }
            else{
                event.getEntityLiving().setHealth(0);
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

        if(GAME.isRunning() && GAME.isInitDone(STATS) &&
                (STATS.getStat("EntitySheep") == 0 || STATS.getStat("EntityWolf") == 0)){

            GAME.end();
            TIMER.stop();
            /*FMLClientHandler.instance().getClient().thePlayer.openGui(GameOfLifeMod.instance, 666, WORLD, 0, 0, 0);
            FMLClientHandler.instance().getClient().thePlayer.openGui(GameOfLifeMod.instance, 555, WORLD, 0, 0, 0);*/
            renderQueue.add(666);
            renderQueue.add(555);
            GAME.reset(WORLD, PLAYER, STATS);
        }


    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event){
        if(currentStats != null){
            STATS.drawStats(currentStats);
        }

        if(GAME.isRunning()){
            TIMER.draw();
        }

        if(!renderQueue.isEmpty() && !GUI_OPENED){
            FMLClientHandler.instance().getClient().thePlayer.openGui(GameOfLifeMod.instance, renderQueue.get(0), WORLD, 0, 0, 0);
            renderQueue.remove(0);
        }


    }

    @SubscribeEvent
    public void hurt(LivingHurtEvent event){
        if (event.getEntity() instanceof EntitySheep){
            if(event.getSource().getEntity() instanceof EntityWolf){
                ((EntityWolf) event.getSource().getEntity()).setCustomNameTag("nomz");
            }
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
            GAME.reset(WORLD, PLAYER, STATS);
            TIMER.stop();
            killAll = true;
        }

        if(CONFIG.START_BUTTON.isPressed()){
            renderQueue.add(111);
            GAME.start(WORLD, spawnQueue);
            TIMER.start();
        }
    }




}
