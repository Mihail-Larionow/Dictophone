package com.example.voice_recorder;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import java.io.File;

public class AudioRecorder {

    private MediaRecorder recorder;
    final String directoryPath;
    final Drawable microphone, recording;
    private boolean isRecording;
    int count;

    public AudioRecorder(Resources res, String directoryPath, int count){
        recorder = new MediaRecorder();
        isRecording = false;

        microphone = ResourcesCompat.getDrawable(res, R.drawable.microphone, null);
        recording = ResourcesCompat.getDrawable(res, R.drawable.recording, null);

        this.directoryPath = directoryPath;
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        this.count = count;
    }

    public void startRecording(){
        try{
            recorder.setOutputFile(createFilePath());
            recorder.prepare();
            recorder.start();
            Log.d("RECORD", "STARTED"); //Debug
        }catch (Exception e) {
            Log.d("RECORD", "ERROR"); //Debug
        }
    }

    public void stopRecording(){
        try {
            recorder.stop();
            recorder.release();
            recorder = null;
            Log.d("RECORD", "STOPPED"); //Debug
        }catch (Exception e){
            Log.d("RECORD", "ERROR"); //Debug
        }
    }

    private String createFilePath(){
        count++;
        File file = new File(directoryPath, "record" + (count) + ".mp3");
        return file.getPath();
    }
    public void setRecordButton(ImageView recordButton){
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRecording){
                    recordButton.setImageDrawable(recording);
                    isRecording = true;
                    startRecording();
                }
                else{
                    recordButton.setImageDrawable(microphone);
                    isRecording = false;
                    stopRecording();
                }
            }
        });
    }
}
