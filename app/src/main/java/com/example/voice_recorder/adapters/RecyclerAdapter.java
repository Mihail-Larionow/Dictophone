package com.example.voice_recorder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voice_recorder.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<RecordCard> recordCards;

    public RecyclerAdapter(List<RecordCard> recordCards){
        this.recordCards = recordCards;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.audio_card, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.setData(recordCards.get(position));
    }

    @Override
    public int getItemCount() {
        return recordCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView nameText, dateText, durationText;
        final ImageView playButton;
        final ProgressBar durationBar;

        public ViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.nameText);
            dateText = (TextView) view.findViewById(R.id.dateText);
            durationText = (TextView) view.findViewById(R.id.durationText);
            playButton = (ImageView) view.findViewById(R.id.playButton);
            durationBar = (ProgressBar) view.findViewById(R.id.durationBar);
        }

        public void setData(RecordCard recordCard){
            nameText.setText(recordCard.getName());
            dateText.setText(recordCard.getDate());
            durationText.setText(recordCard.getDuration());
            recordCard.setPlayButton(playButton);
        }
    }

}
