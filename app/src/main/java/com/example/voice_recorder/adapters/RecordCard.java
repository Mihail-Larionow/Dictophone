package com.example.voice_recorder.adapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import com.example.voice_recorder.AudioRecord;
import com.example.voice_recorder.R;

public class RecordCard {
    private String name;
    final AudioRecord audioRecord;
    final Drawable playing, paused;

    public RecordCard(Resources res, String filePath){
        name = "Без названия";
        audioRecord = new AudioRecord(filePath);
        playing = ResourcesCompat.getDrawable(res, R.drawable.playing, null);
        paused = ResourcesCompat.getDrawable(res, R.drawable.paused, null);
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDate(){
        return audioRecord.getDate();
    }

    public String getDuration(){
        int audioDuration = audioRecord.getDuration();
        int seconds = (audioDuration/1000)%60;
        int minutes = (audioDuration/60000)%60;
        int hours = audioDuration/3600000;
        String duration ="";
        if(hours > 0) duration += hours + ":";
        if(minutes > 0) duration += minutes + ":";
        else duration += 0 + ":";
        if(seconds > 0) duration += Integer.toString(seconds);
        else duration += Integer.toString(0);

        return duration;
    }
    public void setPlayButton(ImageView playButton){
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!audioRecord.isPlaying){
                    playButton.setImageDrawable(paused);
                    audioRecord.isPlaying = true;
                    audioRecord.play();
                }
                else{
                    playButton.setImageDrawable(playing);
                    audioRecord.isPlaying = false;
                    audioRecord.stop();
                }
            }
        });
    }

}
