package com.gnd.calificaprofesores;

/** Aqui presentamos el buscador de universidades **/

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gnd.calificaprofesores.ListItems.BasicListItem;
import com.gnd.calificaprofesores.ListItems.ListItemViewHolder;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotUniListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchUniHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.OpinionItem.AdapterCourseComments;
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

public class ActivitySelectUni extends AppCompatActivity {
    private TextInputEditText mUniInput;
    private RecyclerView mResultList;

    private SearchUniHandler searchUniHandler;
    private AdapterSearch adapterSearch;
    RecyclerView recyclerView;

    private List<UniData> ShownDataListed;
    private ProgressWheel progressWheel;
    private ImageView sadIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_uni);

        ShownDataListed = new ArrayList<>();
        searchUniHandler = new SearchUniHandler();
        adapterSearch = new AdapterSearch(ShownDataListed);


        /*** Cargamos widgets **/
        mUniInput = findViewById(R.id.courseInput2);
        recyclerView = findViewById(R.id.ResultList);

        sadIcon = findViewById(R.id.SadFace);
        progressWheel = findViewById(R.id.LoadingIcon);
        sadIcon.bringToFront();
        progressWheel.bringToFront();

        /** Recycler burocracia **/
        recyclerView.setAdapter(adapterSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
                    SetNoResults();
                }else{
                    SetLoaded();
                }
                /// Convertimos el set a list
                ShownDataListed.clear();
                ShownDataListed.addAll(data);

                for (final UniData item : data){
                    item.SetClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ActivitySelectUni.this, ActivitySearchCourse.class);
                            intent.putExtra("Uni",item.GetId());

                            startActivity(intent);
                        }
                    });
                }
                adapterSearch.notifyDataSetChanged();
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

        searchUniHandler.Search(searchText);

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
        adapterSearch.notifyDataSetChanged();
    }
}
