package com.postex.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.postex.app.R;

public class MainActivity extends AppCompatActivity {
    private Button btLogin;
    private Button btRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigateToLogin();
        navigateToRegister();
    }

    private void navigateToLogin(){
        btLogin = (Button) findViewById(R.id.btnLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void navigateToRegister(){
        btRegister = (Button) findViewById(R.id.btnRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityCadastro.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
