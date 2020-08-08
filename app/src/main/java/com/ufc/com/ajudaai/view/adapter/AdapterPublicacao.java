package com.ufc.com.ajudaai.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ufc.com.ajudaai.R;
import com.ufc.com.ajudaai.view.model.Publicacao;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterPublicacao extends RecyclerView.Adapter<AdapterPublicacao.ViewHolderPublicacoes> {
    private ArrayList<Publicacao> listPublicacao;



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
        setDados(listPublicacao.get(position));
    }

    private void setDados(Publicacao publicacao) {
        /*FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc : docs) {
                            Usuario user = doc.toObject(Usuario.class);
                            if (carona.getIdMotorista().equals(user.getIdUser())) {
                                String[] firstName = user.getNomeUser().split(" ");
                                tvNomeMotorista.setText(firstName[0]);
                                Picasso.get().load(user.getUrlFotoUser()).into(imgPerfil);

                                tvEndSaida.setText(carona.getEnderecoSaida());
                                tvEndChegada.setText(carona.getEnderecoChegada());
                                tvData.setText(carona.getData());
                                tvHora.setText(carona.getHora());
                                tvQtdVagas.setText(String.valueOf(carona.getQtdVagas()));
                                tvHorarioChegadaLista.setText(carona.getHoraChegadaprox());
                                float d = user.getAvaliacao();
                                tvAvaliacao.setText(String.valueOf(d));
                                Log.i("teste", "valor: " + user.getAvaliacao());
                            }
                        }
                    }
                });*/

    }

    @Override
    public int getItemCount() {
        return listPublicacao.size();
    }

    class ViewHolderPublicacoes extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mensagem;
        ImageView imgPerfil;
        TextView nomeUser;
        ImageView imgConfiguracoes;
        ImageView imgPDF1,imgPDF2,imgPDF3;
        ImageView imgCurtir, imgComentar;

        ViewHolderPublicacoes(@NonNull View itemView) {
            super(itemView);
            mensagem = itemView.findViewById(R.id.txtMensagemLista);
            imgPerfil = itemView.findViewById(R.id.imgPerfilLista);
            nomeUser = itemView.findViewById(R.id.txtNomeLista);
            imgConfiguracoes = itemView.findViewById(R.id.imgConfiguracoes);
            imgPDF1 = itemView.findViewById(R.id.imgPDF1ListaDown);
            imgPDF2 = itemView.findViewById(R.id.imgPDF2ListaDown);
            imgPDF3 = itemView.findViewById(R.id.imgPDF3ListaDown);
            imgCurtir = itemView.findViewById(R.id.imgCurtirLista);
            imgComentar = itemView.findViewById(R.id.imgComentarLista);


            imgCurtir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Você curtiu essa publicação!", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }
}
