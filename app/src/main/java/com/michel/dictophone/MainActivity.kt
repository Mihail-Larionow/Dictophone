package com.michel.dictophone

import android.content.ContextWrapper
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.michel.dictophone.adapters.RecyclerAdapter
import java.io.File

class MainActivity : AppCompatActivity() {

    private val utils = Utils()
    private var permissionWasGranted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if(!permissionWasGranted && utils.readPermissionGranted(this)) {
            permissionWasGranted = true
            init()
            Log.w("Resume", "Resumed")
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
        val adapter = RecyclerAdapter(recordCards, recorder, this)
        recycler.adapter = adapter
        recorder.setRecordButton(recordButton, durationTextView, recordCards, adapter, this)
    }


    //Returns directory with files
    private fun getDirectory(): File? {
        val contextWrapper = ContextWrapper(applicationContext)
        return contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
    }

}