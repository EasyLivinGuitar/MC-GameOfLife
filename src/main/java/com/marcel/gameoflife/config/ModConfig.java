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
    public static Vec3d HOME_POS;

    public static KeyBinding SHEEP_SPAWN_BUTTON;
    public static KeyBinding WOLF_SPAWN_BUTTON;

    public static List<Class> DISPLAY_ENTITY_POPULATION;

    public ModConfig(){
        HOME_POS = new Vec3d(200, 4, 200);

        SHEEP_SPAWN_BUTTON = new KeyBinding("key.spawn.sheep", Keyboard.KEY_L, "key.categories.misc");
        WOLF_SPAWN_BUTTON = new KeyBinding("key.spawn.wolf", Keyboard.KEY_K, "key.categories.misc");

        DISPLAY_ENTITY_POPULATION = new ArrayList<Class>();
        DISPLAY_ENTITY_POPULATION.add(EntitySheep.class);
        DISPLAY_ENTITY_POPULATION.add(EntityWolf.class);
    }

}
