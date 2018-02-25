package com.gnd.calificaprofesores.NetworkHandler;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by newtonis on 24/02/18.
 */



public class ProcessVote {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public ProcessVote(final Long CA,final Long CE,final Long A, final Long profId, final Long matId){
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null || mAuth.getCurrentUser().isAnonymous()){
            Log.d("Info","[ProcessVote] Process vote called! But user is or anonymous or not logged \n");
            return ; /// nothing to do user
        }
        /// send https request
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    final String token = task.getResult().getToken();
                    new Thread() {
                        public void run() {
                            try {
                                URL url = new URL("https://us-central1-calificaprofesores-8250c.cloudfunctions.net/SendVote");
                                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                                connection.setRequestMethod("POST");
                                connection.setRequestProperty("authorization", token);
                                connection.setRequestProperty("CA", Long.toString(CA));
                                connection.setRequestProperty("CE", Long.toString(CE));
                                connection.setRequestProperty("A", Long.toString(A));
                                connection.setRequestProperty("ProfId", Long.toString(profId));
                                connection.setRequestProperty("MatId", Long.toString(matId));

                                InputStream in = connection.getInputStream();
                                IOUtils.copy(in, System.out);
                            } catch (MalformedURLException e) {

                            } catch (IOException e) {

                            }
                        }
                    }.start();
                } else {
                    // Handle error -> task.getException();
                }
            }
        });


    }

}
