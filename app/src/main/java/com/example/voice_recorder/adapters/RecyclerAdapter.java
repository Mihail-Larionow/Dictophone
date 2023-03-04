package com.example.voice_recorder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voice_recorder.AudioRecord;
import com.example.voice_recorder.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<AudioRecord> audioRecords;

    public RecyclerAdapter(List<AudioRecord> audioRecords){
        this.audioRecords = audioRecords;
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
        holder.setData(audioRecords.get(position));
    }

    @Override
    public int getItemCount() {
        return audioRecords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText, dateText, durationText;
        private ImageView playButton;
        private ProgressBar durationBar;

        public ViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.nameText);
            dateText = (TextView) view.findViewById(R.id.dateText);
            durationText = (TextView) view.findViewById(R.id.durationText);
            playButton = (ImageView) view.findViewById(R.id.playButton);
            durationBar = (ProgressBar) view.findViewById(R.id.durationBar);
        }

        public void setData(AudioRecord audioRecord){
            nameText.setText(audioRecord.getName());
            dateText.setText(audioRecord.getDate());
            durationText.setText(audioRecord.getDate());
        }
    }

}
