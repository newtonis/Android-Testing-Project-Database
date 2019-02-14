package com.gnd.calificaprofesores.NetworkHandler;

import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;

import java.util.List;

public interface GotUserProfCommentListener {
    void onGotUserProfComments(boolean hasCommented, UserProfComment comment);
}
