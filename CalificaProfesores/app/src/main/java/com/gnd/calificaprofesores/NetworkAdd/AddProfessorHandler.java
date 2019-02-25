package com.gnd.calificaprofesores.NetworkAdd;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProfessorHandler {
    DatabaseReference db;
    ProfessorAddedListener listener;
    public AddProfessorHandler(){
        db = FirebaseDatabase.getInstance().getReference();
    }

    public ProfessorAddedListener getListener() {
        return listener;
    }

    public void setListener(ProfessorAddedListener listener) {
        this.listener = listener;
    }

    public void addProfessor(CompleteProfData profData){
        db
                .child("ProfAddRequests")
                .child(FirebaseAuth.getInstance().getUid())
                .push().setValue(profData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onProfessorAdded();
            }
        });
    }
}
