package com.example.equipojugadores;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.equipojugadores.Model.Data.Equipo;
import com.example.equipojugadores.Model.Data.Jugador;
import com.example.equipojugadores.View.MainViewModel;

public class EditDeleteJugador extends AppCompatActivity {

    private String imageIDE;
    private boolean picked = false;
    private boolean filled = false;
    private ImageView ivFotoEDJ;
    private EditText etNomJugadorEDJ, etApellidosEDJ;
    private MainViewModel viewModel;
    private Button btEditEDJ, btDeleteEDJ;
    private Intent intent;
    private Jugador jugador;

    private String nombreJE, apellidoJE;

    private static final int PHOTO_SELECTED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_jugador);
        
        intent = getIntent();
        
        jugador = intent.getParcelableExtra("jugador");
        
        initComponents();
        
    }

    private void initComponents() {
        ivFotoEDJ = findViewById(R.id.ivFotoEDJ);
        etNomJugadorEDJ = findViewById(R.id.etNombreEDJ);
        etApellidosEDJ = findViewById(R.id.etApellidoEDJ);

        btEditEDJ = findViewById(R.id.btEditJugador);
        btDeleteEDJ = findViewById(R.id.btDeleteJugador);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Uri imageID = Uri.parse(jugador.getFoto());

        Glide.with(this)
                .load(imageID)
                .override(500, 500)
                .into(ivFotoEDJ);

        etApellidosEDJ.setText(jugador.getApellidos());
        etNomJugadorEDJ.setText(jugador.getName());

        initEvents();
    }

    private void initEvents() {
        btEditEDJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprueba();
                if(picked && filled){
                    editarJugador();
                    finish();
                }
            }
        });

        btDeleteEDJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarJugador();
                finish();
            }
        });
    }

    private void borrarJugador() {
        viewModel.deleteJugador(jugador);
    }

    private void editarJugador() {
        Jugador jugadorE = jugador;
        if(picked){
            jugadorE.setName(nombreJE);
            jugadorE.setApellidos(apellidoJE);
            jugadorE.setFoto(imageIDE);
        }else{
            jugadorE.setName(nombreJE);
            jugadorE.setApellidos(apellidoJE);
        }

        viewModel.updateJugador(jugadorE);
    }

    public void comprueba(){
        nombreJE = etNomJugadorEDJ.getText().toString();
        apellidoJE = etApellidosEDJ.getText().toString();

        if(!nombreJE.isEmpty() && !apellidoJE.isEmpty() && !imageIDE.isEmpty()){
            filled = true;
        }
    }

    public void seleccionarImagen(View view) {
        Intent f = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(f, PHOTO_SELECTED);
        picked = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SELECTED && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri imageUri = data.getData();


            Glide.with(this)
                    .load(imageUri)
                    .override(500, 500)// prueba de escalado
                    .into(ivFotoEDJ);
            imageIDE = imageUri.toString();
        }
    }
}
