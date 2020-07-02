package com.postex.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.postex.app.CorrespondenceData;
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

public class PrincipalActivity extends AppCompatActivity {
    TextView total;
    TextView retired;
    TextView pending;
    TextView returned;
    TextView firstName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setElevation(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        total = (TextView) findViewById(R.id.txtTotal);
        retired = (TextView) findViewById(R.id.txtRetired);
        pending = (TextView) findViewById(R.id.txtPending);
        returned = (TextView) findViewById(R.id.txtReturned);
        firstName = (TextView) findViewById(R.id.txtCurrentUser);

        //Pega os dados do usuário
        Bundle data = getIntent().getExtras();
        Recipient recipient = (Recipient) data.getSerializable("recipient");

        String[] fullName = recipient.getName().split(" ");
        firstName.setText(fullName[0]);

        String authToken = "Bearer " + recipient.getToken();

        getCorrespondenceData(recipient.getId(), authToken);
    }

    private void getCorrespondenceData(Integer id, String token){
        ApiConfig config = new ApiConfig();
        RecipientService client = config.createRequest().create(RecipientService.class);
        Call<CorrespondenceData> call = client.getCorrespondencesData(id, token);

        call.enqueue(new Callback<CorrespondenceData>() {
            @Override
            public void onResponse(Call<CorrespondenceData> call, Response<CorrespondenceData> response) {
                if (response.isSuccessful()){
                    if (response.body().getTotal().equals("1")){
                        total.setText(response.body().getTotal()+" correspondência");
                    } else {
                        total.setText(response.body().getTotal()+" correspondências");
                    }

                    if (response.body().getRetired().equals("1")){
                        retired.setText(response.body().getRetired()+" retirada");
                    } else {
                        retired.setText(response.body().getRetired()+" retiradas");
                    }

                    if (response.body().getPending().equals("1")){
                        pending.setText(response.body().getPending()+" pendente");
                    } else {
                        pending.setText(response.body().getPending()+" pendentes");
                    }

                    if (response.body().getReturned().equals("1")){
                        returned.setText(response.body().getReturned()+" devolvida");
                    } else {
                        returned.setText(response.body().getReturned()+" devolvidas");
                    }
                }else {
                    switch (response.code()){
                        case 500 :
                            try {
                                Toast.makeText(PrincipalActivity.this, "Ocorreu um erro ao recuperar as informações." + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 401 :
                            Toast.makeText(PrincipalActivity.this, "Sessão expirada, faça login novamente.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            ApiError apiError = ErrorUtils.parseError(response);
                            Toast.makeText(PrincipalActivity.this, apiError.getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CorrespondenceData> call, Throwable t) {
                Toast.makeText(PrincipalActivity.this, "Erro: verifique sua conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
                finishAndRemoveTask();

            case R.id.edit:
                Bundle data = getIntent().getExtras();
                Recipient recipient = (Recipient) data.getSerializable("recipient");
                Intent editScreen  = new Intent(PrincipalActivity.this, EditarActivity.class);
                editScreen.putExtra("recipient", recipient);
                startActivity(editScreen);
                finish();

        }
        return (super.onOptionsItemSelected(item));
    }

}

