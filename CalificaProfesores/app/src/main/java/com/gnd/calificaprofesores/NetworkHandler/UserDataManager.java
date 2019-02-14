package com.gnd.calificaprofesores.NetworkHandler;

import android.support.annotation.NonNull;

import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;
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
import java.util.Map;
import java.util.TreeMap;

/** Aqui administramos la obtencion de la inforamcion de un usuario **/



public class UserDataManager {
    private GotUserCommentListener mGotUserCommentListener;
    private GotUserExtraDataListener mGotUserExtraDataListener;
    private GotUserProfCommentListener mGotUserProfCommentListener;

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
                                            (String)dataSnapshot.child("content").getValue(),
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

    public void AddGotUserProfCommentsListener(final GotUserProfCommentListener listener){
        this.mGotUserProfCommentListener = listener;
    }

    public void ListenForUserProfComment(Long ProfId){
        String uid = FirebaseAuth.getInstance().getUid();
        mDatabase.child("OpinoinesProf/"+ProfId).child(uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<UserProfComment> comments = new ArrayList<>();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            Map<Long,String> materias = new TreeMap<>();

                            for (DataSnapshot child : dataSnapshot.child("materias").getChildren()){
                                materias.put(Long.parseLong(child.getKey()), (String)child.getValue());
                            }
                            mGotUserProfCommentListener.onGotUserProfComments(true,
                                    new UserProfComment(
                                            (String)dataSnapshot.child("author").getValue(),
                                            (String)dataSnapshot.child("content").getValue(),
                                            materias,
                                            (String)dataSnapshot.child("timestamp").getValue(),
                                            (Long)dataSnapshot.child("amabilidad").getValue(),
                                            (Long)dataSnapshot.child("conocimiento").getValue(),
                                            (Long)dataSnapshot.child("clases").getValue()
                                    ));
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

    public void AddGotUserProfileData(final String uid, final GotUserExtraDataListener listener){
        this.mGotUserExtraDataListener = listener;

        mDatabase.child("UsersExtraData/"+uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listener.gotExtraData(new UserExtraData(
                                uid, (String)dataSnapshot.child("ShownName").getValue()
                        ));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }


}
