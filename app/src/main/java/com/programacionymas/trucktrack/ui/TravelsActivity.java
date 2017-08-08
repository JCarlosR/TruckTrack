package com.programacionymas.trucktrack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.programacionymas.trucktrack.Global;
import com.programacionymas.trucktrack.R;
import com.programacionymas.trucktrack.io.ApiAdapter;
import com.programacionymas.trucktrack.model.Travel;
import com.programacionymas.trucktrack.ui.adapter.TravelAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelsActivity extends AppCompatActivity implements Callback<ArrayList<Travel>> {

    private RecyclerView mRecyclerView;
    private TravelAdapter mAdapter;

    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            latitude = b.getString("lat");
            longitude = b.getString("lng");
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new TravelAdapter(this, latitude, longitude);
        mRecyclerView.setAdapter(mAdapter);

        loadTravels();
    }

    private void loadTravels() {
        /*ArrayList<Travel> travels = new ArrayList<>();
        final Travel travel = new Travel();
        travel.setDepartureDate("17/07/2017");
        travel.setDepartureTime("04:30");
        travel.setRouteName("Trujillo - Lima");
        travels.add(travel);

        mAdapter.addTravels(travels);*/

        final int userId = Global.getIntFromGlobalPreferences(this, "userId");
        Call<ArrayList<Travel>> call = ApiAdapter.getApiService().getTravelsToday(userId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ArrayList<Travel>> call, Response<ArrayList<Travel>> response) {
        if (response.isSuccessful()) {
            ArrayList<Travel> travels = response.body();
            if (travels.size() == 0)
                Toast.makeText(this, "No se encontraron viajes programados para hoy", Toast.LENGTH_SHORT).show();
            else
                mAdapter.addTravels(travels);
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Travel>> call, Throwable t) {
        Toast.makeText(this, "No se han podido cargar los  viajes", Toast.LENGTH_SHORT).show();
    }

}
