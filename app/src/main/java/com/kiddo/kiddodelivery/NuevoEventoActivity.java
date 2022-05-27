package com.kiddo.kiddodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NuevoEventoActivity extends AppCompatActivity {

    private CalendarView mCalendar;
    private EditText Titulo, Direccion, Inicio, Fin;
    private Switch PConoff;
    private Button Crear;

    private String titulo, direccion, inicio, fin, fecha, eventoID;
    private ArrayList<String> listaPCIds = new ArrayList<>();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final String URL = "https://kiddodelivery-7e28a-default-rtdb.europe-west1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);

        Toast.makeText(NuevoEventoActivity.this, "Seleccione la fecha del evento", Toast.LENGTH_SHORT).show();

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
        Asignamos/instanciamos
         */
        mCalendar = findViewById(R.id.calendarView);
        Titulo = findViewById(R.id.editTextTituloEvento);
        Direccion = findViewById(R.id.editTextDireccionEvento);
        Inicio = findViewById(R.id.editTextHoraInicio);
        Fin = findViewById(R.id.editTextHoraFin);
        PConoff = findViewById(R.id.switch1);
        Crear = findViewById(R.id.buttonCrearEvento);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        /*
        Obtenemos UIDs de PC
         */
        obtenerPCids();


        /*
        Funcionalidad
         */
        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                fecha = i2 + "/" + (i1 + 1) + "/" + i;

                mCalendar.setVisibility(View.GONE);
                Titulo.setVisibility(View.VISIBLE);
                Direccion.setVisibility(View.VISIBLE);
                Inicio.setVisibility(View.VISIBLE);
                Fin.setVisibility(View.VISIBLE);
                PConoff.setVisibility(View.VISIBLE);
                Crear.setVisibility(View.VISIBLE);

            }
        });

        Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                titulo = Titulo.getText().toString();
                direccion = Direccion.getText().toString();
                inicio = Inicio.getText().toString();
                fin = Fin.getText().toString();

                /*
                Validaciones
                 */
                if (!titulo.isEmpty() && !direccion.isEmpty() && !inicio.isEmpty() && !fin.isEmpty()){
                    
                    if (inicio.contains(":") && inicio.length() == 5 && fin.contains(":") && fin.length() == 5) {

                        if (PConoff.isChecked())
                            añadirEventoPC();
                        else
                            añadirMiEvento();
                        
                    }else
                        Toast.makeText(NuevoEventoActivity.this, "La hora debe estar en formato hh:mm", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(NuevoEventoActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Método para crear nuevo evento para el propio usuario
     */
    private void añadirMiEvento() {

        String uid = mAuth.getCurrentUser().getUid();

        String key = mDatabase.child("usuarios").child(uid).child("eventos").push().getKey();

        Map<String, Object> map = new HashMap<>();
        map.put("titulo", titulo);
        map.put("direccion", direccion);
        map.put("fecha", fecha);
        map.put("inicio", inicio);
        map.put("fin", fin);
        map.put("id", key);

        mDatabase.child("usuarios").child(uid).child("eventos").child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(NuevoEventoActivity.this, "Nuevo evento registrado", Toast.LENGTH_SHORT).show();

                    mDatabase.child("usuarios").child(uid).child("eventos").child(key).child("id").setValue(key);

                    startActivity(new Intent(NuevoEventoActivity.this, MainActivity.class));
                    finish();
                }else
                    Toast.makeText(NuevoEventoActivity.this, "Error al crear evento", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Método para añadir el evento a todos los PC
     */
    private void añadirEventoPC() {

        String uid = mAuth.getCurrentUser().getUid();

        String key = mDatabase.child("usuarios").child(uid).child("eventos").push().getKey();

        Map<String, Object> map = new HashMap<>();
        map.put("titulo", titulo);
        map.put("direccion", direccion);
        map.put("fecha", fecha);
        map.put("inicio", inicio);
        map.put("fin", fin);
        map.put("id", key);

        mDatabase.child("usuarios").child(uid).child("eventos").child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    mDatabase.child("usuarios").child(uid).child("eventos").limitToLast(1).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot child : snapshot.getChildren()) {

                                    eventoID = child.getKey();

                                    for (int i = 0; i < listaPCIds.size(); i++)
                                        mDatabase.child("usuarios").child(listaPCIds.get(i)).child("eventos").child(eventoID).setValue(map);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(NuevoEventoActivity.this, "Error BD", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Toast.makeText(NuevoEventoActivity.this, "Nuevo evento registrado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NuevoEventoActivity.this, MainActivity.class));
                    finish();

                }else
                    Toast.makeText(NuevoEventoActivity.this, "Error al crear evento", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Método para obtener los UIds de los padres de confianza
     */
    private void obtenerPCids() {

        String Uid = mAuth.getCurrentUser().getUid();

        mDatabase.child("usuarios").child(Uid).child("padresConf").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        listaPCIds.add(child.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NuevoEventoActivity.this, "Error BD", Toast.LENGTH_SHORT).show();
            }
        });
    }
}