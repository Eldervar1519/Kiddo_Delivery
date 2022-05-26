package com.kiddo.kiddodelivery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Eventos_RecyclerViewAdapter extends RecyclerView.Adapter<Eventos_RecyclerViewAdapter.MyViewHolder> {

    /*
    Declaraciones
     */
    Context context;
    static ArrayList<EventosModel> listaEventosModels = new ArrayList<>();

    final String[] opcion = {"Tengo hueco", "Necesito un favor", "Voy completo"};
    int opcionSeleccionada;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    final String URL = "https://kiddodelivery-7e28a-default-rtdb.europe-west1.firebasedatabase.app/";

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
        Comprobamos si ya tiene confirmado el evento
         */
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();
        String uid = mAuth.getCurrentUser().getUid();
        String idEvento = holder.ID.getText().toString();

        mDatabase.child("usuarios").child(uid).child("eventos").child(idEvento).child("confirmacion")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){

                            holder.Notificacion.setVisibility(View.GONE);
                            holder.Mapa.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Error BD", Toast.LENGTH_SHORT).show();
                    }
                });


        /*
        Funcionalidad botones
         */

        /*
        Btn Eliminar Evento
         */
        holder.Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Desea eliminar este evento?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        FirebaseAuth mAuth;
                        DatabaseReference mDatabase;
                        final String URL = "https://kiddodelivery-7e28a-default-rtdb.europe-west1.firebasedatabase.app/";

                        mAuth = FirebaseAuth.getInstance();
                        mDatabase = FirebaseDatabase.getInstance(URL).getReference();
                        String uid = mAuth.getCurrentUser().getUid();
                        String idEvento = holder.ID.getText().toString();

                        mDatabase.child("usuarios").child(uid).child("eventos").child(idEvento).removeValue();

                        removeItem(position);
                    }
                });

                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });

                dialogo1.show();
            }
        });

        /*
        Btn notificación
         */
        holder.Notificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Confirma su asistencia a este evento?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        dialogo1.dismiss();
                        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(context);
                        dialogo2.setTitle("Seleccione una opción:");
                        dialogo2.setCancelable(false);
                        dialogo2.setSingleChoiceItems(opcion, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                opcionSeleccionada = i;
                            }
                        });

                        mAuth = FirebaseAuth.getInstance();
                        mDatabase = FirebaseDatabase.getInstance(URL).getReference();
                        String uid = mAuth.getCurrentUser().getUid();
                        String idEvento = holder.ID.getText().toString();

                        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (opcionSeleccionada == 0)
                                    mDatabase.child("usuarios").child(uid).child("eventos").child(idEvento)
                                            .child("confirmacion").setValue("hueco");

                                else if (opcionSeleccionada == 1)
                                    mDatabase.child("usuarios").child(uid).child("eventos").child(idEvento)
                                            .child("confirmacion").setValue("favor");

                                else
                                    mDatabase.child("usuarios").child(uid).child("eventos").child(idEvento)
                                            .child("confirmacion").setValue("completo");

                            }
                        });

                        dialogo2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo2, int id) {
                                dialogo2.dismiss();
                            }
                        });

                        dialogo2.show();
                    }
                });

                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });

                dialogo1.show();
            }
        });

        /*
        Botón navegación
         */
        holder.Mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String direccion = holder.Direccion.getText().toString();

                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + direccion);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);

            }
        });
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
