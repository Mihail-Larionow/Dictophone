package com.example.voice_recorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.voice_recorder.adapters.RecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    private ImageView button;
    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();

        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        button = (ImageView) findViewById(R.id.recordButton);
        AudioRecorder audioRecorder = new AudioRecorder(res, button);
        recycler.setAdapter(new RecyclerAdapter(audioRecorder.audioRecords));
    }

    private void recordClick(){

    }

}