package com.postex.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.postex.app.R;
import com.postex.app.Recipient;
import com.postex.app.api.ApiConfig;
import com.postex.app.api.RecipientService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final Button btRegister = (Button) findViewById(R.id.button);
        final EditText name = (EditText) findViewById(R.id.txtNome);
        final EditText email = (EditText) findViewById(R.id.txtEmail);
        final EditText phone = (EditText) findViewById(R.id.txtTelefone);
        final EditText password = (EditText) findViewById(R.id.txtSenha);
        final EditText txConfirm = (EditText) findViewById(R.id.txtConfirmaSenha);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(txConfirm.getText().toString())){
                    Recipient recipient = new Recipient(
                            name.getText().toString(),
                            email.getText().toString(),
                            phone.getText().toString(),
                            password.getText().toString()
                    );
                    registerRecipient(recipient);
                } else {
                    Toast.makeText(ActivityCadastro.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerRecipient(Recipient recipient) {
        ApiConfig config = new ApiConfig();
        RecipientService client = config.createRequest().create(RecipientService.class);
        Call<Recipient> call = client.registerRecipient(recipient);

        call.enqueue(new Callback<Recipient>() {
            @Override
            public void onResponse(Call<Recipient> call, Response<Recipient> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(ActivityCadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Recipient> call, Throwable t) {
                Toast.makeText(ActivityCadastro.this, "Algo deu errado! Verifique seus dados.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
