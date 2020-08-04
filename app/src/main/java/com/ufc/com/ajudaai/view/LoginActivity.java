package com.ufc.com.ajudaai.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ufc.com.ajudaai.R;

public class LoginActivity extends AppCompatActivity {
    Button btnCriarConta;
    Button btnLoginSIGAA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        btnCriarConta = findViewById(R.id.btnCriarConta);
        btnLoginSIGAA = findViewById(R.id.btnLoginSIGAA);


        btnLoginSIGAA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), Pagina_Inicial_Activity.class));
            }
        });

        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), CriarContaActivity.class));
            }
        });



        //Criar a tela splash amanha e comecar a tentar fazer de vdd ><



    }
}
