package com.example.myappchronic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class MoreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        setupButtons(view);
        return view;
    }

    private void setupButtons(View view) {
        MaterialButton btnReports = view.findViewById(R.id.btnReports);
        MaterialButton btnDiary = view.findViewById(R.id.btnDiary);
        MaterialButton btnDoctors = view.findViewById(R.id.btnDoctors);

        btnReports.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Неделя/месяц отчёты (демо)", Toast.LENGTH_SHORT).show());
        btnDiary.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Дневник заметок (демо)", Toast.LENGTH_SHORT).show());
        btnDoctors.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Список врачей (демо)", Toast.LENGTH_SHORT).show());
    }
}



