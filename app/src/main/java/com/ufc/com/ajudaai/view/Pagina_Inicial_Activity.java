package com.ufc.com.ajudaai.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.ufc.com.ajudaai.R;

public class Pagina_Inicial_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);

        //verificarAutenticado();

    }

    private void verificarAutenticado() {
        if(FirebaseAuth.getInstance().getUid() == null){
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
        }
    }
}
