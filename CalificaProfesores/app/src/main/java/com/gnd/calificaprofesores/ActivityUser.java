package com.gnd.calificaprofesores;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkNewsHandler.GotNewsListener;
import com.gnd.calificaprofesores.NetworkNewsHandler.NetworkNewsHandler;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NewsItemData;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

/** activity_user.xml **/

public class ActivityUser extends AppCompatActivity {
    private MenuManager menuManager;
    private Adapter adapter;
    private RecyclerView recyclerView;
    private ProgressWheel progressWheel;
    private UserDataManager userDataManager;

    private NetworkNewsHandler networkNewsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerView = findViewById(R.id.RecyclerView);
        progressWheel = findViewById(R.id.LoadingIcon);
        progressWheel.bringToFront();


        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        networkNewsHandler = new NetworkNewsHandler();
        userDataManager = new UserDataManager("");

        networkNewsHandler.setGotNewsListener(new GotNewsListener() {
            @Override
            public void onGotNews(List<NewsItemData> news) {
                SetLoaded();

                for (NewsItemData newsItem : news){
                    adapter.AddElement(newsItem);
                }
                adapter.notifyDataSetChanged();
                SetLoaded();
            }
        });
        networkNewsHandler.ListenForNews();

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

        if (FirebaseAuth.getInstance().getUid() != null){
            userDataManager.setShownName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        }

        SetLoading();
    }
    private void SetLoading(){
        progressWheel.setVisibility(View.VISIBLE);
    }
    private void SetLoaded(){
        progressWheel.setVisibility(View.INVISIBLE);
    }
}
