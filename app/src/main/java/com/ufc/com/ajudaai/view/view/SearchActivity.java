package com.ufc.com.ajudaai.view.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.ufc.com.ajudaai.R;
import com.ufc.com.ajudaai.view.adapter.AdapterPublicacao;
import com.ufc.com.ajudaai.view.model.Publicacao;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rvListPubSearch;
    AdapterPublicacao adapterPublicacao;
    EditText edPesquisar;

    ImageView home2,search2,notify2,perfil2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();


        home2 = findViewById(R.id.homeSE);
        search2 = findViewById(R.id.searchSE);
        notify2 = findViewById(R.id.notifySE);
        perfil2 = findViewById(R.id.perfilSE);


        home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Pagina_Inicial_Activity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                finish();
            }
        });
        search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        notify2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NotifyActivity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                finish();
            }
        });

        perfil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PerfilActivity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                finish();
            }
        });



        rvListPubSearch = findViewById(R.id.rvListPubSearch);
        edPesquisar = findViewById(R.id.edPesquisar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        adapterPublicacao = new AdapterPublicacao();
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setReverseLayout(false);

        rvListPubSearch.setLayoutManager(layoutManager);
        rvListPubSearch.setAdapter(adapterPublicacao);

        buscarPublicacoes();

        edPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
                adapterPublicacao.getListPublicacao().clear();
            }
        });


    }

    private void filter(final String text) {
        final ArrayList<Publicacao> filteredList = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("publicacoes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                            Publicacao p = doc.toObject(Publicacao.class);
                            adapterPublicacao.add(p);
                        }
                        for (Publicacao c : adapterPublicacao.getListPublicacao()) {
                            if (c.getTAGs().toLowerCase().contains(text.toLowerCase())) {
                                filteredList.add(c);
                            }
                        }
                        adapterPublicacao.filterList(filteredList);
                        adapterPublicacao.notifyDataSetChanged();
                    }
                });

    }

    private void buscarPublicacoes() {
        FirebaseFirestore.getInstance().collection("/publicacoes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            Publicacao pub = doc.toObject(Publicacao.class);
                            adapterPublicacao.add(pub);
                        }
                    }
                });
    }
}
