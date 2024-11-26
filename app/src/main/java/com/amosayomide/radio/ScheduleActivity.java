package com.amosayomide.radio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private ScheduleDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Initialize the Notification Channel
        NotificationHelper.createNotificationChannel(this);

        // Initialize the database helper and RecyclerView
        dbHelper = new ScheduleDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load and display the list of schedules
        List<Schedule> schedules = dbHelper.getAllSchedules();
        adapter = new ScheduleAdapter(this, schedules);
        recyclerView.setAdapter(adapter);

        // Floating Action Button to add a new schedule
        FloatingActionButton addScheduleButton = findViewById(R.id.add_schedule_button);
        addScheduleButton.setOnClickListener(v -> showAddScheduleDialog());
    }

    private void showAddScheduleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_schedule, null);
        builder.setView(view);

        EditText titleInput = view.findViewById(R.id.schedule_title);
        EditText dateInput = view.findViewById(R.id.schedule_date);
        EditText timeInput = view.findViewById(R.id.schedule_time);
        Spinner iconSpinner = view.findViewById(R.id.icon_spinner);

        // Date and Time Picker Dialogs
        dateInput.setOnClickListener(v -> showDatePickerDialog(dateInput));
        timeInput.setOnClickListener(v -> showTimePickerDialog(timeInput));

        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = titleInput.getText().toString();
            String date = dateInput.getText().toString();
            String time = timeInput.getText().toString();
            int selectedIcon = iconSpinner.getSelectedItemPosition();

            // Convert the selected date and time to a Calendar object
            Calendar calendar = convertToCalendar(date, time);
            if (calendar != null) {
                Schedule schedule = new Schedule(title, date, time, getIconByPosition(selectedIcon));

                // Save the schedule to the database
                dbHelper.addSchedule(schedule);

                // Schedule the notification at the selected time
                NotificationHelper.scheduleNotification(ScheduleActivity.this, title, "Your radio program is starting!", calendar.getTimeInMillis());

                // Refresh the schedule list
                refreshScheduleList();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private Calendar convertToCalendar(String date, String time) {
        try {
            Calendar calendar = Calendar.getInstance();
            String[] dateParts = date.split("-");
            String[] timeParts = time.split(":");

            // Set the date and time
            calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[2]));
            calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1);  // Month is 0-based
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[0]));
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
            calendar.set(Calendar.SECOND, 0);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showDatePickerDialog(EditText dateInput) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth1) -> {
            dateInput.setText(String.format("%02d-%02d-%04d", dayOfMonth1, month1 + 1, year1));
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(EditText timeInput) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            timeInput.setText(String.format("%02d:%02d", hourOfDay, minute1));
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void refreshScheduleList() {
        List<Schedule> schedules = dbHelper.getAllSchedules();
        adapter.updateSchedules(schedules);
    }

    private int getIconByPosition(int position) {
        switch (position) {
            case 0: return R.drawable.ic_icon1;
            case 1: return R.drawable.ic_icon2;
            case 2: return R.drawable.ic_icon3;
            case 3: return R.drawable.ic_icon4;
            default: return R.drawable.ic_icon1;
        }
    }

    // Deleting a schedule
    public void deleteSchedule(Schedule schedule) {
        dbHelper.deleteSchedule(schedule.getId());
        refreshScheduleList();
    }
}
