package com.gnd.calificaprofesores.IntentsManager;

import android.content.Context;
import android.content.Intent;

/** Aqui vamos a organizar los intents para que sea mas facil usarlos**/

public class IntentCourseManager {
    String CourseName;
    Long CourseId;
    Intent myIntent;
    Context ctx;
    Class cls;

    /** A partir de un Intent generamos la clase **/
    public IntentCourseManager(Intent intent){
        CourseName = intent.getStringExtra("CourseName");
        CourseId = intent.getLongExtra("CourseId", 1L);
    }
    /** A partir de course Name y Course Id generamos la clase **/
    public IntentCourseManager(String _CourseName, Long _CourseId, Context _ctx, Class _cls){
        this.ctx = _ctx;
        this.cls = _cls;
        this.CourseName = _CourseName;
        this.CourseId = _CourseId;

        myIntent = new Intent(ctx, cls);
        myIntent.putExtra("CourseName",_CourseName);
        myIntent.putExtra("CourseId",_CourseId);
    }
    /** Default builder **/
    public IntentCourseManager(){
        CourseName = "Física I";
        CourseId = 1L;
    }
    public Intent GetIntent(){
        return myIntent;
    }
    public String GetCourseName() {
        return CourseName;
    }
    public Long GetCourseId() {
        return CourseId;
    }
    public IntentCourseManager ConvertIntent(Context _ctx, Class _cls) {
        return new IntentCourseManager(
                CourseName, CourseId, _ctx, _cls
        );
    }
}
