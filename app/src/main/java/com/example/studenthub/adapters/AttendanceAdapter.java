package com.example.studenthub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenthub.R;
import com.example.studenthub.models.Attendance;

import java.util.ArrayList;

public class AttendanceAdapter
        extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    ArrayList<Attendance> attendanceList;

    public AttendanceAdapter(ArrayList<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Attendance attendance = attendanceList.get(position);

        holder.txtName.setText(attendance.getStudentName());
        
        holder.txtStudentId.setText(
                "ID: " + attendance.getStudentId());

        holder.txtDate.setText(
                "Date: " + attendance.getDate());

        holder.txtStatus.setText(
                attendance.getStatus());
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtName,
                txtStudentId,
                txtDate,
                txtStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName =
                    itemView.findViewById(R.id.txtStudentName);

            txtStudentId =
                    itemView.findViewById(R.id.txtStudentId);

            txtDate =
                    itemView.findViewById(R.id.txtDate);

            txtStatus =
                    itemView.findViewById(R.id.txtStatus);
        }
    }
}