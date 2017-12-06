package com.gatete.mathmysterybox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class AnswerDialog extends AppCompatDialogFragment {

    private EditText cuadrorespuesta;
    private ImageView img;

    private AnswerDialogListener listener;

    private String pregunta;

    public void setParametros(String pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        cuadrorespuesta = view.findViewById(R.id.cuadroRespuesta);
        img = view.findViewById(R.id.imgDialog);

        builder.setView(view)
                .setTitle("¿Cuál es tu respuesta?")
                .setPositiveButton("Comprobar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String respuesta = cuadrorespuesta.getText().toString().trim().replaceAll(" ", "").replaceAll("ó","o");
                        listener.applyText(pregunta, respuesta);
                    }
                });


        if (this.pregunta.equalsIgnoreCase("cerebro")) {
            img.setImageResource(R.drawable.cerebro);
        } else if (this.pregunta.equalsIgnoreCase("corazon")) {
            img.setImageResource(R.drawable.corazon);
        } else if (this.pregunta.equalsIgnoreCase("cuerpo")) {
            img.setImageResource(R.drawable.cuerpo);
        } else if (this.pregunta.equalsIgnoreCase("adn")) {
            img.setImageResource(R.drawable.adn);
        }

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positive.setTextColor(getResources().getColor(R.color.button));
            }
        });


        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AnswerDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface AnswerDialogListener
    {
        void applyText(String pregunta, String respuesta);
    }
}
