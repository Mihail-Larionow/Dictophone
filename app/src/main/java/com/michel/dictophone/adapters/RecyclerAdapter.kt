package com.michel.dictophone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michel.dictophone.MainActivity
import com.michel.dictophone.R
import com.michel.dictophone.RecordCard
import com.michel.dictophone.Recorder

class RecyclerAdapter (
    private val recordCards: MutableList<RecordCard>,
    private val recorder: Recorder,
    private val activity: MainActivity
    ): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.audio_card, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(recordCards[position], recordCards, recorder, activity)
    }

    //Returns item count in RecyclerView
    override fun getItemCount() = recordCards.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameText: TextView
        private val dateText: TextView
        private val durationText: TextView
        private val playButton: ImageView
        private val durationBar: ProgressBar

        //Initialization
        init {
            nameText = view.findViewById<View>(R.id.nameText) as TextView
            dateText = view.findViewById<View>(R.id.dateText) as TextView
            durationText = view.findViewById<View>(R.id.durationText) as TextView
            playButton = view.findViewById<View>(R.id.playButton) as ImageView
            durationBar = view.findViewById<View>(R.id.durationBar) as ProgressBar
        }

        //Sets data
        fun setData(
            recordCard: RecordCard,
            otherCards: MutableList<RecordCard>,
            recorder: Recorder,
            activity: MainActivity
        ) {
            nameText.text = recordCard.getName()
            dateText.text = recordCard.getDate()
            durationText.text = recordCard.getRecordDuration()
            recordCard.setPlayButton(playButton, otherCards, recorder)
            recordCard.setDurationTextView(durationText)
            recordCard.setProgressBar(durationBar)
            recordCard.setRecordName(nameText, activity)
        }

    }

}