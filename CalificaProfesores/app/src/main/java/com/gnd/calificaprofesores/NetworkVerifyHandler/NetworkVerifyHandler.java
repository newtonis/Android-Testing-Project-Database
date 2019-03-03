package com.gnd.calificaprofesores.NetworkVerifyHandler;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;

import com.gnd.calificaprofesores.NetworkHandler.GotUserExtraDataListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SmallLoadingData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.VerifyInfoData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NetworkVerifyHandler {
    private DatabaseReference database;
    private DoneActionListener doneActionListener = null;
    private GotRequestItemListener gotRequestItemListener = null;
    private ButtonSelectedListener buttonSelectedListener = null;

    private List<UserDataManager> users;

    public NetworkVerifyHandler(){
        database = FirebaseDatabase.getInstance().getReference();
        users = new ArrayList<>();

    }

    public void listenForUniRequests(){

        database
                .child("UniAddRequests")
                .limitToFirst(20)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                        for (final DataSnapshot userContent : dataSnapshot.getChildren()){
                            if (userContent.getKey().equals("user_id")){
                                continue;
                            }
                            for (final DataSnapshot postsnapshot : userContent.getChildren()) {
                                if (!postsnapshot.hasChild("timestamp")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("erase")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("uniCompleteName")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("uniShortName")) {
                                    continue;
                                }

                                UserDataManager userDataManager = new UserDataManager(userContent.getKey());

                                userDataManager
                                        .listenForUserProfileData(new GotUserExtraDataListener() {
                                            @Override
                                            public void gotExtraData(UserExtraData extraData) {
                                                addUniElement(postsnapshot, userContent.getKey(), extraData);
                                            }
                                        });

                                //users.add(userDataManager);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    public void listenForProfAddRequests(){
        database
                .child("ProfAddRequests")
                .limitToFirst(20)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                        for (final DataSnapshot userContent : dataSnapshot.getChildren()){
                            if (userContent.getKey().equals("user_id")){
                                continue;
                            }
                            for (final DataSnapshot postsnapshot : userContent.getChildren()) {
                                if (!postsnapshot.hasChild("conMaterias")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("profId")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("profName")) {
                                    continue;
                                }

                                UserDataManager userDataManager = new UserDataManager(userContent.getKey());

                                userDataManager
                                        .listenForUserProfileData(new GotUserExtraDataListener() {
                                            @Override
                                            public void gotExtraData(UserExtraData extraData) {
                                                addProfElement(postsnapshot, userContent.getKey(), extraData);
                                            }
                                        });

                                //users.add(userDataManager);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
    public void listenForCourseAddRequests(){

    }

    public void addUniElement(final DataSnapshot snapshot, final String key, UserExtraData extraData){
        String institucion = (String)snapshot.child("uniShortName").getValue();
        String sigla = (String)snapshot.child("uniCompleteName").getValue();
        long timestamp = (long) snapshot.child("timestamp").getValue();

        final VerifyInfoData data = new VerifyInfoData(
                "Institución Agregada",
                "El usuario <b>"+extraData.getShowName()+"</b> (uid="+key+
                        ") ha agregado la institución <b>" + institucion+"</b> ("+sigla+")",
                timestamp
        );

        data.setAcceptAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SmallLoadingData loading = new SmallLoadingData();
                if (buttonSelectedListener != null){
                    buttonSelectedListener.onButtonSelected(data, loading);
                }
                database
                        .child("UniAddRequests")
                        .child(key)
                        .child(snapshot.getKey())
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (doneActionListener != null) {
                            doneActionListener.onDoneAction("Institución aceptada", loading);
                        }
                    }
                });
            }
        });

        data.setRejectAction(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final SmallLoadingData loading = new SmallLoadingData();
                 if (buttonSelectedListener != null){
                     buttonSelectedListener.onButtonSelected(data, loading);
                 }
                 database
                         .child("UniAddRequests")
                         .child(key)
                         .child(snapshot.getKey())
                         .child("erase")
                         .setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         doneActionListener.onDoneAction("Institución removida", loading);
                     }
                 });
             }
         }
        );
        gotRequestItemListener.onGotRequestItemListener(data);

    }
    public void addProfElement(
            final DataSnapshot postsnapshot,
            final String key,
            UserExtraData extraData){

        String profName = (String)postsnapshot.child("profName").getValue();
        String profId = (String)postsnapshot.child("profId").getValue();
        String text;
        String title;

        StringBuilder materias = new StringBuilder();

        if (postsnapshot.hasChild("materias")){
            for (DataSnapshot materia : postsnapshot.child("materias").getChildren()){
                String nombre = (String)materia.child("nombre").getValue();
                String facultad = (String)materia.child("facultad").getValue();
                materias = materias.append( "\n - <b>" + nombre + "</b>" + " (" + facultad + ")</dd> \n");
            }
        }
        if (profId.equals("0")){
            text = "El usuario <b>" + extraData.getShowName() + "</b> ha agregado al profesor <b>"
            + profName + "</b> con las materias \n" + materias.toString();
            title = "Profesor agregado";
        }else{
            text = "El usuario " + extraData.getShowName() +
                    " queire agregarle al profesor <b>" + profName
                    + "</b> las materias \n" + materias.toString() ;

            title = "Materias agregadas a profesor";
        }
        long timestamp = (long) postsnapshot.child("timestamp").getValue();

        final VerifyInfoData data = new VerifyInfoData(
                title,
                text,
                timestamp
        );

        gotRequestItemListener.onGotRequestItemListener(data);
    }

    public DoneActionListener getDoneActionListener() {
        return doneActionListener;
    }

    public void setDoneActionListener(DoneActionListener doneActionListener) {
        this.doneActionListener = doneActionListener;
    }

    public GotRequestItemListener getGotRequestItemListener() {
        return gotRequestItemListener;
    }

    public void setGotRequestItemListener(GotRequestItemListener gotRequestItemListener) {
        this.gotRequestItemListener = gotRequestItemListener;
    }

    public ButtonSelectedListener getButtonSelectedListener() {
        return buttonSelectedListener;
    }

    public void setButtonSelectedListener(ButtonSelectedListener buttonSelectedListener) {
        this.buttonSelectedListener = buttonSelectedListener;
    }
}
