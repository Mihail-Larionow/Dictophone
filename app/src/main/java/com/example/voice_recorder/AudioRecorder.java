package com.example.voice_recorder;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import com.example.voice_recorder.adapters.RecyclerAdapter;

import java.io.File;
import java.util.List;

public class AudioRecorder {

    private ImageView recordButton;
    private MediaRecorder recorder;
    private String directoryPath, lastFilePath;
    final Drawable microphone, recording;
    private boolean isRecording;
    int count;
    private Resources res;

    public AudioRecorder(Resources res, String directoryPath, List<RecordCard> recordCards){
        this.res = res;
        count = recordCards.size();
        isRecording = false;

        microphone = ResourcesCompat.getDrawable(res, R.drawable.microphone, null);
        recording = ResourcesCompat.getDrawable(res, R.drawable.recording, null);

        this.directoryPath = directoryPath;
    }

    public void startRecording(){
        try{
            lastFilePath = createFilePath();
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(lastFilePath);
            recorder.prepare();
            recorder.start();
        }catch (Exception e) {
            Log.d("RECORD", "ERROR");
        }
    }

    public void stopRecording(){
        try {
            recorder.stop();
        }catch (Exception e){
            Log.d("RECORD", "ERROR");
        }
    }

    private String createFilePath(){
        count++;
        File file = new File(directoryPath, "record" + (count) + ".mp3");
        return file.getPath();
    }

    public void setRecordButton(ImageView recordButton, List<RecordCard> recordCards, RecyclerAdapter adapter){
        this.recordButton = recordButton;
        this.recordButton.setOnClickListener(new View.OnClickListener() {
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
                    recordCards.add(new RecordCard(res, lastFilePath));
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void loadData(File[] files, List<RecordCard> recordCards){
        recordCards.clear();
        if(files != null)
            for (File file : files) {
                recordCards.add(new RecordCard(res, file.getPath()));
                Log.d("Files", "FileName:" + file.getName()); //Debug
            }
        else {
            Log.d("Files", "No"); //Debug
        }
    }
}
