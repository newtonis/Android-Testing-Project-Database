package com.gnd.calificaprofesores.AdapterProfFrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gnd.calificaprofesores.ActivityClassFrontPageV2;
import com.gnd.calificaprofesores.ActivityOpinarMateria;
import com.gnd.calificaprofesores.ActivityProfFrontPageV2;
import com.gnd.calificaprofesores.ActivitySendCommentProf;
import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.IntentsManager.IntentProfManager;
import com.gnd.calificaprofesores.NetworkHandler.GotUserProfCommentListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkProfOpinion.Materia;
import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;
import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.AdapterElement;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.OpinionProfData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private RecyclerView recyclerView;
    private Adapter adapter;

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

        buttonSend = mView.findViewById(R.id.ButtonOpinar);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ProfManager.ConvertIntent(
                        mView.getContext(),
                        ActivitySendCommentProf.class
                ).GetIntent();
                intent.putExtra("PrevComment", false);

                startActivity(intent);
            }
        });
    }

    public void UpdateComment(final UserProfComment comment){
        mView = mLayoutInflater.inflate(R.layout.layout_your_prof_opinion, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);

        buttonSend = mView.findViewById(R.id.ButtonOpinar);
        recyclerView = mView.findViewById(R.id.RecyclerView);

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.AddElement(new OpinionProfData(comment));

        adapter.notifyDataSetChanged();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ProfManager.ConvertIntent(
                        mView.getContext(),
                        ActivitySendCommentProf.class
                ).GetIntent();

                intent.putExtra("PrevComment", true);
                intent.putExtra("TextContent", comment.getContent());
                intent.putExtra("IsAnonimo", comment.getAnonimo());
                intent.putExtra("HasText", comment.isConTexto());
                intent.putExtra("Conocimiento",comment.getConocimiento());
                intent.putExtra("Amabilidad",comment.getAmabilidad());
                intent.putExtra("Clases",comment.getClases());

                Map<String,String> materias = comment.getMaterias();
                String []mat_array = new String[materias.size()];
                String []mat_array_id = new String[materias.size()];

                Integer index = 0;
                for (String mat_key : materias.keySet()){
                    mat_array[index] = materias.get(mat_key);
                    mat_array[index] = mat_key;
                    index ++;
                }

                intent.putExtra("MateriasCount",materias.size());
                intent.putExtra("Materias", mat_array);
                intent.putExtra("MateriasId", mat_array_id);

                startActivity(intent);

            }
        });

    }
}
