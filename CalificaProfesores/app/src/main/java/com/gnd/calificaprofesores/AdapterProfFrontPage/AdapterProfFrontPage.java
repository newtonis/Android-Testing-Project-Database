package com.gnd.calificaprofesores.AdapterProfFrontPage;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AdapterProfFrontPage extends FragmentPagerAdapter {
    private final String[] TITLES = {
            "VISTA GENERAL",
            "OPINIONES RECIENTES",
            "TU OPINIÓN"}; // Opiniones importantes para futuro

    public AdapterProfFrontPage(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new ActivityProfFrontPageCapital();
        }else if (position == 1){
            return new ActivityProfFrontPageCapital();
        }else if(position == 2){
            return new ActivityYourOpinion();
        }
        return new ActivityProfFrontPageCapital();
    }
}
