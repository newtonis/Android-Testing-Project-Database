package com.gnd.calificaprofesores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.Map;

public class ActivityProfessorList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_list);

        ExpandableListView professorList = findViewById(R.id.ProfessorList);

        Map<String,String> content = new HashMap<String,String>();
        content.put("p1","p2");

        Adapter adapter = new MyAdapter(content);

    }


}
