package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.R;

public class ActivityYourOpinion extends Fragment {

    private static final String ARG_POSITION = "position";
    private int position;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /// necesitamos determinar si el usuario ya emitio su opinion del curso seleccionado



        mView = inflater.inflate(R.layout.layout_opinion_recent, container, false);

        ViewCompat.setElevation(mView, 50);







        return mView;
    }
}
