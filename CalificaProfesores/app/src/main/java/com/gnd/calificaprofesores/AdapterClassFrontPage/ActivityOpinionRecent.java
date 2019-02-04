package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;


/** Aqui acumulamos todas las opiniones recientes de una clase **/

public class ActivityOpinionRecent extends Fragment {

    private static final String ARG_POSITION = "position";

    public static ActivityOpinionRecent newInstance(int position) {

        ActivityOpinionRecent f = new ActivityOpinionRecent();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);

        return f;
    }
}
