package com.kiddo.kiddodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AsistentesActivity extends AppCompatActivity {

    private RecyclerView RVAsistentes;
    private int imagebtnConfirmacion, imagebtnMap;
    static String IdEvento = "";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final String URL = "https://kiddodelivery-7e28a-default-rtdb.europe-west1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistentes);

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
        RVAsistentes = findViewById(R.id.recyclerViewAsistentes);
        imagebtnConfirmacion = R.drawable.ic_baseline_group_add_24;
        imagebtnMap = R.drawable.ic_baseline_map_24;

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        /*
        Para el RecyclerView...
         */
        construirAsistentes();





    }

    /*
    Método para obtener la info y construir asistentes
     */
    private void construirAsistentes(){

        String uid = mAuth.getCurrentUser().getUid();
        ArrayList<String> listaAsistentes = new ArrayList<>();

        mDatabase.child("usuarios").child("eventos")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot child : snapshot.getChildren())
                    listaAsistentes.add(child.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AsistentesActivity.this, "No se encontraron coincidencias", Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(this, "se ejecuta", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < listaAsistentes.size(); i++){
            Toast.makeText(this, listaAsistentes.get(i), Toast.LENGTH_SHORT).show();
        }

    }

}