package com.gnd.calificaprofesores.NetworkHandler;

import android.support.annotation.NonNull;

import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/** Aqui administramos la obtencion de la inforamcion de un usuario **/

interface GotUserCommentListener{
    void GotUserComment(boolean hasCommented, CourseComment comment);
}

public abstract class UserDataManager {
    private GotUserCommentListener mGotUserCommentListener;

    FirebaseUser currentFirebaseUser;
    private DatabaseReference mDatabase;

    public UserDataManager(){
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    }


    public void AddGotUserCommentListener(Long CourseId, final GotUserCommentListener listener){
        this.mGotUserCommentListener = listener;

        mDatabase.child("VotesCourses/"+currentFirebaseUser.getUid()+"/VoteCount/"+CourseId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                        }else{
                            listener.GotUserComment(false, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }
}
