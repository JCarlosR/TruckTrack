package com.programacionymas.trucktrack.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.programacionymas.trucktrack.R;
import com.programacionymas.trucktrack.io.ApiAdapter;
import com.programacionymas.trucktrack.io.response.LoginResponse;
import com.programacionymas.trucktrack.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<LoginResponse> {

    EditText etEmail, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        Call<LoginResponse> call = ApiAdapter.getApiService().getLogin(email, password);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        if (response.isSuccessful()) {
            LoginResponse loginResponse = response.body();
            if (loginResponse.isSuccess()) {
                final User user = loginResponse.getUser();
                Toast.makeText(this, "Bienvenido, " + user.getName(), Toast.LENGTH_SHORT).show();

                goToNextActivity();
            } else {
                Toast.makeText(this, "Sus datos son incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
    }


    private void goToNextActivity()
    {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
