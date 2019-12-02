package com.example.equipojugadores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

import com.example.equipojugadores.Model.Data.Equipo;
import com.example.equipojugadores.View.EquipoAdapter;
import com.example.equipojugadores.View.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {




    private RecyclerView myRecycler;
    private MainViewModel viewModel;
    private EquipoAdapter equipoAdapter;

    private Button btAñadirEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {

        myRecycler = findViewById(R.id.rvEquipos);
        btAñadirEquipo = findViewById(R.id.btAñadirEquipo);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));
        equipoAdapter = new EquipoAdapter(new EquipoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Equipo equipo, View v) {
                Intent i = new Intent(MainActivity.this, ViewEquipo.class);
                i.putExtra("equipo", equipo);
                startActivity(i);
            }
        },this);
        myRecycler.setAdapter(equipoAdapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getEquipoList().observe(this, new Observer<List<Equipo>>() {
            @Override
            public void onChanged(List<Equipo> equipos) {
                equipoAdapter.setMisEquipos(equipos);
            }
        });

        initEvents();
    }

    private void initEvents() {
        btAñadirEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddEquipo.class);
                startActivity(i);
            }
        });
    }
}
