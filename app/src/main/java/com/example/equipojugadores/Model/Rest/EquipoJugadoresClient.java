package com.example.equipojugadores.Model.Rest;

import com.example.equipojugadores.Model.Data.Equipo;
import com.example.equipojugadores.Model.Data.Jugador;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface EquipoJugadoresClient {

    /////////EQUIPO/////////////
    @DELETE("equipo/{id}")
    Call<Boolean> deleteEquipo(@Path("id") long id);

    @GET("equipo/{id}")
    Call<Equipo> getEquipo(@Path("id") long id);

    @GET("equipo")
    Call<ArrayList<Equipo>> getEquipos();

    @POST("equipo")
    Call<Long> postEquipo(@Body Equipo equipo);

    @PUT("equipo/{id}")
    Call<Integer> putEquipo(@Path("id") long id, @Body Equipo equipo);




    ////////JUGADORES//////////////



    @DELETE("jugador/{id}")
    Call<Boolean> deleteJugador(@Path("id") long id);

    @GET("jugador/{id}")
    Call<Jugador> getJugador(@Path("id") long id);

    @GET("jugador")
    Call<ArrayList<Jugador>> getJugadores();

    @POST("jugador")
    Call<Long> postJugador(@Body Jugador jugador);

    @PUT("jugador/{id}")
    Call<Integer> putJugador(@Path("id") long id, @Body Jugador jugador);


    @Multipart
    @POST("upload")
    Call<String> fileUpload(@Part MultipartBody.Part file);

}
