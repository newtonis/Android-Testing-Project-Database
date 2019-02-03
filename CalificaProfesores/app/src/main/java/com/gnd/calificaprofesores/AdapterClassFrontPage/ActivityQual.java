package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;


import com.gnd.calificaprofesores.R;


public class ActivityQual extends Fragment {

    private static final String ARG_POSITION = "position";

    TextView textView;

    private int position;

    public static ActivityQual newInstance(int position) {
        ActivityQual f = new ActivityQual();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_qual,container,false);

        ViewCompat.setElevation(rootView, 50);
        //textView.setText("CARD " + position);
        return rootView;
    }
}