package com.example.tesi;

import android.os.Parcel;
import android.os.Parcelable;

public class Persona implements Parcelable {
    private String nome,cognome,dataN;
    private String numero;

    public Persona(String nome,String cognome,String dataN,String numero) {
        this.cognome=cognome;
        this.nome=nome;
        this.dataN=dataN;
        this.numero=numero;
    }


    protected Persona(Parcel in) {
        nome = in.readString();
        cognome = in.readString();
        dataN = in.readString();
        numero = in.readString();
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(cognome);
        dest.writeString(dataN);
        dest.writeString(numero);
    }

    public String getNumero() {
        return numero;
    }

    public String getDataN() {
        return dataN;
    }

    public void setDataN(String dataN) {
        this.dataN = dataN;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

}
