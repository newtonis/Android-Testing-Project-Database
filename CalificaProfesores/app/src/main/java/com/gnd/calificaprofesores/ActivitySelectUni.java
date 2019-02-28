package com.gnd.calificaprofesores;

/** Aqui presentamos el buscador de universidades **/

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.balysv.materialmenu.MaterialMenuView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gnd.calificaprofesores.ListItems.BasicListItem;
import com.gnd.calificaprofesores.ListItems.ListItemViewHolder;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkHandler.GotUserExtraDataListener;
import com.gnd.calificaprofesores.NetworkHandler.SentUniDataListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotUniListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchUniHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.OpinionItem.AdapterCourseComments;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NoInfoData;
import com.gnd.calificaprofesores.SearchItem.AdapterSearch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static android.support.test.InstrumentationRegistry.getContext;

/** activity_select_uni.xml **/

public class ActivitySelectUni extends AppCompatActivity {
    private TextInputEditText mUniInput;
    private RecyclerView mResultList;

    private SearchUniHandler searchUniHandler;
    private Adapter adapter;
    RecyclerView recyclerView;

    private List<UniData> ShownDataListed;
    private ProgressWheel progressWheel;
    private ImageView sadIcon;
    private UserDataManager userDataManager;
    private MenuManager menuManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_uni);

        Intent intent = getIntent();
        ShownDataListed = new ArrayList<>();
        searchUniHandler = new SearchUniHandler();
        adapter = new Adapter();
        userDataManager = new UserDataManager();

        /*** Cargamos widgets **/
        mUniInput = findViewById(R.id.courseInput2);
        recyclerView = findViewById(R.id.ResultList);

        sadIcon = findViewById(R.id.SadFace);
        progressWheel = findViewById(R.id.LoadingIcon);
        sadIcon.bringToFront();
        progressWheel.bringToFront();

        /** Recycler burocracia **/
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /** Menu manager **/
        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

        /** Eventos **/
        mUniInput.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View view,int keyCode, KeyEvent event){
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SetLoading();
                    String searchText = mUniInput.getText().toString();

                    firebaseUniSearch(searchText);
                    return true;
                }
                return false;
            }
        });

        searchUniHandler.AddOnGetUniListener(new GotUniListener() {
            @Override
            public void onGotUni(Set<UniData> data) {
                if (data.isEmpty()){
                    SetLoaded();
                    adapter.clear();
                    adapter.AddElement(new NoInfoData(
                            "INSTITUCIÓN NO ENCONTRADA",
                            "AGREGAR INSTITUCIÓN",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(
                                            ActivitySelectUni.this,
                                            ActivityAddUni.class
                                    );
                                    startActivity(intent);
                                }
                            }
                    ));
                }else{
                    SetLoaded();
                    adapter.clear();
                    for (final UniData item : data) {
                        item.SetClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SetLoading();

                                selectUni(item.GetId(), item.GetUniShortName());

                            }
                        });
                        item.SetType(18);
                        adapter.AddElement(item);
                    }
                }
                /// Convertimos el set a list



                adapter.notifyDataSetChanged();
            }
        });

        if (!intent.getBooleanExtra("forceSelect",false)) {
            userDataManager.listenForUserProfileData();
            userDataManager.setmGotUserExtraDataListener(new GotUserExtraDataListener() {
                @Override
                public void gotExtraData(UserExtraData extraData) {
                    if (extraData.getUniId() != "") {
                        Intent intent = new Intent(
                                ActivitySelectUni.this,
                                ActivitySearchCourse.class
                        );
                        intent.putExtra("Uni", extraData.getUniId());
                        intent.putExtra("UniName", extraData.getUniName());

                        startActivity(intent);
                    }
                }
            });
        }

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

    }
    protected void selectUni(final String uniId,final String uniShortName){
        userDataManager.setUni(uniShortName, uniId);

        userDataManager.setSentUniDataListener(new SentUniDataListener() {
            @Override
            public void onSentUni() {
                Intent intent = new Intent(ActivitySelectUni.this, ActivitySearchCourse.class);
                intent.putExtra("Uni", uniId);
                intent.putExtra("UniName", uniShortName);

                startActivity(intent);
            }
        });

    }
    /*private FirebaseRecyclerOptions<BasicListItem> SearchAndMakeList(Query firebaseSearchQuery){
        return new FirebaseRecyclerOptions.Builder<BasicListItem>()
                .setQuery(firebaseSearchQuery, new SnapshotParser<BasicListItem>(){
                    @NonNull
                    @Override
                    public BasicListItem parseSnapshot(DataSnapshot snapshot) {
                        String details = "";
                        for (final DataSnapshot postSnapshot : snapshot.child("CompleteName").getChildren()) {
                            details += (String)postSnapshot.getValue();
                            details += "   ";
                        }
                        return new BasicListItem((String)snapshot.child("Name").getValue(),
                                (String)snapshot.child("CompleteName").getValue() ,
                                Long.parseLong(snapshot.getKey()));
                    }
                })
                .build();
    }*/

    protected void firebaseUniSearch(String searchText){

        searchUniHandler.Search(searchText.toLowerCase());

        /// hacemos queries por dos criterios y luego unimos
        /*Query firebaseSearchQuery1 = mUserDatabase
                .child("Facultades")
                .orderByChild("CompleteName")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff")
                .limitToFirst(10);

        Query firebaseSearchQuery2 = mUserDatabase
                .child("Facultades")
                .orderByChild("Name")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff")
                .limitToFirst(10);


        FirebaseRecyclerOptions<BasicListItem> options1 = SearchAndMakeList(firebaseSearchQuery1);
        FirebaseRecyclerOptions<BasicListItem> options2 = SearchAndMakeList(firebaseSearchQuery2);

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<BasicListItem, ListItemViewHolder>(options1) {
            @Override
            protected void onBindViewHolder(ListItemViewHolder holder, int position, BasicListItem model) {
                holder.setDetails(getApplicationContext(), model.getName(),model.getDetail());
                final Long uniId = model.getId();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivitySelectUni.this, ActivitySearchCourse.class);
                        intent.putExtra("Uni",uniId);

                        startActivity(intent);
                    }
                });
            }

            @Override
            public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_list_element, parent, false);

                return new ListItemViewHolder(view);
            }
        };*/

        /*mResultList.setAdapter(adapter);
        adapter.startListening();*/


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
    /** para hacer que se borren las opciones mostradas **/
    private void ClearListItems(){
        ShownDataListed.clear();
        adapter.notifyDataSetChanged();
    }
}
