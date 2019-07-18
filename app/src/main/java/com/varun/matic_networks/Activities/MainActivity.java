package com.varun.matic_networks.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.varun.matic_networks.R;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btNewAccount;
    private MaterialButton btSignIn;

    public static final String SHAREDPREF = "Matic.Network";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setOnClicks();
    }

    private void setViews() {

        btNewAccount = findViewById(R.id.btNewAccount);
        btSignIn = findViewById(R.id.btSignIn);
    }

    private void setOnClicks() {

        btNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
