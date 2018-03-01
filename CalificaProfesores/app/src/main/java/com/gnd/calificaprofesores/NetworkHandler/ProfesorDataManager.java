package com.gnd.calificaprofesores.NetworkHandler;

import android.util.Log;

import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Created by newtonis on 22/02/18.
 */

public abstract class ProfesorDataManager {
    private DatabaseReference mDatabase;

    Map<Integer , MatData > auxData; /// tiene informoacion de materias indexada por ID
    String profName;
    int matCount;
    Long profId;

    public ProfesorDataManager(Long _profId) {
        profId = _profId;
        auxData = new TreeMap<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().getRef();

        mDatabase.child("Prof/" + Long.toString(profId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profName = (String) dataSnapshot.child("Name").getValue();

                //matCount = dataSnapshot.num
                for (final DataSnapshot postSnapshot : dataSnapshot.child("Mat").getChildren()) { // for each children
                    final Integer matId = Integer.parseInt(postSnapshot.getKey());
                    auxData.put(matId, new MatData());
                }

                getMatContent();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ProfesorDataManager.this.onCancelled(databaseError);
            }
        });
    }
    private void getMatContent(){
        for (Map.Entry<Integer,MatData> entry : auxData.entrySet()){
            final String idMat = Integer.toString(entry.getKey());
            final MatData content = entry.getValue();
            mDatabase.child("Materias/" + idMat).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("myTag", "Error couldn't get data");


                    /*content.AddProperty("CA",(Long) dataSnapshot.child("CA").getValue());
                    content.AddProperty("CE",(Long)dataSnapshot.child("CE").getValue());
                    content.AddProperty("A",(Long)dataSnapshot.child("A").getValue());
                    content.AddProperty("CNT",(Long)dataSnapshot.child("CNT").getValue());*/


                    content.setName((String)dataSnapshot.child("Name").getValue());
                    content.setFacultadId((Long)dataSnapshot.child("Facultad").getValue());

                    /// When all data has arrived
                    matCount ++;
                    if (matCount == auxData.size()){
                      //  getFacultades();
                        getScores();
                      //  processData();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    ProfesorDataManager.this.onCancelled(databaseError);
                }
            });
        }
    }
    public void getScores(){
        matCount = 0;
        for (Map.Entry<Integer,MatData> entry : auxData.entrySet()){
            final String idMat = Integer.toString(entry.getKey());
            final MatData content = entry.getValue();

            if (entry.getKey() == 0){
                Vector <String> keys = new Vector<>();
                keys.add("A");
                keys.add("CNT");
                keys.add("CA");
                keys.add("CE");

                for (Integer i = 0;i < keys.size();i++){
                    content.AddProperty(keys.get(i),0L);
                }
                matCount ++;
                if (matCount == auxData.size()){
                    processData();
                }
                continue;
            }
            String code = Long.toString(profId) + "_" + idMat;
            mDatabase.child("Puntajes/"+code).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Vector <String> keys = new Vector<>();
                    keys.add("A");
                    keys.add("CNT");
                    keys.add("CA");
                    keys.add("CE");

                    for (Integer i = 0;i < keys.size();i++){
                        content.AddProperty(keys.get(i),(Long)dataSnapshot.child(keys.get(i)).getValue());
                    }
                    matCount++;
                    if (matCount == auxData.size()){
                        processData();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void processData(){
        Map<String,MatData> ans = new TreeMap<>();

        for (Map.Entry<Integer,MatData> entry : auxData.entrySet()){
            ans.put( entry.getValue().getName() , entry.getValue() );
        }
        onGotProfData(profName , ans);
    }
    public abstract void onGotProfData(String name,Map<String , MatData > Mat);
    public abstract void onCancelled(DatabaseError databaseError);
}
