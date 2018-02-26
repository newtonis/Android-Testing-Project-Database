package com.gnd.calificaprofesores;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.gnd.calificaprofesores.NetworkHandler.ProcessVote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import java.io.Console;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private FirebaseAuth mAuth;

    @Test
    public void makeVote() throws Exception {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("newtonis.penguin@gmail.com","lala").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    new ProcessVote(3l,3l,3l,1l,1l);
                }else{
                    System.out.print("Error: coudln't log in");
                }
            }
        });
    }
}