package com.ufc.com.ajudaai.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.ufc.com.ajudaai.R;

public class Pagina_Inicial_Activity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        getSupportActionBar().hide();




    }
    private void verificarAutenticado() {
        if(FirebaseAuth.getInstance().getUid() == null){
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
        }
    }
}
