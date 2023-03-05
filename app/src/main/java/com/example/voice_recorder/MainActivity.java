package com.example.voice_recorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.example.voice_recorder.adapters.RecordCard;
import com.example.voice_recorder.adapters.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int MICROPHONE_PERMISSION = 100;
    final int WRITE_PERMISSION = 200;
    final int READ_PERMISSION = 300;
    private List<RecordCard> recordCards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(hasMicrophone()){
            getRecordPermission();
        }
        getWritePermission();
        getReadPermission();

        File directory = getDirectory();
        File[] files = directory.listFiles();

        Log.d("Files", "Directory:" + directory.getPath());

        recordCards = new ArrayList<>();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerView);
        ImageView recordButton = (ImageView) findViewById(R.id.recordButton);

        loadData(files);
        AudioRecorder audioRecorder = new AudioRecorder(
                getResources(), directory.getPath(), recordCards.size()
        );
        audioRecorder.setRecordButton(recordButton);
        recycler.setAdapter(new RecyclerAdapter(recordCards));
    }

    //Get directory with files
    private File getDirectory(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        return contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
    }

    //Load files into array
    private void loadData(File[] files){
        if(files != null)
            for (File file : files) {
                recordCards.add(new RecordCard(getResources(), file.getPath()));
                Log.d("Files", "FileName:" + file.getName()); //Debug
            }
        else {
            Log.d("Files", "No"); //Debug
        }
    }

    //Check microphone is unable
    private boolean hasMicrophone(){
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    //Get record audio permission
    private void getRecordPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {
                    android.Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION);
        }
    }

    //Get write external storage permission
    private void getWritePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
        }
    }

    //Get read external storage permission
    private void getReadPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_PERMISSION);
        }
    }

}