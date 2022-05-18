package com.kiddo.kiddodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button SignOut;
    private TextView Bienvenida;
    private final String URL = "https://kiddodelivery-7e28a-default-rtdb.europe-west1.firebasedatabase.app";
    private Button Padres;
    private String UId;
    private Button Hijos;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        SignOut = findViewById(R.id.buttonSignOut);
        Bienvenida = findViewById(R.id.textViewBienvenida);
        Padres = findViewById(R.id.buttonPadres);
        Hijos = findViewById(R.id.itemHijos);

        UId = mAuth.getCurrentUser().getUid();

        /*
        Método para ir a PadresDeConfianzaActivity
         */
        Padres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PadresActivity.class);
                startActivity(i);
            }
        });

        /*
        Método para cerrar sesión
         */
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(MainActivity.this, "Se ha cerrado la sesión",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        getUserName(UId);
    }

    /*
    Método para obtener el nombre de usuario de la BD y mostrar el mensaje de bienvenida
     */
    private void getUserName(String UId) {

        mDatabase.child("usuarios").child(UId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String bienvenida = "Bienvenido " + nombre;
                    Bienvenida.setText(bienvenida);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error BD", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*
    Método para mostrar/ocultar menú overflow
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    /*
    Método para asignar las funcionalidades de las distintas opciones del menú overflow
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.itemNotificaciones)
            //Llamar método notificaciones
            Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show();
        else if (id == R.id.itemHijos)
            //SignOut();
            Toast.makeText(this, "Cerrar sesión", Toast.LENGTH_SHORT).show();
        else if (id == R.id.itemCuenta)
            //LLamar método darse de baja
            Toast.makeText(this, "Darse de baja", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

}