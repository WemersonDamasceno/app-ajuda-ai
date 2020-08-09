package com.ufc.com.ajudaai.view.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
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
import com.ufc.com.ajudaai.view.model.Publicacao;
import com.ufc.com.ajudaai.view.model.Usuario;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

public class CriarUmaPublicacaoActivity extends AppCompatActivity {
    Button btnCriarPub;
    ImageView imgPerfilCriarPub;
    TextView txtNomeCriarPub;
    EditText edMensgCriarPub;
    ImageView imgPDF1Upload,imgPDF2Upload,imgPDF3Upload;
    EditText edTAGCriarPub;
    Publicacao publicacao;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_uma_publicacao);

        btnCriarPub = findViewById(R.id.btnCriarPub);
        imgPerfilCriarPub = findViewById(R.id.imgPerfilCriarPub);
        txtNomeCriarPub = findViewById(R.id.txtNomeCriarPub);
        edMensgCriarPub = findViewById(R.id.edMensgCriarPub);
        imgPDF1Upload =findViewById(R.id.imgPDF1Upload);
        imgPDF2Upload =findViewById(R.id.imgPDF2Upload);
        imgPDF3Upload = findViewById(R.id.imgPDF3Upload);
        edTAGCriarPub = findViewById(R.id.edTAGCriarPub);
        publicacao = new Publicacao();


        btnCriarPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensagem = edMensgCriarPub.getText().toString();
                String TAGs = edTAGCriarPub.getText().toString();
                String idPublicacao = UUID.randomUUID().toString();
                String idUserPub = FirebaseAuth.getInstance().getUid();
                //Falta fazer o upload das listas.
                String urlPDF1 = "teste";
                String urlPDF2 = "teste";
                String urlPDF3 = "teste";

                if (mensagem.equals("") || TAGs.equals("")) {
                    if(mensagem.length() > 175)
                        edMensgCriarPub.setError("A mensagem não pode ter mais de 175 caracteres");
                    if (mensagem.equals(""))
                        edMensgCriarPub.setError("O campo mensagem é obrigatório!");
                    if (TAGs.equals(""))
                        edTAGCriarPub.setError("O campo TAGs é obrigatório!");
                } else {
                    criarNovaPub(mensagem, TAGs, idPublicacao,idUserPub,urlPDF1,urlPDF2,urlPDF3);
                }
       
            }
        });

        setarUser(FirebaseAuth.getInstance().getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference();

        imgPDF1Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDFFile();
            }
        });
        imgPDF2Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgPDF3Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






        //Quando fizer o upload do PDF mudar o icon branco para o PDFCompleto
        

    }

    private void selectPDFFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecione o arquivo de PDF"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uploadPDFFile(data.getData());
        }

    }

    private void uploadPDFFile(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading PDF1");
        progressDialog.show();

        StorageReference reference = mStorageRef.child("pdf_uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.i("teste","Sucesso ao enviar o PDF");

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri url = uriTask.getResult();


                        publicacao.setUrlPDF1(url.toString());
                        //mudar a foto do pdf
                        Picasso.get().load(R.drawable.pdfcompleto).into(imgPDF1Upload);
                        Toast.makeText(CriarUmaPublicacaoActivity.this, "Upload pronto", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("teste","Erro ao enviar o pdf: "+e.getMessage());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: "+(int) progress+"%");

            }
        });
    }

    private void setarUser(final String uid) {
        FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot doc : docs){
                            Usuario usuario = doc.toObject(Usuario.class);
                            if(usuario.getIdUser().equals(uid)){
                                if(!usuario.getUrlFoto().equals("NA"))
                                    Picasso.get().load(usuario.getUrlFoto()).into(imgPerfilCriarPub);
                                txtNomeCriarPub.setText(usuario.getNome());
                            }
                        }
                    }
                });
    }

    private void criarNovaPub(String mensagem, String taGs, String idPublicacao, String idUserPub, String urlPDF1, String urlPDF2, String urlPDF3) {
        publicacao.setMensagem(mensagem);
        publicacao.setIdPublicacao(idPublicacao);
        publicacao.setIdUserPub(idUserPub);
        publicacao.setTAGs(taGs);
        if(publicacao.getUrlPDF1() == null){
            publicacao.setUrlPDF1("teste");
        }
        if(publicacao.getUrlPDF2() == null){
            publicacao.setUrlPDF2("teste");
        }
        if(publicacao.getUrlPDF3() == null){
            publicacao.setUrlPDF3("teste");
        }

        salvarPublicacao(publicacao);


    }

    private void salvarPublicacao(Publicacao publicacao) {
        FirebaseFirestore.getInstance().collection("publicacoes")
                .add(publicacao)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("teste", "Publicacao salva no banco.");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("teste", "erro ao salvar a publicacao: " + e.getMessage());
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Log.i("teste", "Publicacao salva completa");
                FirebaseFirestore.getInstance().collection("users")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                                    final Usuario user = doc.toObject(Usuario.class);
                                    if(user.getIdUser().equals(FirebaseAuth.getInstance().getUid())){
                                        uptadeQtdPubUser(user.getQtdPublicacoes(),doc);
                                    }
                                }
                            }
                        });
                startActivity(new Intent(getBaseContext(),Pagina_Inicial_Activity.class));
            }
        });
    }

    private void uptadeQtdPubUser(int qtd, DocumentSnapshot doc) {
        qtd++;
        final int finalQtd = qtd;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("/users")
                .document(doc.getId())
                .update("qtdPublicacoes",qtd)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("teste","Uptade qtd: "+ finalQtd);
                    }
                });
        db.terminate().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}
