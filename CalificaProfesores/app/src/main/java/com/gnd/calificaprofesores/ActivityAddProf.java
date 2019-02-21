package com.gnd.calificaprofesores;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;

public class ActivityAddProf extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    public ActivityAddProf(){
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setAdapter(adapter);


    }
}
