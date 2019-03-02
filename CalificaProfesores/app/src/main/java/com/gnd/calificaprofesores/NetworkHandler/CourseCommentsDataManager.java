package com.gnd.calificaprofesores.NetworkHandler;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gnd.calificaprofesores.NetworkProfOpinion.ProfExtendedData;
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
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class CourseCommentsDataManager {

    private DatabaseReference mDatabase;
    private String CourseId;
    private String CourseName;
    private GotCourseInfoListener gotCourseDataListener;
    private GotCommentListener gotCommentListener;

    private Integer count;

    public CourseCommentsDataManager(String _CourseId, String _CourseName){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        CourseId = _CourseId;
        CourseName = _CourseName;
    }
    public void listenForComments(){
        mDatabase.child("OpinionesMaterias")
                .child(CourseId)
                .orderByChild("timestamp")
                .limitToFirst(20)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CourseComment> comments = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (postSnapshot.getKey().equals("sin_contenido")) {
                            continue;
                        }
                        final Long valoracion = (Long) postSnapshot.child("valoracion").getValue();
                        final String author = (String) postSnapshot.child("author").getValue();
                        final String contenido = (String) postSnapshot.child("content").getValue();
                        final Long timestamp = (Long) postSnapshot.child("timestamp").getValue();

                        CourseComment comment = new CourseComment(
                                author,
                                contenido,
                                valoracion,
                                0L
                        );
                        comment.setTimestampLong(
                                timestamp
                        );

                        comments.add(comment);
                    }
                }

                gotCommentListener.onGotComment(comments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                gotCommentListener.onCancelled(databaseError);
            }
        });

    }

    public void SendComment(CourseComment comment, final SentCommentListener listener){
        comment.SetTimestamp(ServerValue.TIMESTAMP);
        mDatabase.child("OpinionesMaterias/"+CourseId+"/"+FirebaseAuth.getInstance().getUid()).setValue(comment, new DatabaseReference.CompletionListener(){
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

    public void ListenForCourseDetailedData(){

        mDatabase.
                child("Materias").
                child(CourseId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CourseData courseData = new CourseData(
                        CourseId,
                        (String)dataSnapshot.child("ShownName").getValue(),
                        (String)dataSnapshot.child("FacultadName").getValue()
                );

                List<ProfExtendedData> profData = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.child("Prof").getChildren()){
                    profData.add(new ProfExtendedData(
                            child.getKey(),
                            (String)child.getValue()
                    ));
                }
                courseData.setProfessors(profData);

                long totalScore = (long)dataSnapshot.child("totalScore").getValue();
                long count = (long)dataSnapshot.child("count").getValue();

                if (count != 0) {
                    courseData.setScore((float) totalScore / count);
                }else{
                    courseData.setScore(-1f);
                }

                CallForProfessorsScores(courseData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                gotCourseDataListener.onFailed();
            }
        });
    }
    public void CallForProfessorsScores(final CourseData courseData){
        count = 0;

        for (final ProfExtendedData prof : courseData.getProfessors()) {
            mDatabase
                    .child("Prof")
                    .child(prof.getId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                long sumAmabilidad = (long) dataSnapshot.child("amabilidad").getValue();
                                long sumClases = (long) dataSnapshot.child("clases").getValue();
                                long sumConocimiento = (long) dataSnapshot.child("conocimiento").getValue();
                                long count_value = (long) dataSnapshot.child("count").getValue();

                                if (count != 0) {
                                    prof.setAmabildiad((float) sumAmabilidad / count_value);
                                    prof.setClases((float) sumClases / count_value);
                                    prof.setConocimiento((float) sumConocimiento / count_value);
                                } else {
                                    prof.setAmabildiad(-1f);
                                    prof.setClases(-1f);
                                    prof.setConocimiento(-1f);
                                }
                                count++;

                                if (count == courseData.getProfessors().size()) {
                                    gotCourseDataListener.onGotCourseInfo(courseData);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

    }

    public GotCourseInfoListener getGotCourseDataListener() {
        return gotCourseDataListener;
    }

    public void setGotCourseDataListener(GotCourseInfoListener gotCourseDataListener) {
        this.gotCourseDataListener = gotCourseDataListener;
    }

    public GotCommentListener getGotCommentListener() {
        return gotCommentListener;
    }

    public void setGotCommentListener(GotCommentListener gotCommentListener) {
        this.gotCommentListener = gotCommentListener;
    }
}
