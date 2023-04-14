package com.michel.dictophone

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.media.MediaRecorder
import android.util.Log
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.michel.dictophone.adapters.RecyclerAdapter
import java.io.File

class Recorder(private var res: Resources?, private var directoryPath: String?){

    private val microphone: Drawable? = ResourcesCompat.getDrawable(res!!, R.drawable.microphone, null)
    private val recording: Drawable? = ResourcesCompat.getDrawable(res!!, R.drawable.recording, null)
    private var recorder: MediaRecorder? = null
    private var lastFilePath: String? = null
    private var isRecording = false
    private var count = 0

    //Starts recording audio
    private fun startRecording() {
        try {
            lastFilePath = createFilePath()
            recorder = MediaRecorder()
            recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder!!.setOutputFile(lastFilePath)
            recorder!!.prepare()
            recorder!!.start()
        } catch (e: Exception) {
            Log.d("RECORD", "ERROR")
        }
    }

    //Stops recording audio
    private fun stopRecording() {
        try {
            recorder!!.stop()
        } catch (e: Exception) {
            Log.d("RECORD", "ERROR")
        }
    }

    //Sets record button on layout
    @SuppressLint("NotifyDataSetChanged")
    fun setRecordButton(
        recordButton: ImageView,
        recordCards: MutableList<RecordCard?>,
        adapter: RecyclerAdapter
    ) {
        recordButton.setOnClickListener {
            if (!isRecording) {
                recordButton.setImageDrawable(recording)
                isRecording = true
                startRecording()
            } else {
                recordButton.setImageDrawable(microphone)
                isRecording = false
                stopRecording()
                recordCards.add(RecordCard(res, lastFilePath))
                adapter.notifyDataSetChanged()
            }
        }
    }

    //Loads data
    fun loadData(files: Array<File>?, recordCards: MutableList<RecordCard?>) {
        recordCards.clear()
        if (files != null) for (file in files) {
            recordCards.add(RecordCard(res, file.path))
        }
        count = recordCards.size
    }

    //Returns a correct file path of the record
    private fun createFilePath(): String? {
        count++
        val file = File(directoryPath, "record$count.mp3")
        return file.path
    }

}