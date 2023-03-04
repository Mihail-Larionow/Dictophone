package com.example.voice_recorder;

import android.media.MediaPlayer;
import android.widget.ImageView;

import java.io.IOException;

public class AudioRecord {

    private int duration;
    private String name, date;
    private String filePath;
    private ImageView button;

    public AudioRecord(String filePath){
        name = "Без названия";
        duration = 5;
        date = "today";
        this.filePath = filePath;
    }

    //Play audio
    public void play(){
        try {
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(filePath);
            player.prepare();
            player.start();
        }catch (IOException e){

        }
    }

    //Delete audio
    public void delete(){

    }

    public String getName(){
        return name;
    }

    public String getDate(){
        return date;
    }

    public int getDuration(){
        return duration;
    }

    //Add new audio to collection
    private void add(){

    }
}
