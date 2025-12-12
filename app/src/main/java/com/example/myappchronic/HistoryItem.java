package com.example.myappchronic;

public class HistoryItem {
    private final String date;
    private final String title;
    private final String details;

    public HistoryItem(String date, String title, String details) {
        this.date = date;
        this.title = title;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }
}

