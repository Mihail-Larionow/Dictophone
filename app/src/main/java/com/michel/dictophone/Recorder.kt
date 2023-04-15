package com.michel.dictophone

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.media.MediaRecorder
import android.os.CountDownTimer
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.michel.dictophone.adapters.RecyclerAdapter
import java.io.File

class Recorder(private var res: Resources, private var directoryPath: String){

    private val microphone: Drawable? = ResourcesCompat.getDrawable(res, R.drawable.microphone, null)
    private val recording: Drawable? = ResourcesCompat.getDrawable(res, R.drawable.recording, null)
    private var recorder: MediaRecorder? = null
    private var timer: CountDownTimer? = null
    private var lastFilePath: String? = null
    private var isRecording = false
    private var count = 0
    private val utils = Utils()

    //Starts recording audio
    private fun startRecording() = try {
        isRecording = true
        lastFilePath = createFilePath()
        @Suppress("DEPRECATION")
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

    //Stops recording audio
    private fun stopRecording() = try {
        isRecording = false
        recorder!!.stop()
    } catch (e: Exception) {
        Log.d("RECORD", "ERROR")
    }

    //Sets record button on layout
    @SuppressLint("NotifyDataSetChanged")
    fun setRecordButton(
        recordButton: ImageView,
        durationTextView: TextView,
        recordCards: MutableList<RecordCard>,
        adapter: RecyclerAdapter) {
        utils.setClickAnimation(recordButton)
        recordButton.setOnClickListener {
            if (!isRecording) {
                recordButton.setImageDrawable(recording)
                startRecording()
                addTimer(durationTextView)
            } else {
                recordButton.setImageDrawable(microphone)
                stopRecording()
                recordCards.add(0, RecordCard(res, lastFilePath!!))
                adapter.notifyDataSetChanged()
                deleteTimer(durationTextView)
            }
        }
    }

    //Loads data
    fun loadData(files: Array<File>?, recordCards: MutableList<RecordCard>) {
        recordCards.clear()
        if (files != null) for (file in files) {
            recordCards.add(0, RecordCard(res, file.path))
        }
        count = recordCards.size
    }

    //Returns a correct file path of the record
    private fun createFilePath(): String? {
        count++
        val file = File(directoryPath, "record$count.mp3")
        return file.path
    }

    private fun addTimer(textView: TextView) {
        timer = object : CountDownTimer(Long.MAX_VALUE, 500) {
            var currentDuration: Long = 0
            override fun onTick(l: Long) {
                textView.text = utils.getAudioDuration(currentDuration)
                currentDuration += 500
            }

            override fun onFinish() {
                textView.text = ""
                stopRecording()
            }
        }
        (timer as CountDownTimer).start()
    }

    private fun deleteTimer(textView: TextView) {
        textView.text = ""
        timer!!.cancel()
        timer = null
    }

}