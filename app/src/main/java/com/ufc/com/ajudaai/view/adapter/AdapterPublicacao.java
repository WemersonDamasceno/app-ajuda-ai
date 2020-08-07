package com.ufc.com.ajudaai.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterPublicacao extends RecyclerView.Adapter<AdapterPublicacao.ViewHolderPublicacoes> {


    @NonNull
    @Override
    public ViewHolderPublicacoes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPublicacoes holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolderPublicacoes extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolderPublicacoes(@NonNull View itemView) {


            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
