package com.gnd.calificaprofesores;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.IntentsManager.IntentProfManager;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkProfOpinion.GotProfMatListener;
import com.gnd.calificaprofesores.NetworkProfOpinion.Materia;
import com.gnd.calificaprofesores.NetworkProfOpinion.ProfCommentsDataManager;
import com.gnd.calificaprofesores.NetworkProfOpinion.SentProfCommentListener;
import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;
import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ClickableData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.EditTextData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NoInfoData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ScoreSelectorData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SelectableItem;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static android.support.test.InstrumentationRegistry.getContext;

/** activity_send_comment_prof.xml **/

public class ActivitySendCommentProf extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    private MenuManager menuManager;
    private IntentProfManager intentManager;
    private ProgressWheel progressWheel;
    private ImageView sadIcon;
    private List<SelectableItem> materias;
    private EditTextData editText;
    private ScoreSelectorData scoreSelector;
    private TextView TextBuscarCurso;

    private ProfCommentsDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment_prof);

        recyclerView = findViewById(R.id.RecyclerView);
        sadIcon = findViewById(R.id.SadFace);
        progressWheel = findViewById(R.id.LoadingIcon);
        TextBuscarCurso = findViewById(R.id.TextBuscarCurso);

        adapter = new Adapter();

        recyclerView.setAdapter(adapter);
        intentManager = new IntentProfManager(getIntent());

        TextBuscarCurso.setText(intentManager.GetProfName());

        dataManager = new ProfCommentsDataManager(intentManager.GetProfId());

        dataManager.AddOnGotProfMatListener(new GotProfMatListener() {
            @Override
            public void onGotProfMatListener(List<SelectableItem> data) {
                materias = data;
                onLoaded();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

        SetLoading();

        dataManager.RequestProfMat();

    }
    public void onLoaded(){
        Intent intent = intentManager.GetIntent();

        SetLoaded();

        Integer materiasCount = intent.getIntExtra("MateriasCount",0);

        View.OnClickListener goToAsociarMateriasListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentManager.ConvertIntent(
                        ActivitySendCommentProf.this,
                        ActivityLink.class
                ).GetIntent()
                );
            }
        };

        if (materias.size() == 0){
            adapter.AddElement(new NoInfoData("El profesor no tiene asociada materias",
                    "Asociar materias",
                        goToAsociarMateriasListener
                    )
            );
            adapter.notifyDataSetChanged();

        }else {
            adapter.AddElement(new TitleData("MATERIAS"));

            Set<String> selected_materias = new TreeSet<>();

            if (intent.getBooleanExtra("PrevComment", false)) {
                String[] materiasSelected = intent.getStringArrayExtra("Materias");
                for (Integer index = 0; index < materiasCount; index++) {
                    selected_materias.add(materiasSelected[index]);
                }
            }
            for (SelectableItem materia : materias) {
                if (selected_materias.contains(materia.getId())) {
                    materia.setClicked(true);
                }
                adapter.AddElement(materia);
            }
            adapter.AddElement(new ClickableData(
                    "¿Faltan materias?",
                    goToAsociarMateriasListener
            ));

            adapter.AddElement(new TitleData("CALIFICACIÓN"));

            scoreSelector = new ScoreSelectorData(
                    (int) intent.getLongExtra("Conocimiento", 3L),
                    (int) intent.getLongExtra("Clases", 3L),
                    (int) intent.getLongExtra("Amabilidad", 3L)
            );

            adapter.AddElement(scoreSelector);

            adapter.AddElement(new TitleData("EN PALABRAS"));
            editText = new EditTextData("Comentario ...");
            adapter.AddElement(editText);

            ButtonData sendButton = new ButtonData("ENVIAR");
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendProfComment();
                }
            });

            adapter.AddElement(sendButton);

            editText.setHasText(intent.getBooleanExtra(
                    "HasText", true
            ));
            editText.setAnonimo(intent.getBooleanExtra(
                    "IsAnonimo", false
            ));
            if (intent.getBooleanExtra("PrevComment", false)) {
                String text = intent.getStringExtra("TextContent");
                editText.setText(text);
            }

            adapter.notifyDataSetChanged();
        }
    }
    private void SetLoading(){
        progressWheel.setVisibility(View.VISIBLE);
        sadIcon.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }
    private void SetLoaded(){
        progressWheel.setVisibility(View.INVISIBLE);
        sadIcon.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void SendProfComment(){  // enviamos el comentario
        if (!validate()){
            Toast.makeText(this, "Debes seleccionar al menos una Materia", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> MatData = new TreeMap<>();

        for (SelectableItem materia : materias){
            if (materia.getClicked()){
                MatData.put(materia.getId(), materia.getTitle());
            }
        }

        dataManager.SendComment(new UserProfComment(
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                editText.getEditable().toString(),
                MatData,
                ServerValue.TIMESTAMP,
                (long)scoreSelector.GetValue(0),
                (long)scoreSelector.GetValue(1),
                (long)scoreSelector.GetValue(2),
                editText.isAnonimo(),
                editText.isHasText()
        )).AddOnSentCommentListener(new SentProfCommentListener() {
            @Override
            public void onSentProfComment() {
                commentSent();
            }
        });
    }
    public void commentSent(){
        Toast.makeText(this, "Comentario enviado!", Toast.LENGTH_SHORT).show();
        startActivity(
                intentManager.ConvertIntent(
                        ActivitySendCommentProf.this,
                        ActivityProfFrontPageV2.class
                ).GetIntent()
        );

    }
    private boolean validate(){
        boolean good = false;
        for (SelectableItem materia : materias){
            if (materia.getClicked()){
                good = true;
            }
        }
        return good;
    }
}
