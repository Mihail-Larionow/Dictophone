package com.example.voice_recorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.voice_recorder.adapters.RecyclerAdapter;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int MICROPHONE_PERMISSION = 100;
    final int WRITE_PERMISSION = 200;
    final int READ_PERMISSION = 300;
    private List<RecordCard> recordCards;
    private ArrayList<VKScope> vkDocuments;
    private ImageView recordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vkDocuments = new ArrayList<VKScope>();
        vkDocuments.add(VKScope.DOCS);
        VK.login(this, vkDocuments);
    }

    //Wait until permissions will be granted
    @Override
    protected void onResume() {
        super.onResume();
        if(hasMicrophone() && allPermissionsGranted()){
            init();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKAuthCallback callback = new VKAuthCallback(){
            @Override
            public void onLoginFailed(int i) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG);
            }

            @Override
            public void onLogin(@NonNull VKAccessToken vkAccessToken) {
                Toast.makeText(getApplicationContext(), "Okay", Toast.LENGTH_LONG);
            }
        };

        if(data == null || !VK.onActivityResult(requestCode, resultCode, data, callback))
            super.onActivityResult(requestCode, resultCode, data);
    }

    //Initialization
    private void init(){
        recordCards = new ArrayList<>();

        File directory = getDirectory();
        File[] files = directory.listFiles();

        ImageView recordButton = (ImageView) findViewById(R.id.recordButton);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerView);


        AudioRecorder audioRecorder = new AudioRecorder(
                getResources(), directory.getPath(), recordCards
        );
        audioRecorder.loadData(files, recordCards);

        RecyclerAdapter adapter = new RecyclerAdapter(recordCards);
        recycler.setAdapter(adapter);
        audioRecorder.setRecordButton(recordButton, recordCards, adapter);
    }

    //Get directory with files
    private File getDirectory(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        return contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
    }

    //Check microphone is unable
    private boolean hasMicrophone(){
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    //Get record audio permission
    private boolean getRecordPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {
                    android.Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION);
            return false;
        }
        return true;
    }

    //Get write external storage permission
    private boolean getWritePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
            return false;
        }
        return true;
    }

    //Get read external storage permission
    private boolean getReadPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_PERMISSION);
            return false;
        }
        else return true;
    }

    private boolean allPermissionsGranted(){
        if(!getRecordPermission()) return false;
        if(!getWritePermission()) return false;
        if(!getReadPermission()) return false;
        return true;
    }

}