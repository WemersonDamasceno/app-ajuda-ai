package com.ufc.com.ajudaai.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.ufc.com.ajudaai.R;

public class Pagina_Inicial_Activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtEscreverAqui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        getSupportActionBar().hide();


        txtEscreverAqui = findViewById(R.id.txtEscrevaAqui);
        txtEscreverAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),FazerUmPostActivity.class));
            }
        });




    }
    private void verificarAutenticado() {
        if(FirebaseAuth.getInstance().getUid() == null){
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
        }
    }
}
