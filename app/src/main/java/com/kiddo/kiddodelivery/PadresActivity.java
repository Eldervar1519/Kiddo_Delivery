package com.kiddo.kiddodelivery;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class PadresActivity extends AppCompatActivity {

    /*
    Declaraciones
     */
    private Button AñadirPadres, Añadir, Cancelar;
    private EditText Mail;
    private String Umail, NombrePC, HijoPC, muid;
    private ImageButton Llamar, Eliminar;

    int imageNiño = R.drawable.ic_baseline_child_care_24;
    int imageBtnLlamar = R.drawable.ic_baseline_phone_forwarded_24;
    int imageBtnEliminar = R.drawable.ic_baseline_highlight_off_24;

    ArrayList<PadresDeConfianzaModel> listaPCModels = new ArrayList<>();
    ArrayList<String> listaPCIds = new ArrayList<>();
    ArrayList<String> listaPCNombreApellido = new ArrayList<>();
    ArrayList<String> listaPCHijos = new ArrayList<>();
    ArrayList<String> listaPCTlf = new ArrayList<String>();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final String URL = "https://kiddodelivery-7e28a-default-rtdb.europe-west1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padres);

        Usuario.listaUsuarios.clear(); //comprobar

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
        Inicializamos variables
         */
        AñadirPadres = findViewById(R.id.buttonAñadirPadres);
        Añadir = findViewById(R.id.buttonAñadir);
        Cancelar = findViewById(R.id.buttonCancelar);
        Mail = findViewById(R.id.editTextMailPC);
        RecyclerView RV = findViewById(R.id.recyclerViewPC);
        Llamar = findViewById(R.id.imageButtonCVLlamar);
        Eliminar = findViewById(R.id.imageButtonCVEliminar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();
        muid = mAuth.getCurrentUser().getUid();

        /*
        Creamos modelo RV
         */
        obtenerPCids();

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

        /*
        Para el RecyclerView...
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PC_RecyclerViewAdapter adapter = new PC_RecyclerViewAdapter(PadresActivity.this, listaPCModels);
                RV.setAdapter(adapter);
                RV.setLayoutManager(new LinearLayoutManager(PadresActivity.this));
            }
        }, 1000);
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

                Mail.setText("");

                Añadir.setVisibility(View.GONE);
                Cancelar.setVisibility(View.GONE);
                Mail.setVisibility(View.GONE);
                AñadirPadres.setVisibility(View.VISIBLE);

                Usuario.listaUsuarios.clear();

                triggerRebirth(PadresActivity.this, PadresActivity.class);

            }
        } else
            Toast.makeText(this, "Introduce un mail válido", Toast.LENGTH_SHORT).show();
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
    private void crearPCModels() {

        for (int i = 0; i < listaPCIds.size(); i++) {
            obtenerPCNombreApellido(listaPCIds.get(i));
            obtenerHijo(listaPCIds.get(i));
            obtenerTlf(listaPCIds.get(i));
        }
    }

    /*
    Método para obtener tlf
     */
    private void obtenerTlf(String UId) {

        mDatabase.child("usuarios").child(UId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String tlf = snapshot.child("tlf").getValue().toString();
                    listaPCTlf.add(tlf);
                    listaPCModels.add(new PadresDeConfianzaModel(NombrePC, HijoPC, tlf, UId, muid,
                            imageNiño, imageBtnLlamar, imageBtnEliminar));
                    listaPCTlf.clear();
                    listaPCHijos.clear();
                    listaPCNombreApellido.clear();
                    listaPCIds.clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PadresActivity.this, "Error BD", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Método para obtener hijos
     */
    private void obtenerHijo(String UId) {

        mDatabase.child("usuarios").child(UId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String hijo = snapshot.child("hijos").getValue().toString();
                    listaPCHijos.add(hijo);
                    HijoPC = "Padre de " + hijo;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PadresActivity.this, "Error BD", Toast.LENGTH_SHORT).show();
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

                    crearPCModels();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PadresActivity.this, "Error BD", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Método para obtener nombre y apellidos de un determinado usuario
     */
    private void obtenerPCNombreApellido(String UId) {

        mDatabase.child("usuarios").child(UId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String apellido = snapshot.child("apellidos").getValue().toString();
                    listaPCNombreApellido.add(nombre + " " + apellido);
                    NombrePC = nombre + " " + apellido;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PadresActivity.this, "Error BD", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Método para reiniar app
     */
    public static void triggerRebirth(Context context, Class<PadresActivity> nextIntent) {

        Intent intent = new Intent(context, PadresActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        if (context instanceof Activity)
            ((Activity) context).finish();

        Runtime.getRuntime().exit(0);
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