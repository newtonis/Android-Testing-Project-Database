package com.gnd.calificaprofesores.NetworkSearchQueriesHandler;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Aca vamos a armar la estructura que se encarga de hacer queries para obtener universidades **/

public class SearchUniHandler {
    private DatabaseReference mDatabase;
    private GotUniListener listener;
    private Integer queued;
    private Set<UniData> uni;

    public SearchUniHandler(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        uni = new HashSet<>();
    }

    public void Search(String text){
        text.toLowerCase();

        queued = 2;
        mDatabase.child("Facultades")
                .orderByChild("CompeteName")
                .startAt(text)
                .endAt(text + "\uf8ff")
                .limitToFirst(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        OnePackageRecv();
                        if (AllRecv()) AllDataRecv();
                        AddPackage(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        mDatabase.child("Facultades")
                .orderByChild("Name")
                .startAt(text)
                .endAt(text + "\uf8ff")
                .limitToFirst(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        OnePackageRecv();
                        if (AllRecv()) AllDataRecv();
                        AddPackage(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
    public void AddPackage(@NonNull DataSnapshot dataSnapshot){
        for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
            uni.add(new UniData(
                    Long.parseLong(postSnapshot.getKey()),
                    (String)postSnapshot.child("Name").getValue(),
                    (String)postSnapshot.child("ShownName").getValue()
            ));
        }
    }
    public void OnePackageRecv(){
        queued --;
    }
    public boolean AllRecv(){
        return queued == 0;
    }
    public void AllDataRecv(){
        for (UniData data : uni){
            this.listener.onGotUni(data);
        }
    }
    public void AddOnGetUniListener(GotUniListener listener){
        this.listener = listener;
    }

}
