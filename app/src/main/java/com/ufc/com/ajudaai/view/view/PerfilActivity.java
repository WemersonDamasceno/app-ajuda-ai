package com.ufc.com.ajudaai.view.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.ufc.com.ajudaai.R;
import com.ufc.com.ajudaai.view.adapter.AdapterPublicacao;
import com.ufc.com.ajudaai.view.model.Publicacao;
import com.ufc.com.ajudaai.view.model.Usuario;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class PerfilActivity extends AppCompatActivity {
    RecyclerView rvPubsFeitasPorMim;
    AdapterPublicacao adapterPublicacao;
    TextView txtNomePerfilPerfil,qtdPublicacoes,qtdSeguidores,qtdSeguindo;
    ImageView home4,search4,notify4,perfil4, imgEditarPerfil,imgPerfilPerfil,imgTrocarFotoPerfil, imgSair;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().hide();


        home4 = findViewById(R.id.homePE);
        search4 = findViewById(R.id.searchPE);
        notify4 = findViewById(R.id.notifyPE);
        perfil4 = findViewById(R.id.perfilPE);
        imgEditarPerfil = findViewById(R.id.imgEditarPerfil);
        imgPerfilPerfil = findViewById(R.id.imgPerfilPerfil);
        txtNomePerfilPerfil = findViewById(R.id.txtNomePerfilPerfil);
        qtdPublicacoes = findViewById(R.id.txtQtdPublicacoes);
        qtdSeguidores = findViewById(R.id.txtQtdSeguidores);
        qtdSeguindo = findViewById(R.id.txtQtdSeguindo);
        imgTrocarFotoPerfil = findViewById(R.id.imgTrocarFotoPerfil);
        imgSair = findViewById(R.id.imgSair);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        rvPubsFeitasPorMim = findViewById(R.id.rvPubsFeitasPorMim);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setReverseLayout(false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adapterPublicacao = new AdapterPublicacao(getBaseContext());

        rvPubsFeitasPorMim.setLayoutManager(layoutManager);
        rvPubsFeitasPorMim.setAdapter(adapterPublicacao);

        buscarPubsFeitasPorMim(FirebaseAuth.getInstance().getUid());
        setarDadosUser(FirebaseAuth.getInstance().getUid());

        imgEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilActivity.this, "Editar perfil em breve", Toast.LENGTH_SHORT).show();
            }
        });

        imgSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(v.getContext(),LoginActivity.class));
                finish();
            }
        });

        imgTrocarFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarFoto();
            }
        });


        home4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Pagina_Inicial_Activity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                finish();
            }
        });
        search4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                finish();
            }
        });

        notify4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NotifyActivity.class);
                //Utilizando animação
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(v.getContext(), R.anim.fade_in, R.anim.fade_out);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                finish();
            }
        });
        perfil4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recreate();
            }
        });


    }

    private void selecionarFoto() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"SelecionarFoto"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            Uri uri = data.getData();
            enviarFoto(uri);
            imgPerfilPerfil.setImageURI(uri);
        }
    }
    private void enviarFoto(Uri selectedImage) {
        //salvar imagem no banco
        String fileName = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + fileName);
        ref.putFile(selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri1) {
                                updateUserFoto(uri1.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("teste", "Falha ao fazer upload da foto: " + e.getMessage());
                        if (e.getMessage().equals("A network error (such as timeout, interrupted connection or unreachable host) has occurred.")) {
                            Toast.makeText(PerfilActivity.this, "Falha ao conectar-se com a internet", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                Toast.makeText(PerfilActivity.this, "Aguarde um pouco, " + (int) progress + "% completo", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void updateUserFoto(final String urlFotoPerfil) {
        FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            Usuario usuario = doc.toObject(Usuario.class);
                            if(usuario.getIdUser().equals(FirebaseAuth.getInstance().getUid())){
                                FirebaseFirestore.getInstance().collection("/users")
                                        .document(doc.getId())
                                        .update("urlFoto", urlFotoPerfil)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.i("teste", "Update completo");
                                            }
                                        });
                            }
                        }
                    }
                });
    }



    private void setarDadosUser(final String uid) {
        FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            Usuario user = doc.toObject(Usuario.class);
                            if(user.getIdUser().equals(uid)){
                                if(!user.getUrlFoto().equals("NA"))
                                    Picasso.get().load(user.getUrlFoto()).into(imgPerfilPerfil);
                                txtNomePerfilPerfil.setText(user.getNome());
                                qtdPublicacoes.setText(String.valueOf(user.getQtdPublicacoes()));
                                qtdSeguidores.setText(String.valueOf(user.getQtdSeguidores()));
                                qtdSeguindo.setText(String.valueOf(user.getQtdSeguindo()));
                            }
                        }
                    }
                });
    }

    private void buscarPubsFeitasPorMim(final String uid) {
        FirebaseFirestore.getInstance().collection("/publicacoes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            Publicacao publicacao = doc.toObject(Publicacao.class);
                            if(publicacao.getIdUserPub().equals(uid)){
                                adapterPublicacao.add(publicacao);
                                adapterPublicacao.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}
