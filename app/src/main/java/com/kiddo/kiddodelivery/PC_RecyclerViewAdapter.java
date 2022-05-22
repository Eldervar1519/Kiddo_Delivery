package com.kiddo.kiddodelivery;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public PC_RecyclerViewAdapter(Context context, ArrayList<PadresDeConfianzaModel> padresDeConfianzaModels) {
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

        /*
        Relacionamos
         */
        holder.Nombre.setText(padresDeConfianzaModels.get(position).getNombre());
        holder.Hijo.setText(padresDeConfianzaModels.get(position).getHijos());
        holder.Tlf.setText(padresDeConfianzaModels.get(position).getTlf());
        holder.UId.setText(padresDeConfianzaModels.get(position).getUid());
        holder.MUId.setText(padresDeConfianzaModels.get(position).getMuid());
        holder.imageView.setImageResource(R.drawable.ic_baseline_child_care_24);
        holder.btnLlamar.setImageResource(R.drawable.ic_baseline_phone_forwarded_24);
        holder.btnEliminar.setImageResource(R.drawable.ic_baseline_highlight_off_24);

        /*
        Damos funcionalidad
         */
        holder.btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tlf = holder.Tlf.getText().toString();

                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tlf));
                context.startActivity(i);
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pcuid = holder.UId.getText().toString();
                String muid = holder.MUId.getText().toString();

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Desea eliminar este padre de su lista de confianza?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        FirebaseAuth mAuth;
                        DatabaseReference mDatabase;
                        final String URL = "https://kiddodelivery-7e28a-default-rtdb.europe-west1.firebasedatabase.app/";

                        mAuth = FirebaseAuth.getInstance();
                        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

                        mDatabase.child("usuarios").child(muid).child("padresConf").child(pcuid).removeValue();
                        mDatabase.child("usuarios").child(pcuid).child("padresConf").child(muid).removeValue();

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
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Nombre, Hijo, Tlf, UId, MUId;
        ImageView imageView;
        ImageButton btnLlamar, btnEliminar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nombre = itemView.findViewById(R.id.textViewCVNombrePC);
            Hijo = itemView.findViewById(R.id.textView2CVHijosPC);
            Tlf = itemView.findViewById(R.id.textViewCVTlf);
            UId = itemView.findViewById(R.id.textViewCVUId);
            MUId = itemView.findViewById(R.id.textViewMUId);
            imageView = itemView.findViewById(R.id.imageViewCVAvatar);
            btnLlamar = itemView.findViewById(R.id.imageButtonCVLlamar);
            btnEliminar = itemView.findViewById(R.id.imageButtonCVEliminar);
        }
    }

    public void removeItem(int pos){
        padresDeConfianzaModels.remove(pos);
        notifyItemRemoved(pos);
    }


}
