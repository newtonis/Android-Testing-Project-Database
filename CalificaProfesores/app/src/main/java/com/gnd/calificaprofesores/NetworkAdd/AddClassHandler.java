package com.gnd.calificaprofesores.NetworkAdd;

import android.provider.ContactsContract;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddClassHandler {
    DatabaseReference db;
    ClassAddedListener classAddedListener;

    public AddClassHandler(){
        db = FirebaseDatabase.getInstance().getReference();
        classAddedListener = null;
    }

    public DatabaseReference getDb() {
        return db;
    }

    public ClassAddedListener getClassAddedListener() {
        return classAddedListener;
    }

    public void setClassAddedListener(ClassAddedListener classAddedListener) {
        this.classAddedListener = classAddedListener;
    }

    public void setDb(DatabaseReference db) {
        this.db = db;
    }
    public void addClass(CompleteClassData classData){
        db
                .child("ClassAddRequests")
                .child(FirebaseAuth.getInstance().getUid())
                .push().setValue(classData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (classAddedListener != null) {
                            classAddedListener.onClassAdded();
                        }
                    }
                });
    }
}
