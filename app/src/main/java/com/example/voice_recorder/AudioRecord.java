package com.example.voice_recorder;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.File;
import java.util.Date;

public class AudioRecord {

    final String filePath;
    private MediaPlayer player;
    public boolean isPlaying;

    public AudioRecord(String filePath){
        this.filePath = filePath;
        isPlaying = false;
        player = new MediaPlayer();
    }

    //Play audio
    public void play(){
        try {
            player.reset();
            player.setDataSource(filePath);
            player.prepare();
            player.start();
        }catch (Exception e){
            Log.d("PLAY", "ERROR");
        }
    }

    //Stop audio
    public void stop(){
        try {
            player.stop();
            Log.d("STOP", "OKAY");
        }catch (Exception e){
            Log.d("STOP", "ERROR");
        }
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
