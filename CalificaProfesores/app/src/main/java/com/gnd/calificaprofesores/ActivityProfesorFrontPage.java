package com.gnd.calificaprofesores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ActivityProfesorFrontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_front_page);

        StackedBarChart mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);
        StackedBarModel s1 = new StackedBarModel("CA");
        StackedBarModel s2 = new StackedBarModel("CE");
        StackedBarModel s3 = new StackedBarModel("A");

        s1.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s1.addBar(new BarModel(2.7f, 0xFFFFFFFF));

        s2.addBar(new BarModel(3.0f, 0xFF56B7F1));
        s2.addBar(new BarModel(2.0f, 0xFFFFFFFF));

        s3.addBar(new BarModel(4.5f, 0xFFCDA67F));
        s3.addBar(new BarModel(0.5f, 0xFFFFFFFF));


        mStackedBarChart.addBar(s1);
        mStackedBarChart.addBar(s2);
        mStackedBarChart.addBar(s3);

        mStackedBarChart.startAnimation();

        ListView subjectView = findViewById(R.id.ListViewSubject);

        Map<String,String> data = new TreeMap<>();
        data.put("Matematica I","ITBA");
        data.put("Matematica II","ITBA");
        data.put("Fisica I","UTN");
        data.put("Fisica II","UTN");
        data.put("Fisica III","UTN");

        AdapterListSubject adapterSubject = new AdapterListSubject(data);

        subjectView.setAdapter(adapterSubject);

        //barChart.start

    }
}
