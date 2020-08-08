package com.ufc.com.ajudaai.view.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ufc.com.ajudaai.R;

public class PerfilActivity extends AppCompatActivity {

    ImageView home4,search4,notify4,perfil4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().hide();


        home4 = findViewById(R.id.homeSE);
        search4 = findViewById(R.id.searchSE);
        notify4 = findViewById(R.id.notifySE);
        perfil4 = findViewById(R.id.perfilSE);





        home4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Pagina_Inicial_Activity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
            }
        });
        search4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
            }
        });

        notify4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NotifyActivity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
            }
        });

        perfil4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recreate();
            }
        });



    }
}
