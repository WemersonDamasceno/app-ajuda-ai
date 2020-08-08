package com.ufc.com.ajudaai.view.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.ufc.com.ajudaai.R;
import com.ufc.com.ajudaai.view.adapter.AdapterPublicacao;
import com.ufc.com.ajudaai.view.model.Publicacao;

import java.util.List;

import javax.annotation.Nullable;

public class Pagina_Inicial_Activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtEscreverAqui;
    RecyclerView rvListPublicacoes;
    AdapterPublicacao adapterPublicacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        getSupportActionBar().hide();


        rvListPublicacoes = findViewById(R.id.rvListPostagens);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setReverseLayout(false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        adapterPublicacao = new AdapterPublicacao();
        rvListPublicacoes.setLayoutManager(layoutManager);
        rvListPublicacoes.setAdapter(adapterPublicacao);

        txtEscreverAqui = findViewById(R.id.txtEscrevaAqui);
        txtEscreverAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), CriarUmaPublicacaoActivity.class));
            }
        });

        verificarAutenticado();
        buscarPublicacoes();

    }

    private void buscarPublicacoes() {
        FirebaseFirestore.getInstance().collection("/publicacoes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot doc : docs){
                            Publicacao pub = doc.toObject(Publicacao.class);
                            adapterPublicacao.add(pub);
                            adapterPublicacao.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void verificarAutenticado() {
        if(FirebaseAuth.getInstance().getUid() == null){
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
        }
    }
}
