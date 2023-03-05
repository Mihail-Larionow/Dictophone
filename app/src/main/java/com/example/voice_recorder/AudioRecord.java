package com.example.voice_recorder;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.File;
import java.util.Date;

public class AudioRecord {

    final String filePath;
    final MediaPlayer player;
    public boolean isPlaying;

    public AudioRecord(String filePath){
        this.filePath = filePath;
        isPlaying = false;
        player = new MediaPlayer();
    }

    //Play audio
    public void play(){
        try {
            player.setDataSource(filePath);
            player.prepare();
            player.start();

            Log.d("PLAY", "STARTED"); //Debug
        }catch (Exception e){
            Log.d("PLAY", "ERROR"); //Debug
        }
    }

    public void pause(){
        player.pause();
    }
    //Delete audio
    public void delete(){

    }

    public int getDuration(){
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(filePath);
        return Integer.parseInt(
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        );
    }

    public String getDate(){
        File file = new File(filePath);
        Date date = new Date(file.lastModified());
        return date.toString();
    }

}
