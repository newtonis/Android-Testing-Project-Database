package com.gnd.calificaprofesores;

/* Aqui logeamos con google, facebook, o accedemos al menu para logear con el sistema
manual de la aplicación */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.data.model.User;
import com.gnd.calificaprofesores.NetworkHandler.GotUserExtraDataListener;
import com.gnd.calificaprofesores.NetworkHandler.GotUserRightsListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManagerInstance;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraData;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraDataInstance;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class ActivityLogin extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;

    private static GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginLogs";
    private CallbackManager callbackManager;

    public static GoogleApiClient mGoogleApiClient;


    private static final int REQ_CODE = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setSupportActionBar(toolbar);

        Button signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivitySignIn.class);
                startActivity(intent);
            }
        });

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ActivityLogin.this,ActivitySignUp.class);
                startActivity(intent);
            }
        });

        Button GuestButton = findViewById(R.id.GuestButton);
        GuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this,ActivityUser.class);
                startActivity(intent);
            }
        });

        /** Objetos de login de google **/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        /** Fin objetos de login de google **/


        SignInButton googleSignInButton = findViewById(R.id.sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);

        /// para checkear si ya estuvo logeado
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            Intent intent = new Intent(ActivityLogin.this, ActivityUser.class);
            startActivity(intent);
        }

        googleSignInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                googleSignIn();
            }
        });

        callbackManager = CallbackManager.Factory.create();

        /*LoginButton loginButton = findViewById(R.id.facebook_sign_in_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });*/

    }
    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQ_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT);

                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            loginSucessful();



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    public void loginSucessful(){
        /*UserDataManager dataManager = UserDataManagerInstance.getInstance();

        dataManager.setGotUserRightsListener(new GotUserRightsListener() {
            @Override
            public void onGotUserRights(boolean admin) {
                UserExtraDataInstance.getInstance().setAdmin(admin);
            }
        });

        dataManager.setmGotUserExtraDataListener(new GotUserExtraDataListener() {
            @Override
            public void gotExtraData(UserExtraData extraData) {
                UserExtraDataInstance.getInstance().setUniName(extraData.getUniName());
                UserExtraDataInstance.getInstance().setShowName(extraData.getShowName());
                UserExtraDataInstance.getInstance().setUniId(extraData.getUniId());
            }
        });
        dataManager.listenForUserRights();
        dataManager.listenForUserProfileData();*/

        Intent intent = new Intent(ActivityLogin.this, ActivityUser.class);
        startActivity(intent);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Intent intent = new Intent(ActivityLogin.this, ActivityUser.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(ActivityLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


}
