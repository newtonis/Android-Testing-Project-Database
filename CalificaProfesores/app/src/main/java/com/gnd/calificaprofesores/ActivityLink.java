package com.gnd.calificaprofesores;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.IntentsManager.IntentProfManager;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkAdd.AddClassHandler;
import com.gnd.calificaprofesores.NetworkAdd.AddProfessorHandler;
import com.gnd.calificaprofesores.NetworkAdd.ClassAddedListener;
import com.gnd.calificaprofesores.NetworkAdd.CompleteClassData;
import com.gnd.calificaprofesores.NetworkAdd.CompleteProfData;
import com.gnd.calificaprofesores.NetworkAdd.ProfessorAddedListener;
import com.gnd.calificaprofesores.NetworkAdd.SmallMateriaData;
import com.gnd.calificaprofesores.NetworkHandler.CourseData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotCourseListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotProfListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.ProfData;
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
    private View.OnClickListener goToAddProfesorListener;

    private AddProfessorHandler addProfessorHandler;
    private AddClassHandler addClassHandler;
    private MiniSearchData miniSearchData;
    private MenuManager menuManager;

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

        goToAddProfesorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ActivityLink.this,
                        ActivityAddProf.class
                );
                startActivity(intent);
            }
        };

        searchCourseHandler = new SearchCourseHandler("", "");
        searchProfHandler = new SearchProfHandler();

        intent = getIntent();
        String mode = intent.getStringExtra("Mode");

       //SetLinkProf();
        //SetLinkCourse();
        if (mode.equals("Prof")){
            SetLinkProf();
        }else if(mode.equals("Course")){
            SetLinkCourse();
        }

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));
    }

    protected void SetLinkProf(){
        final IntentProfManager intentProfManager = new IntentProfManager(
                getIntent()
        ); // para modificar
        //intentProfManager.setProfUni("ITBA");
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

        miniSearchData = new MiniSearchData(
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
                    Toast.makeText(
                            ActivityLink.this,
                            "Debes agregar al menos una materia",
                            Toast.LENGTH_SHORT).show();
                }else {
                    addProfessorHandler.addProfessor(new CompleteProfData(
                            intentProfManager.GetProfName(),
                            intentProfManager.GetProfId(),
                            false,
                            new TreeMap<String, String>(),
                            materias,
                            false
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

                goToNovedades();
            }
        });

        adapter.AddElement(button);

        adapter.notifyDataSetChanged();
    }

    protected void SetLinkCourse(){
        final IntentCourseManager intentCourseManager = new IntentCourseManager(
            getIntent()
        );
        //intentCourseManager.setCourseId("-LZeDZAkJt2Gxkk1J2Bf");
        //intentCourseManager.setCourseName("lengua");

        String CourseName = intentCourseManager.GetCourseName();
        String CourseId = intentCourseManager.GetCourseId();
        String CourseUni = intentCourseManager.getUniName();

        UniData data = new UniData(
                CourseId,
                CourseName,
                CourseUni
        );

        data.SetType(18);

        adapter.AddElement(data);

        adapter.AddElement(new TitleData("AGREGAR PROFESORES"));

        miniSearchData = new MiniSearchData(
                "Materia ...",
                "",
                false,
                -1L,
                new SearchCalledListener() {
                    @Override
                    public void onSearchCalled(String text) {
                        searchProfHandler.Search(text);
                    }
                }
        );

        adapter.AddElement(miniSearchData);

        searchProfHandler.AddOnGotProfListener(new GotProfListener() {
            @Override
            public void onGotProf(Set<ProfData> prof) {
                List<UniData> convertedData = new ArrayList<>();

                for (ProfData item : prof){
                    convertedData.add(new UniData(item));
                }

                miniSearchData.SearchResults(convertedData);

                if (convertedData.size() == 0){
                    miniSearchData.AddElement(new NoInfoData(
                            "NO FUE ENCONTRADA EL PROFESOR",
                            "AGREGAR PROFESOR",
                            goToAddProfesorListener
                    ));
                }

            }
        });

        button = new ButtonData(
                "ENVIAR"
        );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> prof = new TreeMap<>();

                for (UniData element : miniSearchData.getElementSet()){
                    prof.put(
                            element.GetId(),
                            element.GetUniShortName()
                    );
                }

                addClassHandler.addClass(new CompleteClassData(
                        intentCourseManager.GetCourseName(),
                        "",
                        intentCourseManager.getUniName(),
                        intentCourseManager.GetCourseId(),
                        prof
                ));

                adapter.removeElement(button);
                adapter.AddElement(new SmallLoadingData());
            }
        });

        adapter.AddElement(button);


        addClassHandler.setClassAddedListener(new ClassAddedListener() {
            @Override
            public void onClassAdded() {
                Toast.makeText(ActivityLink.this, "El profesor será enlazado en unos momentos",
                        Toast.LENGTH_SHORT).show();
                goToNovedades();
            }
        });

        adapter.notifyDataSetChanged();
    }
    protected void goToNovedades(){
        Intent intent = new Intent(
                ActivityLink.this,
                ActivityUser.class
        );
        startActivity(intent);
    }
}
