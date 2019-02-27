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
import java.util.TreeSet;

/** Aca vamos a armar la estructura que se encarga de hacer queries para obtener universidades **/

public class SearchUniHandler {
    private DatabaseReference mDatabase;
    private GotUniListener listener;
    private Integer queued;
    private Set<UniData> uni;

    public SearchUniHandler(){
        mDatabase = FirebaseDatabase.getInstance().getReference().getRef();
        uni = new TreeSet<>();
    }

    public void Search(String text){
        text.toLowerCase();
        uni.clear();

        queued = 2;
        mDatabase.child("Facultades")
                .orderByChild("CompleteName")
                .startAt(text)
                .endAt(text + "\uf8ff")
                .limitToFirst(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AddPackage(dataSnapshot);
                        OnePackageRecv();
                        if (AllRecv()) AllDataRecv();
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
                        AddPackage(dataSnapshot);
                        OnePackageRecv();
                        if (AllRecv()) AllDataRecv();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
    public void AddPackage(@NonNull DataSnapshot dataSnapshot){
        for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
            uni.add(new UniData(
                    postSnapshot.getKey(),
                    ((String)postSnapshot.child("Name").getValue()).toUpperCase(),
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
        this.listener.onGotUni(uni);
    }
    public void AddOnGetUniListener(GotUniListener listener){
        this.listener = listener;
    }
    public Set<UniData> GetUniList(){
        return uni;
    }
}
