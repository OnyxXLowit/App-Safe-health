package com.example.myappchronic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedViewHolder> {

    public interface OnMedicationTakenListener {
        void onTaken(int position);
    }

    private final List<MedicationItem> items;
    private final OnMedicationTakenListener listener;

    public MedicationAdapter(List<MedicationItem> items, OnMedicationTakenListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication, parent, false);
        return new MedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {
        MedicationItem item = items.get(position);
        holder.txtName.setText(item.getName());
        holder.txtTime.setText("Время: " + item.getTime());
        
        // Формируем строку с деталями: дозировка, тип применения, частота
        StringBuilder details = new StringBuilder();
        if (!item.getDose().isEmpty() && !item.getDose().equals("Не указано")) {
            details.append(item.getDose());
        }
        if (!item.getFormType().isEmpty() && !item.getFormType().equals("Не указано")) {
            if (details.length() > 0) details.append(", ");
            details.append(item.getFormType());
        }
        if (!item.getFrequency().isEmpty()) {
            if (details.length() > 0) details.append(" • ");
            details.append(item.getFrequency());
        }
        
        holder.txtDose.setText(details.length() > 0 ? details.toString() : "Детали не указаны");
        holder.btnTaken.setEnabled(!item.isTaken());
        holder.btnTaken.setText(item.isTaken() ? "Отмечено" : "Принято");

        holder.btnTaken.setOnClickListener(v -> {
            if (!item.isTaken()) {
                listener.onTaken(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MedViewHolder extends RecyclerView.ViewHolder {
        final TextView txtName;
        final TextView txtTime;
        final TextView txtDose;
        final MaterialButton btnTaken;

        MedViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtMedName);
            txtTime = itemView.findViewById(R.id.txtMedTime);
            txtDose = itemView.findViewById(R.id.txtMedDose);
            btnTaken = itemView.findViewById(R.id.btnTaken);
        }
    }
}

