package com.example.myappchronic;

import java.util.ArrayList;
import java.util.List;

public class MedicationManager {
    private static final MedicationManager instance = new MedicationManager();
    private final List<MedicationItem> medications = new ArrayList<>();
    
    private MedicationManager() {
        // Инициализация с демо-данными
        medications.add(new MedicationItem("Бисопролол", "08:00", "5 мг", "Таблетка", "Каждый день"));
        medications.add(new MedicationItem("Лизиноприл", "14:00", "10 мг", "Таблетка", "Каждый день"));
        medications.add(new MedicationItem("Метформин", "20:30", "850 мг", "Таблетка", "Каждый день"));
    }
    
    public static MedicationManager getInstance() {
        return instance;
    }
    
    public List<MedicationItem> getMedications() {
        return medications;
    }
    
    public void addMedication(MedicationItem medication) {
        medications.add(medication);
    }
    
    public void removeMedication(MedicationItem medication) {
        medications.remove(medication);
    }
    
    public List<MedicationItem> getTodayMedications() {
        // Возвращаем все лекарства для сегодня (можно добавить фильтрацию по дате)
        return new ArrayList<>(medications);
    }
}

