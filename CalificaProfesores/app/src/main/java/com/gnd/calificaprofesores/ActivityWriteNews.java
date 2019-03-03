package com.gnd.calificaprofesores;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkNewsHandler.GotNewsListener;
import com.gnd.calificaprofesores.NetworkNewsHandler.NetworkNewsHandler;
import com.gnd.calificaprofesores.NetworkNewsHandler.SentNewsListener;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.InputLineTextData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NewsItemData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SmallLoadingData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;

import java.util.List;

public class ActivityWriteNews extends AppCompatActivity {
    private Adapter adapter;
    private RecyclerView recyclerView;
    private NetworkNewsHandler networkNewsHandler;

    private InputLineTextData titulo, contenido;
    private ButtonData button;
    private MenuManager menuManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_news);

        adapter = new Adapter();
        networkNewsHandler = new NetworkNewsHandler();
        recyclerView = findViewById(R.id.RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        titulo = new InputLineTextData(
                "Título ...",
                "Título"
        );
        adapter.AddElement(titulo);

        contenido = new InputLineTextData(
                "Contenido ...",
                "Contenido"
        );
        contenido.setSingleLine(false);

        adapter.AddElement(contenido);

        button = new ButtonData(
                "ENVIAR"
        );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsItemData noticia = new NewsItemData(
                        titulo.getText(),
                        contenido.getText(),
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                        0L
                );
                noticia.setTimestamp(ServerValue.TIMESTAMP);
                networkNewsHandler.sendNews(
                        noticia
                );
                adapter.removeElement(button);
                adapter.AddElement(new SmallLoadingData());

                networkNewsHandler.setSentNewsListener(new SentNewsListener() {
                    @Override
                    public void onSentNews() {
                        Toast.makeText(ActivityWriteNews.this, "Noticia enviada", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(
                                ActivityWriteNews.this,
                                ActivityUser.class
                        );
                        startActivity(intent);
                    }
                });

            }
        });
        adapter.AddElement(button);



        adapter.notifyDataSetChanged();

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));
    }
}
