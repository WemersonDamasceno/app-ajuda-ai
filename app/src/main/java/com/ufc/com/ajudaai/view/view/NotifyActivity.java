package com.ufc.com.ajudaai.view.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ufc.com.ajudaai.R;

public class NotifyActivity extends AppCompatActivity {
    ImageView home3,search3,notify3,perfil3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        getSupportActionBar().hide();



        home3 = findViewById(R.id.homeNO);
        search3 = findViewById(R.id.searchNO);
        notify3 = findViewById(R.id.notifyNO);
        perfil3 = findViewById(R.id.perfilNO);


        home3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Pagina_Inicial_Activity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                finish();
            }
        });
        search3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                finish();
                }
        });

        notify3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        perfil3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PerfilActivity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                finish();
            }
        });




    }
}
