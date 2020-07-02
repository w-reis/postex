package com.postex.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.postex.app.R;
import com.postex.app.Recipient;
import com.postex.app.api.ApiConfig;
import com.postex.app.api.RecipientService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarActivity extends AppCompatActivity {
    EditText txEmail;
    EditText txName;
    EditText txPhone;
    Button btSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        txEmail = (EditText) findViewById(R.id.txtEditEmail);
        txName = (EditText) findViewById(R.id.txtEditNome);
        txPhone = (EditText) findViewById(R.id.txtEditTelefone);
        btSave = (Button) findViewById(R.id.btnSave);

        Bundle data = getIntent().getExtras();
        Recipient recipient = (Recipient) data.getSerializable("recipient");

        txEmail.setText(recipient.getEmail().toString());
        txName.setText(recipient.getName().toString());
        txPhone.setText(recipient.getPhone().toString());

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = getIntent().getExtras();
                Recipient user = (Recipient) data.getSerializable("recipient");
                Recipient recipient = new Recipient(
                        txName.getText().toString(),
                        txEmail.getText().toString(),
                        txPhone.getText().toString(),
                        " "
                );

                updateRecipient(user.getId(), recipient, user.getToken());
            }
        });

    }

    private void updateRecipient(Integer id, Recipient recipient, String token){
        ApiConfig config = new ApiConfig();
        RecipientService client = config.createRequest().create(RecipientService.class);
        String authToken = "Bearer " + token;
        Call<Void> call = client.updateRecipient(id, recipient, authToken);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(EditarActivity.this, "Cadastro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                    recipient.setToken(token);
                    recipient.setId(id);
                    intent.putExtra("recipient", recipient);

                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(EditarActivity.this, "Preencha todos os campos! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditarActivity.this, "Algo deu errado! Verifique seus dados. ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        Bundle data = getIntent().getExtras();
        Recipient recipient = (Recipient) data.getSerializable("recipient");
        Intent editScreen  = new Intent(EditarActivity.this, PrincipalActivity.class);
        editScreen.putExtra("recipient", recipient);
        startActivity(editScreen);
        finish();
    }
}