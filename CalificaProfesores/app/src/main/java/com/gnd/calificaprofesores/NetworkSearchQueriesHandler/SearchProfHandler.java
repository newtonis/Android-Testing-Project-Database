package com.gnd.calificaprofesores.NetworkSearchQueriesHandler;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;
import java.util.TreeSet;

public class SearchProfHandler {
    private DatabaseReference mDatabase;
    private GotProfListener listener;
    private Set<ProfData> profs;

    public SearchProfHandler(){
        mDatabase = FirebaseDatabase.getInstance().getReference().getRef();
        profs = new TreeSet<>();
    }

    public void Search(String text){
        text = text.toLowerCase();
        profs.clear();

        mDatabase
                .child("Prof")
                .orderByChild("SearchName")
                .startAt(text)
                .endAt(text + "\uf8ff")
                .limitToFirst(10)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AddPackage(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    public void AddPackage(@NonNull DataSnapshot dataSnapshot) {
        for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
            String facultadesText = "";
            boolean begin = true;
            for (DataSnapshot fac : postSnapshot.child("Facultades").getChildren()){
                if (begin){
                    begin = false;
                }else{
                    facultadesText += ",";
                }
                facultadesText += (String)fac.getValue();

            }
            profs.add(new ProfData(
                    (String)postSnapshot.child("Name").getValue(),
                    postSnapshot.getKey(),
                    facultadesText
            ));
        }
        listener.onGotProf(profs);
    }
    public void AddOnGotProfListener(GotProfListener listener) {
        this.listener = listener;
    }
}
