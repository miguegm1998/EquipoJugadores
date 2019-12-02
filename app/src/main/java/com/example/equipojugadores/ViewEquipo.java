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
import com.example.equipojugadores.Model.Data.Equipo;

public class ViewEquipo extends AppCompatActivity {

    private TextView tvNomEquipoVE, tvCiudadVE, tvEstadioVE, tvAforoVE;
    private ImageView ivEscudoVE;
    private Button btEditDeleteEquipoVE, btVerJugadorVE;
    private Equipo equipo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_equipo);

        initComponents();

        intent = getIntent();
    }

    private void initComponents() {
        tvNomEquipoVE = findViewById(R.id.tvNomEquipoVE);
        tvCiudadVE = findViewById(R.id.tvCiudadVE);
        tvEstadioVE = findViewById(R.id.tvEstadioVE);
        tvAforoVE = findViewById(R.id.tvAforoVE);
        ivEscudoVE = findViewById(R.id.ivEscudoVE);
        btEditDeleteEquipoVE = findViewById(R.id.btEditDeleteEquipo);
        btVerJugadorVE = findViewById(R.id.btVerJugadoresVE);

        equipo = intent.getParcelableExtra("equipo");

        Uri imageID = Uri.parse(equipo.getEscudo());

        Glide.with(this)
                .load(imageID).override(500, 500)
                .into(ivEscudoVE);

        tvNomEquipoVE.setText(equipo.getNombre());
        tvCiudadVE.setText(equipo.getCiudad());
        tvEstadioVE.setText(equipo.getEstadio());
        tvAforoVE.setText(""+equipo.getAforo());

        initEvents();
    }

    private void initEvents() {

        btEditDeleteEquipoVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewEquipo.this, EditDeleteEquipo.class);
                i.putExtra("equipoED", equipo);
                startActivity(i);
                finish();
            }
        });

        btVerJugadorVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(ViewEquipo.this, ViewJugadores.class);
                f.putExtra("equipoVJ", equipo);
                startActivity(f);
            }
        });
    }
}
