package com.gnd.calificaprofesores;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gnd.calificaprofesores.ListItems.BasicListItem;
import com.gnd.calificaprofesores.ListItems.ListItemViewHolder;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseData;
import com.gnd.calificaprofesores.NetworkHandler.GotCourseInfoListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotCourseListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchCourseHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchUniHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NoInfoData;
import com.gnd.calificaprofesores.SearchItem.AdapterSearch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by newtonis on 29/11/18.
 * Actividad para que el usuario seleccione un curso
 * se necesita como informacion del intent Uni: codigo de universidad
 */


public class ActivitySearchCourse extends AppCompatActivity {
    private TextInputEditText mCourseInput;
    private ImageButton mSearchButton;

    private RecyclerView recyclerView;
    private List<UniData> ShownDataListed;

    private DatabaseReference mUserDatabase;
    private Adapter adapter;

    private SearchCourseHandler searchCourseHandler;
    private String uniId, uniName; // identificacion de la univerisdad de la que se buscaran cursos
    private CourseCommentsDataManager courseDataManager;

    private ProgressWheel progressWheel;
    private ImageView sadIcon;

    private MenuManager menuManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);

        /** Cargamos la universidad de la que buscaremos cursos **/
        Intent intent = getIntent();
        uniId = intent.getStringExtra("Uni");
        uniName = intent.getStringExtra("UniName");

        searchCourseHandler = new SearchCourseHandler(uniId, uniName);
        ShownDataListed = new ArrayList<>();
        adapter = new Adapter();
        mUserDatabase = FirebaseDatabase.getInstance().getReference("MateriasPorFacultad/"+String.valueOf(uniId));


        /*** Cargamos widgets **/
        mCourseInput = findViewById(R.id.courseInput);
        recyclerView = findViewById(R.id.ResultList);
        sadIcon = findViewById(R.id.SadFace);
        progressWheel = findViewById(R.id.LoadingIcon);
        sadIcon.bringToFront();
        progressWheel.bringToFront();

        TextView uniText = findViewById(R.id.UniversityText);
        uniText.setText(intent.getStringExtra("UniName"));

        ConstraintLayout layoutUni = findViewById(R.id.SelectUniLayout);
        layoutUni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySearchCourse.this, ActivitySelectUni.class);
                intent.putExtra("forceSelect",true);
                startActivity(intent);
            }
        });

        /** Eventos **/
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /** Menu manager **/
        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

        mCourseInput.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View view,int keyCode, KeyEvent event){
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SetLoading(); // show loading icon
                    String searchText = mCourseInput.getText().toString();

                    firebaseClassSearch(searchText);
                    return true;
                }
                return false;
            }
        });

        final View.OnClickListener goToAddClassListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySearchCourse.this, ActivityAddClass.class);

                startActivity(intent);
            }
        };

        searchCourseHandler.AddOnGotCourseListener(new GotCourseListener() {
            @Override
            public void onGotCourse(Set<CourseData> data) {
                if (data.isEmpty()){
                    //SetNoResults();
                    //recyclerView
                    SetLoaded();
                    adapter.AddElement(new NoInfoData(
                            "NO FUE ENCONTRADA LA MATERIA",
                            "AGREGAR NUEVA MATERIA",
                            goToAddClassListener
                    ));
                }else{
                    SetLoaded();
                    adapter.clear();

                    for (final CourseData course : data){
                        course.SetClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ActivitySearchCourse.this, ActivityClassFrontPageV2.class);
                                intent.putExtra("CourseName",course.GetShownName());
                                intent.putExtra("CourseId",course.GetId());
                                intent.putExtra("UniName",course.GetDetail());
                                startActivity(intent);
                            }
                        });

                        UniData nuevo = new UniData(course);
                        nuevo.SetType(18);
                        adapter.AddElement(nuevo);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void firebaseClassSearch(String searchTextOriginal){

        searchCourseHandler.Search(searchTextOriginal.toLowerCase());
    }
    private void SetLoading(){
        progressWheel.setVisibility(View.VISIBLE);
        sadIcon.setVisibility(View.INVISIBLE);
        ClearListItems();
    }
    private void SetLoaded(){
        progressWheel.setVisibility(View.INVISIBLE);
        sadIcon.setVisibility(View.INVISIBLE);
        ClearListItems();
    }
    private void SetNoResults(){
        progressWheel.setVisibility(View.INVISIBLE);
        sadIcon.setVisibility(View.VISIBLE);
        ClearListItems();
    }
    private void ClearListItems(){
        adapter.clear();
        adapter.notifyDataSetChanged();
    }
}