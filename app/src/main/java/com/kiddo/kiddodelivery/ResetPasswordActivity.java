package com.kiddo.kiddodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText Mail;
    private Button ResetPassword;
    private String mail = "";

    private FirebaseAuth mAuth;
    private ProgressDialog Dialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

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

        mAuth = FirebaseAuth.getInstance();
        Dialogo = new ProgressDialog(this);

        ResetPassword = findViewById(R.id.buttonResetPasswordActivity);
        Mail = findViewById(R.id.editTextMailReset);

        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mail = Mail.getText().toString();

                if (!mail.isEmpty()) {
                    Dialogo.setMessage("Espere un momento...");
                    Dialogo.setCanceledOnTouchOutside(false);
                    Dialogo.show();
                    resetPassword();
                    Intent i = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(ResetPasswordActivity.this, "Introduzca el email asociado a su cuenta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword(){
        
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                    Toast.makeText(ResetPasswordActivity.this, "Se ha enviado un correo para reestablecer la contraseña", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ResetPasswordActivity.this, "Error al enviar el correo", Toast.LENGTH_SHORT).show();

                Dialogo.dismiss();
            }
        });

    }
}