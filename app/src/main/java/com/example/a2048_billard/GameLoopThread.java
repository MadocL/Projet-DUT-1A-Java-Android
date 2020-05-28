package com.example.a2048_billard;

import com.example.a2048_billard.Boule;
import com.example.a2048_billard.Terrain;

import java.util.ArrayList;

public class GameLoopThread extends Thread
{
    // on définit arbitrairement le nombre d'images par secondes à 30
    private final static int FRAMES_PER_SECOND = 30;

    // si on veut X images en 1 seconde, soit en 1000 ms,
    // on doit en afficher une toutes les (1000 / X) ms.
    private final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    private boolean running = false; // état du thread, en cours ou non

    //setter
    private void setRunning(boolean run) {
        running = run;
    }

    //TODO : faire des tests du thread (c'est la boucle de jeu qui fait les vérifs de colision, de vitesse, et les affichages graphiques en permanance)
    //TODO : gérer la fin de la partie

    @Override
    public void run() //démarrage du thread
    {
        Terrain partie = new Terrain();
        // déclaration des temps de départ et de pause
        long startTime;
        long sleepTime;
        setRunning(true);
        partie.nouvelleBoule();

        // boucle tant que running est vrai
        // il devient faux par setRunning(false), notamment lors de l'arrêt de l'application
        while (running)
        {
            // horodatage actuel
            startTime = System.currentTimeMillis();
            partie.handleInput();
            partie.update();
            //clear le canvas
            partie.dessiner();
            if (! partie.BallsAreMoving() && partie.isBouleTiree()){
                partie.nouvelleBoule();
            }


            // Calcul du temps de pause, et pause si nécessaire
            // afin de ne réaliser le travail ci-dessus que X fois par secondes
            sleepTime = SKIP_TICKS-(System.currentTimeMillis() - startTime);
            if (sleepTime >= 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
