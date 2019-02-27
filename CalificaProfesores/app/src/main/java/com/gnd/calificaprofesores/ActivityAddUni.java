package com.gnd.calificaprofesores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.gnd.calificaprofesores.NetworkAdd.AddUniHandler;
import com.gnd.calificaprofesores.NetworkAdd.CompleteUniData;
import com.gnd.calificaprofesores.NetworkAdd.UniAddedListener;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.InputLineTextData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SmallLoadingData;

/** activity_add_uni.xml **/

public class ActivityAddUni extends AppCompatActivity {
    private Adapter adapter;
    private RecyclerView recyclerView;
    private InputLineTextData uniInput;
    private InputLineTextData uniShortNameInput;
    private ButtonData button;
    private AddUniHandler addUniHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_uni);

        adapter = new Adapter();

        recyclerView = findViewById(R.id.RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        addUniHandler = new AddUniHandler();

        uniInput = new InputLineTextData("Institución ...","Nombre completo de la institucion");

        uniShortNameInput = new InputLineTextData("Sigla ... (UBA, UTN)","Sigla (en mayúscula)");

        adapter.AddElement(uniInput);
        adapter.AddElement(uniShortNameInput);

        button = new ButtonData("ENVIAR");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeElement(button);
                adapter.AddElement(new SmallLoadingData());

                sendUni();
                adapter.notifyDataSetChanged();
            }
        });

        adapter.AddElement(button);

        addUniHandler.setUniAddedListener(new UniAddedListener() {
            @Override
            public void OnUniAdded() {
                Toast.makeText(ActivityAddUni.this,
                        "La institución será agregada en unos instantes",
                        Toast.LENGTH_SHORT
                ).show();
                Intent intent = new Intent(ActivityAddUni.this, ActivityUser.class);
                startActivity(intent);
            }
        });
    }
    protected void sendUni(){
        String uniName = uniInput.getText();
        String uniSigla = uniShortNameInput.getText();

        addUniHandler.AddUni(new CompleteUniData(
            uniName,
            uniSigla
        ));
    }
}
