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

public class PC_RecyclerViewAdapter extends RecyclerView.Adapter<PC_RecyclerViewAdapter.MyViewHolder> {

    /*
    Declaraciones
     */
    Context context;
    ArrayList<PadresDeConfianzaModel> padresDeConfianzaModels;

    /*
    Constructor
     */
    public PC_RecyclerViewAdapter(Context context, ArrayList<PadresDeConfianzaModel> padresDeConfianzaModels){
        this.context = context;
        this.padresDeConfianzaModels = padresDeConfianzaModels;
    }

    @NonNull
    /*
    Infla layout, le da una mirada a cada una de las filas
     */
    @Override
    public PC_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new PC_RecyclerViewAdapter.MyViewHolder(view);
    }

    /*
    Le da contenido a cada una de las filas del recycler_view_row layout, basado en la posición del RV
     */
    @Override
    public void onBindViewHolder(@NonNull PC_RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.Nombre.setText(padresDeConfianzaModels.get(position).getNombre());
        holder.Hijo.setText(padresDeConfianzaModels.get(position).getHijos());
        holder.imageView.setImageResource(R.drawable.ic_baseline_child_care_24);
        holder.btnLlamar.setImageResource(R.drawable.ic_baseline_phone_forwarded_24);
        holder.btnEliminar.setImageResource(R.drawable.ic_baseline_highlight_off_24);

    }

    /*
    Número de elementos mostrados a la vez
     */
    @Override
    public int getItemCount() {
        return padresDeConfianzaModels.size();
    }

    /*
    Coge las view del archivo recycler_view_layout
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Nombre, Hijo;
        ImageView imageView;
        ImageButton btnLlamar, btnEliminar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nombre = itemView.findViewById(R.id.textViewCVNombrePC);
            Hijo = itemView.findViewById(R.id.textView2CVHijosPC);
            imageView = itemView.findViewById(R.id.imageViewCVAvatar);
            btnLlamar = itemView.findViewById(R.id.imageButtonCVLlamar);
            btnEliminar = itemView.findViewById(R.id.imageButtonCVEliminar);
        }
    }
}
