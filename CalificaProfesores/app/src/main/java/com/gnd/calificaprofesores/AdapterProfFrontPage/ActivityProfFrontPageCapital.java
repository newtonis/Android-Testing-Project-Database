package com.gnd.calificaprofesores.AdapterProfFrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.ActivityError404;
import com.gnd.calificaprofesores.IntentsManager.IntentProfManager;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkProfOpinion.GotProfQualListener;
import com.gnd.calificaprofesores.NetworkProfOpinion.ProfCommentsDataManager;
import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Error404Data;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ProfessorData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ShownQualData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.StarsData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;

/** layout asociado: layout_recycler_view.xml **/

public class ActivityProfFrontPageCapital extends Fragment {
    private View mView;
    private RecyclerView recyclerView;
    private Adapter myAdapter;
    private IntentProfManager intentProfManager;
    private ViewGroup placeholder;
    private ViewGroup mContainer;
    private UserDataManager userData;
    private LayoutInflater mLayoutInflater;

    ProfCommentsDataManager profCommentsDataManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.layout_loading_icon,container,false);
        placeholder = (ViewGroup)mView;
        mContainer = container;
        mLayoutInflater = inflater;

        intentProfManager = new IntentProfManager(getActivity().getIntent());

        profCommentsDataManager = new ProfCommentsDataManager(intentProfManager.GetProfId());

        profCommentsDataManager.AddOnGotQualListener(new GotProfQualListener() {
            @Override
            public void onGotProfQualListener(float conocimiento, float clases, float amabilidad) {
                SetLoaded(
                        conocimiento,
                        clases,
                        amabilidad
                );
            }

            @Override
            public void onError() {
                setFailure();
            }
        });

        profCommentsDataManager.RequestProfQual();

        return mView;

    }
    public void SetLoaded(float conocimiento, float clases, float amabilidad){

        mView = mLayoutInflater.inflate(R.layout.layout_recycler_view, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);

        recyclerView = mView.findViewById(R.id.RecyclerView);

        myAdapter = new Adapter();
        recyclerView.setAdapter(myAdapter);

        myAdapter.AddElement(new TitleData("CALIFICACIÓN GENERAL"));
        myAdapter.AddElement(new StarsData((conocimiento+amabilidad+clases)/3f));
        myAdapter.AddElement(new ShownQualData(conocimiento,clases,amabilidad));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        myAdapter.notifyDataSetChanged();
    }

    public void setFailure(){
        mView = mLayoutInflater.inflate(R.layout.layout_recycler_view, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);

        recyclerView = mView.findViewById(R.id.RecyclerView);

        myAdapter = new Adapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myAdapter.AddElement(new Error404Data());

        myAdapter.notifyDataSetChanged();
    }
}
