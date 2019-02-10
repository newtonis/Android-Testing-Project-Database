package com.gnd.calificaprofesores.NetworkHandler;

public interface GotCourseInfoListener {
    void onGotCourseInfo(CourseData course);
    void onFailed();
}
