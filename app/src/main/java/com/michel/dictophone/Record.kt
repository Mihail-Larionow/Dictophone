package com.michel.dictophone

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.util.Log

class Record(private var filePath: String){

    private var mediaPlayer: MediaPlayer = MediaPlayer()

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

    //Returns duration of the record
    fun getDuration(): Long {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(filePath)
        return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toLong()
    }

    //Returns current duration of the record
    fun getCurrentDuration() = mediaPlayer.currentPosition.toLong()

}