package com.kiddo.kiddodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventosActivity extends AppCompatActivity {

    private Button NuevoEvento;
    private RecyclerView RVEventos;
    int imageIcono, imagebtnNotificacion, imagebtnEliminar, imagebtnMap;

    private ArrayList<String> listaEventosIds = new ArrayList<>();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final String URL = "https://kiddodelivery-7e28a-default-rtdb.europe-west1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        Eventos_RecyclerViewAdapter.listaEventosModels.clear();

        /*
        redondear imagen
         */
        //extraemos el drawable en un bitmap
        Drawable originalDrawable = getResources().getDrawable(R.drawable.icono_kiddodelivery);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        ImageView imageView = (ImageView) findViewById(R.id.imageViewIcono);
        imageView.setImageDrawable(roundedDrawable);


        /*
        Relacionamos/instanciamos
         */
        NuevoEvento = findViewById(R.id.buttonNuevoEvento);
        RVEventos = findViewById(R.id.RecyclerViewEvents);

        imageIcono = R.drawable.ic_baseline_directions_car_24;
        imagebtnNotificacion = R.drawable.ic_baseline_notification_important_24;
        imagebtnEliminar = R.drawable.ic_baseline_highlight_off_24;
        imagebtnMap = R.drawable.ic_baseline_map_24;

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        /*
        Funcionalidad
         */
        NuevoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventosActivity.this, NuevoEventoActivity.class);
                startActivity(i);
            }
        });

        /*
        Para el RecyvlerView...
         */
        construirEventos();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Eventos_RecyclerViewAdapter adapter = new Eventos_RecyclerViewAdapter(EventosActivity.this, Eventos_RecyclerViewAdapter.listaEventosModels);
                RVEventos.setAdapter(adapter);
                RVEventos.setLayoutManager(new LinearLayoutManager(EventosActivity.this));
            }
        }, 2000);
    }

    /*
    Método para construir EventosModels
     */
    private void construirEventos() {

        String Uid = mAuth.getCurrentUser().getUid();

        mDatabase.child("usuarios").child(Uid).child("eventos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren())
                        listaEventosIds.add(child.getKey());

                    for (int i = 0; i < listaEventosIds.size(); i++){
                        mDatabase.child("usuarios").child(Uid).child("eventos").child(listaEventosIds.get(i))
                                .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){

                                    String titulo = snapshot.child("titulo").getValue().toString();
                                    String direccion = snapshot.child("direccion").getValue().toString();
                                    String fecha = snapshot.child("fecha").getValue().toString();
                                    String inicio = snapshot.child("inicio").getValue().toString();
                                    String fin = snapshot.child("fin").getValue().toString();
                                    String id = snapshot.getKey();

                                    String fechaHora = fecha + " " +
                                            inicio + "-" +
                                            fin;

                                    Eventos_RecyclerViewAdapter.listaEventosModels.add(new EventosModel(titulo,
                                            fechaHora,
                                            direccion,
                                            id,
                                            imageIcono,
                                            imagebtnNotificacion,
                                            imagebtnEliminar,
                                            imagebtnMap));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error){
                                Toast.makeText(EventosActivity.this, "Error al acceder a BBDD", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EventosActivity.this, "Error BD", Toast.LENGTH_SHORT).show();
            }
        });
    }
}