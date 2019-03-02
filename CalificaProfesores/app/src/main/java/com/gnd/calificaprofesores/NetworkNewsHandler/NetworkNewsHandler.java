package com.gnd.calificaprofesores.NetworkNewsHandler;

import android.support.annotation.NonNull;

import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NewsItemData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NewsItemViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import retrofit2.http.POST;

public class NetworkNewsHandler {
    private DatabaseReference mDatabase;
    private GotNewsListener gotNewsListener = null;
    private SentNewsListener sentNewsListener = null;
    public NetworkNewsHandler(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void ListenForNews(){
        mDatabase
                .child("Novedades")
                .orderByChild("timestamp")
                .limitToLast(10)
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
        Stack<NewsItemData> queue = new Stack<>();
        for (DataSnapshot postsnaphot : snapshot.getChildren()){
            queue.add(new NewsItemData(
                    (String) postsnaphot.child("title").getValue(),
                    (String) postsnaphot.child("content").getValue(),
                    (String)postsnaphot.child("author").getValue(),
                    (Long) postsnaphot.child("timestamp").getValue()
            ));
        }
        while (!queue.isEmpty()){
            news.add(queue.pop());
        }
        gotNewsListener.onGotNews(news);
    }

    public void sendNews(NewsItemData novedad){
        mDatabase
                .child("Novedades")
                .push()
                .setValue(
                        novedad
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (sentNewsListener != null){
                    sentNewsListener.onSentNews();
                }
            }
        });
    }

    public GotNewsListener getGotNewsListener() {
        return gotNewsListener;
    }

    public void setGotNewsListener(GotNewsListener gotNewsListener) {
        this.gotNewsListener = gotNewsListener;
    }

    public SentNewsListener getSentNewsListener() {
        return sentNewsListener;
    }

    public void setSentNewsListener(SentNewsListener sentNewsListener) {
        this.sentNewsListener = sentNewsListener;
    }
}
