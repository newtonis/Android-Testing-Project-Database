package com.gnd.calificaprofesores.AdapterProfFrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gnd.calificaprofesores.ActivityOpinarMateria;
import com.gnd.calificaprofesores.ActivityProfFrontPageV2;
import com.gnd.calificaprofesores.ActivitySendCommentProf;
import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.IntentsManager.IntentProfManager;
import com.gnd.calificaprofesores.NetworkHandler.GotUserProfCommentListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;
import com.gnd.calificaprofesores.R;

/** layout_prof_opnion.xml **/

/** layout_no_prof_opinion.xml **/
/** layout_your_prof_opinion.xml **/
/** layout_loading_icon.xml **/

public class ActivityYourOpinion extends Fragment {
    private View mView;
    private ViewGroup placeholder;
    private ViewGroup mContainer;
    private UserDataManager userData;

    private LayoutInflater mLayoutInflater;
    IntentProfManager ProfManager; // para saber en que curso estamos

    Button buttonSend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userData = new UserDataManager();
        ProfManager = new IntentProfManager(); // a modificar cuando sea la version final
        // por ahora el profesor selecionado es siempre el mismo
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_loading_icon, container, false);
        mLayoutInflater = inflater;
        mContainer = container;

        userData.AddGotUserProfCommentsListener(new GotUserProfCommentListener() {
            @Override
            public void onGotUserProfComments(boolean hasCommented, UserProfComment comment) {
                if (!hasCommented){
                    UpdateNoComment();
                }else{
                    UpdateComment(comment);
                }
            }
        });
        userData.ListenForUserProfComment(ProfManager.GetProfId());
        placeholder = (ViewGroup) mView;

        return mView;
    }

    public void UpdateNoComment(){
        mView = mLayoutInflater.inflate(R.layout.layout_no_opinion, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);

        buttonSend = mView.findViewById(R.id.buttonOpinar);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ProfManager.ConvertIntent(
                        mView.getContext(),
                        ActivitySendCommentProf.class
                ).GetIntent());
            }
        });
    }

    public void UpdateComment(UserProfComment comment){
        mView = mLayoutInflater.inflate(R.layout.layout_your_prof_opinion, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);


    }
}
