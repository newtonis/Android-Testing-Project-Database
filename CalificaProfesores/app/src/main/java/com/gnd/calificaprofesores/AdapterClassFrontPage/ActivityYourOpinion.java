package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.auth.data.model.User;
import com.gnd.calificaprofesores.ActivityClassFrontPageV2;
import com.gnd.calificaprofesores.ActivityOpinarMateria;
import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.GotUserCommentListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.R;

public class ActivityYourOpinion extends Fragment {

    private static final String ARG_POSITION = "position";
    private int position;
    private View mView;
    private ViewGroup mContainer;
    private ViewGroup placeholder;
    private LayoutInflater mLayoutInflater;
    IntentCourseManager CourseManager; // para saber en que curso estamos

    Button buttonSend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /// necesitamos determinar si el usuario ya emitio su opinion del curso seleccionado
        mView = inflater.inflate(R.layout.layout_empty, container, false);
        mContainer = container;
        mLayoutInflater = inflater;

        UserDataManager userData = new UserDataManager();

        Long CourseId = 1L;

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
                startActivity(CourseManager.ConvertIntent(this, ActivityOpinarMateria.class ).GetIntent());
            }
        });
    }

    public void UpdateComment(CourseComment comment){

    }

}
