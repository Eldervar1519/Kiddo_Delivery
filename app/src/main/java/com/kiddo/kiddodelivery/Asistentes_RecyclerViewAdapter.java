package com.kiddo.kiddodelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Asistentes_RecyclerViewAdapter extends RecyclerView.Adapter<Asistentes_RecyclerViewAdapter.MyViewHolder> {

    /*
    Declaraciones
    */
    Context context;
    static ArrayList<AsistentesModel> listaAsistentesModels = new ArrayList<>();

    @NonNull
    /*
    Infla layout, le da una mirada a cada una de las filas
     */
    @Override
    public Asistentes_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_asistentes, parent, false);

        return new Asistentes_RecyclerViewAdapter.MyViewHolder(view);
    }

    /*
    Le da contenido a cada una de las filas del recycler_view_row layout, basado en la posición del RV
    */
    @Override
    public void onBindViewHolder(@NonNull Asistentes_RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.Nombre.setText(listaAsistentesModels.get(position).getNombre());
        holder.Hijo.setText(listaAsistentesModels.get(position).getHijo());
        holder.ID.setText(listaAsistentesModels.get(position).getIdPc());
        holder.IdEvento.setText(listaAsistentesModels.get(position).getIdEvento());
        holder.Confirmacion.setText(listaAsistentesModels.get(position).getConfirmacion());
        holder.IBtnComfirmacion.setImageResource(R.drawable.ic_baseline_group_add_24);
        holder.IBtnMapa.setImageResource(R.drawable.ic_baseline_map_24);
    }

    /*
    Número de elementos mostrados
    */
    @Override
    public int getItemCount() {
        return listaAsistentesModels.size();
    }

    /*
    Clase estática, coje las view del layout RV_row_events, relacionamos
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Nombre, Hijo, ID, IdEvento, Confirmacion;
        ImageButton IBtnComfirmacion, IBtnMapa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nombre = itemView.findViewById(R.id.textViewCV3NombrePC);
            Hijo = itemView.findViewById(R.id.textViewCV3HijoPC);
            ID = itemView.findViewById(R.id.textViewCV3IDPC);
            IdEvento = itemView.findViewById(R.id.textViewCV3IDEvento);
            Confirmacion = itemView.findViewById(R.id.textViewCV3Confirmacion);
            IBtnComfirmacion = itemView.findViewById(R.id.imageButtonCV3Conf);
            IBtnMapa = itemView.findViewById(R.id.imageButtonCV3Mapa);
        }

    }
}
