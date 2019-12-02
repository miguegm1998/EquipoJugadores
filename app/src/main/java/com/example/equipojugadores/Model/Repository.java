package com.example.equipojugadores.Model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.equipojugadores.Model.Data.Equipo;
import com.example.equipojugadores.Model.Data.Jugador;
import com.example.equipojugadores.Model.Rest.EquipoJugadoresClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private EquipoJugadoresClient apiClient;
    private String url="18.233.163.16";
    private MutableLiveData<List<Equipo>> mutableEquipoList = new MutableLiveData<>();
    private MutableLiveData<List<Jugador>> mutableJugadorList = new MutableLiveData<>();

    private final String TAG = "xxxx";

    public Repository(){
        retrieveApiClient(url);
        fetchEquipoList();
        fetchJugadorList();
    }


    private void retrieveApiClient(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + url + "/web/psp/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient = retrofit.create(EquipoJugadoresClient.class);
    }

    private void fetchEquipoList() {
        Call<ArrayList<Equipo>> call = apiClient.getEquipos();
        call.enqueue(new Callback<ArrayList<Equipo>>(){

            @Override
            public void onResponse(Call<ArrayList<Equipo>> call, Response<ArrayList<Equipo>> response) {
                mutableEquipoList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Equipo>> call, Throwable t) {
                mutableEquipoList = new MutableLiveData<>();
            }
        });
    }

    private void fetchJugadorList() {
        Call<ArrayList<Jugador>> call = apiClient.getJugadores();
        call.enqueue(new Callback<ArrayList<Jugador>>(){

            @Override
            public void onResponse(Call<ArrayList<Jugador>> call, Response<ArrayList<Jugador>> response) {
                mutableJugadorList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Jugador>> call, Throwable t) {
                mutableJugadorList = new MutableLiveData<>();
            }
        });

    }

    public MutableLiveData<List<Equipo>> getLiveEquipoList(){
        fetchEquipoList();
        return mutableEquipoList;
    }

    public MutableLiveData<List<Jugador>> getLiveJugadorList(){
        fetchJugadorList();
        return mutableJugadorList;
    }

    public void addEquipo(Equipo equipo) {
        Call<Long> call = apiClient.postEquipo(equipo);
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                fetchEquipoList();
                Log.v(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public void addJugador(Jugador jugador) {
        Call<Long> call = apiClient.postJugador(jugador);
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                fetchEquipoList();
                Log.v(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public void updateEquipo(Equipo equipoF) {
        Call<Integer> call = apiClient.putEquipo(equipoF.getId(), equipoF);
        fetchEquipoList();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                fetchEquipoList();
                System.out.println("Actualiza");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }


    public void updateJugador(Jugador jugadorF) {
        Call<Integer> call = apiClient.putJugador(jugadorF.getId(), jugadorF);
        fetchJugadorList();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                fetchJugadorList();
                System.out.println("Actualiza jugador");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public void setUrl(String url) {
        retrieveApiClient(url);
    }

    public void deleteEquipo(Equipo deletedEquipo) {
        Call<Boolean> call = apiClient.deleteEquipo(deletedEquipo.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                fetchEquipoList();
                System.out.println("Borrado");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public void deleteJugador(Jugador deletedJugador) {
        Call<Boolean> call = apiClient.deleteJugador(deletedJugador.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                fetchJugadorList();
                System.out.println("Borrado");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public void upload(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part request = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Call<String> call = apiClient.fileUpload(request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v(TAG, response.body());
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }


}
