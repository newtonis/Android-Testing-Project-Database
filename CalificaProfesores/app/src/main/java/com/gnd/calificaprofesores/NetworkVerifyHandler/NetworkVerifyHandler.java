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
                .orderByChild("timestamp")
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
                .orderByChild("timestamp")
                .limitToFirst(20)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                        for (final DataSnapshot userContent : dataSnapshot.getChildren()){
                            if (userContent.getKey().equals("user_id")){
                                continue;
                            }
                            for (final DataSnapshot postsnapshot : userContent.getChildren()) {
                                if (!postsnapshot.hasChild("profId")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("profName")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("erase")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("create")) {
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
        database
                .child("ClassAddRequests")
                .limitToFirst(20)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                        for (final DataSnapshot userContent : dataSnapshot.getChildren()){
                            if (userContent.getKey().equals("user_id")){
                                continue;
                            }
                            for (final DataSnapshot postsnapshot : userContent.getChildren()) {
                                if (!postsnapshot.hasChild("classId")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("facultadId")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("facultadName")) {
                                    continue;
                                }
                                if (!postsnapshot.hasChild("name")){
                                    continue;
                                }
                                if (!postsnapshot.hasChild("timestamp")){
                                    continue;
                                }

                                UserDataManager userDataManager = new UserDataManager(userContent.getKey());

                                userDataManager
                                        .listenForUserProfileData(new GotUserExtraDataListener() {
                                            @Override
                                            public void gotExtraData(UserExtraData extraData) {
                                                addCourseElement(postsnapshot, userContent.getKey(), extraData);
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
    public void addProfElement(final DataSnapshot postsnapshot, final String key, UserExtraData extraData){

        String profName = (String)postsnapshot.child("profName").getValue();
        boolean create = (boolean)postsnapshot.child("create").getValue();

        String text;
        String title;

        StringBuilder materias = new StringBuilder();

        if (postsnapshot.hasChild("materias")){
            for (DataSnapshot materia : postsnapshot.child("materias").getChildren()){
                String nombre = (String)materia.child("nombre").getValue();
                String facultad = (String)materia.child("facultad").getValue();
                materias = materias.append( "<br> - <b>" + nombre + "</b>" + " (" + facultad + ")");
            }
        }
        if (create){
            text = "El usuario <b>" + extraData.getShowName() + "</b> ha agregado al profesor <b>"
            + profName + "</b> con las materias <br>" + materias.toString();
            title = "Profesor agregado";
        }else{
            text = "El usuario <b>" + extraData.getShowName() +
                    "</b> quiere agregarle al profesor <b>" + profName
                    + "</b> las materias \n" + materias.toString() ;

            title = "Materias agregadas a profesor";
        }
        long timestamp = (long) postsnapshot.child("timestamp").getValue();

        final VerifyInfoData data = new VerifyInfoData(
                title,
                text,
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
                        .child("ProfAddRequests")
                        .child(key)
                        .child(postsnapshot.getKey())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        doneActionListener.onDoneAction("Profesor aceptado", loading);
                    }
                });
            }
        });

        data.setRejectAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SmallLoadingData loading = new SmallLoadingData();
                if (buttonSelectedListener != null) {
                    buttonSelectedListener.onButtonSelected(data, loading);
                }
                database
                        .child("ProfAddRequests")
                        .child(key)
                        .child(postsnapshot.getKey())
                        .child("erase")
                        .setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        doneActionListener.onDoneAction("Profesor removido", loading);
                    }
                });
            }
        });

        gotRequestItemListener.onGotRequestItemListener(data);
    }
    public void addCourseElement(final DataSnapshot postsnapshot, final String key, UserExtraData extraData){

        String courseName = (String)postsnapshot.child("name").getValue();
        String facultadName = (String)postsnapshot.child("facultadName").getValue();
        String classId = (String)postsnapshot.child("classId").getValue();

        String text;
        String title;

        StringBuilder profesoresText = new StringBuilder();

        profesoresText = profesoresText.append("con los profesores");
        if (postsnapshot.hasChild("prof")){
            for (DataSnapshot child : postsnapshot.child("prof").getChildren()) {
                String nombre = (String)child.getValue();
                String profId = (String)child.getKey();

                profesoresText = profesoresText.append( "<br> - <b>" + nombre + "</b>" + " (" + profId + ")");
            }
        }

        if (classId.equals("0")){
            if (profesoresText.toString().equals("con los profesores")){
                profesoresText = new StringBuilder();
                profesoresText = profesoresText.append(" sin profesores");
            }
            text = "El usuario <b>" + extraData.getShowName() + "</b> ha agregado la materia <b>"
                    + courseName + "</b> de la institución <b>" + facultadName + "</b> "
                    + profesoresText;
            title = "Curso agregado";
        }else{
            if (profesoresText.toString().equals("con los profesores")){
                profesoresText = new StringBuilder();
                profesoresText = profesoresText.append(" sin profesores");
            }
            text = "El usuario " + extraData.getShowName() +
                    " le ha agregado a la materia <b>" + courseName + "</b> de la institución <b>" + facultadName
                    + "</b> a la <b>" + facultadName + "</b>" +
                    profesoresText;

            title = "Profesores agregados al curso";
        }
        long timestamp = (long) postsnapshot.child("timestamp").getValue();

        final VerifyInfoData data = new VerifyInfoData(
                title,
                text,
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
                        .child("ClassAddRequests")
                        .child(key)
                        .child(postsnapshot.getKey())
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        doneActionListener.onDoneAction("Curso aceptado", loading);
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
                        .child("ClassAddRequests")
                        .child(key)
                        .child(postsnapshot.getKey())
                        .child("erase")
                        .setValue(true)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        doneActionListener.onDoneAction("Curso removido", loading);
                    }
                });
            }
        });

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
