package com.michel.dictophone

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

class RecordCard (res: Resources?, filePath: String?){

    private var durationTextView: TextView? = null
    private var progressBar: ProgressBar? = null
    var playing: Drawable? = ResourcesCompat.getDrawable(res!!, R.drawable.playing, null)
    var paused: Drawable? = ResourcesCompat.getDrawable(res!!, R.drawable.paused, null)
    var record: Record? = Record(filePath!!)
    private var playButton: ImageView? = null
    private var timer: CountDownTimer? = null
    var duration: String? = getAudioDuration(record!!.getDuration().toInt())
    private var name: String? = "Без названия"

    //Returns name of the record
    fun getName(): String? {
        return name
    }

    //Returns name of the record
    fun getDate(): String {
        return record!!.getDate()
    }

    //Returns duration of the record
    fun getRecordDuration(): String? {
        return duration
    }

    //Sets a text view on card
    fun setDurationTextView(textView: TextView?) {
        durationTextView = textView
    }

    //Sets a play button on card
    fun setPlayButton(playButton: ImageView) {
        this.playButton = playButton
        this.playButton!!.setOnClickListener {
            if (!record!!.getState()) {
                playButton.setImageDrawable(paused)
                record!!.setState(true)
                record!!.play()
                addTimer()
            } else {
                playButton.setImageDrawable(playing)
                record!!.setState(false)
                deleteTimer()
                record!!.stop()
            }
        }
    }

    //Sets a progress bar on card
    fun setProgressBar(progressBar: ProgressBar?) {
        this.progressBar = progressBar
        this.progressBar!!.max = record!!.getDuration().toInt()
    }

    //Returns the record duration in String
    private fun getAudioDuration(recordDuration: Int): String {
        val seconds = recordDuration / 1000 % 60
        val minutes = recordDuration / 60000 % 60
        val hours = recordDuration / 3600000
        var duration = ""
        if (hours > 0) duration += "$hours:"
        duration += if (minutes > 0) "$minutes:" else 0.toString() + ":"
        duration += if (seconds > 0) seconds.toString() else 0.toString()
        return duration
    }

    //Adds timer of record playing
    private fun addTimer() {
        timer = object : CountDownTimer(record!!.getDuration(), 500) {
            override fun onTick(l: Long) {
                val currentDuration: Int = record!!.getCurrentDuration()
                durationTextView!!.text = (getAudioDuration(currentDuration) + " / " + duration)
                progressBar!!.progress = currentDuration
            }

            override fun onFinish() {
                playButton!!.setImageDrawable(playing)
                record!!.setState(false)
                durationTextView!!.text = duration
                progressBar!!.progress = 0
            }
        }
        (timer as CountDownTimer).start()
    }

    private fun deleteTimer() {
        timer!!.cancel()
        timer = null
    }

}