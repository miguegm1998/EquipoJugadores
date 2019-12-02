package com.example.equipojugadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.equipojugadores.Model.Data.Jugador;

public class ViewJugadorUnico extends AppCompatActivity {

    
    private ImageView ivFotoVJ;
    private TextView tvNombreJVJ, tvApellidosVJ;
    private Button btEditDeleteJugador;
    private Jugador jugadorActual = new Jugador();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jugador_unico);

        Intent intent = getIntent();
        jugadorActual = intent.getParcelableExtra("jugador");
        
        initComponents();
    }

    private void initComponents() {
        ivFotoVJ = findViewById(R.id.ivFotoVJ);
        tvNombreJVJ = findViewById(R.id.tvNombreJVJ);
        tvApellidosVJ = findViewById(R.id.tvApellidosVJ);

        Uri imageID = Uri.parse(jugadorActual.getFoto());

        Glide.with(this)
                .load(imageID).override(500, 500)
                .into(ivFotoVJ);
        tvNombreJVJ.setText(jugadorActual.getName());
        tvApellidosVJ.setText(jugadorActual.getApellidos());

        btEditDeleteJugador = findViewById(R.id.btEditDeleteJugador);

        initEvents();
    }

    private void initEvents() {
        btEditDeleteJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewJugadorUnico.this, EditDeleteJugador.class);
                i.putExtra("jugador", jugadorActual);
                startActivity(i);
            }
        });
    }



}
