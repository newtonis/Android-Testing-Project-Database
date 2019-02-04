package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;


import com.gnd.calificaprofesores.AdapterListSubject;
import com.gnd.calificaprofesores.NetworkHandler.ClassDataManager;
import com.gnd.calificaprofesores.NetworkHandler.ProfData;
import com.gnd.calificaprofesores.R;
import com.google.firebase.database.DatabaseError;

import java.util.Map;
import java.util.TreeMap;


public class ActivityQual extends Fragment {

    private static final String ARG_POSITION = "position";

    TextView textView;

    private int position;
    private View mView;

    private static String CourseName;
    private static Long CourseId;

    boolean dataIsHere; /// se pone en true cuando ya esta la informacion para mostrar los puntajes del profesor

    // Data to show qualifications
    Map<String, ProfData> profData;
    AdapterListSubject adapterSubject;
    ClassDataManager manager;

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
        mView = inflater.inflate(R.layout.layout_qual,container,false);

        ViewCompat.setElevation(mView, 50);

        //Intent intent = getIntent();
        CourseName = "Física I"; //intent.getStringExtra("CourseName");
        CourseId = 1L; //intent.getLongExtra("CourseId", 1L);

        // Primero obtenemos la información en función del curso seleccionado
        profData = new TreeMap<>();

        manager = new ClassDataManager(CourseId){
            @Override
            public void onGotClassData(Map<Long, ProfData> data) {
                Map<String,String> listContent = new TreeMap<>();
                for (Map.Entry<Long,ProfData> entry : data.entrySet()){
                    profData.put(entry.getValue().getName(),entry.getValue());
                    listContent.put(entry.getValue().getName(),"");
                }
                ListView subjectView = mView .findViewById(R.id.ListViewSubject);
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

        //textView.setText("CARD " + position);
        return mView;
    }

    public void updateRanking(){
        //if (!dataIsHere) return; // no hay informacion aun

        StackedBarChart mStackedBarChart = (StackedBarChart) mView.findViewById(R.id.stackedbarchart);
        mStackedBarChart.clearChart();
        ListView subjectView = mView.findViewById(R.id.ListViewSubject);


        StackedBarModel s1 = new StackedBarModel("CE");
        StackedBarModel s2 = new StackedBarModel("CA");
        StackedBarModel s3 = new StackedBarModel("A");


        float cnt = 0f;
        float CA = 0f;
        float CE = 0f;
        float A = 0f;

        for (Map.Entry<String, ProfData> entry : profData.entrySet()){
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

        //TextView ScoreText = mView.findViewById(R.id.ScoreText);
        //Float score = (CE+CA+A)/3;
        //score = (float)Math.round(score);
        //ScoreText.setText( Float.toString(score) );
    }
}
