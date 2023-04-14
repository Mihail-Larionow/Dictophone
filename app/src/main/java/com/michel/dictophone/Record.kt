package com.michel.dictophone

import android.annotation.SuppressLint
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Record(private var filePath: String){

    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private val file : File = File(filePath)
    private var isPlaying: Boolean = false
    private val day = 86400000

    //Starts playing a record
    fun play() = try {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(filePath)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }catch (e: Exception){
        Log.e("mediaPlayer", "ERROR")
    }

    //Stops playing a record
    fun stop() = try {
        mediaPlayer.stop()
    }catch (e: Exception){
        Log.e("mediaPlayer", "ERROR")
    }

    //Returns date of the file
    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        val date = Date(file.lastModified())
        if (date.time - Date().time < day)
            return "Сегодня " + SimpleDateFormat("HH:mm").format(date)
        else if (date.time - Date().time < 2 * day)
            return "Вчера " + SimpleDateFormat("HH:mm").format(date)
        return SimpleDateFormat("dd.MM HH:mm").format(date)
    }

    //Returns duration of the record
    fun getDuration(): Long {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(filePath)
        return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toLong()
    }

    //Returns current duration of the record
    fun getCurrentDuration(): Int {
        return mediaPlayer.currentPosition
    }

    //Returns state of the record
    fun getState(): Boolean {
        return isPlaying
    }

    //Set state of the record
    fun setState(state: Boolean){
        isPlaying = state
    }

}