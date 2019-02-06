package com.gnd.calificaprofesores.NetworkHandler;

import android.support.annotation.NonNull;

import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/** Aqui administramos la obtencion de la inforamcion de un usuario **/



public class UserDataManager {
    private GotUserCommentListener mGotUserCommentListener;
    private GotUserExtraDataListener mGotUserExtraDataListener;

    FirebaseUser currentFirebaseUser;
    private DatabaseReference mDatabase;

    public UserDataManager(){
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference( ).getRef();
    }

    public void AddGotUserCommentListener(Long CourseId, final GotUserCommentListener listener){
        this.mGotUserCommentListener = listener;

        mDatabase.child("OpinionesMaterias/"+CourseId+"/"+currentFirebaseUser.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()){
                            listener.GotUserComment(false, null);
                        }else{
                            listener.GotUserComment(true,
                                    new CourseComment(
                                            (String)currentFirebaseUser.getDisplayName(),
                                            (String)dataSnapshot.child("contenido").getValue(),
                                            (Long)dataSnapshot.child("likes").getValue(),
                                            (Long)dataSnapshot.child("valoracion").getValue()
                                    ));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    public void AddGotUserProfileData(final String uid, final GotUserExtraDataListener listener){
        this.mGotUserExtraDataListener = listener;

        mDatabase.child("UsersExtraData/"+uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listener.gotExtraData(new UserExtraData(
                                uid, (String)dataSnapshot.child("ShowName").getValue()
                        ));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

}
