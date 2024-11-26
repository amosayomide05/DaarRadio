package com.amosayomide.radio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context context;
    private List<Schedule> schedules;
    private ScheduleActivity scheduleActivity;

    public ScheduleAdapter(ScheduleActivity scheduleActivity, List<Schedule> schedules) {
        this.context = scheduleActivity;
        this.schedules = schedules;
        this.scheduleActivity = scheduleActivity;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        holder.title.setText(schedule.getTitle());
        holder.date.setText(schedule.getDate());
        holder.time.setText(schedule.getTime());
        holder.icon.setImageResource(schedule.getIconResId());

        // Delete Button logic
        holder.delete.setOnClickListener(v -> {
            scheduleActivity.deleteSchedule(schedule);
        });
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public void updateSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, time;
        ImageView icon, delete;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            date = itemView.findViewById(R.id.item_date);
            time = itemView.findViewById(R.id.item_time);
            icon = itemView.findViewById(R.id.item_icon);
            delete = itemView.findViewById(R.id.item_delete); // Delete button
        }
    }
}
