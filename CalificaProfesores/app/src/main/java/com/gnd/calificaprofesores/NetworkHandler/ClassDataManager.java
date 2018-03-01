package com.gnd.calificaprofesores.NetworkHandler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Created by newtonis on 28/02/18.
 */

public abstract class ClassDataManager {
    Map<Long , ProfData> auxData;
    private DatabaseReference mDatabase;
    private long classId;
    private long cntProcess; // cuenta de informacion conseguida de los campos necesarios
    public ClassDataManager(Long _classId){
        classId = _classId;

        auxData = new TreeMap<>();

        mDatabase = FirebaseDatabase.getInstance().getReference( ).getRef();
        cntProcess = 0;

        mDatabase.child("Materias/"+Long.toString(classId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.child("Prof").getChildren()) { // for each children
                    final Long profId = Long.parseLong(postSnapshot.getKey());
                    final String profName = (String)postSnapshot.getValue();
                    auxData.put(profId, new ProfData(profId,profName));
                }
                getProfContent();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void getProfContent(){
        //mDatabase = FirebaseDatabase.getInstance().getReference("Puntajes");
        for (final Map.Entry<Long,ProfData> entry : auxData.entrySet()){
            final ProfData content = entry.getValue();
            String profData = Long.toString(classId) +"_" + Long.toString(content.getId());
            mDatabase.child("Puntajes").child(profData).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Long A = (Long)dataSnapshot.child("A").getValue();
                    Long CA = (Long)dataSnapshot.child("CA").getValue();
                    Long CE = (Long)dataSnapshot.child("CE").getValue();
                    Long CNT = (Long)dataSnapshot.child("CE").getValue();
                    //entry.getValue().addProperty("A", );


                    Vector<String> keys = new Vector<>();
                    keys.add("A");
                    keys.add("CNT");
                    keys.add("CA");
                    keys.add("CE");

                    for (Integer i = 0;i < keys.size();i++){
                        content.addProperty(keys.get(i),(Long)dataSnapshot.child(keys.get(i)).getValue());
                    }

                    cntProcess ++;

                    if (cntProcess == auxData.size()){
                        onGotClassData(auxData);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    onCancelled(databaseError);
                }
            });
        }
    }
    public abstract void onGotClassData(Map<Long , ProfData> data);
    public abstract void onCancelled(DatabaseError databaseError);

}
