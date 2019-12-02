package com.example.equipojugadores;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddEquipo extends AppCompatActivity {

    private String imageIDE;
    private boolean picked = false;
    private boolean filled = false;
    private ImageView ivEscudoAE;
    private EditText etNomEquipoAE, etCiudadAE, etEstadioAE, etAforoAE;
    private MainViewModel viewModel;
    private Button btAddAE;
    private Intent intent;
    private Equipo equipo;

    private String nombreE, ciudadE, estadioE;
    private int aforoE;

    private static final int PHOTO_SELECTED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipo);

        initComponents();

        intent = getIntent();
    }

    private void initComponents() {

        ivEscudoAE = findViewById(R.id.ivEscudoAE);
        etNomEquipoAE = findViewById(R.id.etNombreEquipoAE);
        etCiudadAE = findViewById(R.id.etCiudadAE);
        etEstadioAE = findViewById(R.id.etEstadioAE);
        etAforoAE = findViewById(R.id.etAforoAE);
        btAddAE = findViewById(R.id.btAddEquipoAE);

        equipo = new Equipo();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        initEvents();

    }

    private void initEvents() {
        btAddAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprueba();
                if(picked && filled){
                    insertaEquipo();
                    finish();
                }else {
                    Toast.makeText(AddEquipo.this, R.string.err_selected, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void comprueba(){
        nombreE = etNomEquipoAE.getText().toString();
        ciudadE = etCiudadAE.getText().toString();
        estadioE = etEstadioAE.getText().toString();
        aforoE = Integer.parseInt(etAforoAE.getText().toString());


        if(!nombreE.isEmpty() && !ciudadE.isEmpty() && !estadioE.isEmpty() && aforoE != 0 && !imageIDE.isEmpty()){
            filled = true;
        }
    }


    private void insertaEquipo() {
        equipo.setNombre(nombreE);
        equipo.setCiudad(ciudadE);
        equipo.setEstadio(estadioE);
        equipo.setAforo(aforoE);
        saveSelectedImageInFile(Uri.parse(imageIDE));
        equipo.setEscudo(imageIDE);

        viewModel.addEquipo(equipo);

    }

    public void seleccionarImagen(View view) {
        Intent f = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        f.setType("*/*");
        String[] types = {"image/*"};
        f.putExtra(Intent.EXTRA_MIME_TYPES, types);
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
                    .into(ivEscudoAE);
            imageIDE = imageUri.toString();
            picked = true;
        }
    }

    private void saveSelectedImageInFile(Uri uri) {
        Bitmap bitmap = null;
        if(Build.VERSION.SDK_INT < 28) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                bitmap = null;
            }
        } else {
            try {
                final InputStream in = this.getContentResolver().openInputStream(uri);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                //ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
                //bitmap = ImageDecoder.decodeBitmap(source);
            } catch (IOException e) {
                bitmap = null;
            }
        }
        if(bitmap != null) {
            File file = saveBitmapInFile(bitmap);
            if(file != null) {
                viewModel.upload(file);
            }
        }
    }
    private File saveBitmapInFile(Bitmap bitmap) {

        File file = new File(getFilesDir(), "temp.jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            file = null;
        }
        return file;
    }
}
