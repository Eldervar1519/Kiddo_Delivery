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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AsistentesActivity extends AppCompatActivity {

    private RecyclerView RVAsistentes;
    private int imagebtnConfirmacion, imagebtnMap;
    static String IdEvento = "";
    ArrayList<String> listaAsistentes = new ArrayList<>();


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
        ImageView imageView = findViewById(R.id.imageViewIcono);
        imageView.setImageDrawable(roundedDrawable);

        /*
        Relacionamos/instanciamos
         */
        RVAsistentes = findViewById(R.id.recyclerViewAsistentes);
        imagebtnConfirmacion = R.drawable.ic_baseline_group_add_24;
        imagebtnMap = R.drawable.ic_baseline_map_24;

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        Toast.makeText(this, IdEvento, Toast.LENGTH_SHORT).show();

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

        ArrayList<String> listaIdsUsuarios = new ArrayList<>();

        mDatabase.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    listaIdsUsuarios.add(child.getKey());
                }
                
                



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toast.makeText(this, "Se ejecuta", Toast.LENGTH_SHORT).show();


        for (int i = 0; i < listaIdsUsuarios.size(); i++){

            Toast.makeText(this, listaIdsUsuarios.get(i), Toast.LENGTH_SHORT).show();
            
            mDatabase.child("usuarios").child(listaIdsUsuarios.get(i)).child("eventos")
                    .orderByChild("id").equalTo(IdEvento).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        Toast.makeText(AsistentesActivity.this, "Existe", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(AsistentesActivity.this, "No existe", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            
        }


/*
        Query query = FirebaseDatabase.getInstance(URL).getReference("eventos")
                .orderByChild("id")
                .equalTo(IdEvento);

        query.addListenerForSingleValueEvent(VEL);

        for (int i = 0; i < listaAsistentes.size(); i++){
            Toast.makeText(this, listaAsistentes.get(i), Toast.LENGTH_SHORT).show();
        }

    }

    ValueEventListener VEL = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {

                Toast.makeText(AsistentesActivity.this, snapshot.getKey(), Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(AsistentesActivity.this, "No se encontraron coincidencias", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(AsistentesActivity.this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
        }
    };

 */

}
}