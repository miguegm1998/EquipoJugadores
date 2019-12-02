package com.example.equipojugadores.Model.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipo implements Parcelable {

    private long id;
    private String nombre;
    private String ciudad;
    private String estadio;
    private int aforo;
    private String escudo;

    public Equipo() {
    }

    public Equipo(long id, String nombre, String ciudad, String estadio, int aforo, String escudo) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estadio = estadio;
        this.aforo = aforo;
        this.escudo = escudo;
    }

    protected Equipo(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        ciudad = in.readString();
        estadio = in.readString();
        aforo = in.readInt();
        escudo = in.readString();
    }

    public static final Creator<Equipo> CREATOR = new Creator<Equipo>() {
        @Override
        public Equipo createFromParcel(Parcel in) {
            return new Equipo(in);
        }

        @Override
        public Equipo[] newArray(int size) {
            return new Equipo[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nombre);
        dest.writeString(ciudad);
        dest.writeString(estadio);
        dest.writeInt(aforo);
        dest.writeString(escudo);
    }
}
