package com.gnd.calificaprofesores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gnd.calificaprofesores.NetworkHandler.ProcessVote;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityCalProf extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button calButton; // Boton para emitir el voto

    ImageButton infoCA,infoCE,infoA;
    Toast mToast;
    SeekBar seekCA,seekCE,seekA;
    TextView scoreText;

    Long profId, matId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_prof);

        //Intent intent = getIntent();
        profId = 1l; //intent.getLongExtra("ProfId",1l);
        matId = 1l; //intent.getLongExtra("MatId",1l);


        calButton = findViewById(R.id.CalButton);
        infoCE = findViewById(R.id.ButtonInfoCE);
        infoCA = findViewById(R.id.ButtonInfoCA);
        infoA = findViewById(R.id.ButtonInfoA);
        seekCA = findViewById(R.id.SeekBarCA);
        seekCE = findViewById(R.id.SeekBarCE);
        seekA  = findViewById(R.id.seekBarA);
        scoreText = findViewById(R.id.ScoreText);

        seekCA.setProgress(50);
        seekA.setProgress(50);
        seekCE.setProgress(50);

        mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null || mAuth.getCurrentUser().isAnonymous()){
            calButton.setText(getResources().getText(R.string.TextRegisterAndVote));
        }

        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() == null || mAuth.getCurrentUser().isAnonymous()){
                    Intent intent = new Intent(ActivityCalProf.this,ActivitySignUp.class);
                    startActivity(intent);
                }else{
                    new ProcessVote(getCA(),getCE(),getA(),profId,matId);
                    addToast("Processing vote");
                }
            }
        });

        infoCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToast((String) getResources().getText(R.string.InfoCE));
            }
        });
        infoCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToast((String) getResources().getText(R.string.InfoCA));
            }
        });
        infoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToast((String) getResources().getText(R.string.InfoA));
            }
        });

        seekCE.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateScoreText();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekCA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateScoreText();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateScoreText();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
    protected void addToast(String text){
        mToast.setText(text);
        mToast.show();
    }
    protected Long getCE(){
        return (long)seekCE.getProgress();
    }
    protected  Long getCA(){
        return (long)seekCA.getProgress();
    }
    protected  Long getA(){
        return (long)seekA.getProgress();
    }
    protected Long getAverage(){
        return (getCE()+getCA()+getA())/3;
    }
    protected void updateScoreText(){
        scoreText.setText( Float.toString((float)getAverage()/10f)  );
    }

}
