package com.gnd.calificaprofesores.MenuManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.firebase.ui.auth.AuthUI;
import com.gnd.calificaprofesores.ActivityAtribuciones;
import com.gnd.calificaprofesores.ActivityLogin;
import com.gnd.calificaprofesores.ActivityPrivacyPolicy;
import com.gnd.calificaprofesores.ActivitySearchCourse;
import com.gnd.calificaprofesores.ActivitySearchProfessor;
import com.gnd.calificaprofesores.ActivitySelectUni;
import com.gnd.calificaprofesores.ActivitySignIn;
import com.gnd.calificaprofesores.ActivityUser;
import com.gnd.calificaprofesores.ActivityVerificar;
import com.gnd.calificaprofesores.ActivityWriteNews;
import com.gnd.calificaprofesores.NetworkHandler.GotUserExtraDataListener;
import com.gnd.calificaprofesores.NetworkHandler.GotUserRightsListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManagerInstance;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraData;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraDataInstance;
import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.LateralMenuItems.MenuButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.LateralMenuItems.MenuSeparatorData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

/*** Manejamos el menu para navegar por la aplicación ***/
// https://stackoverflow.com/questions/19442841/how-to-open-navigation-drawer-on-button-click-in-main-fragment
/** layout_lateral_menu.xml **/

public class MenuManager {

    private RecyclerView recyclerView;
    private Adapter adapter;

    private MaterialMenuDrawable materialMenu;
    private MaterialMenuView materialMenuView;
    private DrawerLayout mDrawerLayout;
    private UserDataManager userDataManager;
    private MenuButtonData
            ButtonSalir,
            buttonBuscarMateria,
            buttonBuscarProfesor,
            buttonNovedades,
            buttonCambiarFacultad,
            buttonAtribuciones,
            buttonPrivacyPolicy,
            buttonSalir;

    private MenuButtonData
            buttonVerificar,
            buttonWriteNews;

