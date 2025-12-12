package com.example.myappchronic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final List<HistoryItem> items;

    public HistoryAdapter(List<HistoryItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem item = items.get(position);
        holder.txtDate.setText(item.getDate());
        holder.txtTitle.setText(item.getTitle());
        holder.txtDetails.setText(item.getDetails());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        final TextView txtDate;
        final TextView txtTitle;
        final TextView txtDetails;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtHistoryDate);
            txtTitle = itemView.findViewById(R.id.txtHistoryTitle);
            txtDetails = itemView.findViewById(R.id.txtHistoryDetails);
        }
    }
}

