package com.ufc.com.ajudaai.view.adapter;

import android.annotation.SuppressLint;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.ufc.com.ajudaai.R;
import com.ufc.com.ajudaai.view.model.Publicacao;
import com.ufc.com.ajudaai.view.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdapterPublicacao extends RecyclerView.Adapter<AdapterPublicacao.ViewHolderPublicacoes> {
    private ArrayList<Publicacao> listPublicacao;


    public AdapterPublicacao(){
        listPublicacao = new ArrayList<>();
    }

    public ArrayList<Publicacao> getListPublicacao(){
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

    class ViewHolderPublicacoes extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mensagem;
        ImageView imgPerfil;
        TextView nomeUser;
        ImageView imgConfiguracoes;
        ImageView imgPDF1,imgPDF2,imgPDF3;
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
                                    if (!user.getUrlFoto().equals("NA")) {
                                        Picasso.get().load(user.getUrlFoto()).into(imgPerfil);
                                    }
                                    if (publicacao.getUrlPDF1().equals("teste")
                                            && publicacao.getUrlPDF2().equals("teste")
                                            && publicacao.getUrlPDF3().equals("teste")) {
                                        llPDFsListaDown.setVisibility(View.GONE);
                                    }
                                    if (publicacao.getUrlPDF1().equals("teste")
                                            || publicacao.getUrlPDF2().equals("teste")
                                            || publicacao.getUrlPDF3().equals("teste")) {
                                        if(publicacao.getUrlPDF1().equals("teste")){
                                            imgPDF1.setVisibility(View.GONE);
                                        }else if(publicacao.getUrlPDF2().equals("teste")){
                                            imgPDF2.setVisibility(View.GONE);
                                        }else if(publicacao.getUrlPDF3().equals("teste")){
                                            imgPDF3.setVisibility(View.GONE);
                                        }
                                    } else
                                        Picasso.get().load(publicacao.getUrlPDF1()).into(imgPDF1);
                                        Picasso.get().load(publicacao.getUrlPDF2()).into(imgPDF2);
                                        Picasso.get().load(publicacao.getUrlPDF3()).into(imgPDF3);
                                }
                                if(publicacao.getIdUserPub().equals(FirebaseAuth.getInstance().getUid())){
                                    imgConfiguracoes.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });

        }


    }
}
