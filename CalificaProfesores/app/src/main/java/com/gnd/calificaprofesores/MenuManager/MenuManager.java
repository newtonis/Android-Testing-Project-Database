package com.gnd.calificaprofesores.MenuManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.NetworkHandler.GotUserExtraDataListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraData;
import com.gnd.calificaprofesores.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

/*** Manejamos el menu para navegar por la aplicación ***/
// https://stackoverflow.com/questions/19442841/how-to-open-navigation-drawer-on-button-click-in-main-fragment

public class MenuManager {

    private MaterialMenuDrawable materialMenu;
    private MaterialMenuView materialMenuView;
    private DrawerLayout mDrawerLayout;
    private UserDataManager userDataManager;

    public MenuManager(Context ctx, MaterialMenuView _materialMenuView, DrawerLayout _mDrawerLayout){
        this.materialMenuView = _materialMenuView;
        this.mDrawerLayout = _mDrawerLayout;

        this.materialMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        this.materialMenu = new MaterialMenuDrawable(ctx, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);

        SetTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

    }
    public void SetTitle(String title){
        TextView text = mDrawerLayout.findViewById(R.id.ShownName);
        text.setText(title);
    }
}
