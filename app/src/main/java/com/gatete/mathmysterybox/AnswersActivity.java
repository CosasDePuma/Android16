package com.gatete.mathmysterybox;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class AnswersActivity extends AppCompatActivity implements AnswerDialog.AnswerDialogListener {

    private int counter = 4;
    private String answer[] = { null, null, null, null };
    private ImageView imgBrain, imgHearth, imgBody, imgDNA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        String team = getIntent().getStringExtra("team");

        if(team.equalsIgnoreCase("verde"))
        {
            answer[0] = "Imaginacion";
            answer[1] = "Sueño";
            answer[2] = "222";
            answer[3] = "TGTAGCATAT";
        } else if(team.equalsIgnoreCase("rojo"))
        {
            answer[0] = "Neurona";
            answer[1] = "Amor";
            answer[2] = "612";
            answer[3] = "TGTAGCATAT";
        } else if(team.equalsIgnoreCase("azul"))
        {
            answer[0] = "Idea";
            answer[1] = "Vida";
            answer[2] = "422";
            answer[3] = "TGTAGCATAT";
        }

        setContentView(R.layout.activity_answers);

        imgBrain = findViewById(R.id.imgCerebro);
        imgBody = findViewById(R.id.imgCuerpo);
        imgHearth = findViewById(R.id.imgCorazon);
        imgDNA = findViewById(R.id.imgADN);

        imgBrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putTheAnswer("cerebro");
            }
        });

        imgBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putTheAnswer("cuerpo");
            }
        });

        imgHearth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putTheAnswer("corazon");
            }
        });

        imgDNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putTheAnswer("adn");
            }
        });
    }

    public void putTheAnswer(String pregunta) {
        AnswerDialog answerDialog = new AnswerDialog();
        answerDialog.setParametros(pregunta);
        answerDialog.show(getSupportFragmentManager(), "AnswerDialog");
    }

    @Override
    public void applyText(String pregunta, String respuesta) {
        if(pregunta.equalsIgnoreCase("cerebro") && respuesta.equalsIgnoreCase(answer[0])) {
            imgBrain.setClickable(false);
            imgBrain.setImageResource(R.drawable.correcto);
            counter--;
        } else if(pregunta.equalsIgnoreCase("cuerpo") && respuesta.equalsIgnoreCase(answer[1])) {
            imgBody.setClickable(false);
            imgBody.setImageResource(R.drawable.correcto);
            counter--;
        } else if(pregunta.equalsIgnoreCase("corazon") && respuesta.equalsIgnoreCase(answer[2])) {
            imgHearth.setClickable(false);
            imgHearth.setImageResource(R.drawable.correcto);
            counter--;
        } else if(pregunta.equalsIgnoreCase("adn") && respuesta.equalsIgnoreCase(answer[3])) {
            imgDNA.setClickable(false);
            imgDNA.setImageResource(R.drawable.correcto);
            counter--;
        } else {
            System.out.println(respuesta);
            Snackbar.make(findViewById(R.id.answersbg), "Respuesta incorrecta, vuelve a intentarlo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if(counter == 0) {
            Intent intent = new Intent(AnswersActivity.this, LabActivity.class);
            intent.putExtra("color", getResources().getColor(R.color.button));
            startActivity(intent);
            finish();
        }
    }
}
