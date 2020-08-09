package com.ufc.com.ajudaai.view.adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.ufc.com.ajudaai.R;
import com.ufc.com.ajudaai.view.model.Publicacao;
import com.ufc.com.ajudaai.view.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class AdapterPublicacao extends RecyclerView.Adapter<AdapterPublicacao.ViewHolderPublicacoes> {
    private ArrayList<Publicacao> listPublicacao;
    private StorageReference mStorageRef;
    private StorageReference ref;
    private Publicacao publicacao;
    private Context getContext;

    public AdapterPublicacao(Context getContext) {
        listPublicacao = new ArrayList<>();
        publicacao = new Publicacao();
        this.getContext = getContext;
    }

    public ArrayList<Publicacao> getListPublicacao() {
        return listPublicacao;
    }

    public void setListPublicacao(ArrayList<Publicacao> listPublicacao) {
        this.listPublicacao = listPublicacao;
    }


    @NonNull
    @Override
    public ViewHolderPublicacoes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.publicacao_listagem, null, false);
        return new ViewHolderPublicacoes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPublicacoes holder, int position) {
        publicacao = listPublicacao.get(position);
        holder.setDados(listPublicacao.get(position));
    }


    @Override
    public int getItemCount() {
        return listPublicacao.size();
    }

    public void add(Publicacao pub) {
        listPublicacao.add(pub);
    }

    public void filterList(ArrayList<Publicacao> filteredList) {
        listPublicacao = filteredList;
        notifyDataSetChanged();
    }

    class ViewHolderPublicacoes extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mensagem;
        ImageView imgPerfil;
        TextView nomeUser;
        ImageView imgConfiguracoes;
        ImageView imgPDF1, imgPDF2, imgPDF3;
        ImageView imgCurtir, imgComentar;
        LinearLayout llPDFsListaDown;

        ViewHolderPublicacoes(@NonNull View itemView) {
            super(itemView);
            mensagem = itemView.findViewById(R.id.txtMensagemLista);
            imgPerfil = itemView.findViewById(R.id.imgPerfilLista);
            nomeUser = itemView.findViewById(R.id.txtNomeLista);
            imgConfiguracoes = itemView.findViewById(R.id.imgConfiguracoes);
            imgConfiguracoes.setVisibility(View.GONE);
            imgPDF1 = itemView.findViewById(R.id.imgPDF1ListaDown);
            imgPDF2 = itemView.findViewById(R.id.imgPDF2ListaDown);
            imgPDF3 = itemView.findViewById(R.id.imgPDF3ListaDown);
            imgCurtir = itemView.findViewById(R.id.imgCurtirLista);
            imgComentar = itemView.findViewById(R.id.imgComentarLista);
            llPDFsListaDown = itemView.findViewById(R.id.llPDFsListaDown);

            mStorageRef = FirebaseStorage.getInstance().getReference();

            imgPDF1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadPDF1();
                    Toast.makeText(getContext, "Fazendo o download do arquivo...", Toast.LENGTH_SHORT).show();
                }
            });

            imgCurtir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Você curtiu essa publicação!", Toast.LENGTH_SHORT).show();
                }
            });

            imgConfiguracoes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Configuracões!....", Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void downloadPDF1() {
            mStorageRef = FirebaseStorage.getInstance().getReference();
            ref = mStorageRef.child("pdf_uploads/");
            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    //Terminar isso amanha de fazer download de PDF.
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    downloadFiles(getContext, "1596933296931",".pdf",DIRECTORY_DOWNLOADS,publicacao.getUrlPDF1());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("teste","Falha ao baixar o arquivo: "+e.getMessage());
                }
            });
        }

        private void downloadFiles(Context context, String filename, String extension, String destination, String url) {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalFilesDir(context,destination,filename+extension);

            downloadManager.enqueue(request);
        }

        @Override
        public void onClick(View v) {

        }

        private void setDados(final Publicacao publicacao) {
            FirebaseFirestore.getInstance().collection("/users")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot doc : docs) {
                                Usuario user = doc.toObject(Usuario.class);
                                if (publicacao.getIdUserPub().equals(user.getIdUser())) {
                                    nomeUser.setText(user.getNome());
                                    mensagem.setText(publicacao.getMensagem());
                                    //Fazer depois pra upload da foto de perfil.
                                    if (!user.getUrlFoto().equals("NA") && user.getUrlFoto() != null) {
                                        Picasso.get().load(user.getUrlFoto()).into(imgPerfil);
                                    }
                                    if (publicacao.getUrlPDF1().equals("teste")
                                            && publicacao.getUrlPDF2().equals("teste")
                                            && publicacao.getUrlPDF3().equals("teste")) {
                                        llPDFsListaDown.setVisibility(View.GONE);
                                    }

                                    if (publicacao.getUrlPDF1().equals("teste")) {
                                        imgPDF1.setVisibility(View.GONE);
                                    } else {
                                        Picasso.get().load(R.drawable.icon_pdf).into(imgPDF1);
                                        imgPDF1.setVisibility(View.VISIBLE);
                                    }
                                    imgPDF2.setVisibility(View.GONE);
                                    imgPDF3.setVisibility(View.GONE);
                                }
                                if (publicacao.getIdUserPub().equals(FirebaseAuth.getInstance().getUid())) {
                                    imgConfiguracoes.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });

        }


    }


}
