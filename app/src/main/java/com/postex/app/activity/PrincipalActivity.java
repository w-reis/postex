package com.postex.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.postex.app.R;
import com.postex.app.Recipient;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //Pega os dados do usuário
        Bundle data = getIntent().getExtras();
        Recipient recipient = (Recipient) data.getSerializable("recipient");
    }
}
