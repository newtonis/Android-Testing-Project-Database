package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.support.constraint.Placeholder;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.gnd.calificaprofesores.ActivityClassFrontPageV2;
import com.gnd.calificaprofesores.ActivityOpinarMateria;
import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.GotCommentListener;
import com.gnd.calificaprofesores.NetworkHandler.GotUserCommentListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.OpinionItem.AdapterCourseComments;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.R;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ActivityYourOpinion extends Fragment {

    private static final String ARG_POSITION = "position";
    private View mView;
    private ViewGroup mContainer;
    private ViewGroup placeholder;
    private LayoutInflater mLayoutInflater;
    IntentCourseManager CourseManager; // para saber en que curso estamos
    private AdapterCourseComments mAdapterCourseComments;

    private List<CourseComment> Comments;
    private RecyclerView recyclerView; // para mostrar el comentario que ya hizo el usuario

    Button buttonSend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /// necesitamos determinar si el usuario ya emitio su opinion del curso seleccionado
        mView = inflater.inflate(R.layout.layout_loading_icon, container, false);
        mContainer = container;
        mLayoutInflater = inflater;
        //placeholder = new Placeholder();
        CourseManager = new IntentCourseManager();
        UserDataManager userData = new UserDataManager();

        Long CourseId = CourseManager.GetCourseId();

        userData.AddGotUserCommentListener(CourseId, new GotUserCommentListener() {
            @Override
            public void GotUserComment(boolean hasCommented, CourseComment comment) {
                if (!hasCommented){
                    UpdateNoComment();
                }else{
                    UpdateComment(comment);
                }
            }
        });
        placeholder = (ViewGroup) mView;

        return placeholder;
    }
    public void UpdateNoComment(){
        mView = mLayoutInflater.inflate(R.layout.layout_no_opinion, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);
        buttonSend = mView.findViewById(R.id.ButtonOpinar);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CourseManager.ConvertIntent(mView.getContext(), ActivityOpinarMateria.class ).GetIntent());
            }
        });
    }

    public void UpdateComment(CourseComment comment) {
        // suponiendo el usuario ya escribió un cometario de la materia
        mView = mLayoutInflater.inflate(R.layout.layout_your_opinion, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);

        buttonSend = mView.findViewById(R.id.ButtonOpinar);
        recyclerView = mView.findViewById(R.id.RecyclerView);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CourseManager.ConvertIntent(mView.getContext(), ActivityOpinarMateria.class).GetIntent());
            }
        });
        Comments = new ArrayList<>();
        Comments.add(comment);
        // le decimos al adapter los comentarios a mostrar
        mAdapterCourseComments = new AdapterCourseComments(Comments);
        /// le decimos al recycler view cual es su adapter
        recyclerView.setAdapter(mAdapterCourseComments);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

    }

}
