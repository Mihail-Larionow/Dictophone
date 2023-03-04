package com.example.voice_recorder;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioRecorder {

    private MediaRecorder recorder;
    private String audioFilePath;
    private ImageView recordButton;
    private Drawable microphone, recording;
    private boolean isRecording;
    public List<AudioRecord> audioRecords;

    public AudioRecorder(Resources res, ImageView recordButton){
        recorder = new MediaRecorder();
        this.recordButton = recordButton;
        buttonClickListener();
        isRecording = false;
        audioRecords = new ArrayList<>();

        microphone = ResourcesCompat.getDrawable(res, R.drawable.microphone, null);
        recording = ResourcesCompat.getDrawable(res, R.drawable.recording, null);
        recordButton.setImageDrawable(microphone);

        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "test.3gp";
        //recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        //recorder.setOutputFile(audioFilePath);

        audioRecords.add(new AudioRecord(audioFilePath));
        audioRecords.add(new AudioRecord(audioFilePath));
    }

    public void startRecording(){
        try{
            recorder.prepare();
            recorder.start();
        }catch (IllegalStateException e){

        }catch (IOException e){

        }
    }

    public void stopRecording(){
        //recorder.stop();
        //recorder.release();
        //recorder = null;
        audioRecords.add(new AudioRecord( audioFilePath));
    }

    private void buttonClickListener(){
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRecording){
                    recordButton.setImageDrawable(microphone);
                    isRecording = true;
                    startRecording();
                }
                else{
                    recordButton.setImageDrawable(recording);
                    isRecording = false;
                    stopRecording();
                }
            }
        });
    }
}
