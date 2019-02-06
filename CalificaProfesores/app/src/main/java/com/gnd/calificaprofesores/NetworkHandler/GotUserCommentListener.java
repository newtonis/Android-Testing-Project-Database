package com.gnd.calificaprofesores.NetworkHandler;

import com.gnd.calificaprofesores.OpinionItem.CourseComment;

public interface GotUserCommentListener{
    void GotUserComment(boolean hasCommented, CourseComment comment);
    void GotUserExtraData(UserExtraData extraData);
}