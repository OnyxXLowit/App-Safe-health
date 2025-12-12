package com.example.myappchronic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private final List<MedicationItem> medsToday = new ArrayList<>();
    private final List<HistoryItem> history = new ArrayList<>();

    private MedicationAdapter medicationAdapter;
    private HistoryAdapter historyAdapter;

    private TextView vitalPulse;
    private TextView vitalPressure;
    private TextView vitalUpdated;
    private TextView upcomingTitle;
    private TextView upcomingTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bindViews(view);
        setupVitals(view);
        setupMeds(view);
        setupHistory(view);
        return view;
    }

    private void bindViews(View view) {
        vitalPulse = view.findViewById(R.id.vitalPulse);
        vitalPressure = view.findViewById(R.id.vitalPressure);
        vitalUpdated = view.findViewById(R.id.vitalUpdated);
        upcomingTitle = view.findViewById(R.id.upcomingTitle);
        upcomingTime = view.findViewById(R.id.upcomingTime);
    }

    private void setupVitals(View view) {
        vitalPulse.setText("Пульс: 78 уд/мин");
        vitalPressure.setText("Давление: 124/78 мм рт.ст.");
        vitalUpdated.setText("Обновлено: сегодня, 08:10");

        MaterialButton refresh = view.findViewById(R.id.btnRefreshVitals);
        refresh.setOnClickListener(v -> {
            vitalPulse.setText("Пульс: 76 уд/мин");
            vitalPressure.setText("Давление: 122/80 мм рт.ст.");
            vitalUpdated.setText("Обновлено: сейчас, из тонометра");
            history.add(0, new HistoryItem("Сегодня", "Обновление показателей",
                    "Пульс 76, давление 122/80"));
            historyAdapter.notifyItemInserted(0);
        });
    }

    private void setupMeds(View view) {
        // Загружаем лекарства из общего менеджера
        medsToday.clear();
        medsToday.addAll(MedicationManager.getInstance().getTodayMedications());

        RecyclerView medsRecycler = view.findViewById(R.id.homeMedsRecycler);
        medsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        medicationAdapter = new MedicationAdapter(medsToday, position -> {
            MedicationItem item = medsToday.get(position);
            item.setTaken(true);
            medicationAdapter.notifyItemChanged(position);
            history.add(0, new HistoryItem("Сегодня", "Лекарство: " + item.getName(),
                    "Отметка о приёме " + item.getTime()));
            historyAdapter.notifyItemInserted(0);
            updateUpcoming();
        });
        medsRecycler.setAdapter(medicationAdapter);

        updateUpcoming();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Обновляем список лекарств при возвращении на главный экран
        if (medicationAdapter != null) {
            medsToday.clear();
            medsToday.addAll(MedicationManager.getInstance().getTodayMedications());
            medicationAdapter.notifyDataSetChanged();
            updateUpcoming();
        }
    }

    private void setupHistory(View view) {
        history.clear();
        history.add(new HistoryItem("Сегодня", "Пульс", "78 уд/мин, после прогулки"));
        history.add(new HistoryItem("Сегодня", "Давление", "124/78 мм рт.ст."));
        history.add(new HistoryItem("Вчера", "Отчёт", "Краткий недельный отчёт сформирован"));

        RecyclerView historyRecycler = view.findViewById(R.id.homeHistoryRecycler);
        historyRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        historyAdapter = new HistoryAdapter(history);
        historyRecycler.setAdapter(historyAdapter);
    }

    private void updateUpcoming() {
        MedicationItem next = null;
        for (MedicationItem item : medsToday) {
            if (!item.isTaken()) {
                next = item;
                break;
            }
        }
        if (next == null) {
            upcomingTitle.setText("Все приёмы на сегодня закрыты");
            upcomingTime.setText("История обновлена");
        } else {
            upcomingTitle.setText(next.getName());
            upcomingTime.setText("Следующий приём: " + next.getTime());
        }
    }
}



