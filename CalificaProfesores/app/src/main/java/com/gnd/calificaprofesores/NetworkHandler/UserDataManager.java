package com.gnd.calificaprofesores.NetworkHandler;

import android.support.annotation.NonNull;

import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Aqui administramos la obtencion de la inforamcion de un usuario **/



public class UserDataManager {
    private GotUserCommentListener mGotUserCommentListener;
    private GotUserExtraDataListener mGotUserExtraDataListener;
    private GotUserProfCommentListener mGotUserProfCommentListener;
    private SentUniDataListener sentUniDataListener;

    private String uid;

    FirebaseUser currentFirebaseUser;
    private DatabaseReference mDatabase;
    private int count;

    public UserDataManager(){
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference( ).getRef();
        uid = FirebaseAuth.getInstance().getUid();
    }

    public void setUni(String uniName, String uniId){
        count = 0;

        mDatabase
                .child("UsersExtraData/"+uid+"/UniName")
                .setValue(uniName).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                count ++;
                if (count == 2){
                    sentUniDataListener.onSentUni();
                }
            }
        });

        mDatabase
                .child("UsersExtraData/"+uid+"/UniId")
                .setValue(uniId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                count ++;
                if (count == 2){
                    sentUniDataListener.onSentUni();
                }
            }
        });
    }

    public void setShownName(String shownName){
        mDatabase
                .child("UsersExtraData/"+uid+"/ShownName")
                .setValue(shownName);
    }

    public void AddGotUserCommentListener(String CourseId, final GotUserCommentListener listener){
        this.mGotUserCommentListener = listener;

        mDatabase.child("OpinionesMaterias/"+CourseId+"/"+currentFirebaseUser.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()){
                            listener.GotUserComment(false, null);
                        }else{
                            CourseComment comment = new CourseComment(
                                    currentFirebaseUser.getDisplayName(),
                                    (String)dataSnapshot.child("content").getValue(),
                                    (Long)dataSnapshot.child("valoracion").getValue(),
                                    (Long)dataSnapshot.child("likes").getValue()
                            );
                            comment.setConTexto((boolean)dataSnapshot.child("conTexto").getValue());
                            comment.setAnonimo((boolean)dataSnapshot.child("anonimo").getValue());

                            listener.GotUserComment(true, comment);

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    public void AddGotUserProfCommentsListener(final GotUserProfCommentListener listener){
        this.mGotUserProfCommentListener = listener;
    }

    public void ListenForUserProfComment(String ProfId){
        String uid = FirebaseAuth.getInstance().getUid();
        mDatabase.child("OpinionesProf")
                .child(ProfId)
                .child(uid)
                .addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<UserProfComment> comments = new ArrayList<>();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            Map<String ,String> materias = new TreeMap<>();

                            for (DataSnapshot child : dataSnapshot.child("materias").getChildren()){
                                materias.put(child.getKey(), (String)child.getValue());
                            }
                            UserProfComment comment = new UserProfComment(
                                    (String) dataSnapshot.child("author").getValue(),
                                    (String) dataSnapshot.child("content").getValue(),
                                    materias,
                                    new TreeMap<String, String>(),
                                    (Long) dataSnapshot.child("amabilidad").getValue(),
                                    (Long) dataSnapshot.child("conocimiento").getValue(),
                                    (Long) dataSnapshot.child("clases").getValue(),
                                    (boolean) dataSnapshot.child("anonimo").getValue(),
                                    (boolean) dataSnapshot.child("conTexto").getValue()
                            );
                            comment.setTimestamp_long((long)dataSnapshot.child("timestamp").getValue());
                            mGotUserProfCommentListener.onGotUserProfComments(
                                    true,
                                    comment);
                        }else{
                            mGotUserProfCommentListener.onGotUserProfComments(false, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );

    }

    public void listenForUserProfileData(){

        mDatabase.child("UsersExtraData/"+uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserExtraData data = new UserExtraData(
                                (String)dataSnapshot.child("ShownName").getValue()
                        );

                        if (dataSnapshot.hasChild("UniId")){
                            data.setUniId(
                                    (String)dataSnapshot.child("UniId").getValue()
                            );
                        }
                        if (dataSnapshot.hasChild("UniName")){
                            data.setUniName(
                                    (String)dataSnapshot.child("UniName").getValue()
                            );
                        }
                        mGotUserExtraDataListener.gotExtraData(data);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    public GotUserExtraDataListener getmGotUserExtraDataListener() {
        return mGotUserExtraDataListener;
    }

    public void setmGotUserExtraDataListener(GotUserExtraDataListener mGotUserExtraDataListener) {
        this.mGotUserExtraDataListener = mGotUserExtraDataListener;
    }

    public SentUniDataListener getSentUniDataListener() {
        return sentUniDataListener;
    }

    public void setSentUniDataListener(SentUniDataListener sentUniDataListener) {
        this.sentUniDataListener = sentUniDataListener;
    }
}
