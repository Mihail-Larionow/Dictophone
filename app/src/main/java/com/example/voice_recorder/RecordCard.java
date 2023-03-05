package com.example.voice_recorder;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.voice_recorder.AudioRecord;
import com.example.voice_recorder.R;

public class RecordCard {
    private String name;
    final String duration;
    final AudioRecord audioRecord;
    final Drawable playing, paused;
    private CountDownTimer timer;
    private ImageView playButton;
    private TextView durationTextView;
    private ProgressBar progressBar;

    public RecordCard(Resources res, String filePath){
        name = "Без названия";
        audioRecord = new AudioRecord(filePath);
        playing = ResourcesCompat.getDrawable(res, R.drawable.playing, null);
        paused = ResourcesCompat.getDrawable(res, R.drawable.paused, null);
        duration = getAudioDuration(audioRecord.getDuration());
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

    private String getAudioDuration(int recordDuration){
        int seconds = (recordDuration/1000)%60;
        int minutes = (recordDuration/60000)%60;
        int hours = recordDuration/3600000;

        String duration ="";
        if(hours > 0) duration += hours + ":";
        if(minutes > 0) duration += minutes + ":";
        else duration += 0 + ":";
        if(seconds > 0) duration += Integer.toString(seconds);
        else duration += Integer.toString(0);

        return duration;
    }

    public String getDuration(){
        return duration;
    }

    public void setDurationTextView(TextView textView){
        durationTextView = textView;
    }

    public void setPlayButton(ImageView playButton){
        this.playButton = playButton;
        this.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!audioRecord.isPlaying){
                    playButton.setImageDrawable(paused);
                    audioRecord.isPlaying = true;
                    audioRecord.play();
                    addTimer();
                }
                else{
                    playButton.setImageDrawable(playing);
                    audioRecord.isPlaying = false;
                    deleteTimer();
                    audioRecord.stop();
                }
            }
        });
    }

    public void setProgressBar(ProgressBar progressBar){
        this.progressBar = progressBar;
        this.progressBar.setMax(audioRecord.getDuration());
    }

    private void addTimer(){
        timer = new CountDownTimer(audioRecord.getDuration(), 500) {
            @Override
            public void onTick(long l) {
                int currentDuration = audioRecord.getCurrentDuration();
                durationTextView.setText(
                        getAudioDuration(currentDuration)
                        + " / " + duration
                );
                progressBar.setProgress(currentDuration);
            }
            @Override
            public void onFinish() {
                playButton.setImageDrawable(playing);
                audioRecord.isPlaying = false;
                durationTextView.setText(duration);
                progressBar.setProgress(0);
            }
        };
        timer.start();
    }

    private void deleteTimer(){
        timer.cancel();
        timer = null;
    }

}
