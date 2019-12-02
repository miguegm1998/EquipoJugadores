package com.example.equipojugadores.View;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.equipojugadores.Model.Data.Equipo;
import com.example.equipojugadores.Model.Data.Jugador;
import com.example.equipojugadores.R;

import java.util.List;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.MyViewHolder>{

    private LayoutInflater inflaterJ;
    public static List<Jugador> misJugadores;
    private JugadorAdapter.OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(Jugador jugador, View v);
    }

    public JugadorAdapter(JugadorAdapter.OnItemClickListener listener, Context context){
        this.listener = listener;
        inflaterJ = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflaterJ.inflate(R.layout.item_jugadores, parent, false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(misJugadores != null){
            final Jugador current = misJugadores.get(position);
            Uri imageUri = Uri.parse(current.getFoto());

            Glide.with(context)
                    .load(imageUri)
                    .override(500, 500)
                    .into(holder.ivFoto);

            holder.tvNombreJugador.setText(current.getName());

            holder.clItemJugador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(current, v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNombreJugador;
        ConstraintLayout clItemJugador;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFotoJugador);
            tvNombreJugador = itemView.findViewById(R.id.tvNombreJugador);
            clItemJugador = itemView.findViewById(R.id.clItemJugador);
        }
    }

    public void setMisJugadores(List<Jugador> misJugadores){
        this.misJugadores = misJugadores;
        notifyDataSetChanged();
    }
}
