package com.gnd.calificaprofesores.AdapterProfFrontPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.GotCommentListener;
import com.gnd.calificaprofesores.NetworkProfOpinion.GotProfCommentListener;
import com.gnd.calificaprofesores.NetworkProfOpinion.ProfCommentsDataManager;
import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.AdapterElement;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.OpinionProfData;
import com.google.firebase.database.DatabaseError;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** layout_recycler_view.xml **/

public class ActivityOpinion extends Fragment {
    private View mView;
    private Adapter adapter;

    private static String ProfName;
    private static Long ProfId;

    private RecyclerView recyclerView;
    IntentCourseManager CourseManager;
    ProfCommentsDataManager mProfCommentsDataManager;
    ViewGroup mContainer;
    LayoutInflater mLayoutInflater;
    ViewGroup placeholder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ActivityOpinion(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_loading_icon, container, false);
        mContainer = container;
        mLayoutInflater = inflater;
        placeholder = (ViewGroup)mView;

        CourseManager = new IntentCourseManager();

        adapter = new Adapter();

        mProfCommentsDataManager = new ProfCommentsDataManager(CourseManager.GetCourseId());

        mProfCommentsDataManager.AddOnGotCommentListener(new GotProfCommentListener() {
            @Override
            public void onGotProfCommentsListener(List<UserProfComment> comments) {
                recvComments(comments);
            }
        });
        mProfCommentsDataManager.ListenForComments();

        return mView;
    }
    public void recvComments(List<UserProfComment> comment){
        SetLoaded();

        recyclerView = mView.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        for (UserProfComment com : comment){
            addComment(com);
        }

        adapter.notifyDataSetChanged();
    }
    public void addComment(UserProfComment comment){
        String materiasText = "";
        Float stars = (comment.getConocimiento() + comment.getClases() + comment.getAmabilidad()) / 3f;

        Map<String, String> materias = comment.getMaterias();

        boolean start = true;
        for (String materia_key : materias.keySet()){
            if (start){
                start = false;
            }else{
                materiasText += ", ";
            }
            materiasText += materias.get(materia_key);
        }

        adapter.AddElement(new OpinionProfData(
                comment.getAuthor(),
                materiasText,
                comment.getContent(),
                comment.getConocimiento(),
                comment.getClases(),
                comment.getAmabilidad(),
                comment.getTimestamp_long(),
                stars
                ));
    }


    public void SetLoaded(){
        mView = mLayoutInflater.inflate(R.layout.layout_recycler_view, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);
    }
}
