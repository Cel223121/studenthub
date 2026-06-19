package com.example.studenthub.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenthub.R;
import com.example.studenthub.models.ElearningResource;

import java.util.List;

public class ElearningResourceAdapter extends RecyclerView.Adapter<ElearningResourceAdapter.ViewHolder> {

    private List<ElearningResource> list;

    public ElearningResourceAdapter(List<ElearningResource> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_elearning_resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ElearningResource resource = list.get(position);
        holder.tvTitle.setText(resource.getTitle());
        holder.tvDesc.setText(resource.getDescription());

        holder.itemView.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resource.getUrl()));
                v.getContext().startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "Could not open resource", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvResourceTitle);
            tvDesc = itemView.findViewById(R.id.tvResourceDesc);
        }
    }
}