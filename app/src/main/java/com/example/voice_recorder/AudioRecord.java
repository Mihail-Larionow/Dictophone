package com.example.voice_recorder;

import java.io.File;
import java.util.Date;
import android.util.Log;
import android.media.MediaPlayer;
import java.text.SimpleDateFormat;
import android.media.MediaMetadataRetriever;

public class AudioRecord {

    final MediaPlayer player;
    public boolean isPlaying;
    final int DAY = 86400000;
    final String filePath;

    public AudioRecord(String filePath){
        this.filePath = filePath;
        isPlaying = false;
        player = new MediaPlayer();
    }

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

    public void stop(){
        try {
            player.stop();
        }catch (Exception e){
            Log.d("STOP", "ERROR");
        }
    }

    public String getDate(){
        File file = new File(filePath);
        Date date = new Date(file.lastModified());
        if(date.getTime() - new Date().getTime() < DAY)
            return "Сегодня " + new SimpleDateFormat("HH:mm").format(date);
        else if (date.getTime() - new Date().getTime() < 2*DAY)
            return "Вчера " + new SimpleDateFormat("HH:mm").format(date);
        return new SimpleDateFormat("dd.MM HH:mm").format(date);
    }

    public int getDuration(){
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(filePath);
        return Integer.parseInt(
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        );
    }

    public int getCurrentDuration(){
        return player.getCurrentPosition();
    }

}
