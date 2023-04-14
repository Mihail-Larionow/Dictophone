package com.michel.dictophone

import android.Manifest
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.michel.dictophone.adapters.RecyclerAdapter
import java.io.File

class MainActivity : AppCompatActivity() {

    private val microphonePermission = 100
    private val writePermission = 200
    private val readPermission = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (hasMicrophone() && allPermissionsGranted()) {
            init()
        }
    }

    private fun init() {
        val recordCards: MutableList<RecordCard?> = ArrayList()
        val directory = getDirectory()
        val files = directory!!.listFiles()
        val recordButton = findViewById<View>(R.id.recordButton) as ImageView
        val recycler = findViewById<View>(R.id.recyclerView) as RecyclerView
        val recorder = Recorder(resources, directory.path)
        recorder.loadData(files, recordCards)
        val adapter = RecyclerAdapter(recordCards)
        recycler.adapter = adapter
        recorder.setRecordButton(recordButton, recordCards, adapter)
    }


    //Get directory with files
    private fun getDirectory(): File? {
        val contextWrapper = ContextWrapper(applicationContext)
        return contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
    }

    //Check microphone is unable
    private fun hasMicrophone(): Boolean {
        return this.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
    }

    //Get record audio permission
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

    //Get write external storage permission
    private fun getWritePermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), writePermission
            )
            return false
        }
        return true
    }

    //Get read external storage permission
    private fun getReadPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), readPermission
            )
            false
        } else true
    }

    private fun allPermissionsGranted(): Boolean {
        if (!getRecordPermission()) return false
        return if (!getWritePermission()) false else getReadPermission()
    }
}