    public MenuManager(Context ctx, MaterialMenuView _materialMenuView, DrawerLayout _mDrawerLayout) {
        this.materialMenuView = _materialMenuView;
        this.mDrawerLayout = _mDrawerLayout;

        userDataManager = new UserDataManager();

        this.materialMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        this.materialMenu = new MaterialMenuDrawable(ctx, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);

        SetTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        if (UserExtraDataInstance.getInstance().isGotDataAdmin()){
            addMenus(UserExtraDataInstance.getInstance().isAdmin());
        }else {
            UserDataManagerInstance.getInstance().setGotUserRightsListener(new GotUserRightsListener() {
                @Override
                public void onGotUserRights(boolean admin) {
                    addMenus(admin);
                    UserExtraDataInstance.getInstance().setAdmin(admin);
                }
            });
            UserDataManagerInstance.getInstance().listenForUserRights();
        }
    }
    public void addMenus(boolean admin){
        adapter = new Adapter();
        recyclerView = mDrawerLayout.findViewById(R.id.RecyclerViewMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(mDrawerLayout.getContext()));
        recyclerView.setAdapter(adapter);

        buttonNovedades = new MenuButtonData(
                getText(R.string.TextNovedades),
                true
        );

        buttonNovedades.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.getContext().startActivity(
                        new Intent(
                                mDrawerLayout.getContext(),
                                ActivityUser.class
                        )
                );
            }
        });

        adapter.AddElement(buttonNovedades);

        buttonBuscarMateria = new MenuButtonData(
                getText(R.string.SearchTextMateria),
                true
        );
        buttonBuscarMateria.setClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        mDrawerLayout.getContext().startActivity(
                                new Intent(
                                        mDrawerLayout.getContext(),
                                        ActivitySelectUni.class
                                )
                        );
                    }
                }
        );

        adapter.AddElement(buttonBuscarMateria);


        buttonBuscarProfesor = new MenuButtonData(
                getText(R.string.SearchTextProfesor),
                true
        );

        buttonBuscarProfesor.setClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDrawerLayout.getContext().startActivity(
                        new Intent(
                                mDrawerLayout.getContext(),
                                ActivitySearchProfessor.class
                        )
                );
            }
        });

        adapter.AddElement(buttonBuscarProfesor);


        buttonCambiarFacultad = new MenuButtonData(
                getText(R.string.TextoBotonSelFacultad),
                true
        );

        buttonCambiarFacultad.setClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(
                           mDrawerLayout.getContext(),
                           ActivitySelectUni.class
                   );
                   intent.putExtra("forceSelect",true);

                   mDrawerLayout.getContext().startActivity(
                           intent
                   );
               }
           }
        );

        adapter.AddElement(buttonCambiarFacultad);

        adapter.AddElement(new MenuSeparatorData());

        if (admin){
            addAdminMenus();
            adapter.AddElement(new MenuSeparatorData());
        }

        buttonAtribuciones = new MenuButtonData(
                getText(R.string.TextAtribuciones),
                false
        );

        buttonAtribuciones.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        mDrawerLayout.getContext(),
                        ActivityAtribuciones.class
                );
                mDrawerLayout.getContext().startActivity(
                        intent
                );
            }
        });


        adapter.AddElement(buttonAtribuciones);

        buttonPrivacyPolicy = new MenuButtonData(
                getText(R.string.TextPrivacyPolicy),
                false
        );

        buttonPrivacyPolicy.setClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(
                             mDrawerLayout.getContext(),
                             ActivityPrivacyPolicy.class
                     );
                     mDrawerLayout.getContext().startActivity(
                             intent
                     );
                 }
             }
        );



        adapter.AddElement(buttonPrivacyPolicy);

        buttonSalir = new MenuButtonData(
                getText(R.string.TextSalir),
                false
        );

        buttonSalir.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        adapter.AddElement(buttonSalir);

        adapter.notifyDataSetChanged();



        /*ButtonSalir = mDrawerLayout.findViewById(R.id.ButtonSalir);
        ButtonSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        buttonBuscarMateria = mDrawerLayout.findViewById(R.id.ButtonBuscarMateria);

        buttonBuscarMateria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDrawerLayout.getContext().startActivity(
                        new Intent(
                                mDrawerLayout.getContext(),
                                ActivitySelectUni.class
                        )
                );
            }
        });
        buttonBuscarProfesor = mDrawerLayout.findViewById(R.id.buttonBuscarProfesor);
        buttonBuscarProfesor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDrawerLayout.getContext().startActivity(
                        new Intent(
                                mDrawerLayout.getContext(),
                                ActivitySearchProfessor.class
                        )
                );
            }
        });

        buttonNovedades = mDrawerLayout.findViewById(R.id.ButtonNovedades);
        buttonNovedades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.getContext().startActivity(
                        new Intent(
                                mDrawerLayout.getContext(),
                                ActivityUser.class
                        )
                );
            }
        });

        buttonCambiarFacultad = mDrawerLayout.findViewById(R.id.ButtonCambiarFacultad);
        buttonCambiarFacultad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        mDrawerLayout.getContext(),
                        ActivitySelectUni.class
                );
                intent.putExtra("forceSelect",true);

                mDrawerLayout.getContext().startActivity(
                        intent
                );
            }
        });

        buttonAtribuciones = mDrawerLayout.findViewById(R.id.ButtonAtribuciones);
        buttonAtribuciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        mDrawerLayout.getContext(),
                        ActivityAtribuciones.class
                );
                mDrawerLayout.getContext().startActivity(
                        intent
                );
            }
        });*/
    }
    public void addAdminMenus(){
        buttonWriteNews = new MenuButtonData(
                getText(R.string.TextWriteNews),
                true
        );
        buttonWriteNews.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        mDrawerLayout.getContext(),
                        ActivityWriteNews.class
                );
                mDrawerLayout.getContext().startActivity(intent);
            }
        });
        adapter.AddElement(buttonWriteNews);

        buttonVerificar = new MenuButtonData(
                getText(R.string.TextVerificar),
                true
        );

        buttonVerificar.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        mDrawerLayout.getContext(),
                        ActivityVerificar.class
                );
                mDrawerLayout.getContext().startActivity(intent);
            }
        });
        adapter.AddElement(buttonVerificar);

    }
    private String getText(int id){
        return  mDrawerLayout.getContext().getResources().getText(id).toString();
    }
    public void SetTitle(String title){
        TextView text = mDrawerLayout.findViewById(R.id.ShownName);
        text.setText(title);


    }
    private void LogOut(){
        UserExtraDataInstance.getInstance().logOut();

        AuthUI.getInstance().signOut(mDrawerLayout.getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               mDrawerLayout.getContext().startActivity(
                                                       new Intent(
                                                               mDrawerLayout.getContext(),
                                                               ActivityLogin.class
                                                       )
                                               );
                                           }
                                       }
                );

    }
}
