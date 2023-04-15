package com.michel.dictophone

import android.Manifest
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.michel.dictophone.adapters.RecyclerAdapter
import java.io.File

class MainActivity : AppCompatActivity() {

    private val microphonePermission = 100
    private val readPermission = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (hasMicrophone() && allPermissionsGranted()) {
            init()
        }
    }

    //Initialization
    private fun init() {
        val recordCards: MutableList<RecordCard> = ArrayList()
        val directory = getDirectory()
        val files = directory!!.listFiles()
        val recordButton = findViewById<View>(R.id.recordButton) as ImageView
        val durationTextView = findViewById<View>(R.id.durationText) as TextView
        val recycler = findViewById<View>(R.id.recyclerView) as RecyclerView
        val recorder = Recorder(resources, directory.path)
        recorder.loadData(files, recordCards)
        val adapter = RecyclerAdapter(recordCards)
        recycler.adapter = adapter
        recorder.setRecordButton(recordButton, durationTextView, recordCards, adapter)
    }


    //Returns directory with files
    private fun getDirectory(): File? {
        val contextWrapper = ContextWrapper(applicationContext)
        return contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
    }

    //Check microphone is unable
    private fun hasMicrophone(): Boolean {
        return this.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
    }

    //Returns if it has record audio permission
    private fun getRecordPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.RECORD_AUDIO
                ), microphonePermission
            )
            return false
        }
        return true
    }

    //Returns if it has read external storage permission
    private fun getReadPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), readPermission
            )
            false
        } else true
    }

    //Checks all permissions granted
    private fun allPermissionsGranted(): Boolean {
        if (!getRecordPermission()) return false
        return getReadPermission()
    }
}