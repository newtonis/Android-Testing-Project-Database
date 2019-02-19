package com.gnd.calificaprofesores.NetworkNewsHandler;

import android.support.annotation.NonNull;

import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NewsItemData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NewsItemViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

public class NetworkNewsHandler {
    private DatabaseReference mDatabase;
    private GotNewsListener gotNewsListener;

    public NetworkNewsHandler(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void ListenForNews(){
        mDatabase
                .child("Novedades")
                .orderByChild("timestamp")
                .limitToFirst(10)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ProcessNews(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
    public void ProcessNews(DataSnapshot snapshot){
        List<NewsItemData> news = new ArrayList<>();
        for (DataSnapshot postsnaphot : snapshot.getChildren()){
            news.add(new NewsItemData(
                    (String) postsnaphot.child("title").getValue(),
                    (String) postsnaphot.child("content").getValue(),
                    (String)postsnaphot.child("author").getValue(),
                    (Long) postsnaphot.child("timestamp").getValue()
            ));
        }
        gotNewsListener.onGotNews(news);
    }

    public GotNewsListener getGotNewsListener() {
        return gotNewsListener;
    }

    public void setGotNewsListener(GotNewsListener gotNewsListener) {
        this.gotNewsListener = gotNewsListener;
    }
}
