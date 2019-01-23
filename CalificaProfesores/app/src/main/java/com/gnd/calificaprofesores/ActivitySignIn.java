
/* En esta actividad logeamos usando el sistema particular de de la aplicacion, NO con google,
facebook, etc */


package com.gnd.calificaprofesores;


import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivitySignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText emailInput;
    TextInputEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailInput = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);

        Button signInButton = findViewById(R.id.signInButton);

        mAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(emailInput.getText().toString(),password.getText().toString()).addOnCompleteListener(ActivitySignIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(ActivitySignIn.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ActivitySignIn.this, ActivityUser.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ActivitySignIn.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
