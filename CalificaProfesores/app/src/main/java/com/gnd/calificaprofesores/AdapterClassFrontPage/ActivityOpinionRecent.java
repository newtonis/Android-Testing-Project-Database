package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.GotCommentListener;
import com.gnd.calificaprofesores.OpinionItem.AdapterCourseComments;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.R;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/** Aqui acumulamos todas las opiniones recientes de una clase **/

public class ActivityOpinionRecent extends Fragment {

    private static final String ARG_POSITION = "position";
    private int position;
    private View mView;
    private AdapterCourseComments mAdapterCourseComments;

    private List<CourseComment> Comments;
    private static String CourseName;
    private static Long CourseId;

    private RecyclerView recyclerView;
    CourseCommentsDataManager mCourseCommentsDataManager;
    IntentCourseManager CourseManager; // para saber en que curso estamos

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_opinion_recent, container, false);

        //ViewCompat.setElevation(mView, 50);

        recyclerView = mView.findViewById(R.id.RecyclerView);
        Comments = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        mAdapterCourseComments = new AdapterCourseComments(Comments);
        recyclerView.setAdapter(mAdapterCourseComments);

        CourseManager = new IntentCourseManager();

        mCourseCommentsDataManager = new CourseCommentsDataManager(
                CourseManager.GetCourseId(),
                CourseManager.GetCourseName()
        );

        mCourseCommentsDataManager.AddOnGotCommentListener(new GotCommentListener() {
            @Override
            public void onGotComment(CourseComment comment) {
                addComment(comment);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"Error buscando comentarios",Toast.LENGTH_SHORT).show();
            }
        });

        return mView;
    }
    public void addComment(CourseComment comment){
        //Toast.makeText(this.getContext(),"Comentario recibido!",Toast.LENGTH_SHORT);
        Comments.add(comment);
    }
}
