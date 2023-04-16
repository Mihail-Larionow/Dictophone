package com.michel.dictophone

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import java.io.File

class RecordCard (res: Resources, var filePath: String){

    private var durationTextView: TextView? = null
    private var progressBar: ProgressBar? = null
    var playing: Drawable? = ResourcesCompat.getDrawable(res, R.drawable.playing, null)
    var paused: Drawable? = ResourcesCompat.getDrawable(res, R.drawable.paused, null)
    var record: Record = Record(filePath)
    private var playButton: ImageView? = null
    private var timer: CountDownTimer? = null
    private var name: String = "Без названия"
    private val utils = Utils()
    private var isPlaying: Boolean = false

    //Stops playing the record
    fun stopPlayingRecord(){
        isPlaying = false
        playButton!!.setImageDrawable(playing)
        durationTextView!!.text = getRecordDuration()
        progressBar!!.progress = 0
        deleteTimer()
        record.stop()
    }

    //Returns name of the record
    fun getName() = name

    //Returns name of the record
    fun getDate() = utils.getDate(File(filePath))

    //Returns state of the record
    fun getState() = isPlaying

    //Returns duration of the record
    fun getRecordDuration() = utils.getAudioDuration(record.getDuration())

    //Sets a text view on card
    fun setDurationTextView(textView: TextView?) {
        durationTextView = textView
    }

    //Sets a play button on card
    fun setPlayButton(playButton: ImageView, otherCards: List<RecordCard>, recorder: Recorder) {
        this.playButton = playButton
        this.playButton!!.setOnClickListener {
            if (!isPlaying && !recorder.getState()) {
                utils.stopPlayingRecords(otherCards)
                playButton.setImageDrawable(paused)
                isPlaying = true
                record.play()
                addTimer()
            } else if (isPlaying) {
                stopPlayingRecord()
            }
        }
    }

    //Sets a progress bar on card
    fun setProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
        this.progressBar!!.max = record.getDuration().toInt()
    }

    //Adds timer of record playing
    private fun addTimer() {
        timer = object : CountDownTimer(record.getDuration(), 500) {
            override fun onTick(l: Long) {
                val text: String
                val currentDuration: Long = record.getCurrentDuration()
                text = utils.getAudioDuration(currentDuration) + " / " + getRecordDuration()
                durationTextView!!.text = text
                progressBar!!.progress = currentDuration.toInt()
            }

            override fun onFinish() {
                playButton!!.setImageDrawable(playing)
                isPlaying = false
                durationTextView!!.text = getRecordDuration()
                progressBar!!.progress = 0
            }
        }
        (timer as CountDownTimer).start()
    }

    //Deletes the timer
    private fun deleteTimer() {
        timer!!.cancel()
        timer = null
    }

}