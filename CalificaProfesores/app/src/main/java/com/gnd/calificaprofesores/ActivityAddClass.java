package com.gnd.calificaprofesores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkAdd.AddClassHandler;
import com.gnd.calificaprofesores.NetworkAdd.AddProfessorHandler;
import com.gnd.calificaprofesores.NetworkAdd.ClassAddedListener;
import com.gnd.calificaprofesores.NetworkAdd.CompleteClassData;
import com.gnd.calificaprofesores.NetworkHandler.CourseData;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManagerInstance;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraData;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraDataInstance;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotProfListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotUniListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.ProfData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchCourseHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchProfHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchUniHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.AdapterElement;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.InputLineTextData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.MiniSearchData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NoInfoData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SearchCalledListener;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SmallLoadingData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;
import com.google.zxing.client.result.AddressBookAUResultParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ActivityAddClass extends AppCompatActivity {
    private Adapter adapter;
    private MenuManager menuManager;
    private SearchProfHandler searchProfHandler;
    private SearchUniHandler searchUniHandler;

    private RecyclerView recyclerView;
    private InputLineTextData courseInput;
    private ButtonData button;
    private AddClassHandler addClassHandler;
    private TitleData titleData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        adapter = new Adapter();
        searchProfHandler = new SearchProfHandler();
        searchUniHandler = new SearchUniHandler();
        addClassHandler = new AddClassHandler();

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        courseInput = new InputLineTextData("Materia ...","Nombre de la materia");
        //courseInput.clear();

        Intent intent = getIntent();

        if (intent.hasExtra("className")){
            String className = intent.getStringExtra("className");
            courseInput.setDefaultText(className);
        }

        adapter.AddElement(courseInput);
        titleData = new TitleData("SELECCIONAR FACULTAD");


        adapter.AddElement(titleData);

        final MiniSearchData miniSearchData = new MiniSearchData(
                "Facultad ...",
                "",
                false,
                1L,
                new SearchCalledListener() {
            @Override
            public void onSearchCalled(String text) {
                searchUniHandler.Search(text.toLowerCase());
            }
        });
        UserExtraData userExtraData = UserExtraDataInstance.getInstance();



        searchUniHandler.AddOnGetUniListener(new GotUniListener() {
            @Override
            public void onGotUni(Set<UniData> data) {
                List<UniData> convertedData = new ArrayList<>();
                for (UniData item : data){
                    convertedData.add(item);
                }
                miniSearchData.SearchResults(convertedData);
                if (convertedData.size() == 0){
                    miniSearchData.AddElement(new NoInfoData(
                            "NO SE ENCONTRO LA INSTITUCION",
                            "AGREGAR INSTITUCIÓN",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(
                                            ActivityAddClass.this,
                                            ActivityAddUni.class
                                    );
                                    startActivity(intent);
                                }
                            }
                    ));
                    miniSearchData.notifyDataSetChanged();
                }
            }
        });

        adapter.AddElement(miniSearchData);

        adapter.AddElement(new TitleData("SELECCIONAR PROFESORES"));

        final MiniSearchData miniSearchData2 = new MiniSearchData(
                "Profesor ...",
                "AGREGAR PROFESORES DESPUÉS",
                true,
                -1L,
                new SearchCalledListener() {
                    @Override
                    public void onSearchCalled(String text) {
                        searchProfHandler.Search(text.toLowerCase());
                    }
                });

        miniSearchData2.setEnabled(false);

        searchProfHandler.AddOnGotProfListener(new GotProfListener() {
            @Override
            public void onGotProf(Set<ProfData> data) {
                List<UniData> convertedData = new ArrayList<>();
                for (ProfData item : data){
                    convertedData.add(new UniData(item));
                }
                miniSearchData2.SearchResults(convertedData);

                if (convertedData.size() == 0){
                    miniSearchData2.AddElement(new NoInfoData(
                            "NO SE ENCONTRO EL PROFESOR",
                            "AGREGAR PROFESOR",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(
                                            ActivityAddClass.this,
                                            ActivityAddProf.class
                                    );
                                    startActivity(intent);
                                }
                            }
                    ));
                    miniSearchData2.notifyDataSetChanged();
                }
            }
        });

        adapter.AddElement(miniSearchData2);

        button = new ButtonData("ENVIAR");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facultadId = "";
                String facultadName = "";
                Map<String, String> prof = new TreeMap<>();

                if (!miniSearchData.isAllowSwitch()){
                    if (miniSearchData.getElementSet().size() < 1){
                        Toast.makeText(ActivityAddClass.this,
                                "Debes seleccionar al menos una universidad",
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }else{
                        for (UniData facultad : miniSearchData.getElementSet()){
                            facultadId = facultad.GetId();
                            facultadName = facultad.GetUniShortName();
                        }
                    }
                }

                for (UniData item : miniSearchData2.getElementSet()){
                    prof.put(item.GetId(), item.GetUniShortName());
                }

                addClassHandler.addClass(new CompleteClassData(
                        courseInput.getText(),
                        facultadId,
                        facultadName,
                        "0",
                        prof
                ));

                adapter.removeElement(button);
                adapter.AddElement(new SmallLoadingData());

                adapter.notifyDataSetChanged();
            }
        });

        addClassHandler.setClassAddedListener(new ClassAddedListener() {
            @Override
            public void onClassAdded() {
                classAdded();
            }
        });
        adapter.AddElement(button);

        adapter.notifyDataSetChanged();

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

        if (!userExtraData.getUniName().equals("")){
            miniSearchData.addElementToSet2(
                    new UniData(
                            userExtraData.getUniId(),
                            userExtraData.getUniName(),
                            userExtraData.getUniCompleteName()
                    )
            );
            miniSearchData.notifyDataSetChanged();
        }
    }
    public void classAdded(){
        Toast.makeText(
                this,
                "La clase será agregada en unos instantes",
                Toast.LENGTH_SHORT
        ).show();
        Intent intent = new Intent(ActivityAddClass.this, ActivityUser.class);
        startActivity(intent);
    }
}
