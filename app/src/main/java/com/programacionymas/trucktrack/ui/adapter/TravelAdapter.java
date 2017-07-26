package com.programacionymas.trucktrack.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.programacionymas.trucktrack.R;
import com.programacionymas.trucktrack.model.Travel;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private ArrayList<Travel> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTravelId, tvRouteName, tvDepartureDate, tvDepartureTime;
        Button btnStartTravel;

        public ViewHolder(View v) {
            super(v);
            tvTravelId = (TextView) v.findViewById(R.id.tvTravelId);
            tvRouteName = (TextView) v.findViewById(R.id.tvRouteName);
            tvDepartureDate = (TextView) v.findViewById(R.id.tvDepartureDate);
            tvDepartureTime = (TextView) v.findViewById(R.id.tvDepartureTime);
            btnStartTravel = (Button) v.findViewById(R.id.btnStartTravel);
        }
    }

    public TravelAdapter() {
        mDataSet = new ArrayList<>();
    }

    public TravelAdapter(ArrayList<Travel> myDataSet) {
        mDataSet = myDataSet;
    }

    public void addTravels(ArrayList<Travel> travels) {
        mDataSet.addAll(travels);
        notifyDataSetChanged();
    }

    @Override
    public TravelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // Creamos una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_travel, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Travel travel = mDataSet.get(position);

        holder.tvTravelId.setText(String.valueOf(travel.getId()));
        holder.tvDepartureDate.setText("Programado para el " + travel.getDepartureDate());
        holder.tvDepartureTime.setText("A las " + travel.getDepartureTime() + " horas");
        holder.tvRouteName.setText(travel.getRouteName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}