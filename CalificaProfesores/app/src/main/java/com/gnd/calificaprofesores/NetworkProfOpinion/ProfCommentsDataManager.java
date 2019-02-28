package com.gnd.calificaprofesores.NetworkProfOpinion;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SelectableItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProfCommentsDataManager {
    private DatabaseReference mDatabase;
    private String ProfId;
    private GotProfCommentListener gotProfCommentListener;
    private SentProfCommentListener sentCommentListener;
    private GotProfMatListener gotProfMatListener;
    private GotProfQualListener gotProfQualListener;

    public ProfCommentsDataManager(String _ProfId){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ProfId = _ProfId;
    }
    public void ListenForComments(){
        // listen for recent comments

        mDatabase
                .child("OpinionesProf")
                .child(ProfId)
                .orderByChild("timestamp")
                .limitToFirst(10)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<UserProfComment> comments = new ArrayList<>();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Map<String,String> materias = new TreeMap<>();

                            for (DataSnapshot child : postSnapshot.child("materias").getChildren()){
                                materias.put(child.getKey(), (String)child.getValue());
                            }

                            comments.add(new UserProfComment(
                                    (String)postSnapshot.child("author").getValue(),
                                    (String)postSnapshot.child("content").getValue(),
                                    materias,
                                    new TreeMap<String, String>(),
                                    (Long)postSnapshot.child("amabilidad").getValue(),
                                    (Long)postSnapshot.child("conocimiento").getValue(),
                                    (Long)postSnapshot.child("clases").getValue(),
                                    (boolean)postSnapshot.child("anonimo").getValue(),
                                    (boolean)postSnapshot.child("conTexto").getValue()
                            ));


                        }
                        gotProfCommentListener.onGotProfCommentsListener(comments);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
    public ProfCommentsDataManager SendComment(UserProfComment comment){
        String uid = FirebaseAuth.getInstance().getUid();

        mDatabase
                .child("OpinionesProf")
                .child(ProfId)
                .child(uid)
                .setValue(comment)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        sentCommentListener.onSentProfComment();
                    }
                });
        return this;
    }
    public void RequestProfMat(){
        mDatabase
                .child("Prof")
                .child(ProfId)
                .child("Mat")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<SelectableItem> materias = new ArrayList<>();

                                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                                    materias.add(new SelectableItem(
                                            postsnapshot.getKey(),
                                            (String)postsnapshot.child("nombre").getValue(),
                                            (String)postsnapshot.child("facultad").getValue()
                                    ));
                                }
                                gotProfMatListener.onGotProfMatListener(materias);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }
                );

    }
    public void RequestProfQual(){
        mDatabase
                .child("Prof")
                .child(ProfId)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (
                                        dataSnapshot.hasChild("conocimiento") &&
                                        dataSnapshot.hasChild("clases") &&
                                        dataSnapshot.hasChild("amabilidad") &&
                                        dataSnapshot.hasChild("count")){

                                    long count_long = (long)dataSnapshot.child("count").getValue();
                                    long conocimiento = (long)dataSnapshot.child("conocimiento").getValue();
                                    long clases = (long)dataSnapshot.child("clases").getValue();
                                    long amabilidad = (long)dataSnapshot.child("amabilidad").getValue();

                                    float count = (float)count_long;

                                    if (count != 0) {
                                        gotProfQualListener.onGotProfQualListener(
                                                conocimiento / count,
                                                clases/ count,
                                                amabilidad / count
                                        );
                                    }else{
                                        gotProfQualListener.onGotProfQualListener(
                                                0f,
                                                0f,
                                                0f
                                        );
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }
                );
    }
    public void AddOnGotCommentListener(GotProfCommentListener listener){
        this.gotProfCommentListener = listener;
    }
    public void AddOnGotProfMatListener(GotProfMatListener listener){
        this.gotProfMatListener = listener;
    }

    public void AddOnSentCommentListener(SentProfCommentListener listener){
        this.sentCommentListener = listener;
    }

    public void AddOnGotQualListener(GotProfQualListener listener){
        this.gotProfQualListener = listener;
    }
}
