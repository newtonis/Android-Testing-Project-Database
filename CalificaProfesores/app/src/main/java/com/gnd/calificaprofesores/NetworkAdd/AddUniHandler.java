package com.gnd.calificaprofesores.NetworkAdd;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUniHandler {
    DatabaseReference db;
    UniAddedListener uniAddedListener;
    public AddUniHandler(){
        db = FirebaseDatabase.getInstance().getReference();
        uniAddedListener = null;
    }

    public UniAddedListener getUniAddedListener() {
        return uniAddedListener;
    }

    public void setUniAddedListener(UniAddedListener uniAddedListener) {
        this.uniAddedListener = uniAddedListener;
    }

    public void AddUni(CompleteUniData uniData){
        db
                .child("UniAddRequests")
                .child(FirebaseAuth.getInstance().getUid())
                .push().setValue(uniData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (uniAddedListener != null){
                    uniAddedListener.OnUniAdded();
                }
            }
        });
    }
}
