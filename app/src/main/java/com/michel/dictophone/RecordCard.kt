package com.michel.dictophone

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

class RecordCard (private var res: Resources?, private var filePath: String?){
    private var durationTextView: TextView? = null
    private var progressBar: ProgressBar? = null
    var playing: Drawable? = ResourcesCompat.getDrawable(res!!, R.drawable.playing, null)
    var paused: Drawable? = ResourcesCompat.getDrawable(res!!, R.drawable.paused, null)
    var record: Record? = Record(filePath!!)
    private var playButton: ImageView? = null
    private var timer: CountDownTimer? = null
    var duration: String? = getAudioDuration(record!!.getDuration().toInt())
    private var name: String? = "Без названия"

    fun getName(): String? {
        return name
    }

    fun getDate(): String? {
        return record!!.getDate()
    }

    fun getRecordDuration(): String? {
        return duration
    }

    fun setDurationTextView(textView: TextView?) {
        durationTextView = textView
    }

    fun setPlayButton(playButton: ImageView) {
        this.playButton = playButton
        this.playButton!!.setOnClickListener { view: View? ->
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

    fun setProgressBar(progressBar: ProgressBar?) {
        this.progressBar = progressBar
        this.progressBar!!.max = record!!.getDuration().toInt()
    }

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