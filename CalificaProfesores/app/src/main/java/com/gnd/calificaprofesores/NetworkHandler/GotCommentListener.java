package com.gnd.calificaprofesores.NetworkHandler;

import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.google.firebase.database.DatabaseError;

public interface GotCommentListener {
    void onGotComment(CourseComment comment);
    void onCancelled(DatabaseError databaseError);
}
