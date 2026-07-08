package com.example.studenthub.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenthub.R;
import com.example.studenthub.models.CampusBuilding;

import java.util.ArrayList;

public class CampusBuildingAdapter extends RecyclerView.Adapter<CampusBuildingAdapter.ViewHolder> {

    Context context;
    ArrayList<CampusBuilding> buildingList;

    public CampusBuildingAdapter(Context context, ArrayList<CampusBuilding> buildingList) {
        this.context = context;
        this.buildingList = buildingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_building, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CampusBuilding building = buildingList.get(position);

        holder.txtBuildingName.setText(building.getBuildingName());
        holder.txtLatitude.setText(building.getLatitude());
        holder.txtLongitude.setText(building.getLongitude());
        holder.txtDate.setText(building.getDate());
        holder.txtTime.setText(building.getTime());

        holder.itemView.setOnClickListener(v -> {

            String latitude =
                    building.getLatitude()
                            .replace("Latitude : ","");

            String longitude =
                    building.getLongitude()
                            .replace("Longitude : ","");

            Uri uri = Uri.parse(
                    "geo:" +
                            latitude +
                            "," +
                            longitude +
                            "?q=" +
                            latitude +
                            "," +
                            longitude
            );

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    uri
            );

            intent.setPackage("com.google.android.apps.maps");

            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return buildingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtBuildingName, txtLatitude, txtLongitude, txtDate, txtTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBuildingName = itemView.findViewById(R.id.txtBuildingName);
            txtLatitude = itemView.findViewById(R.id.txtLatitude);
            txtLongitude = itemView.findViewById(R.id.txtLongitude);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
