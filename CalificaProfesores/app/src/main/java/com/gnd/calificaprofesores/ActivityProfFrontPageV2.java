package com.gnd.calificaprofesores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.AdapterClassFrontPage.AdapterClassFrontPage;
import com.gnd.calificaprofesores.AdapterProfFrontPage.AdapterProfFrontPage;
import com.gnd.calificaprofesores.IntentsManager.IntentProfManager;
import com.gnd.calificaprofesores.MenuManager.MenuManager;

/** Activity asociado: activity_prof_front_page_v2.xml **/

public class ActivityProfFrontPageV2 extends AppCompatActivity{
    PagerSlidingTabStrip tabs;
    ViewPager pager;

    private AdapterProfFrontPage adapter;

    private IntentProfManager intentManager;
    private Toolbar toolbar;

    private MenuManager menuManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_front_page_v2);

        toolbar = findViewById(R.id.toolbar);

        intentManager = new IntentProfManager();

        TextView title = findViewById(R.id.TextProfName);
        title.setText(intentManager.GetProfName());

        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        setSupportActionBar(toolbar);

        adapter = new AdapterProfFrontPage(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(1);
        tabs.setViewPager(pager);

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));
    }
}
