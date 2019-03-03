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
    private GotUserRightsListener gotUserRightsListener;


    FirebaseUser currentFirebaseUser;
    private DatabaseReference mDatabase;
    private int count;
    private boolean external;
    private String uid;

    public UserDataManager(String _uid){
        external = !_uid.equals("");
        uid = _uid;

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference( ).getRef();
        gotUserRightsListener = null;
        sentUniDataListener = null;
        mGotUserProfCommentListener = null;
        mGotUserExtraDataListener = null;
    }
    private String getUid(){
        if (external){
            return uid;
        }else{
            return FirebaseAuth.getInstance().getUid();
        }
    }
    public void setUni(String uniName, String uniId){
        count = 0;

        mDatabase
                .child("UsersExtraData/"+getUid()+"/UniName")
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
                .child("UsersExtraData/"+getUid()+"/UniId")
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
                .child("UsersExtraData/"+getUid()+"/ShownName")
                .setValue(shownName);
    }

    public void AddGotUserCommentListener(String CourseId, final GotUserCommentListener listener){
        this.mGotUserCommentListener = listener;

        mDatabase.child("OpinionesMaterias/"+CourseId+"/"+getUid()).addListenerForSingleValueEvent(
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
        String uid = getUid();
        mDatabase.child("OpinionesProf")
                .child(ProfId)
                .child(uid)
                .addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            Map<String ,String> materias = new TreeMap<>();

                            for (DataSnapshot child : dataSnapshot.child("materias").getChildren()){
                                materias.put(
                                        child.getKey(),
                                        (String)child.getValue()
                                );
                            }
                            UserProfComment comment = new UserProfComment(
                                    (String) dataSnapshot.child("author").getValue(),
                                    (String) dataSnapshot.child("content").getValue(),
                                    materias,
                                    new TreeMap<String, String>(),
                                    (Long) dataSnapshot.child("conocimiento").getValue(),
                                    (Long) dataSnapshot.child("clases").getValue(),
                                    (Long) dataSnapshot.child("amabilidad").getValue(),
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

    public void listenForUserProfileData(final GotUserExtraDataListener customListener){

        mDatabase.child("UsersExtraData/"+getUid()).addListenerForSingleValueEvent(
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
                        if (customListener == null) {
                            mGotUserExtraDataListener.gotExtraData(data);
                        }else{
                            customListener.gotExtraData(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    public void listenForUserRights(){
        mDatabase
                .child("Admin")
                .child(getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (gotUserRightsListener != null){
                            gotUserRightsListener.onGotUserRights(dataSnapshot.exists());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

    public GotUserRightsListener getGotUserRightsListener() {
        return gotUserRightsListener;
    }

    public void setGotUserRightsListener(GotUserRightsListener gotUserRightsListener) {
        this.gotUserRightsListener = gotUserRightsListener;
    }
}
