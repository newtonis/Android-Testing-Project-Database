package com.gnd.calificaprofesores;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gnd.calificaprofesores.ListItems.BasicListItem;
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

        /*FirebaseRecyclerOptions<BasicListItem> options =
                new FirebaseRecyclerOptions.Builder<BasicListItem>()
                        .setQuery(firebaseSearchQuery, new SnapshotParser<BasicListItem>() {
                            @NonNull
                            @Override
                            public BasicListItem parseSnapshot(@NonNull DataSnapshot snapshot) {
                                String details = "";

                            }
                        });*/
    }
}
