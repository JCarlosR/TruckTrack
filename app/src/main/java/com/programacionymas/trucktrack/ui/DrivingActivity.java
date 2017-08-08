package com.programacionymas.trucktrack.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.programacionymas.trucktrack.Global;
import com.programacionymas.trucktrack.R;
import com.programacionymas.trucktrack.io.ApiAdapter;
import com.programacionymas.trucktrack.io.response.SimpleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrivingActivity extends AppCompatActivity implements View.OnClickListener, Callback<SimpleResponse> {

    private int travelId;
    private String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);

        Button btnPanic = (Button) findViewById(R.id.btnPanic);
        btnPanic.setOnClickListener(this);

        Button btnEndTravel = (Button) findViewById(R.id.btnEndTravel);
        btnEndTravel.setOnClickListener(this);

        String titleTravel = "";

        Bundle b = getIntent().getExtras();
        if (b != null) {
            travelId = b.getInt("travelId");
            titleTravel = b.getString("travelTitle");
            latitude = b.getString("latitude");
            longitude = b.getString("longitude");
        }

        TextView tvRouteName = (TextView) findViewById(R.id.tvRouteName);
        tvRouteName.setText(titleTravel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPanic:
                sendDistressCall();
                break;

            case R.id.btnEndTravel:
                endTravel();
                break;
        }
    }

    private void sendDistressCall() {
        final int userId = Global.getIntFromGlobalPreferences(this, "userId");

        Toast.makeText(this, "Enviando llamado de emergencia", Toast.LENGTH_SHORT).show();

        Call<SimpleResponse> call = ApiAdapter.getApiService().postDistressCall(userId, travelId, latitude, longitude);
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if (response.isSuccessful()) {
                    SimpleResponse simpleResponse = response.body();
                    if (simpleResponse.isSuccess()) {
                        Toast.makeText(DrivingActivity.this, "Denuncia enviada correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DrivingActivity.this, "Ocurrió un error al enviar la denuncia", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Toast.makeText(DrivingActivity.this, "Ocurrió un error inesperado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void endTravel() {
        final int status = 2; // End
        Call<SimpleResponse> call = ApiAdapter.getApiService().postTravelStatus(travelId, status);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
        if (response.isSuccessful()) {
            SimpleResponse simpleResponse = response.body();
            if (simpleResponse.isSuccess()) {
                Toast.makeText(this, "Que hayas tenido un feliz viaje", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onFailure(Call<SimpleResponse> call, Throwable t) {
        Toast.makeText(this, "Ocurrió un error inesperado", Toast.LENGTH_SHORT).show();
    }
}
