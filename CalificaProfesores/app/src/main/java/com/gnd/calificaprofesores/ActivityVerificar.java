package com.gnd.calificaprofesores;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkVerifyHandler.DoneActionListener;
import com.gnd.calificaprofesores.NetworkVerifyHandler.ButtonSelectedListener;
import com.gnd.calificaprofesores.NetworkVerifyHandler.GotRequestItemListener;
import com.gnd.calificaprofesores.NetworkVerifyHandler.NetworkVerifyHandler;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SmallLoadingData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.VerifyInfoData;

public class ActivityVerificar extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private MenuManager menuManager;
    private NetworkVerifyHandler networkVerifyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar);

        adapter = new Adapter();
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        networkVerifyHandler = new NetworkVerifyHandler();

        networkVerifyHandler.setGotRequestItemListener(new GotRequestItemListener() {
            @Override
            public void onGotRequestItemListener(VerifyInfoData data) {
                adapter.AddElement(data);
                adapter.notifyDataSetChanged();
            }
        });

        networkVerifyHandler.listenForUniRequests();
        networkVerifyHandler.listenForProfAddRequests();
        networkVerifyHandler.listenForCourseAddRequests();

        networkVerifyHandler.setDoneActionListener(new DoneActionListener() {
            @Override
            public void onDoneAction(String result, SmallLoadingData loadingReference) {
                adapter.removeElement(loadingReference);
                adapter.notifyDataSetChanged();
                Toast.makeText(
                        ActivityVerificar.this,
                        result,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        networkVerifyHandler.setButtonSelectedListener(new ButtonSelectedListener() {
            @Override
            public void onButtonSelected(VerifyInfoData item, SmallLoadingData loadingReference) {
                adapter.replaceElement(item , loadingReference);
                adapter.notifyDataSetChanged();
            }
        });

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

    }
}
