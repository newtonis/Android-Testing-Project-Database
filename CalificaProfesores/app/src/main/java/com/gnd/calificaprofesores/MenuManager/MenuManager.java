package com.gnd.calificaprofesores.MenuManager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.gnd.calificaprofesores.R;

public class MenuManager {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    View mView;

    public MenuManager(View view, Activity HostActivity){
        mView = view;
        /*mDrawerLayout = (DrawerLayout) view.findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(
                HostActivity,
                mDrawerLayout,
                R.string.DrawerOpen,
                R.string.DrawerClose);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });*/

    }
}
