package com.michel.dictophone

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    private val day = 86400000
    //Returns the record duration in String
    fun getAudioDuration(recordDuration: Long): String {
        val seconds = recordDuration / 1000 % 60
        val minutes = recordDuration / 60000 % 60
        val hours = recordDuration / 3600000
        var duration = ""
        if (hours > 0) duration += "$hours:"
        duration += if (minutes > 0) "$minutes:" else 0.toString() + ":"
        duration += if (seconds > 0) seconds.toString() else 0.toString()
        return duration
    }

    //Returns date of the file
    @SuppressLint("SimpleDateFormat")
    fun getDate(file: File): String {
        val date = Date(file.lastModified())
        if (Date().time - date.time < day)
            return "Сегодня " + SimpleDateFormat("HH:mm").format(date)
        else if (Date().time - date.time < 2 * day)
            return "Вчера " + SimpleDateFormat("HH:mm").format(date)
        return SimpleDateFormat("dd.MM HH:mm").format(date)
    }

    //Checks if any record is playing at the time
    fun checkPlayingRecords(cards: List<RecordCard>): Boolean{
        for(card: RecordCard in cards){
            if(card.getState()) return true
        }
        return false
    }

    //Sets click animation
    fun setClickAnimation(view: View){
        val scaleUpAnimation = AnimationUtils.loadAnimation(view.context, R.anim.scale_up)
        val scaleDownAnimation = AnimationUtils.loadAnimation(view.context, R.anim.scale_down)
        view.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> view.startAnimation(scaleUpAnimation)
                    MotionEvent.ACTION_UP -> view.startAnimation(scaleDownAnimation)
                }

                return v?.onTouchEvent(event) ?: true
            }
        })
    }

}