package com.kiddo.kiddodelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Eventos_RecyclerViewAdapter extends RecyclerView.Adapter<Eventos_RecyclerViewAdapter.MyViewHolder> {

    /*
    Declaraciones
     */
    Context context;
    static ArrayList<EventosModel> listaEventosModels = new ArrayList<>();

    /*
    Constructor
     */
    public Eventos_RecyclerViewAdapter(Context context, ArrayList<EventosModel> listaEventosModels) {
        this.context = context;
        this.listaEventosModels = listaEventosModels;
    }

    @NonNull
    /*
    Infla layout, le da una mirada a cada una de las filas
     */
    @Override
    public Eventos_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_events, parent, false);

        return new Eventos_RecyclerViewAdapter.MyViewHolder(view);
    }

    /*
    Le da contenido a cada una de las filas del recycler_view_row layout, basado en la posición del RV
    */
    @Override
    public void onBindViewHolder(@NonNull Eventos_RecyclerViewAdapter.MyViewHolder holder, int position) {

        /*
        Relacionamos
         */
        holder.Titulo.setText(listaEventosModels.get(position).getTitulo());
        holder.FechaHora.setText(listaEventosModels.get(position).getFechaHora());
        holder.Direccion.setText(listaEventosModels.get(position).getDireccion());
        holder.ID.setText(listaEventosModels.get(position).getId());
        holder.AvatarCoche.setImageResource(R.drawable.ic_baseline_directions_car_24);
        holder.Eliminar.setImageResource(R.drawable.ic_baseline_highlight_off_24);
        holder.Mapa.setImageResource(R.drawable.ic_baseline_map_24);
        holder.Notificacion.setImageResource(R.drawable.ic_baseline_notification_important_24);

        /*
        Funcionalidad botones
         */

    }

    /*
    Número de elementos mostrados
     */
    @Override
    public int getItemCount() {
        return listaEventosModels.size();
    }

    /*
    Clase estática, coje las view del layout RV_row_events
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Titulo, FechaHora, Direccion, ID;
        ImageView AvatarCoche;
        ImageButton Eliminar, Notificacion, Mapa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Titulo = itemView.findViewById(R.id.textViewCV2TituloEvento);
            FechaHora = itemView.findViewById(R.id.textViewCV2HoraEvento);
            Direccion = itemView.findViewById(R.id.textViewCV2Direccion);
            ID = itemView.findViewById(R.id.textViewCV2ID);
            AvatarCoche = itemView.findViewById(R.id.imageViewCV2AvatarCoche);
            Eliminar = itemView.findViewById(R.id.imageButtonEventosEliminar);
            Notificacion = itemView.findViewById(R.id.imageButtonCV2Notificacion);
            Mapa = itemView.findViewById(R.id.imageButtonCV2Map);
        }
    }

    /*
    Método para eliminar evento
     */
    public void removeItem(int pos){
        listaEventosModels.remove(pos);
        notifyItemRemoved(pos);
    }
}
