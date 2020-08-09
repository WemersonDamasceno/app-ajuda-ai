package com.ufc.com.ajudaai.view.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.ufc.com.ajudaai.R;
import com.ufc.com.ajudaai.view.model.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class EditarPerfilActivity extends AppCompatActivity {
    final int GALERIA_IMAGENS = 1;
    final int PERMISSAO_REQUEST = 2;
    StorageReference mStorageRef;
    EditText edNome,edEmail,edSenha,edSenhaNova;
    ImageView imgPerfilNova;
    Uri selectedImage;
    ProgressBar progressBar;
    Usuario user;


    Button btnCancelar, btnConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);


        mStorageRef = FirebaseStorage.getInstance().getReference();

        imgPerfilNova = findViewById(R.id.imgNovaPerfil);
        edNome = findViewById(R.id.txtNomePerfilNovo);
        edEmail = findViewById(R.id.edEmailPerfilNovo);
        edSenha = findViewById(R.id.edNovaSenhaPerfilNovo);
        edSenhaNova = findViewById(R.id.edConfirmeSenhaPerfilNovo);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        user = new Usuario();
        selectedImage = null;
        progressBar = findViewById(R.id.circularBarCriarConta);
        progressBar.setVisibility(View.GONE);



        setarDadosUser(FirebaseAuth.getInstance().getUid());

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),PerfilActivity.class));
            }
        });


        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("/users")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                                    Usuario usuario = doc.toObject(Usuario.class);
                                    if(usuario.getIdUser().equals(FirebaseAuth.getInstance().getUid())){
                                        doUpdateInUser(doc);
                                    }
                                }
                            }
                        });
            }
        });

        imgPerfilNova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }

    private void doUpdateInUser(DocumentSnapshot doc) {
        user.setNome(edNome.getText().toString());
        user.setEmail(edEmail.getText().toString());
        user.setSenha(edSenha.getText().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("/users")
                .document(doc.getId()).update("nome",user.getNome(),"email",user.getEmail(),"senha",user.getSenha(),"urlFoto", user.getUrlFoto())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("teste","Upload completo");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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
                                edEmail.setText(user.getEmail());
                                edNome.setText(user.getNome());
                            }
                        }
                    }
                });
    }

}
