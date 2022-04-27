package com.kiddo.kiddodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    /*
    Declaraciones
     */
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText Nombre, Apellidos, DNI, Tlf, Calle, Poblacion, Mail, Password, Password2;
    private String nombre = "", apellidos = "", dni = "", calle = "", poblacion = "", tlf = "", mail = "", password = "", password2 = "";
    private Button Registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        /*
        Instanciamos variables
         */
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        Nombre = findViewById(R.id.editTextEmailLogin);
        Apellidos = findViewById(R.id.editTextApellidos);
        DNI = findViewById(R.id.editTextDNI);
        Tlf = findViewById(R.id.editTextTlf);
        Calle = findViewById(R.id.editTextCalleNumero);
        Poblacion = findViewById(R.id.editTextPoblacion);
        Mail = findViewById(R.id.editTextMail);
        Password = findViewById(R.id.editTextPasswordLogin);
        Password2 = findViewById(R.id.editTextPassword2);
        Registro = findViewById(R.id.buttonRegistrarsePaso2);

        /*
        Funcionalidad botón Registrarse
         */
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = Nombre.getText().toString();
                apellidos = Apellidos.getText().toString();
                dni = DNI.getText().toString();
                calle = Calle.getText().toString();
                poblacion = Poblacion.getText().toString();
                tlf = Tlf.getText().toString();
                mail = Mail.getText().toString();
                password = Password.getText().toString();
                password2 = Password2.getText().toString();

                /*
                Validaciones de los campos
                 */
                if (!nombre.isEmpty() && !apellidos.isEmpty() && !dni.isEmpty() && !calle.isEmpty()
                        && !poblacion.isEmpty() && !tlf.isEmpty() && !mail.isEmpty() && !password.isEmpty()
                        && !password2.isEmpty()) {

                    if (password.length() >= 6 && password2.length() >= 6 && password.equals(password2)) {

                        registrarUsuario();

                    } else {
                        Toast.makeText(RegistroActivity.this, "La contraseña tiene menos de 6 caracteres o no coinciden", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegistroActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    /*
    Método para registrar nuevo usuario
     */
    public void registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Map<String, Object> map = new HashMap<>();
                            map.put("nombre", nombre);
                            map.put("apellidos", apellidos);
                            map.put("dni", dni);
                            map.put("calle", calle);
                            map.put("poblacion", poblacion);
                            map.put("tlf", tlf);
                            map.put("mail", mail);
                            map.put("password", password);

                            String id = mAuth.getCurrentUser().getUid();
                            mDatabase.child("usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if (task2.isSuccessful()){
                                        startActivity(new Intent(RegistroActivity.this, ProfileActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(RegistroActivity.this, "No se pudo crear el usuario correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                            Toast.makeText(RegistroActivity.this, "Nuevo usuario registrado",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
/*
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
*/
                        } else {
                            Toast.makeText(RegistroActivity.this, "Autenticación fallida",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}