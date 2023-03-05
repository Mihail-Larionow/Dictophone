package com.example.voice_recorder;

import java.io.File;
import java.util.List;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.media.MediaRecorder;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import com.example.voice_recorder.adapters.RecyclerAdapter;

public class AudioRecorder {

    final Drawable microphone, recording;
    private MediaRecorder recorder;
    private String lastFilePath;
    private boolean isRecording;
    final String directoryPath;
    final Resources res;
    int count;

    public AudioRecorder(Resources res, String directoryPath){
        this.res = res;
        this.directoryPath = directoryPath;
        isRecording = false;
        microphone = ResourcesCompat.getDrawable(res, R.drawable.microphone, null);
        recording = ResourcesCompat.getDrawable(res, R.drawable.recording, null);
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

    public void setRecordButton(ImageView recordButton, List<RecordCard> recordCards, RecyclerAdapter adapter){
        recordButton.setOnClickListener(view -> {
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
        });
    }

    public void loadData(File[] files, List<RecordCard> recordCards){
        recordCards.clear();
        if(files != null)
            for (File file : files) {
                recordCards.add(new RecordCard(res, file.getPath()));
            }
        count = recordCards.size();
    }

    private String createFilePath(){
        count++;
        File file = new File(directoryPath, "record" + (count) + ".mp3");
        return file.getPath();
    }
}
