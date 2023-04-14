package com.michel.dictophone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michel.dictophone.R
import com.michel.dictophone.RecordCard

class RecyclerAdapter (private val recordCards: List<RecordCard?>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.audio_card, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(recordCards!![position]!!)
    }

    override fun getItemCount(): Int {
        return recordCards!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView
        val dateText: TextView
        val durationText: TextView
        val playButton: ImageView
        val durationBar: ProgressBar

        init {
            nameText = view.findViewById<View>(R.id.nameText) as TextView
            dateText = view.findViewById<View>(R.id.dateText) as TextView
            durationText = view.findViewById<View>(R.id.durationText) as TextView
            playButton = view.findViewById<View>(R.id.playButton) as ImageView
            durationBar = view.findViewById<View>(R.id.durationBar) as ProgressBar
        }

        fun setData(recordCard: RecordCard) {
            nameText.text = recordCard.getName()
            dateText.text = recordCard.getDate()
            durationText.text = recordCard.getRecordDuration()
            recordCard.setPlayButton(playButton)
            recordCard.setDurationTextView(durationText)
            recordCard.setProgressBar(durationBar)
        }
    }

}