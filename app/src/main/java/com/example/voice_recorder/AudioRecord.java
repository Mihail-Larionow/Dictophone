package com.example.voice_recorder;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioRecord {

    final String filePath;
    final MediaPlayer player;
    public boolean isPlaying;
    final int DAY = 86400000;

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

    //Stop playing audio
    public void stop(){
        try {
            player.stop();
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

    public int getCurrentDuration(){
        return player.getCurrentPosition();
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

}
