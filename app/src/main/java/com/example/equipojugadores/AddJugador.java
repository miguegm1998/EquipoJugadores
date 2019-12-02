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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.equipojugadores.Model.Data.Jugador;
import com.example.equipojugadores.View.MainViewModel;

public class AddJugador extends AppCompatActivity {

    private long idEquipo;

    private String imageIDE;
    private boolean picked = false;
    private boolean filled = false;
    private EditText etNombreAJ, etApellidoAJ;
    private ImageView ivFotoAJ;
    private Button btAddJugador;
    private MainViewModel viewModel;
    private String nombreAJ, apellidoAJ;

    private Jugador jugador;

    private static final int PHOTO_SELECTED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jugador);

        Intent i = getIntent();

        idEquipo = i.getLongExtra("idEquipo", 0);

        initComponents();
    }

    private void initComponents() {

        etApellidoAJ = findViewById(R.id.etApellidoAJ);
        etNombreAJ = findViewById(R.id.etNombreAJ);
        ivFotoAJ = findViewById(R.id.ivFotoAJ);
        btAddJugador = findViewById(R.id.btAddJugador);

        jugador = new Jugador();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        initEvents();

    }

    private void initEvents() {
        btAddJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprueba();
                if(picked && filled){
                    insertaJugador();
                    finish();
                }else {
                    Toast.makeText(AddJugador.this, R.string.err_selected, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void insertaJugador() {
        jugador.setName(nombreAJ);
        jugador.setApellidos(apellidoAJ);
        jugador.setIdEquipo(idEquipo);
        jugador.setFoto(imageIDE);

        viewModel.addJugador(jugador);
    }

    public void comprueba(){
        nombreAJ = etNombreAJ.getText().toString();
        apellidoAJ = etApellidoAJ.getText().toString();


        if(!nombreAJ.isEmpty() && !apellidoAJ.isEmpty()  && !imageIDE.isEmpty()){
            filled = true;
        }
    }

    public void seleccionarImagen(View view) {
        Intent f = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(f, PHOTO_SELECTED);
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
                    .into(ivFotoAJ);
            imageIDE = imageUri.toString();
            picked = true;
        }
    }
}
