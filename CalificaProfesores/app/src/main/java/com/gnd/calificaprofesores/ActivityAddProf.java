package com.gnd.calificaprofesores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gnd.calificaprofesores.NetworkHandler.CourseData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotCourseListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchCourseHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.MiniSearchData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SearchCalledListener;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ActivityAddProf extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private SearchCourseHandler searchCourseHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prof);

        adapter = new Adapter();
        searchCourseHandler = new SearchCourseHandler(-1L);

        recyclerView = findViewById(R.id.RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.AddElement(new TitleData("SELECCIONAR MATERIAS"));

        final MiniSearchData miniSearchData = new MiniSearchData(
                "Materia ...",
                "SELECCIONAR MATERIA",
                true, new SearchCalledListener() {
            @Override
            public void onSearchCalled(String text) {
                searchCourseHandler.Search(text.toLowerCase());
            }
        });


        searchCourseHandler.AddOnGotCourseListener(new GotCourseListener() {
            @Override
            public void onGotCourse(Set<CourseData> data) {
                List<UniData> convertedData = new ArrayList<>();

                for (CourseData item : data){
                    convertedData.add(new UniData(item));
                }

                miniSearchData.SearchResults(convertedData);

                /*for (CourseData item : data){
                    UniData casted = new UniData(item);

                }*/

            }
        });

        adapter.AddElement(miniSearchData);

        adapter.notifyDataSetChanged();
    }
}
