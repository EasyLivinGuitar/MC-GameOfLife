package com.marcel.gameoflife.misc.osc;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

import java.net.SocketException;
import java.util.Date;

/**
 * Created by kipu5728 on 7/5/16.
 */
public class OscReceiver {
    private OSCPortIn receiver;

    public OscReceiver(int port){
        try {
            this.receiver = new OSCPortIn(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        OSCListener listener = new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                System.out.println(oscMessage);
            }
        };

        this.receiver.addListener("/minecraft",listener);
        this.receiver.startListening();
    }

    public static void main(String argv[]){
        OscReceiver receiver = new OscReceiver(6666);
        receiver.listen();
    }
}
