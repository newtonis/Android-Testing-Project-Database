package com.gnd.calificaprofesores;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gnd.calificaprofesores.ListItems.BasicListItem;
import com.gnd.calificaprofesores.ListItems.ListItemViewHolder;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseData;
import com.gnd.calificaprofesores.NetworkHandler.GotCourseInfoListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotCourseListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchCourseHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchUniHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
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
    private AdapterSearch adapterSearch;

    private SearchCourseHandler searchCourseHandler;
    private Long uniId; // identificacion de la univerisdad de la que se buscaran cursos
    private CourseCommentsDataManager courseDataManager;

    private ProgressWheel progressWheel;
    private ImageView sadIcon;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);

        /** Cargamos la universidad de la que buscaremos cursos **/
        Intent intent = getIntent();
        uniId = intent.getLongExtra("Uni",1L);
        searchCourseHandler = new SearchCourseHandler(uniId);
        ShownDataListed = new ArrayList<>();
        adapterSearch = new AdapterSearch(ShownDataListed);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("MateriasPorFacultad/"+String.valueOf(uniId));


        /*** Cargamos widgets **/
        mCourseInput = findViewById(R.id.courseInput);
        recyclerView = findViewById(R.id.ResultList);
        sadIcon = findViewById(R.id.SadFace);
        progressWheel = findViewById(R.id.LoadingIcon);


        /** Eventos **/
        recyclerView.setAdapter(adapterSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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

        searchCourseHandler.AddOnGotCourseListener(new GotCourseListener() {
            @Override
            public void onGotCourse(Set<CourseData> data) {
                if (data.isEmpty()){
                    SetNoResults();
                }else{
                    SetLoaded();
                }
                ShownDataListed.clear();

                for (final CourseData course : data){
                    course.SetClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ActivitySearchCourse.this, ActivityClassFrontPageV2.class);
                            intent.putExtra("CourseName",course.GetShownName());
                            intent.putExtra("CourseId",course.GetId());

                            startActivity(intent);
                        }
                    });

                    ShownDataListed.add(new UniData(
                            course.GetId(),
                            course.GetShownName(),
                            course.GetDetail()
                    ));
                }
                adapterSearch.notifyDataSetChanged();
            }
        });
    }
    public void firebaseClassSearch(String searchTextOriginal){
        searchCourseHandler.Search(searchTextOriginal);

        /*String searchText = searchTextOriginal.toLowerCase();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("Name").startAt(searchText).endAt(searchText + "\uf8ff");

        /** Conseguimos la informacion de la query de seachText **/
        /*FirebaseRecyclerOptions<BasicListItem> options =
                new FirebaseRecyclerOptions.Builder<BasicListItem>()
                        .setQuery(firebaseSearchQuery, new SnapshotParser<BasicListItem>() {
                            @NonNull
                            @Override
                            public BasicListItem parseSnapshot(@NonNull DataSnapshot snapshot) {

                                return new BasicListItem((String) snapshot.child("Name").getValue(),
                                        "",
                                        (Long)snapshot.child("id").getValue()
                                );

                            }
                        }).build();*/

        /** La colocamos en la lista de cosas **/
        /*adapter = new FirebaseRecyclerAdapter<BasicListItem,ListItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final ListItemViewHolder holder, int position, final BasicListItem model) {

                courseDataManager = new CourseCommentsDataManager(model.getId(),"");

                courseDataManager.AddOnGotCourseDataListener(model.getId(), new GotCourseInfoListener() {
                    @Override
                    public void onGotCourseInfo(CourseData course) {
                        holder.setDetails(getApplicationContext(), course.GetShownName(), course.GetDetail());
                    }

                    @Override
                    public void onFailed() {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    }
                });

                courseDataList.add(courseDataManager);

                final String courseName = model.getName();
                final Long courseId = model.getId();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivitySearchCourse.this, ActivityClassFrontPageV2.class);
                        intent.putExtra("CourseName",courseName);
                        intent.putExtra("CourseId",courseId);

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
        };

        mResultList.setAdapter(adapter);
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
    private void ClearListItems(){
        ShownDataListed.clear();
        adapterSearch.notifyDataSetChanged();
    }
}