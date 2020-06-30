package com.postex.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.postex.app.R;
import com.postex.app.Recipient;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setElevation(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //Pega os dados do usuário
        Bundle data = getIntent().getExtras();
        Recipient recipient = (Recipient) data.getSerializable("recipient");
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
                Toast.makeText(getApplicationContext(), "Adicionar clicado", Toast.LENGTH_SHORT).show();
                return (true);

            case R.id.edit:
                Intent tela = new Intent(PrincipalActivity.this, EditarActivity.class);
                startActivity(tela);
                return (true);

        }
        return (super.onOptionsItemSelected(item));
    }

}

