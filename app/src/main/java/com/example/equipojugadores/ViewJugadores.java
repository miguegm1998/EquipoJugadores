package com.example.equipojugadores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.equipojugadores.Model.Data.Equipo;
import com.example.equipojugadores.Model.Data.Jugador;
import com.example.equipojugadores.View.JugadorAdapter;
import com.example.equipojugadores.View.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewJugadores extends AppCompatActivity {

    private Button btAddJugador;
    private RecyclerView myRecyclerJugadores;
    private MainViewModel viewModel;
    private JugadorAdapter jugadorAdapter;
    private Equipo eqActual;
    public static List<Jugador> jugadores = new ArrayList<>();
    private List<Jugador> jugadoresEquipo;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jugadores);

        intent = getIntent();
        eqActual = intent.getParcelableExtra("equipoVJ");

        initComponents();

    }

    private void initComponents() {

        btAddJugador = findViewById(R.id.btAddJugador);
        myRecyclerJugadores = findViewById(R.id.rvJugadores);
        myRecyclerJugadores.setLayoutManager(new LinearLayoutManager(this));

        jugadorAdapter = new JugadorAdapter(new JugadorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Jugador jugador, View v) {
                Intent i = new Intent(ViewJugadores.this, ViewJugadorUnico.class);
                i.putExtra("jugador", jugador);
                startActivity(i);
            }
        }, this);

        myRecyclerJugadores.setAdapter(jugadorAdapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getJugadorList().observe(this, new Observer<List<Jugador>>() {
            @Override
            public void onChanged(List<Jugador> jugadores) {
                ViewJugadores.jugadores = jugadores;
                recuperaJugadores();
                jugadorAdapter.setMisJugadores(jugadoresEquipo);
            }
        });

        initEvents();

    }

    private void initEvents() {
        btAddJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewJugadores.this, AddJugador.class);
                i.putExtra("idEquipo", eqActual.getId());
                startActivity(i);
            }
        });
    }

    private void recuperaJugadores() {
        jugadoresEquipo = new ArrayList<>();

        for(Jugador j:jugadores){
            if(j.getIdEquipo() == eqActual.getId()){
                jugadoresEquipo.add(j);
            }
        }
    }
}
