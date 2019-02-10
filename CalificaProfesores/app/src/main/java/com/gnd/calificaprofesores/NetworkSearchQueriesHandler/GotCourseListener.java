package com.gnd.calificaprofesores.NetworkSearchQueriesHandler;

import com.gnd.calificaprofesores.NetworkHandler.CourseData;

import java.util.Set;

public interface GotCourseListener {
    void onGotCourse(Set<CourseData> data);
}
