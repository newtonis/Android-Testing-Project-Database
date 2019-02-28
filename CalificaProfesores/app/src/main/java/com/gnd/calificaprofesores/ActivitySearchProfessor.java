package com.gnd.calificaprofesores;

/** Esta actividad es search professor, el nombre es equivocado **/

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.IntentsManager.IntentProfManager;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotProfListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.ProfData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchProfHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;

import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NoInfoData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Layout asociado activity_search_professor.xml **/
/** layout items: search_list_element.xml **/

public class ActivitySearchProfessor extends AppCompatActivity {

    private TextInputEditText mProfInput;
    private RecyclerView mResultList;
    private List<UniData> ShownDataListed;

    private DatabaseReference mUserDatabase;

    private ProgressWheel progressWheel;
    private ImageView sadIcon;

    private Adapter adapter;

    private MenuManager menuManager;
    private SearchProfHandler searchProfHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_professor);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Prof");
        searchProfHandler = new SearchProfHandler();

        ShownDataListed = new ArrayList<>();
        adapter = new Adapter();


        /** Cargamos widgets **/
        mProfInput = findViewById(R.id.ProfInput);
        mResultList = findViewById(R.id.ResultList);
        sadIcon = findViewById(R.id.SadFace);
        progressWheel = findViewById(R.id.LoadingIcon);

        mResultList.setAdapter(adapter);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        mProfInput.setOnKeyListener(

                new View.OnKeyListener(){
            public boolean onKey(View view,int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SetLoading();
                    String searchText = mProfInput.getText().toString();
                    firebaseProfSearch(searchText);
                    return true;
                }
                return false;
            }
        });
        final View.OnClickListener goToAddProfessorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySearchProfessor.this, ActivityAddProf.class);
                startActivity(intent);
            }
        };
        searchProfHandler.AddOnGotProfListener(new GotProfListener() {
            @Override
            public void onGotProf(Set<ProfData> data) {
                if (data.isEmpty()){
                    SetLoaded();
                    adapter.clear();
                    adapter.AddElement(new NoInfoData(
                            "NO FUE ENCONTRADO EL/LA PROFESOR/A",
                            "AGREGAR NUEVO PROFESOR/A",
                            goToAddProfessorListener
                    ));
                }else{
                    SetLoaded();
                    adapter.clear();
                    for (final ProfData prof : data){
                        prof.SetClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IntentProfManager intent = new IntentProfManager(
                                        new Intent(
                                                ActivitySearchProfessor.this,
                                                ActivityProfFrontPageV2.class),
                                        prof.GetName(),
                                        prof.GetId()
                                );
                                startActivity(intent.GetIntent());
                            }
                        });

                        UniData nuevo = new UniData(
                                prof.GetId(),
                                prof.GetName(),
                                prof.GetDetails()
                        );
                        nuevo.SetType(18);

                        nuevo.SetClickListener(prof.GetClickListener());
                        adapter.AddElement(nuevo);
                    }
                }


                adapter.notifyDataSetChanged();
            }
        });

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));
    }

    protected void firebaseProfSearch(String searchText){
        searchProfHandler.Search(searchText);
    }
    private void SetLoading(){
        progressWheel.setVisibility(View.VISIBLE);
        sadIcon.setVisibility(View.INVISIBLE);
        mResultList.setVisibility(View.INVISIBLE);
        ClearListItems();
    }
    private void SetLoaded(){
        progressWheel.setVisibility(View.INVISIBLE);
        sadIcon.setVisibility(View.INVISIBLE);
        mResultList.setVisibility(View.VISIBLE);
        ClearListItems();
    }
    private void SetNoResults(){
        progressWheel.setVisibility(View.INVISIBLE);
        sadIcon.setVisibility(View.VISIBLE);
        mResultList.setVisibility(View.INVISIBLE);
        ClearListItems();
    }
    private void ClearListItems(){
        ShownDataListed.clear();
        adapter.notifyDataSetChanged();
    }
}
