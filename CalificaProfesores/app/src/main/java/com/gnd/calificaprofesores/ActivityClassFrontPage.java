package com.gnd.calificaprofesores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gnd.calificaprofesores.NetworkHandler.ClassDataManager;
import com.gnd.calificaprofesores.NetworkHandler.MatData;
import com.gnd.calificaprofesores.NetworkHandler.ProfData;
import com.gnd.calificaprofesores.NetworkHandler.ProfesorDataManager;
import com.google.firebase.database.DatabaseError;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;
import org.w3c.dom.Text;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by newtonis on 26/02/18.
 */

public class ActivityClassFrontPage extends AppCompatActivity {

    ClassDataManager manager;
    Map<String, ProfData> profData;
    AdapterListSubject adapterSubject;
    boolean dataIsHere; /// se pone en true cuando ya esta la informacion para mostrar los puntajes del profesor

    String className;
    Long classId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_front_page);

        profData = new TreeMap<>();

        Intent intent = getIntent();

        className = "Probabilidad y Estadistica"; //intent.getStringExtra("ClassName");
        classId = intent.getLongExtra("ClassNameId",1L);

        TextView title = findViewById(R.id.ClassName);
        title.setText(className);

        manager = new ClassDataManager(classId){
            @Override
            public void onGotClassData(Map<Long, ProfData> data) {
                Map<String,String> listContent = new TreeMap<>();
                for (Map.Entry<Long,ProfData> entry : data.entrySet()){
                    profData.put(entry.getValue().getName(),entry.getValue());
                    listContent.put(entry.getValue().getName(),"");
                }
                ListView subjectView = findViewById(R.id.ListViewSubject);
                adapterSubject = new AdapterListSubject(listContent){
                    @Override
                    public void OnCheckBoxPress(){
                        updateRanking();
                    }
                };
                subjectView.setAdapter(adapterSubject);
                dataIsHere = true;
                updateRanking();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

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

        for (Map.Entry<String,ProfData> entry : profData.entrySet()){
            if (adapterSubject.isChecked(entry.getKey())){
                cnt += entry.getValue().getProperty("CNT");
                CA  += entry.getValue().getProperty("CA");
                CE  += entry.getValue().getProperty("CE");
                A   += entry.getValue().getProperty("A");
            }
        }
        if (cnt > 0){
            CA /= cnt;CE /= cnt; A /= cnt;
        }

        CA = (float)Math.round(CA)/10f;
        CE = (float)Math.round(CE)/10f;
        A  = (float)Math.round(A)/10f;
        //s1.addBar(new BarModel(CA,));
        s1.addBar(new BarModel(CE,this.getResources().getColor(R.color.CA_color_a)));
        s1.addBar(new BarModel(10f - CE ,this.getResources().getColor(R.color.CA_color_b)));

        s2.addBar(new BarModel(CA,this.getResources().getColor(R.color.CE_color_a)));
        s2.addBar(new BarModel(10f - CA,this.getResources().getColor(R.color.CA_color_b)));

        s3.addBar(new BarModel(A,this.getResources().getColor(R.color.A_color_a)));
        s3.addBar(new BarModel(10f - A,this.getResources().getColor(R.color.A_color_b)));

        mStackedBarChart.addBar(s1);
        mStackedBarChart.addBar(s2);
        mStackedBarChart.addBar(s3);
        mStackedBarChart.startAnimation();

        TextView ScoreText = findViewById(R.id.ScoreText);
        Float score = (CE+CA+A)/3;
        score = (float)Math.round(score);
        ScoreText.setText( Float.toString(score) );
    }







}
