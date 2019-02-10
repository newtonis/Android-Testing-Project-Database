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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gnd.calificaprofesores.ListItems.BasicListItem;
import com.gnd.calificaprofesores.ListItems.ListItemViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ActivitySelectUni extends AppCompatActivity {
    private TextInputEditText mUniInput;
    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_uni);

        mUserDatabase = FirebaseDatabase.getInstance().getReference().getRef();


        mUniInput = findViewById(R.id.courseInput2);
        mResultList = findViewById(R.id.ResultList);

        mResultList.setLayoutManager(new LinearLayoutManager(this));


        mUniInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = mUniInput.getText().toString();

                firebaseUniSearch(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private FirebaseRecyclerOptions<BasicListItem> SearchAndMakeList(Query firebaseSearchQuery){
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
    }
    protected void firebaseUniSearch(String searchText){
        /// hacemos queries por dos criterios y luego unimos
        Query firebaseSearchQuery1 = mUserDatabase
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
        };

        mResultList.setAdapter(adapter);
        adapter.startListening();
    }
}
