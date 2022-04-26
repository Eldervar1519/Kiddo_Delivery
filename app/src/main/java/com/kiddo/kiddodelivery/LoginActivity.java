package com.kiddo.kiddodelivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    /*
    Declaraciones
     */
    private FirebaseAuth mAuth;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;

    private EditText Email;
    private EditText Password;
    private ImageButton Google;
    private ImageButton Twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        Relacionamos variables con elementos de la IU
         */
        Email = findViewById(R.id.editTextEmailLogin);
        Password = findViewById(R.id.editTextPasswordLogin);
        Google = findViewById(R.id.imageButtonGoogle);
        Twitter = findViewById(R.id.imageButtonTwitter);

        /*
        Inicializamos Firebase
         */
        mAuth = FirebaseAuth.getInstance();

        /*
        Inicializamos gso y gsc para inicio de sesión con Google
         */
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        gsc = GoogleSignIn.getClient(this, gso);

        /*
        Enlazamos botones con sus funcionalidades
         */
        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesionGoogle();
            }
        });

        Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, TwitterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });



    }

    /*
    Métodos para iniciar sesión con Google
     */
    private void iniciarSesionGoogle() {
        Intent i = gsc.getSignInIntent();
        startActivityForResult(i, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            HomeActivity();
            try {
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    Método para ir a página principal
     */
    private void HomeActivity() {
        finish();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    /*
    Método de inicio de sesión con mail y contraseña
     */
    public void iniciarSesion (View view){
        mAuth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCustomToken:success");
                            Toast.makeText(LoginActivity.this, "Autenticación realizada con éxito",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Autenticación fallida",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    /*
    Método para pasar a pantalla de registro
     */
    public void irRegistrarse(View view){
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }
}