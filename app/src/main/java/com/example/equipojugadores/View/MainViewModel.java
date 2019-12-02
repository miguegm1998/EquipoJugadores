package com.example.equipojugadores.View;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.equipojugadores.Model.Data.Equipo;
import com.example.equipojugadores.Model.Data.Jugador;
import com.example.equipojugadores.Model.Repository;

import java.io.File;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public MutableLiveData<List<Equipo>> getEquipoList(){
        return repository.getLiveEquipoList();
    }

    public MutableLiveData<List<Jugador>> getJugadorList(){
        return repository.getLiveJugadorList();
    }

    public void setUrl(String url) {
        repository.setUrl(url);
    }

    public void addEquipo(Equipo equipo) {
        repository.addEquipo(equipo);
    }

    public void updateEquipo(Equipo equipoF) {
        repository.updateEquipo(equipoF);
    }
    public void deleteEquipo(Equipo deletedEquipo) {
        repository.deleteEquipo(deletedEquipo);
    }

    public void addJugador(Jugador jugador) {
        repository.addJugador(jugador);
    }

    public void updateJugador(Jugador jugadorF) {
        repository.updateJugador(jugadorF);
    }

    public void deleteJugador(Jugador deletedJugador) {
        repository.deleteJugador(deletedJugador);
    }

    public void upload(File file) {
        repository.upload(file);
    }
}
