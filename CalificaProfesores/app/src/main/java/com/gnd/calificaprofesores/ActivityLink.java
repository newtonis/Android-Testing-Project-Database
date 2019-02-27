package com.gnd.calificaprofesores;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gnd.calificaprofesores.IntentsManager.IntentProfManager;
import com.gnd.calificaprofesores.NetworkAdd.AddClassHandler;
import com.gnd.calificaprofesores.NetworkAdd.AddProfessorHandler;
import com.gnd.calificaprofesores.NetworkAdd.CompleteProfData;
import com.gnd.calificaprofesores.NetworkAdd.ProfessorAddedListener;
import com.gnd.calificaprofesores.NetworkAdd.SmallMateriaData;
import com.gnd.calificaprofesores.NetworkHandler.CourseData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotCourseListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchCourseHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchProfHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.MiniSearchData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NoInfoData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SearchCalledListener;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SmallLoadingData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ActivityLink extends AppCompatActivity {
    private Intent intent;
    private Adapter adapter;
    private RecyclerView recyclerView;
    private SearchProfHandler searchProfHandler;
    private SearchCourseHandler searchCourseHandler;
    private ButtonData button;
    private View.OnClickListener goToAddMateriaListener;

    private AddProfessorHandler addProfessorHandler;
    private AddClassHandler addClassHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        recyclerView = findViewById(R.id.RecyclerView);
        adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        addProfessorHandler = new AddProfessorHandler();
        addClassHandler = new AddClassHandler();

        goToAddMateriaListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ActivityLink.this,
                        ActivityAddClass.class
                );
                startActivity(intent);
            }
        };

        searchCourseHandler = new SearchCourseHandler(-1L);
        searchProfHandler = new SearchProfHandler();

        intent = getIntent();
        String mode = intent.getStringExtra("Mode");

        SetLinkProf();

        /*if (mode.equals("Prof")){
            SetLinkProf();
        }else if(mode.equals("Course")){
            SetLinkCourse();
        }*/


    }

    protected void SetLinkProf(){
        final IntentProfManager intentProfManager = new IntentProfManager(
                new Intent(), "Rob", "-LZjdPj-P7lhBm1CI7pa"
        ); // para modificar
        intentProfManager.setProfUni("ITBA");
        //adapter.AddElement(new TitleData("PROFESOR"));

        String profName = intentProfManager.GetProfName();
        String profUni = intentProfManager.getProfUni();
        String profId = intentProfManager.GetProfId();

        UniData data = new UniData(
                profId,
                profName,
                profUni
        );
        data.SetType(18);

        adapter.AddElement(data);

        adapter.AddElement(new TitleData("AGREGAR MATERIAS"));

        final MiniSearchData miniSearchData = new MiniSearchData(
                "Materia ...",
                "",
                false,
                -1L,
                new SearchCalledListener() {
                    @Override
                    public void onSearchCalled(String text) {
                        searchCourseHandler.Search(text);
                    }
                }
        );
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
                }
            }
        });
        adapter.AddElement(miniSearchData);

        button = new ButtonData(
            "ENVIAR"
        );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, SmallMateriaData> materias = new TreeMap<>();

                for (UniData item : miniSearchData.getElementSet()){
                    materias.put(
                            item.GetId(),
                            new SmallMateriaData(
                                item.GetUniShownName(),
                                item.GetUniShortName()
                            )
                    );
                }

                if (materias.size() == 0){
                    Toast.makeText(ActivityLink.this, "Debes agregar al menos una materia",
                            Toast.LENGTH_SHORT).show();
                }else {
                    addProfessorHandler.addProfessor(new CompleteProfData(
                            "",
                            intentProfManager.GetProfId(),
                            new TreeMap<String, String>(),
                            materias,
                            false,
                            true
                    ));
                    adapter.removeElement(button);
                    adapter.AddElement(new SmallLoadingData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        addProfessorHandler.setProfessorAddedListener(new ProfessorAddedListener() {
            @Override
            public void onProfessorAdded() {
                Toast.makeText(ActivityLink.this, "La materia sera enlazada en unos momentos",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(
                        ActivityLink.this,
                        ActivityUser.class
                );
                startActivity(intent);
            }
        });

        adapter.AddElement(button);

        adapter.notifyDataSetChanged();
    }

    protected void SetLinkCourse(){

    }
}