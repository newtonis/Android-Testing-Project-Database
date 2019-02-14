package com.gnd.calificaprofesores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.EditTextData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ScoreSelectorData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SelectableItem;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static android.support.test.InstrumentationRegistry.getContext;

/** activity_send_comment_prof.xml **/

public class ActivitySendCommentProf extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment_prof);

        recyclerView = findViewById(R.id.RecyclerView);
        adapter = new Adapter();

        recyclerView.setAdapter(adapter);

        adapter.AddElement(new TitleData("MATERIAS"));
        adapter.AddElement(new SelectableItem("Física I","ITBA"));
        adapter.AddElement(new SelectableItem("Mátematica I","ITBA"));

        adapter.AddElement(new TitleData("CALIFICACIÓN"));
        adapter.AddElement(new ScoreSelectorData(3, 3, 3));

        adapter.AddElement(new TitleData("EN PALABRAS"));
        adapter.AddElement(new EditTextData("Comentario ..."));

        adapter.AddElement(new ButtonData("ENVIAR"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.notifyDataSetChanged();

    }
}
