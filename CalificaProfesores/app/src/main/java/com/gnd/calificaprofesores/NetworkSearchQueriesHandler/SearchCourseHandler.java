package com.gnd.calificaprofesores.NetworkSearchQueriesHandler;

import android.bluetooth.le.AdvertiseData;
import android.support.annotation.NonNull;

import com.gnd.calificaprofesores.NetworkHandler.CourseData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class SearchCourseHandler {
    private DatabaseReference mDatabase;
    private GotCourseListener listener;
    private Set<CourseData> courses;
    private String uniId;
    private String uniName;


    public SearchCourseHandler(String _uniId, String _uniName){
        mDatabase = FirebaseDatabase.getInstance().getReference().getRef();
        courses = new TreeSet<>();
        uniId = _uniId;
        uniName = _uniName.toUpperCase();
        /*** nota si el usuareio tiene una universidad que no existe crashea ***/


       /*mDatabase.child("Facultades").child(uniId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            uniName = (String) dataSnapshot.child("Name").getValue();
                            uniName.toUpperCase();
                        }else{
                            uniName = "";
                            uniId = "";
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
    }

    public void Search(String text) {
        text.toLowerCase();
        courses.clear();


        if (uniId != "") {
            mDatabase
                    .child("MateriasPorFacultad")
                    .child(uniId)
                    .orderByChild("Name")
                    .startAt(text)
                    .endAt(text + "\uf8ff")
                    .limitToFirst(10)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            AddPackage(
                                    dataSnapshot,
                                    true
                            );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }else{
            mDatabase
                    .child("Materias")
                    .orderByChild("Name")
                    .startAt(text)
                    .endAt(text + "\uf8ff")
                    .limitToFirst(10)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            AddPackage(
                                    dataSnapshot,
                                    false
                            );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
    }
    public void AddPackage(@NonNull DataSnapshot dataSnapshot, boolean mode){
        courses.clear();
        for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
            if (mode) {
                courses.add(new CourseData(
                        postSnapshot.getKey(),
                        (String) postSnapshot.child("ShownName").getValue(),
                        uniName.toUpperCase()
                ));
            }else{
                courses.add(new CourseData(
                        postSnapshot.getKey(),
                        (String) postSnapshot.child("ShownName").getValue(),
                        (String) postSnapshot.child("FacultadName").getValue()
                ));
            }
        }

        listener.onGotCourse(courses);
    }
    public void AddOnGotCourseListener(GotCourseListener listener){
        this.listener = listener;
    }
    public String GetUniName(){
        return uniName;
    }
}
