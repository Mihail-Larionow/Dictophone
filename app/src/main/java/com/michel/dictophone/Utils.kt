package com.michel.dictophone

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    private val microphonePermission = 100
    private val readPermission = 300
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

    //Stops playing record cards
    fun stopPlayingRecords(cards: List<RecordCard>){
        for(card: RecordCard in cards){
            if(card.getState()) card.stopPlayingRecord()
        }
    }

    //Check microphone is unable
    fun hasMicrophone(activity: MainActivity): Boolean {
        return activity.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
    }

    //Returns if it has read external storage permission
    fun readPermissionGranted(activity: MainActivity): Boolean {
        return if (ContextCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), readPermission
            )
            false
        } else true
    }

    //Returns if it has record audio permission
    fun recordPermissionGranted(activity: MainActivity): Boolean {
        if (ContextCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.RECORD_AUDIO
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.RECORD_AUDIO
                ), microphonePermission
            )
            return false
        }
        return true
    }

}