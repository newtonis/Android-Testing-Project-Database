package com.gnd.calificaprofesores;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;


import com.gnd.calificaprofesores.AdapterClassFrontPage.AdapterClassFrontPage;

import com.gnd.calificaprofesores.R;

/*** Class front page: aqui adminsitramos la pagina principal de un curso. Tanto las diversas
 * opiniones como el puntaje ***/

/*** Se divide por el momento en cuatro tabs la informacion ***/
/* Vista general */
/* Opioniones */
/* Tu opinión */

/* Se mostrara siempre el puntaje en estrellas del curso */


public class ActivityClassFrontPageV2 extends AppCompatActivity {

    Toolbar toolbar;
    PagerSlidingTabStrip tabs;
    ViewPager pager;

    private AdapterClassFrontPage adapter;
    private Drawable oldBackground = null;
    private int currentColor;

    private static String CourseName;
    private static Long CourseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_front_page_v2);


        // CourseName
        // CourseId
        Intent intent = getIntent();
        CourseName = "Física I"; //intent.getStringExtra("CourseName");
        CourseId = 1L; //intent.getLongExtra("CourseId", 1L);

        TextView title = findViewById(R.id.ClassName);
        title.setText(CourseName);


        /// Tabs and pager managment

        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        setSupportActionBar(toolbar);
        adapter = new AdapterClassFrontPage(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(1);
        tabs.setViewPager(pager);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // esta función es para reaccionar cuando se cambia de tab

        /*switch (item.getItemId()) {
            case R.id.action_contact:
                QuickContactFragment.newInstance().show(getSupportFragmentManager(), "QuickContactFragment");
                return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}
