package com.amosayomide.radio;

public class Schedule {
    private int id;
    private String title;
    private String date;
    private String time;
    private int iconResId;

    public Schedule(String title, String date, String time, int iconResId) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.iconResId = iconResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getIconResId() {
        return iconResId;
    }
}
