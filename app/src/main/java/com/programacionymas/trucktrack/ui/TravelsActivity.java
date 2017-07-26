package com.programacionymas.trucktrack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.programacionymas.trucktrack.R;
import com.programacionymas.trucktrack.model.Travel;
import com.programacionymas.trucktrack.ui.adapter.TravelAdapter;

import java.util.ArrayList;

public class TravelsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TravelAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new TravelAdapter();
        mRecyclerView.setAdapter(mAdapter);

        loadTravels();
    }

    private void loadTravels() {
        ArrayList<Travel> travels = new ArrayList<>();
        final Travel travel = new Travel();
        travel.setDepartureDate("17/07/2017");
        travel.setDepartureTime("04:30");
        travel.setRouteName("Trujillo - Lima");
        travels.add(travel);

        mAdapter.addTravels(travels);
    }

}
