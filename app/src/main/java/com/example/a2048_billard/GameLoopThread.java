package com.example.a2048_billard;

import android.graphics.Canvas;

public class GameLoopThread extends Thread
{
    // on définit arbitrairement le nombre d'images par secondes à 30
    private final static int FRAMES_PER_SECOND = 60;

    // si on veut X images en 1 seconde, soit en 1000 ms,
    // on doit en afficher une toutes les (1000 / X) ms.
    private final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    private boolean running = false; // état du thread, en cours ou non
    private final GameView view;

    public GameLoopThread(GameView view) {
        this.view = view;
    }

    //setter
    void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() //démarrage du thread : boucle de jeu qui fait les vérifs de colision, de vitesse, et les affichages graphiques en permanance
    {
        // déclaration des temps de départ et de pause
        long startTime;
        long sleepTime;

        // boucle tant que running est vrai
        // il devient faux par setRunning(false), notamment lors de l'arrêt de l'application
        while (running)
        {
            // horodatage actuel
            startTime = System.currentTimeMillis();

            synchronized (view.getHolder()) {view.update();}

            // Rendu de l'image, tout en vérrouillant l'accès car nous
            // y accédons à partir d'un processus distinct
            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {view.doDraw(c);}
            }
            finally
            {
                if (c != null) {view.getHolder().unlockCanvasAndPost(c);}
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
