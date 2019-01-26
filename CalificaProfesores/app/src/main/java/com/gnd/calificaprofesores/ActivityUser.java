package com.gnd.calificaprofesores;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ActivityUser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);



        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        /*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();*/

        TextView welcomeText = findViewById(R.id.welcomeText);

        String username;
        if (!user.isAnonymous()){
            username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        }else{
            username = "invitado";
        }

        welcomeText.setText("Bienvenido, "+username);

        Button searchProfesorButton = findViewById(R.id.SerachProfessor);

        searchProfesorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityUser.this, ActivityProfessorList.class);
                startActivity(intent);
            }
        });

        Button searchClassButton = findViewById(R.id.SearchClass);

        searchClassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ActivityUser.this, ActivitySearchCourse.class);
                startActivity(intent);
            }
        });

        /*** Logout function ***/
        Button exitButton = findViewById(R.id.ButtonExit);

        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mAuth.getInstance().signOut();
                Intent intent = new Intent(ActivityUser.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
        /*** end logout function ***/

    }
}
