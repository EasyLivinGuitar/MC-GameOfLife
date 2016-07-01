package com.marcel.gameoflife.config;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kipu5728 on 6/28/16.
 */
public class ModConfig {
    public static Vec3d HOME_POS = new Vec3d(275, 24, 196);

    public static Vec3d SHEEP_SPAWN_POS = new Vec3d(275, 4, 179);
    public static Vec3d WOLF_SPAWN_POS = new Vec3d(276, 4, 214);

    public static Vec3d WALL_START_POS = new Vec3d(266, 4, 196);
    public static Vec3d WALL_END_POS = new Vec3d(285, 6, 196);

    public static Vec3d ARENA_START_POS = new Vec3d(264, 3, 176);
    public static Vec3d ARENA_END_POS = new Vec3d(287, 3, 215);

    public static Vec3d ARENA_GRASS_START_POS = new Vec3d(266, 3, 181);
    public static Vec3d ARENA_GRASS_END_POS = new Vec3d(285, 3, 211);

    public static int INITIAL_POPULATION = 20;

    public static KeyBinding SHEEP_SPAWN_BUTTON;
    public static KeyBinding WOLF_SPAWN_BUTTON;
    public static KeyBinding RESET_BUTTON;
    public static KeyBinding START_BUTTON;

    public static List<Class> DISPLAY_ENTITY_POPULATION;

    public static long SHEEP_EATING_PERIOD = 1000;
    public static long SHEEP_STARVING_PERIOD = 8000;

    public static long WOLF_STARVING_PERIOD = 6000;

    public ModConfig(){
        SHEEP_SPAWN_BUTTON = new KeyBinding("key.spawn.sheep", Keyboard.KEY_ADD, "key.categories.misc");
        WOLF_SPAWN_BUTTON = new KeyBinding("key.spawn.wolf", Keyboard.KEY_SUBTRACT, "key.categories.misc");
        RESET_BUTTON = new KeyBinding("key.reset.game", Keyboard.KEY_BACK, "key.categories.misc");
        START_BUTTON = new KeyBinding("key.start.game", Keyboard.KEY_RETURN, "key.categories.misc");

        DISPLAY_ENTITY_POPULATION = new ArrayList<Class>();
        DISPLAY_ENTITY_POPULATION.add(EntitySheep.class);
        DISPLAY_ENTITY_POPULATION.add(EntityWolf.class);
    }

}
