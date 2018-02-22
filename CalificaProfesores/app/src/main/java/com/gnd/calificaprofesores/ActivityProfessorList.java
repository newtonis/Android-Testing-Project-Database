package com.gnd.calificaprofesores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

public class ActivityProfessorList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_list);

        /*ListView professorList = findViewById(R.id.p);

        Map<String,String> content = new HashMap<>();
        content.put("Martin Garcia","ITBA");
        content.put("Joaquin Gonzalez","ITBA");
        content.put("Moria Rodriguez","ITBA");

        AdapterList adapter = new AdapterList(content);

        professorList.setAdapter(adapter);*/

    }


}
