package com.example.a2048_billard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class jeuActivity extends AppCompatActivity{
    private GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        createView();
        if (view.partie.isWin()){
            dispWinDialog();
        }
    }

    public void createView(){
        view = new GameView(this);
        setContentView(view);
    }

    public void returnToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void dispWinDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(this)
                        .setTitle("Partie gagnée !")
                        .setMessage("Bravo, vous avez réussi à former une boule 2048 !")
                        .setNeutralButton("Rejouer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNeutralButton("Menu", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                returnToMain();
                            }
                        });

        AlertDialog winDialog = alertDialogBuilder.create();
        winDialog.show();
    }
}
