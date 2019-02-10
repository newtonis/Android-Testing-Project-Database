package com.gnd.calificaprofesores.NetworkHandler;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class CourseCommentsDataManager {

    private DatabaseReference mDatabase;
    private long CourseId;
    private String CourseName;
    private List<GotCourseInfoListener> listenersCourseInfo;

    public CourseCommentsDataManager(Long _CourseId, String _CourseName){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        CourseId = _CourseId;
        CourseName = _CourseName;
        listenersCourseInfo = new ArrayList<>();
    }
    public void AddOnGotCommentListener(final GotCommentListener listener){

        mDatabase.child("OpinionesMaterias/"+Long.toString(CourseId))
                .orderByChild("timestamp")
                .limitToFirst(20)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    // fix pendiente
                    final Long valoracion = (Long) postSnapshot.child("valoracion").getValue();
                    final String uid = (String) postSnapshot.getKey();
                    final String contenido = (String) postSnapshot.child("content").getValue();
                    final Long likes = (Long) postSnapshot.child("likes").getValue();

                    UserDataManager userData = new UserDataManager();
                    userData.AddGotUserProfileData(uid, new GotUserExtraDataListener() {
                        @Override
                        public void gotExtraData(UserExtraData extraData) {
                            listener.onGotComment(new CourseComment(extraData.GetShownName(), contenido, valoracion, likes));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });

    }

    public void SendComment(CourseComment comment, final SentCommentListener listener){
        comment.SetTimestamp(ServerValue.TIMESTAMP);
        mDatabase.child("OpinionesMaterias/"+Long.toString(CourseId)+"/"+FirebaseAuth.getInstance().getUid()).setValue(comment, new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    listener.onSentComment();
                }else{
                    listener.onFailedComment(databaseError, databaseReference);
                }
            }
        });
    }

    public void AddOnGotCourseDataListener(final Long CourseId, final GotCourseInfoListener listener){

        mDatabase.child("Materias/"+CourseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onGotCourseInfo(new CourseData(
                        CourseId,
                        (String)dataSnapshot.child("ShownName").getValue(),
                        (String)dataSnapshot.child("FacultadName").getValue()
                ));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailed();
            }
        });
    }
}
