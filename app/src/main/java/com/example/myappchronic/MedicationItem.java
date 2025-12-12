package com.example.myappchronic;

public class MedicationItem {
    private final String name;
    private final String time;
    private final String dose;
    private final String formType; // тип применения (таблетка, порошок и т.д.)
    private final String frequency; // частота приема (каждый день, через день и т.д.)
    private boolean taken;

    public MedicationItem(String name, String time, String dose, String formType, String frequency) {
        this.name = name;
        this.time = time;
        this.dose = dose;
        this.formType = formType;
        this.frequency = frequency;
        this.taken = false;
    }

    // Конструктор для обратной совместимости
    public MedicationItem(String name, String time, String dose) {
        this(name, time, dose, "Не указано", "Каждый день");
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDose() {
        return dose;
    }

    public String getFormType() {
        return formType;
    }

    public String getFrequency() {
        return frequency;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}

