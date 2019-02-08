package com.gnd.calificaprofesores;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.gnd.calificaprofesores.AdapterClassFrontPage.ActivityOpinionRecent;
import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.GotUserExtraDataListener;
import com.gnd.calificaprofesores.NetworkHandler.SentCommentListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraData;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class ActivityOpinarMateria extends AppCompatActivity {


    Button ButtonOpinar;
    EditText TextOpinion;
    RatingBar Rating;
    CourseCommentsDataManager mCourseCommentsDatamanager;
    FirebaseAuth mAuth;
    UserDataManager mUserDataManager;
    Toast toast;
    IntentCourseManager intentCourseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinar_materia);

        ButtonOpinar = (Button)findViewById(R.id.ButtonOpinar);
        TextOpinion = (EditText)findViewById(R.id.CommentInput);
        Rating = (RatingBar)findViewById(R.id.RatingBar);
        // Debe ser modificada esta linea
        intentCourseManager = new IntentCourseManager();

        ButtonOpinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendOpinion( TextOpinion.getText().toString() , (long)(Rating.getRating() * 2f) );
            }
        });

        mCourseCommentsDatamanager = new CourseCommentsDataManager(intentCourseManager.GetCourseId(),intentCourseManager.GetCourseName());
        mAuth = FirebaseAuth.getInstance();
        mUserDataManager = new UserDataManager();
    }
    private void SendOpinion(final String text, final Long calificacion){

        mUserDataManager.AddGotUserProfileData(mAuth.getUid(), new GotUserExtraDataListener() {
            @Override
            public void gotExtraData(UserExtraData extraData) {

                mCourseCommentsDatamanager.SendComment(
                        new CourseComment(extraData.GetShownName(), text, calificacion, 0L), new SentCommentListener() {
                            @Override
                            public void onSentComment() {
                                toast = Toast.makeText(getApplicationContext(), "Opinión enviada", Toast.LENGTH_SHORT);
                                toast.show();
                                startActivity(intentCourseManager.ConvertIntent(ActivityOpinarMateria.this, ActivityClassFrontPageV2.class ).GetIntent());
                            }

                            @Override
                            public void onFailedComment(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                toast = Toast.makeText(getApplicationContext(), "Error enviando opinión ", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
            }
        });

    }
}
