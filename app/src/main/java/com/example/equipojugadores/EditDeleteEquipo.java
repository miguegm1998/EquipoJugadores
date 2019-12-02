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
import com.example.equipojugadores.Model.Data.Equipo;
import com.example.equipojugadores.View.MainViewModel;

public class EditDeleteEquipo extends AppCompatActivity {

    private String imageIDE;
    private boolean picked = false;
    private boolean filled = false;
    private ImageView ivEscudoEDE;
    private EditText etNomEquipoEDE, etCiudadEDE, etEstadioEDE, etAforoEDE;
    private MainViewModel viewModel;
    private Button btEditEDE, btDeleteEDE;
    private Intent intent;
    private Equipo equipo;

    String nombreE, ciudadE, estadioE;
    int aforoE;

    private static final int PHOTO_SELECTED = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_equipo);

        intent = getIntent();

        initComponents();

    }

    private void initComponents() {
        ivEscudoEDE = findViewById(R.id.ivEscudoEDE);
        etNomEquipoEDE = findViewById(R.id.etNomEquipoEDE);
        etCiudadEDE = findViewById(R.id.etCiudadEDE);
        etEstadioEDE = findViewById(R.id.etEstadioEDE);
        etAforoEDE = findViewById(R.id.etAforoEDE);

        btEditEDE = findViewById(R.id.btEditEquipoEDE);
        btDeleteEDE = findViewById(R.id.btDeleteEquipoEDE);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        equipo = intent.getParcelableExtra("equipoED");

        Uri imageID = Uri.parse(equipo.getEscudo());

        Glide.with(this)
                .load(imageID)
                .override(500, 500)
                .into(ivEscudoEDE);

        etNomEquipoEDE.setText(equipo.getNombre());
        etCiudadEDE.setText(equipo.getCiudad());
        etEstadioEDE.setText(equipo.getEstadio());
        etAforoEDE.setText(""+equipo.getAforo());

        initEvent();

    }

    private void initEvent() {
        seleccionarImagen(ivEscudoEDE);

        btDeleteEDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarEquipo();
                finish();
            }
        });

        btEditEDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprueba();
                if (picked && filled) {
                editarEquipo();
                finish();
            }else{
                    Toast.makeText(EditDeleteEquipo.this, R.string.err_selected, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void comprueba(){
        nombreE = etNomEquipoEDE.getText().toString();
        ciudadE = etCiudadEDE.getText().toString();
        estadioE = etEstadioEDE.getText().toString();
        aforoE = Integer.parseInt(etAforoEDE.getText().toString());


        if(!nombreE.isEmpty() && !ciudadE.isEmpty() && !estadioE.isEmpty() && aforoE != 0 && !imageIDE.isEmpty()){
            filled = true;
        }
    }

    private void editarEquipo() {
        Equipo equipoEdited = equipo;

        nombreE = etNomEquipoEDE.getText().toString();
        ciudadE = etCiudadEDE.getText().toString();
        estadioE = etEstadioEDE.getText().toString();
        aforoE = Integer.parseInt(etAforoEDE.getText().toString());

        if(picked){
            equipoEdited.setNombre(nombreE);
            equipoEdited.setCiudad(ciudadE);
            equipoEdited.setEstadio(estadioE);
            equipoEdited.setAforo(aforoE);
            equipoEdited.setEscudo(imageIDE);
            viewModel.updateEquipo(equipoEdited);
        }else{
            equipoEdited.setNombre(nombreE);
            equipoEdited.setCiudad(ciudadE);
            equipoEdited.setEstadio(estadioE);
            equipoEdited.setAforo(aforoE);
            viewModel.updateEquipo(equipoEdited);
        }
    }

    private void borrarEquipo() {
        viewModel.deleteEquipo(equipo);
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
                    .into(ivEscudoEDE);
            imageIDE = imageUri.toString();
        }
    }
}
