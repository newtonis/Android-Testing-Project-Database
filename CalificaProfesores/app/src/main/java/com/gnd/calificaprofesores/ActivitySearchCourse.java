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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gnd.calificaprofesores.ListItems.BasicListItem;
import com.gnd.calificaprofesores.ListItems.ListItemViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by newtonis on 29/11/18.
 */

public class ActivitySearchCourse extends AppCompatActivity {
    private TextInputEditText mCourseInput;
    private ImageButton mSearchButton;
    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Materias");

        mCourseInput = findViewById(R.id.courseInput);

        mResultList = findViewById(R.id.ResultList);

        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mCourseInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = mCourseInput.getText().toString();
                firebaseClassSearch(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void firebaseClassSearch(String searchText){
        Query firebaseSearchQuery = mUserDatabase.orderByChild("Name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerOptions<BasicListItem> options =
                new FirebaseRecyclerOptions.Builder<BasicListItem>()
                        .setQuery(firebaseSearchQuery, new SnapshotParser<BasicListItem>() {
                            @NonNull
                            @Override
                            public BasicListItem parseSnapshot(@NonNull DataSnapshot snapshot) {
                                String facultad = (String)snapshot.child("Facultad").getValue();


                                return new BasicListItem((String)snapshot.child("Name").getValue(), facultad,Long.parseLong(snapshot.getKey()));
                            }
                        }).build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<BasicListItem,ListItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(ListItemViewHolder holder, int position, BasicListItem model) {
                holder.setDetails(getApplicationContext(), model.getName(), model.getDetail());
                /*final String professorName = model.getName();
                final Long professorId = model.getId();*/

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*Intent intent = new Intent(ActivityProfessorList.this, ActivityProfesorFrontPage.class);
                        intent.putExtra("ProfessorName",professorName);
                        intent.putExtra("ProfessorId",professorId);

                        startActivity(intent);*/
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
        adapter.startListening();

    }
}
