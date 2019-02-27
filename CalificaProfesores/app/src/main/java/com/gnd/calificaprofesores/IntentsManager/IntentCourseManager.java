package com.gnd.calificaprofesores.IntentsManager;

import android.content.Context;
import android.content.Intent;

/** Aqui vamos a organizar los intents para que sea mas facil usarlos**/

public class IntentCourseManager {
    String CourseName;
    String CourseId;
    String UniName;

    Intent myIntent;
    Context ctx;
    Class cls;

    /** A partir de un Intent generamos la clase **/
    public IntentCourseManager(Intent intent){
        CourseName = intent.getStringExtra("CourseName");
        CourseId = intent.getStringExtra("CourseId");
        UniName = intent.getStringExtra("UniName");
        this.myIntent = intent;
    }

    public String getUniName() {
        return UniName;
    }

    public void setUniName(String uniName) {
        UniName = uniName;
        myIntent.putExtra("UniName", uniName);
    }

    /** A partir de course Name y Course Id generamos la clase **/
    public IntentCourseManager(String _CourseName, String _CourseId, Context _ctx, Class _cls){
        this.ctx = _ctx;
        this.cls = _cls;
        this.CourseName = _CourseName;
        this.CourseId = _CourseId;

        myIntent = new Intent(ctx, cls);
        myIntent.putExtra("CourseName",_CourseName);
        myIntent.putExtra("CourseId",_CourseId);
        myIntent.putExtra("Mode", "Course");
    }
    /** Default builder **/
    public IntentCourseManager(){
        CourseName = "Física I";
        CourseId = "";
        UniName = "ITBA";
    }
    public Intent GetIntent(){
        return myIntent;
    }
    public String GetCourseName() {
        return CourseName;
    }
    public String GetCourseId() {
        return CourseId;
    }
    public IntentCourseManager ConvertIntent(Context _ctx, Class _cls) {
        return new IntentCourseManager(
                CourseName, CourseId, _ctx, _cls
        );
    }
}
