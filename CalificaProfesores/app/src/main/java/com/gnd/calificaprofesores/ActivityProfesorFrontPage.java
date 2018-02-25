package com.gnd.calificaprofesores;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.gnd.calificaprofesores.NetworkHandler.MatData;
import com.gnd.calificaprofesores.NetworkHandler.ProfesorDataManager;
import com.google.firebase.database.DatabaseError;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ActivityProfesorFrontPage extends AppCompatActivity {
    ProfesorDataManager manager;
    Map<String, MatData> rankingData;
    AdapterListSubject adapterSubject;
    boolean dataIsHere; /// se pone en true cuando ya esta la informacion para mostrar los puntajes del profesor

    String professorName;
    Long professorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_front_page);

        Intent intent = getIntent();

        professorName = intent.getStringExtra("ProfessorName");
        professorId = intent.getLongExtra("ProfessorId",1L);

        manager = new ProfesorDataManager(professorId) {
            @Override
            public void onGotProfData(String name, Map<String, MatData> Mat) {
                rankingData = Mat;

                Map<String,String> subjectContent = new TreeMap<>();

                for (Map.Entry<String,MatData> entry : Mat.entrySet()){
                    subjectContent.put(entry.getValue().getName(),entry.getValue().getFacultad());
                }
                ListView subjectView = findViewById(R.id.ListViewSubject);
                adapterSubject = new AdapterListSubject(subjectContent){
                    @Override
                    public void OnCheckBoxPress(){
                        updateRanking();
                    }
                };
                subjectView.setAdapter(adapterSubject);
                TextView titleProf = findViewById(R.id.ProfessorName);
                titleProf.setText(name);
                dataIsHere = true;
                updateRanking();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("myTag", "Error couldn't get data");
            }
        };
        Button checkAllButton = findViewById(R.id.CheckAll);
        checkAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterSubject.checkAll();
                updateRanking();
            }
        });
    }
    public void updateRanking(){
        if (!dataIsHere) return; // no hay informacion aun

        StackedBarChart mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);
        mStackedBarChart.clearChart();
        ListView subjectView = findViewById(R.id.ListViewSubject);


        StackedBarModel s1 = new StackedBarModel("CE");
        StackedBarModel s2 = new StackedBarModel("CA");
        StackedBarModel s3 = new StackedBarModel("A");


        float cnt = 0f;
        float CA = 0f;
        float CE = 0f;
        float A = 0f;

        for (Map.Entry<String,MatData> entry : rankingData.entrySet()){
            if (adapterSubject.isChecked(entry.getKey())){
                cnt += entry.getValue().getProp("CNT");
                CA  += entry.getValue().getProp("CA");
                CE  += entry.getValue().getProp("CE");
                A   += entry.getValue().getProp("A");
            }
        }
        if (cnt > 0){
            CA /= cnt;CE /= cnt; A /= cnt;
        }

        CA = (float)Math.round(CA*10f)/10f;
        CE = (float)Math.round(CE*10f)/10f;
        A  = (float)Math.round(A *10f)/10f;
        //s1.addBar(new BarModel(CA,));
        s1.addBar(new BarModel(CE,this.getResources().getColor(R.color.CA_color_a)));
        s1.addBar(new BarModel(5f - CE ,this.getResources().getColor(R.color.CA_color_b)));

        s2.addBar(new BarModel(CA,this.getResources().getColor(R.color.CE_color_a)));
        s2.addBar(new BarModel(5f - CA,this.getResources().getColor(R.color.CA_color_b)));

        s3.addBar(new BarModel(A,this.getResources().getColor(R.color.A_color_a)));
        s3.addBar(new BarModel(5f - A,this.getResources().getColor(R.color.A_color_b)));

        mStackedBarChart.addBar(s1);
        mStackedBarChart.addBar(s2);
        mStackedBarChart.addBar(s3);
        mStackedBarChart.startAnimation();

        TextView ScoreText = findViewById(R.id.ScoreText);
        Float score = (CE+CA+A)/3;
        score *= 10f;
        score = (float)Math.round(score);
        score /= 10f;
        ScoreText.setText( Float.toString(score) );
    }

}
