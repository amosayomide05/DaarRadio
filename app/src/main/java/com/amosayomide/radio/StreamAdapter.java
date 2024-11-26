package com.amosayomide.radio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StreamAdapter extends RecyclerView.Adapter<StreamAdapter.StreamViewHolder> {

    private final List<Stream> streams;
    private final OnStreamClickListener listener;

    public interface OnStreamClickListener {
        void onStreamClick(Stream stream);
    }

    public StreamAdapter(List<Stream> streams, OnStreamClickListener listener) {
        this.streams = streams;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StreamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stream, parent, false);
        return new StreamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StreamViewHolder holder, int position) {
        Stream stream = streams.get(position);
        holder.textStreamName.setText(stream.getName());
        holder.imageStreamLogo.setImageResource(stream.getLogoResId());
        holder.itemView.setOnClickListener(v -> listener.onStreamClick(stream));
    }

    @Override
    public int getItemCount() {
        return streams.size();
    }

    static class StreamViewHolder extends RecyclerView.ViewHolder {
        ImageView imageStreamLogo;
        TextView textStreamName;

        StreamViewHolder(View itemView) {
            super(itemView);
            imageStreamLogo = itemView.findViewById(R.id.imageStreamLogo);
            textStreamName = itemView.findViewById(R.id.textStreamName);
        }
    }
}
