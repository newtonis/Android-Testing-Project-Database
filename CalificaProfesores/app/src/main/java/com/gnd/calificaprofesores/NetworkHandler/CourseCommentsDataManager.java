package com.gnd.calificaprofesores.NetworkHandler;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public abstract class CourseCommentsDataManager {
    Vector<CourseComment> comments;

    private DatabaseReference mDatabase;
    private long CourseId;
    private String CourseName;

    public CourseCommentsDataManager(Long _CourseId, String _CourseName){
        CourseId = _CourseId;
        CourseName = _CourseName;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("OpinionesMaterias/"+Long.toString(CourseId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Long cal = (Long) postSnapshot.child("cal").getValue();
                    String author = (String) postSnapshot.child("autor").getValue();
                    String contenido = (String) postSnapshot.child("contenido").getValue();
                    Long popularidad = (Long) postSnapshot.child("popularidad").getValue();

                    onGotComment(new CourseComment(author, contenido, popularidad, cal));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                onCancelled(databaseError);
            }
        });
    }

    public abstract void onGotComment(CourseComment comment);
    public abstract void onCancelled(DatabaseError databaseError);
}
