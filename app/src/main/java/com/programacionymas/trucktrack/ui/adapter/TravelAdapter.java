package com.programacionymas.trucktrack.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.programacionymas.trucktrack.R;
import com.programacionymas.trucktrack.io.ApiAdapter;
import com.programacionymas.trucktrack.io.response.SimpleResponse;
import com.programacionymas.trucktrack.model.Travel;
import com.programacionymas.trucktrack.ui.DrivingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private ArrayList<Travel> mDataSet;
    static Context context;
    private static String lat, lng;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Callback<SimpleResponse> {

        int travelId;
        TextView tvTravelId, tvRouteName, tvDepartureDate, tvDepartureTime;
        Button btnStartTravel;

        public ViewHolder(View v) {
            super(v);
            tvTravelId = (TextView) v.findViewById(R.id.tvTravelId);
            tvRouteName = (TextView) v.findViewById(R.id.tvRouteName);
            tvDepartureDate = (TextView) v.findViewById(R.id.tvDepartureDate);
            tvDepartureTime = (TextView) v.findViewById(R.id.tvDepartureTime);
            btnStartTravel = (Button) v.findViewById(R.id.btnStartTravel);

            btnStartTravel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int status = 1; // Driving
            Call<SimpleResponse> call = ApiAdapter.getApiService().postTravelStatus(travelId, status);
            call.enqueue(this);
        }

        @Override
        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
            if (response.isSuccessful()) {
                SimpleResponse simpleResponse = response.body();
                if (simpleResponse.isSuccess()) {
                    startDrivingActivity();
                } else {
                    Toast.makeText(context, "Inténtelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<SimpleResponse> call, Throwable t) {
            Toast.makeText(context, "Ocurrió un error inesperado", Toast.LENGTH_SHORT).show();
        }

        private void startDrivingActivity() {
            Bundle b = new Bundle();
            b.putInt("travelId", travelId);
            b.putString("travelTitle", "Viajando " + tvRouteName.getText().toString());
            b.putString("latitude", lat);
            b.putString("longitude", lng);

            Intent intent = new Intent(context, DrivingActivity.class);
            intent.putExtras(b);
            context.startActivity(intent);
        }
    }

    public TravelAdapter(Context ctx, String latitude, String longitude) {
        this.mDataSet = new ArrayList<>();
        context = ctx;
        lat = latitude;
        lng = longitude;
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

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_travel, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Travel travel = mDataSet.get(position);

        holder.tvTravelId.setText("Viaje " + travel.getId());
        holder.tvDepartureDate.setText("Programado para el " + travel.getDepartureDate());
        holder.tvDepartureTime.setText("A las " + travel.getDepartureTime() + " horas");
        holder.tvRouteName.setText(travel.getRouteName());

        holder.travelId = travel.getId();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}