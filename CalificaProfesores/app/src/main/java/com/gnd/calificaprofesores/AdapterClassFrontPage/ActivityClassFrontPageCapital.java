package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.NetworkHandler.ClassDataManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseData;
import com.gnd.calificaprofesores.NetworkHandler.GotCommentListener;
import com.gnd.calificaprofesores.NetworkHandler.GotCourseInfoListener;
import com.gnd.calificaprofesores.NetworkProfOpinion.ProfExtendedData;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.AdapterElement;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ProfessorData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.StarsData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;
import com.google.firebase.database.DatabaseError;

/*** Aqui mostramos la informacion más importante de las clases **/

/** Layout asociado: layout_course_front_info.xml **/

public class ActivityClassFrontPageCapital extends Fragment {
    private View mView;
    ClassDataManager manager;
    RecyclerView recyclerView;
    Adapter myAdapter;
    CourseCommentsDataManager dataManager;
    ViewGroup placeholder, mContainer;
    LayoutInflater mLayoutInflater;
    IntentCourseManager CourseManager;
    CourseData course;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_loading_icon,container,false);
        mContainer = container;
        placeholder = (ViewGroup)mView;
        mLayoutInflater = inflater;
        CourseManager = new IntentCourseManager();

        dataManager = new CourseCommentsDataManager(
                CourseManager.GetCourseId(),CourseManager.GetCourseName()
        );

        dataManager.setGotCourseDataListener(new GotCourseInfoListener() {
            @Override
            public void onGotCourseInfo(CourseData _course) {
                course = _course;
                onLoaded();
            }

            @Override
            public void onFailed() {

            }
        });
        dataManager.ListenForCourseDetailedData();

        return placeholder;
    }
    public void onLoaded(){
        mView = mLayoutInflater.inflate(R.layout.layout_course_front_info, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);

        recyclerView = mView.findViewById(R.id.RecyclerView);

        myAdapter = new Adapter();
        recyclerView.setAdapter(myAdapter);


        //myAdapter.AddElement(new StarsData(3f));myAdapter.AddElement(new TitleData("CALIFICACIÓN GENERAL"));
        if (course.getScore() != -1f) {
            myAdapter.AddElement(new StarsData(course.getScore()));
        }else{
            myAdapter.AddElement(new TitleData("No hay información de la calificación"));
        }

        myAdapter.AddElement(new TitleData("PROFESORES"));

        if (course.getProfessors().size() == 0) {
            myAdapter.AddElement(new TitleData("No hay información de profesores registrada"));
        }else{
            for (ProfExtendedData prof : course.getProfessors()){
                myAdapter.AddElement(new ProfessorData(
                        prof.getName(),
                        prof.getConocimiento(),
                        prof.getClases(),
                        prof.getAmabildiad()
                ));
            }
        }
        //myAdapter.AddElement(new ProfessorData("James Watt",1.0f,0.5f,0.25f));
        //myAdapter.AddElement(new ProfessorData("Albert Einstein",1.0f,0.5f,0.25f));
        //myAdapter.AddElement(new ProfessorData("Tiger Wood",0.5f,0.5f,0.67f));

        //myAdapter.AddElement(new ProfessorData("Albert Einstein",1.0f,0.5f,0.25f));

        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setHasFixedSize(true);

        myAdapter.notifyDataSetChanged();
    }
}

