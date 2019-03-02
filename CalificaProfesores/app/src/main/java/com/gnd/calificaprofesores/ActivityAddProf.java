package com.gnd.calificaprofesores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkAdd.AddProfessorHandler;
import com.gnd.calificaprofesores.NetworkAdd.CompleteProfData;
import com.gnd.calificaprofesores.NetworkAdd.ProfessorAddedListener;
import com.gnd.calificaprofesores.NetworkAdd.SmallMateriaData;
import com.gnd.calificaprofesores.NetworkHandler.CourseData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotCourseListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchCourseHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.InputLineTextData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.MiniSearchData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NoInfoData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SearchCalledListener;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SmallLoadingData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class ActivityAddProf extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private SearchCourseHandler searchCourseHandler;
    private AddProfessorHandler addProfessorHandler;
    private InputLineTextData profInput;
    private ButtonData button;
    private MenuManager menuManager;
    private View.OnClickListener goToAddMateriaListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prof);

        adapter = new Adapter();
        searchCourseHandler = new SearchCourseHandler("", "");

        recyclerView = findViewById(R.id.RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addProfessorHandler = new AddProfessorHandler();

        goToAddMateriaListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ActivityAddProf.this,
                        ActivityAddClass.class
                );
                startActivity(intent);
            }
        };

        profInput = new InputLineTextData("Profesor ...","Nombre del profesor");
        adapter.AddElement(profInput);

        adapter.AddElement(new TitleData("SELECCIONAR MATERIAS"));

        final MiniSearchData miniSearchData = new MiniSearchData(
                "Materia ...",
                "LAS MATERIAS NO FIGURAN",
                true,
                -1L,
                new SearchCalledListener() {
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

                if (convertedData.size() == 0){
                    miniSearchData.AddElement(new NoInfoData(
                            "NO FUE ENCONTRADA LA MATERIA",
                            "AGREGAR MATERIA",
                            goToAddMateriaListener
                    ));
                    miniSearchData.notifyDataSetChanged();
                }
            }
        });
        adapter.AddElement(miniSearchData);

        button = new ButtonData("ENVIAR");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> facultades = new TreeMap<>();
                Map<String, SmallMateriaData> materias = new TreeMap<>();

                Set<UniData> matList = miniSearchData.getElementSet();

                Set<String> uniSet = new TreeSet<>();

                for (UniData element : matList){
                    materias.put(
                            element.GetId(),
                            new SmallMateriaData(
                                    element.GetUniShownName(),
                                    element.GetUniShortName()
                            )
                    );
                    uniSet.add(element.GetUniShownName());
                }
                Long count = 1L;
                for (String uni : uniSet){
                    facultades.put(Long.toString(count) , uni);
                    count ++;
                }

                addProfessorHandler.addProfessor(new CompleteProfData(
                        profInput.getText(),
                        "0",
                        facultades,
                        materias,
                        false,
                        miniSearchData.isAllowSwitch()
                ));
                adapter.removeElement(button);
                adapter.AddElement(new SmallLoadingData());
            }
        });
        addProfessorHandler.setProfessorAddedListener(new ProfessorAddedListener() {
            @Override
            public void onProfessorAdded() {
                professorAdded();

            }
        });
        adapter.AddElement(button);
        adapter.notifyDataSetChanged();


        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));
    }
    public void professorAdded(){
        Toast.makeText(this,
                "Profesor será agregado en unos instantes",
                Toast.LENGTH_SHORT
        ).show();
        Intent intent = new Intent(ActivityAddProf.this, ActivityUser.class);
        startActivity(intent);
    }
}
