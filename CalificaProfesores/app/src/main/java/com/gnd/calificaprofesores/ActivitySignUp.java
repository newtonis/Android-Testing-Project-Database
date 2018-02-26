package com.gnd.calificaprofesores;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.Console;

public class ActivitySignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText usernameText,passwordText,emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        usernameText = findViewById(R.id.usernameText);

        Button SignUpButton = findViewById(R.id.SignUpButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(usernameText.getText().toString() , emailText.getText().toString(),passwordText.getText().toString());
            }
        });
    }
    protected void createUser(final String username, String email, String pass){
        Log.d("debug", "creating " + email + " " + pass + " " + username);

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(ActivitySignUp.this, "Can't create account",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            setUsername(username);
                        }
                    }
                });
    }
    protected void setUsername(String username){
        FirebaseUser user = mAuth.getCurrentUser();
        Toast.makeText(ActivitySignUp.this, "User created", Toast.LENGTH_SHORT).show();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder() .setDisplayName(username).build();
        user.updateProfile(profileUpdates);
    }
}
