package com.ufc.com.ajudaai.view.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pessoa implements Parcelable {
    private String nome;
    private String email;
    private String senha;
    private String idUser;
    private String urlFoto;



    public Pessoa(){};
    public Pessoa(String nome, String email, String senha, String idUser, String urlFoto) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idUser = idUser;
        this.urlFoto = urlFoto;
    }


    protected Pessoa(Parcel in) {
        nome = in.readString();
        email = in.readString();
        senha = in.readString();
        idUser = in.readString();
        urlFoto = in.readString();
    }

    public static final Creator<Pessoa> CREATOR = new Creator<Pessoa>() {
        @Override
        public Pessoa createFromParcel(Parcel in) {
            return new Pessoa(in);
        }

        @Override
        public Pessoa[] newArray(int size) {
            return new Pessoa[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeString(idUser);
        dest.writeString(urlFoto);
    }
}
