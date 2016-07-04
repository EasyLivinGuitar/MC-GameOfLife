package com.marcel.gameoflife.gui;

import com.marcel.gameoflife.events.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kipu5728 on 7/3/16.
 */
public class ScoreboardGui extends GuiScreen implements GuiYesNoCallback {
    private ScaledResolution resolution;
    private FontRenderer fontRenderer;
    private Map<String, Long> scores;
    private DateFormat format;

    private String scorePath;

    public ScoreboardGui(){}

    @Override
    public void initGui(){
        this.mc = Minecraft.getMinecraft();
        this.resolution = new ScaledResolution(this.mc);
        this.fontRenderer = mc.fontRendererObj;
        this.scorePath = "./resources/scores/scores.txt";
        this.loadScores();
        this.addScore();
        this.writeScores();
        this.format = new SimpleDateFormat("mm:ss");

        EventHandler.GUI_OPENED = true;
    }

    @Override
    public void drawScreen(int x, int y, float ticks){
        this.drawDefaultBackground();

        this.drawString(this.fontRenderer, "Highscores:",
                this.resolution.getScaledWidth()/2 - this.fontRenderer.getStringWidth("Highscores:")/2,
                this.resolution.getScaledHeight()/2 - 50,
                0xe60000);

        int height = -40;
        int counter = 1;
        for(Map.Entry<String, Long> entry: this.scores.entrySet()){

            this.drawString(this.fontRenderer, String.format("%02d. %-10s%-25s",counter, format.format(entry.getValue()),entry.getKey()),
                    this.resolution.getScaledWidth()/2 - 60,
                    this.resolution.getScaledHeight()/2 + height,
                    0xFFFFFF);

            counter++;
            height+=10;

            if(counter % 11 == 0){
                break;
            }
        }


    }

    @Override
    public boolean doesGuiPauseGame(){
        return true;
    }

    @Override
    protected void keyTyped(char typedChar, int keycode){
        try {
            super.keyTyped(typedChar, keycode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(keycode == 1){
            EventHandler.GUI_OPENED = false;
        }
    }


    public void loadScores(){
        try {
            File file = new File("./resources/scores/");

            if(!file.exists()) {
                file.mkdirs();
            }

            if(new File(this.scorePath).exists()){
                this.scores = new HashMap<String, Long>();
                BufferedReader reader = new BufferedReader(new FileReader(this.scorePath));

                String line;

                while((line = reader.readLine()) != null){
                    this.scores.put(line.split(", ")[0], Long.parseLong(line.split(", ")[1]));
                }

                reader.close();

            }else{
                this.scores = new TreeMap<String, Long>();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addScore(){
        System.out.println(this.scores);

        if(this.scores.containsKey(EventHandler.PLAYER.getCustomNameTag())){
            if(this.scores.get(EventHandler.PLAYER.getCustomNameTag()) < EventHandler.TIMER.getCurrentTime()){
                this.scores.put(EventHandler.PLAYER.getCustomNameTag(), EventHandler.TIMER.getCurrentTime());
            }
            else{
                System.out.println(this.scores.get(EventHandler.PLAYER.getCustomNameTag()));

            }
        }
        else{
            this.scores.put(EventHandler.PLAYER.getCustomNameTag(), EventHandler.TIMER.getCurrentTime());
        }

        Map<String, Long> sorted = new TreeMap<String, Long>(new ValueComparator(this.scores));
        sorted.putAll(this.scores);

        this.scores = sorted;
    }

    public void writeScores(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.scorePath));


            for(Map.Entry<String, Long> entry: this.scores.entrySet()){
                writer.write(entry.getKey()+", "+entry.getValue()+"\n");
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class ValueComparator implements Comparator<String>{
        private Map<String, Long> input;

        public ValueComparator(Map<String, Long> input){
            this.input = input;
        }

        @Override
        public int compare(String o1, String o2) {
            if(this.input == null || o1 == null || o2 == null){
                return 0;
            }

            if(!this.input.containsKey(o1) || !this.input.containsKey(o2)){
                return 0;
            }

            if(o1.equals(o2)){
                return 0;
            }

            if(input.get(o1) > input.get(o2)){
                return -1;
            }
            else if(input.get(o1) == input.get(o2)){
                return 0;
            }
            else{
                return 1;
            }
        }
    }
}
