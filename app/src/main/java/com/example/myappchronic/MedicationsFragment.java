package com.example.myappchronic;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicationsFragment extends Fragment {

    private final List<MedicationItem> medications = new ArrayList<>();
    private MedicationAdapter adapter;
    
    private TextInputEditText inputName;
    private AutoCompleteTextView spinnerMedForm;
    private AutoCompleteTextView spinnerFrequency;
    private TextInputEditText inputDose;
    private TextInputEditText inputMedTime;
    
    private final String[] formTypes = {
        "Таблетка", "Капсула", "Порошок", "Инъекция", 
        "Капли", "Сироп", "Мазь", "Спрей", "Свечи"
    };
    
    private final String[] frequencies = {
        "Каждый день", "Через день", "2 раза в день", 
        "3 раза в день", "4 раза в день", "Раз в неделю", 
        "2 раза в неделю", "3 раза в неделю"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medications, container, false);
        setupList(view);
        setupAddForm(view);
        return view;
    }

    private void setupList(View view) {
        // Загружаем лекарства из общего менеджера
        medications.clear();
        medications.addAll(MedicationManager.getInstance().getMedications());

        RecyclerView recycler = view.findViewById(R.id.medsList);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MedicationAdapter(medications, position -> {
            MedicationItem item = medications.get(position);
            item.setTaken(true);
            adapter.notifyItemChanged(position);
            Toast.makeText(requireContext(), "Отметка: " + item.getName(), Toast.LENGTH_SHORT).show();
        });
        recycler.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Обновляем список при возвращении на экран
        if (adapter != null) {
            medications.clear();
            medications.addAll(MedicationManager.getInstance().getMedications());
            adapter.notifyDataSetChanged();
        }
    }

    private void setupAddForm(View view) {
        inputName = view.findViewById(R.id.inputMedName);
        spinnerMedForm = view.findViewById(R.id.spinnerMedForm);
        spinnerFrequency = view.findViewById(R.id.spinnerFrequency);
        inputDose = view.findViewById(R.id.inputMedDose);
        inputMedTime = view.findViewById(R.id.inputMedTime);
        MaterialButton btnAdd = view.findViewById(R.id.btnAddMed);

        // Настройка выпадающих списков
        ArrayAdapter<String> formAdapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            formTypes
        );
        spinnerMedForm.setAdapter(formAdapter);
        spinnerMedForm.setOnItemClickListener((parent, v, position, id) -> {
            spinnerMedForm.setText(formTypes[position], false);
        });

        ArrayAdapter<String> frequencyAdapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            frequencies
        );
        spinnerFrequency.setAdapter(frequencyAdapter);
        spinnerFrequency.setOnItemClickListener((parent, v, position, id) -> {
            spinnerFrequency.setText(frequencies[position], false);
        });

        // Выбор времени через TimePicker
        inputMedTime.setOnClickListener(v -> showTimePicker());

        btnAdd.setOnClickListener(v -> {
            String name = inputName.getText().toString().trim();
            String formType = spinnerMedForm.getText().toString().trim();
            String frequency = spinnerFrequency.getText().toString().trim();
            String dose = inputDose.getText().toString().trim();
            String time = inputMedTime.getText().toString().trim();
            
            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Введите название препарата", Toast.LENGTH_SHORT).show();
                return;
            }
            if (formType.isEmpty()) {
                Toast.makeText(requireContext(), "Выберите тип применения", Toast.LENGTH_SHORT).show();
                return;
            }
            if (frequency.isEmpty()) {
                Toast.makeText(requireContext(), "Выберите частоту приёма", Toast.LENGTH_SHORT).show();
                return;
            }
            if (time.isEmpty()) {
                Toast.makeText(requireContext(), "Выберите время приёма", Toast.LENGTH_SHORT).show();
                return;
            }
            
            MedicationItem newMed = new MedicationItem(name, time, dose.isEmpty() ? "Не указано" : dose, formType, frequency);
            
            // Добавляем в общий менеджер
            MedicationManager.getInstance().addMedication(newMed);
            
            // Добавляем в локальный список
            medications.add(newMed);
            adapter.notifyItemInserted(medications.size() - 1);
            
            // Очистка формы
            inputName.setText("");
            spinnerMedForm.setText("");
            spinnerFrequency.setText("");
            inputDose.setText("");
            inputMedTime.setText("");
            
            Toast.makeText(requireContext(), "Лекарство добавлено в календарь", Toast.LENGTH_SHORT).show();
        });
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
            requireContext(),
            (view, selectedHour, selectedMinute) -> {
                String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                inputMedTime.setText(time);
            },
            hour,
            minute,
            DateFormat.is24HourFormat(requireContext())
        );
        timePickerDialog.show();
    }
}



