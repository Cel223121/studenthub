package com.example.studenthub.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;
import com.example.studenthub.models.Student;
import com.example.studenthub.activities.EditStudentActivity;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private ArrayList<Student> list;

    public StudentAdapter(ArrayList<Student> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {

        Student student = list.get(position);

        holder.name.setText(student.getName());
        holder.email.setText(student.getEmail());
        holder.course.setText(student.getCourse());

        // VIEW DETAILS
        holder.btnView.setOnClickListener(v ->
                Toast.makeText(v.getContext(),
                        "Student: " + student.getName() +
                                "\nEmail: " + student.getEmail() +
                                "\nCourse: " + student.getCourse(),
                        Toast.LENGTH_LONG).show()
        );

        // EDIT STUDENT
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditStudentActivity.class);
            intent.putExtra("id", student.getId());
            intent.putExtra("name", student.getName());
            intent.putExtra("email", student.getEmail());
            intent.putExtra("course", student.getCourse());
            intent.putExtra("year", student.getYear());
            intent.putExtra("phone", student.getPhone());
            v.getContext().startActivity(intent);
        });

        // DELETE STUDENT
        holder.btnDelete.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete " + student.getName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        DatabaseHelper db = new DatabaseHelper(v.getContext());
                        
                        int id = Integer.parseInt(student.getId());
                        boolean deleted = db.deleteStudent(id);

                        if (deleted) {
                            if (currentPosition != RecyclerView.NO_POSITION) {
                                list.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                                notifyItemRangeChanged(currentPosition, list.size());
                            }
                            Toast.makeText(v.getContext(), "Student deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<Student> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, course;
        ImageButton btnEdit, btnDelete, btnView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.studentName);
            email = itemView.findViewById(R.id.studentEmail);
            course = itemView.findViewById(R.id.studentCourse);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnView = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}