package com.gnd.calificaprofesores.AdapterProfFrontPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ProfessorData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.StarsData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;

/** layout asociado: layout_recycler_view.xml **/

public class ActivityProfFrontPageCapital extends Fragment {
    private View mView;
    RecyclerView recyclerView;
    Adapter myAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_recycler_view,container,false);
        recyclerView = mView.findViewById(R.id.RecyclerView);

        myAdapter = new Adapter();
        recyclerView.setAdapter(myAdapter);

        myAdapter.AddElement(new TitleData("CALIFICACIÓN GENERAL"));
        myAdapter.AddElement(new StarsData(3f));

        myAdapter.AddElement(new TitleData("PERCEPCIÓN POR MATERIA"));
        myAdapter.AddElement(new ProfessorData("Física I",1.0f,0.5f,0.25f));
        myAdapter.AddElement(new ProfessorData("Física II",1.0f,0.5f,0.25f));
        myAdapter.AddElement(new ProfessorData("Met. Del Aprendizaje",0.5f,0.5f,0.67f));

        myAdapter.AddElement(new ProfessorData("Organización Industrial",1.0f,0.5f,0.25f));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setHasFixedSize(true);

        myAdapter.notifyDataSetChanged();

        return mView;

    }
}
