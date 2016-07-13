package com.marcel.gameoflife.misc.osc;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;
import com.marcel.gameoflife.config.ModConfig;
import com.marcel.gameoflife.events.EventHandler;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.common.Mod;

import java.net.SocketException;
import java.util.Date;


/**
 * Created by kipu5728 on 7/5/16.
 */
public class OscReceiver{
    private OSCPortIn receiver;

    public OscReceiver(int port){
        try {
            this.receiver = new OSCPortIn(port);
            this.listen();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        OSCListener listener = new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                if (oscMessage.getArguments().get(0).equals("sheep")) {
                    EntitySheep sheep = new EntitySheep(EventHandler.WORLD);

                    sheep.setPosition(ModConfig.SHEEP_SPAWN_POS.xCoord,
                            ModConfig.SHEEP_SPAWN_POS.yCoord,
                            ModConfig.SHEEP_SPAWN_POS.zCoord);
                    sheep.setFleeceColor(EnumDyeColor.LIGHT_BLUE);

                    EventHandler.spawnQueue.add(sheep);
                } else if (oscMessage.getArguments().get(0).equals("wolf")) {
                    EntityWolf wolf = new EntityWolf(EventHandler.WORLD);

                    wolf.setPosition(ModConfig.WOLF_SPAWN_POS.xCoord,
                            ModConfig.WOLF_SPAWN_POS.yCoord,
                            ModConfig.WOLF_SPAWN_POS.zCoord);

                    EventHandler.spawnQueue.add(wolf);
                } else if (oscMessage.getArguments().get(0).equals("start")) {
                    EventHandler.renderQueue.add(111);
                    EventHandler.gameCommandQueue.add("start");
                } else if (oscMessage.getArguments().get(0).equals("reset")) {
                    EventHandler.gameCommandQueue.add("reset");
                }
            }
        };

        this.receiver.addListener("/minecraft", listener);
        this.receiver.startListening();
    }
}
