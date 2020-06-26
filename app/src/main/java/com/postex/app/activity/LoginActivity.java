package com.postex.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.postex.app.R;
import com.postex.app.Recipient;
import com.postex.app.api.ApiConfig;
import com.postex.app.api.ApiError;
import com.postex.app.api.ErrorUtils;
import com.postex.app.api.RecipientService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btEntrar;
    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.txEmail);
        password = (EditText) findViewById(R.id.txSenha);

        btEntrar = (Button) findViewById(R.id.button);
        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String base = email.getText().toString() + ":" + password.getText().toString();
                String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
                login(authHeader);
            }
        });
    }

    private void login(String credentials){
        ApiConfig config = new ApiConfig();
        RecipientService client = config.createRequest().create(RecipientService.class);
        Call<Recipient> call = client.login(credentials);

        call.enqueue(new Callback<Recipient>() {
            @Override
            public void onResponse(Call<Recipient> call, Response<Recipient> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                    intent.putExtra("recipient", response.body());
                    startActivity(intent);
                }else {
                    switch (response.code()){
                        case 500 :
                            Toast.makeText(LoginActivity.this, "Erro interno no servidor, tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            ApiError apiError = ErrorUtils.parseError(response);
                            Toast.makeText(LoginActivity.this, apiError.getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Recipient> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erro: verifique sua conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
