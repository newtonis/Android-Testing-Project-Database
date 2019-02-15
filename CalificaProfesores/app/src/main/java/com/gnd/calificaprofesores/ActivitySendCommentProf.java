package com.gnd.calificaprofesores;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;
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
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.EditTextData;
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
import java.util.TreeMap;

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

    private ProfCommentsDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment_prof);

        recyclerView = findViewById(R.id.RecyclerView);
        sadIcon = findViewById(R.id.SadFace);
        progressWheel = findViewById(R.id.LoadingIcon);

        adapter = new Adapter();

        recyclerView.setAdapter(adapter);
        intentManager = new IntentProfManager();

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
        SetLoaded();
        adapter.AddElement(new TitleData("MATERIAS"));

        for (SelectableItem materia : materias){
            adapter.AddElement(materia);
        }

        adapter.AddElement(new TitleData("CALIFICACIÓN"));
        scoreSelector = new ScoreSelectorData(3, 3, 3);

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

        adapter.notifyDataSetChanged();
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
                MatData.put(Long.toString(materia.getId()), materia.getTitle());
            }
        }

        dataManager.SendComment(new UserProfComment(
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                editText.getEditable().toString(),
                MatData,
                ServerValue.TIMESTAMP,
                (long)scoreSelector.GetValue(0),
                (long)scoreSelector.GetValue(1),
                (long)scoreSelector.GetValue(2)
        )).AddOnSentCommentListener(new SentProfCommentListener() {
            @Override
            public void onSentProfComment() {
                commentSent();
            }
        });
    }
    public void commentSent(){
        Toast.makeText(this, "Comentario enviado!", Toast.LENGTH_SHORT).show();
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
