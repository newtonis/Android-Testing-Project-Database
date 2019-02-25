package com.gnd.calificaprofesores.NetworkAdd;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProfessorHandler {
    DatabaseReference db;
    ProfessorAddedListener professorAddedListener;
    public AddProfessorHandler(){
        db = FirebaseDatabase.getInstance().getReference();
        professorAddedListener = null;
    }

    public ProfessorAddedListener getProfessorAddedListener() {
        return professorAddedListener;
    }

    public void setProfessorAddedListener(ProfessorAddedListener professorAddedListener) {
        this.professorAddedListener = professorAddedListener;
    }

    public void addProfessor(CompleteProfData profData){
        db
                .child("ProfAddRequests")
                .child(FirebaseAuth.getInstance().getUid())
                .push().setValue(profData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (professorAddedListener != null) {
                    professorAddedListener.onProfessorAdded();
                }
            }
        });
    }
}
