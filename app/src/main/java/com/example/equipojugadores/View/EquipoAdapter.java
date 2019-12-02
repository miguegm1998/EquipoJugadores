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
import com.example.equipojugadores.R;



import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.MyViewHolder> {

    private LayoutInflater inflaterE;
    public static List<Equipo> misEquipos;
    private EquipoAdapter.OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(Equipo equipo, View v);
    }

    public EquipoAdapter(EquipoAdapter.OnItemClickListener listener, Context context){
        this.listener = listener;
        inflaterE = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflaterE.inflate(R.layout.item_equipo, parent, false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(misEquipos != null){
            final Equipo current = misEquipos.get(position);
            Uri imageUri = Uri.parse(current.getEscudo());

            Glide.with(context)
                    .load(imageUri).override(500, 500)
                    .into(holder.ivEscudo);
            holder.tvNomEquipo.setText(current.getNombre());

            holder.clItemEquipo.setOnClickListener(new View.OnClickListener() {
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
        ImageView ivEscudo;
        TextView tvNomEquipo;
        ConstraintLayout clItemEquipo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEscudo = itemView.findViewById(R.id.ivEscudo);
            tvNomEquipo = itemView.findViewById(R.id.tvNomEquipo);
            clItemEquipo = itemView.findViewById(R.id.clItemEquipo);
        }
    }

    public void setMisEquipos(List<Equipo> misEquipos){
        this.misEquipos = misEquipos;
        notifyDataSetChanged();
    }
}
