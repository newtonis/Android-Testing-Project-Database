package com.gnd.calificaprofesores.NetworkHandler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public interface SentCommentListener {
    void onSentComment();
    void onFailedComment(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference);
}
