package com.amosayomide.radio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDatabaseHelper {
    private static final String DATABASE_NAME = "schedule.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;

    public ScheduleDatabaseHelper(Context context) {
        // Create or open database
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        createTableIfNeeded();
    }

    // Create table if it doesn't exist
    private void createTableIfNeeded() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS schedules (id INTEGER PRIMARY KEY, title TEXT, date TEXT, time TEXT, iconResId INTEGER)";
        database.execSQL(createTableSQL);
    }

    // Add a new schedule to the database
    public void addSchedule(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put("title", schedule.getTitle());
        values.put("date", schedule.getDate());
        values.put("time", schedule.getTime());
        values.put("iconResId", schedule.getIconResId());
        database.insert("schedules", null, values);
    }

    // Get all schedules from the database
    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM schedules", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule(
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("time")),
                        cursor.getInt(cursor.getColumnIndex("iconResId"))
                );
                schedule.setId(cursor.getInt(cursor.getColumnIndex("id")));
                schedules.add(schedule);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return schedules;
    }

    // Delete a schedule from the database
    public void deleteSchedule(int scheduleId) {
        database.delete("schedules", "id = ?", new String[]{String.valueOf(scheduleId)});
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }
}
