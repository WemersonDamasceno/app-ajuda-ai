package com.ufc.com.ajudaai.view.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Publicacao implements Parcelable {
    private String mensagem;
    private String TAGs;
    private String idPublicacao;
    private String idUserPub;
    private String urlPDF1;
    private String urlPDF2;
    private String urlPDF3;

    public Publicacao() {
    }

    public Publicacao(String mensagem, String TAGs,
                      String idPublicacao, String idUserPub, String urlPDF1, String urlPDF2, String urlPDF3) {
        this.mensagem = mensagem;
        this.TAGs = TAGs;
        this.idPublicacao = idPublicacao;
        this.idUserPub = idUserPub;
        this.urlPDF1 = urlPDF1;
        this.urlPDF2 = urlPDF2;
        this.urlPDF3 = urlPDF3;
    }


    protected Publicacao(Parcel in) {
        mensagem = in.readString();
        TAGs = in.readString();
        idPublicacao = in.readString();
        idUserPub = in.readString();
        urlPDF1 = in.readString();
        urlPDF2 = in.readString();
        urlPDF3 = in.readString();
    }

    public static final Creator<Publicacao> CREATOR = new Creator<Publicacao>() {
        @Override
        public Publicacao createFromParcel(Parcel in) {
            return new Publicacao(in);
        }

        @Override
        public Publicacao[] newArray(int size) {
            return new Publicacao[size];
        }
    };

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTAGs() {
        return TAGs;
    }

    public void setTAGs(String TAGs) {
        this.TAGs = TAGs;
    }

    public String getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(String idPublicacao) {
        this.idPublicacao = idPublicacao;
    }

    public String getIdUserPub() {
        return idUserPub;
    }

    public void setIdUserPub(String idUserPub) {
        this.idUserPub = idUserPub;
    }

    public String getUrlPDF1() {
        return urlPDF1;
    }

    public void setUrlPDF1(String urlPDF1) {
        this.urlPDF1 = urlPDF1;
    }

    public String getUrlPDF2() {
        return urlPDF2;
    }

    public void setUrlPDF2(String urlPDF2) {
        this.urlPDF2 = urlPDF2;
    }

    public String getUrlPDF3() {
        return urlPDF3;
    }

    public void setUrlPDF3(String urlPDF3) {
        this.urlPDF3 = urlPDF3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mensagem);
        dest.writeString(TAGs);
        dest.writeString(idPublicacao);
        dest.writeString(idUserPub);
        dest.writeString(urlPDF1);
        dest.writeString(urlPDF2);
        dest.writeString(urlPDF3);
    }
}
