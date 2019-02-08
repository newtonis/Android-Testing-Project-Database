package com.gnd.calificaprofesores;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;


import com.gnd.calificaprofesores.AdapterClassFrontPage.AdapterClassFrontPage;

import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.R;

/*** Class front page: aqui adminsitramos la pagina principal de un curso. Tanto las diversas
 * opiniones como el puntaje ***/

/*** Se divide por el momento en cuatro tabs la informacion ***/
/* Vista general */
/* Opioniones */
/* Tu opinión */

/* Se mostrara siempre el puntaje en estrellas del curso */


public class ActivityClassFrontPageV2 extends AppCompatActivity {

    PagerSlidingTabStrip tabs;
    ViewPager pager;

    private AdapterClassFrontPage adapter;
    private Drawable oldBackground = null;
    private int currentColor;

    private static String CourseName;
    private static Long CourseId;

    private Toolbar toolbar;
    /// Controlador del panel menu para hacer distintas cosas
   // MenuManager mMenuManager;

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_front_page_v2);

        toolbar = findViewById(R.id.Toolbar);
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

        //View mView = getWindow().getDecorView().findViewById(android.R.id.content);
        /// Inicializamo el panel para hacer cosas
        //mMenuManager = new MenuManager(mView, this);

        //getSupportActionBar().setTitle("Hola");
        //mPlanetTitles = getResources().getStringArray(R.array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.DrawerOpen,R.string.DrawerClose){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle.setDrawerIndicatorEnabled(true);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
