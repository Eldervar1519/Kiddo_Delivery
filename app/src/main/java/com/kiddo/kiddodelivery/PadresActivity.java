package com.kiddo.kiddodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PadresActivity extends AppCompatActivity {

    /*
    Declaraciones
     */
    private Button AñadirPadres, Añadir, Cancelar;
    private EditText Mail;
    private String Umail;
    private ArrayList<PadresDeConfianzaModel> listaPCModels = new ArrayList<>();
    private ArrayList<String> listaPCids = new ArrayList<>();


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final String URL = "https://kiddodelivery-7e28a-default-rtdb.europe-west1.firebasedatabase.app/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padres);

        /*
        Inicializamos variables
         */
        AñadirPadres = findViewById(R.id.buttonAñadirPadres);
        Añadir = findViewById(R.id.buttonAñadir);
        Cancelar = findViewById(R.id.buttonCancelar);
        Mail = findViewById(R.id.editTextMailPC);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        /*
        Funcionalidad de los botones
         */
        AñadirPadres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Añadir.setVisibility(View.VISIBLE);
                Cancelar.setVisibility(View.VISIBLE);
                Mail.setVisibility(View.VISIBLE);
                AñadirPadres.setVisibility(View.GONE);
            }
        });

        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PadresActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        Añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                añadirPC();
            }
        });
    }

    /*
    Método para añadir padres de confianza
     */
    public void añadirPC() {

        String mail = Mail.getText().toString();
        String PCid;

        if (mail.contains("@") && mail.contains(".")) {

            Query query = FirebaseDatabase.getInstance(URL).getReference("usuarios")
                    .orderByChild("mail")
                    .equalTo(mail);

            query.addListenerForSingleValueEvent(VEL);
            String Uid = mAuth.getCurrentUser().getUid();
            getUserMail(Uid);

            for (int x = 0; x < Usuario.listaUsuarios.size(); x++) {
                Usuario usuario = Usuario.listaUsuarios.get(x);
                PCid = usuario.getId();

                mDatabase.child("usuarios").child(Uid).child("padresConf").child(PCid).setValue(mail);
                mDatabase.child("usuarios").child(PCid).child("padresConf").child(Uid).setValue(Umail);

                Toast.makeText(this, "Padres añadidos!", Toast.LENGTH_SHORT).show();

                Añadir.setVisibility(View.GONE);
                Cancelar.setVisibility(View.GONE);
                Mail.setVisibility(View.GONE);
                AñadirPadres.setVisibility(View.VISIBLE);
            }
        }
    }

    /*
    Método para obtener el mail del usuario logeado
     */
    private void getUserMail(String Uid) {

        mDatabase.child("usuarios").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Umail = snapshot.child("mail").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PadresActivity.this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Método para obtener el usuario que se quiere añadir a PC
     */
    ValueEventListener VEL = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot DSnapshot : snapshot.getChildren()) {
                    Usuario usuario = DSnapshot.getValue(Usuario.class);
                    Usuario.listaUsuarios.add(usuario);
                }
            } else
                Toast.makeText(PadresActivity.this, "No se encontraron coincidencias", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(PadresActivity.this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
        }
    };

    /*
    Método para diseñar los cardViews del RecyclerView de padres de confianza
     */
    private void construirPCModels() {


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

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PadresActivity.this, "Error BD", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

/*
DB QUERIES

1. SELECT * FROM USUARIOS
mDatabase = FirebaseDatabase.getInstance().getReference("usuarios");
mDatabase.addListenerForSingleEventValue(valueEventListener); -> montar valueEventListener y pasarlo

2. SELECT * FROM USUARIOS WHERE DNI = "..."
Query query = FirebaseDatabase.getInstance().getReference("usuarios")
    .orderByChild("dni")
    .equalTo("...");
query.addListenerForSingleEventValue(valueEventListener); -> montar valueEventListener y pasarlo

3. SELECT * FROM USUARIOS WHERE POBLACION = "..."
Query query = FirebaseDatabase.getInstance().getReference("usuarios")
    .orderByChild("poblacion")
    .equalTo("...");
query.addListenerForSingleEventValue(valueEventListener); -> montar valueEventListener y pasarlo

4. SELECT * FROM USUARIOS LIMIT 2
Query query = FirebaseDatabase.getInstance().getReference("usuarios").limitToFirst(2);
query.addListenerForSingleEventValue(valueEventListener); -> montar valueEventListener y pasarlo

5. SELECT * FROM USUARIOS WHERE EDAD < 30
Query query = FirebaseDatabase.getInstance().getReference("usuarios")
    .orderByChild("edad")
    .endAt(29);
query.addListenerForSingleEventValue(valueEventListener); -> montar valueEventListener y pasarlo

6. SELECT * FROM USUARIOS WHERE NOMBRE = "A$"
Query query = FirebaseDatabase.getInstance().getReference("usuarios")
    .orderByChild("nombre")
    .startAt("A")
    endAt("A\uf8ff");
query.addListenerForSingleEventValue(valueEventListener); -> montar valueEventListener y pasarlo

 */