package com.kiddo.kiddodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistroActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText Nombre, Apellidos, DNI, Tlf, Calle, Poblacion, Mail, Password, Password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        Nombre = findViewById(R.id.editTextEmailLogin);
        Apellidos = findViewById(R.id.editTextApellidos);
        DNI = findViewById(R.id.editTextDNI);
        Tlf = findViewById(R.id.editTextTlf);
        Calle = findViewById(R.id.editTextCalleNumero);
        Poblacion = findViewById(R.id.editTextPoblacion);
        Mail = findViewById(R.id.editTextMail);
        Password = findViewById(R.id.editTextPasswordLogin);
        Password2 = findViewById(R.id.editTextPassword2);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void registrarUsuario(View view){

        if (Password.getText().toString().equals(Password2.getText().toString())){

            mAuth.createUserWithEmailAndPassword(Mail.getText().toString(), Password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithCustomToken:success");
                                Toast.makeText(RegistroActivity.this, "Nuevo usuario registrado",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                Toast.makeText(RegistroActivity.this, "Autenticación fallida",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }


    }
}