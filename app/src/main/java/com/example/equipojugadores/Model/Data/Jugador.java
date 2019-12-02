package com.example.equipojugadores.Model.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Jugador implements Parcelable {

    private long id;
    private String name;
    private long idEquipo;
    private String apellidos;
    private String foto;

    public Jugador() {
    }

    public Jugador(long id, String name, long idEquipo, String apellidos, String foto) {
        this.id = id;
        this.name = name;
        this.idEquipo = idEquipo;
        this.apellidos = apellidos;
        this.foto = foto;
    }

    protected Jugador(Parcel in) {
        id = in.readLong();
        name = in.readString();
        idEquipo = in.readLong();
        apellidos = in.readString();
        foto = in.readString();
    }

    public static final Creator<Jugador> CREATOR = new Creator<Jugador>() {
        @Override
        public Jugador createFromParcel(Parcel in) {
            return new Jugador(in);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(idEquipo);
        dest.writeString(apellidos);
        dest.writeString(foto);
    }
}
