package com.programacionymas.trucktrack.ui;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.programacionymas.trucktrack.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final String TAG = "TestStart";
    
    private TextView txtLat, txtLng;
    private ToggleButton btnUpdateGps;

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;
    private GoogleApiClient apiClient;

    private LocationRequest locRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnPanic = (Button) findViewById(R.id.btnPanic);
        btnPanic.setOnClickListener(this);

        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        txtLat = (TextView) findViewById(R.id.txtLat);
        txtLng = (TextView) findViewById(R.id.txtLng);
        btnUpdateGps = (ToggleButton) findViewById(R.id.btnUpdateGPS);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void updateCoords(Location loc) {
        if (loc != null) {
            txtLat.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            txtLng.setText("Longitud: " + String.valueOf(loc.getLongitude()));
        } else {
            txtLat.setText("Latitud: (desconocida)");
            txtLng.setText("Longitud: (desconocida)");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPanic:
                Toast.makeText(this, "BOOOM", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnUpdateGPS:
                toggleLocationUpdates(btnUpdateGps.isChecked());
                break;
            case R.id.btnLogout:
                finish();
                break;
        }
    }

    private void toggleLocationUpdates(boolean enable) {
        System.out.println("The location updates will be set to => " + enable);
        if (enable)
            enableLocationUpdates();
        else
            disableLocationUpdates();
    }

    private void enableLocationUpdates() {

        locRequest = new LocationRequest();
        locRequest.setInterval(5000);
        locRequest.setFastestInterval(2000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest locSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locRequest)
                        .build();

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        apiClient, locSettingsRequest);

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    
                    case LocationSettingsStatusCodes.SUCCESS:
                        System.out.println("Configuración correcta");
                        startLocationUpdates();
                        break;
                    
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            System.out.println("Se requiere actuación del usuario");
                            status.startResolutionForResult(StartActivity.this, PETICION_CONFIG_UBICACION);
                        } catch (IntentSender.SendIntentException e) {
                            btnUpdateGps.setChecked(false);
                            System.out.println("Error al intentar solucionar configuración de ubicación");
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        System.out.println("No se puede cumplir la configuración de ubicación necesaria");
                        btnUpdateGps.setChecked(false);
                        break;
                    default:
                        System.out.println("Código de respuesta desconocido: " + status.getStatusCode());
                        break;
                }
            }
        });
    }

    private void disableLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                apiClient, this);

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(StartActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            // Sería recomendable implementar la posible petición en caso de no tenerlo.

            System.out.println("Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, StartActivity.this);
        } else {
            System.out.println("No se pudo iniciar la recepción de ubicaciones");
        }
    }
    
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        System.out.println("Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Successfully connected to a Google Play Services

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateCoords(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("Se ha interrumpido la conexión con Google Play Services");
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Permission granted

                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);

                updateCoords(lastLocation);

            } else {
                // Permission denied
                // Deberíamos deshabilitar toda la funcionalidad relativa a la localización
                System.out.println("Permiso denegado");
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // New location received !
        Toast.makeText(this, "Ubicación actualizada", Toast.LENGTH_SHORT).show();
        updateCoords(location);
    }
}
