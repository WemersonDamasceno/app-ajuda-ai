package com.ufc.com.ajudaai.view.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        //Quando fizer o upload do PDF mudar o icon branco para o PDFCompleto
        //O texto nao poder ter mais que 175 caracteres.

        

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
        publicacao = new Publicacao();
        publicacao.setMensagem(mensagem);
        publicacao.setIdPublicacao(idPublicacao);
        publicacao.setIdUserPub(idUserPub);
        publicacao.setTAGs(taGs);
        publicacao.setUrlPDF1(urlPDF1);
        publicacao.setUrlPDF2(urlPDF2);
        publicacao.setUrlPDF3(urlPDF3);


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
                startActivity(new Intent(getBaseContext(),Pagina_Inicial_Activity.class));
            }
        });
    }
}
