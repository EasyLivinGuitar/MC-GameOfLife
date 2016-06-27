package com.marcel.gameoflife;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by marcel on 24.06.16.
 */
public class Launcher {
    public static void main(String[] argv){
        try {
            String line;
            String cmd = "./gradlew runClient";

            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}
