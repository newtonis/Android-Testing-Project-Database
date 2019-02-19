package com.gnd.calificaprofesores.NetworkHandler;

import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public interface GotCommentListener {
    void onGotComment(List<CourseComment> comments);
    void onCancelled(DatabaseError databaseError);
}
