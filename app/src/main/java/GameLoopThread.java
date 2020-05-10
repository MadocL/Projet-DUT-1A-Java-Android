import com.example.a2048_billard.Boule;

import java.util.ArrayList;

public class GameLoopThread extends Thread
{
    // on définit arbitrairement le nombre d'images par secondes à 30
    private final static int FRAMES_PER_SECOND = 30;

    // si on veut X images en 1 seconde, soit en 1000 ms,
    // on doit en afficher une toutes les (1000 / X) ms.
    private final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    private boolean running = false; // état du thread, en cours ou non

    // défini l'état du thread : true ou false
    public void setRunning(boolean run) {
        running = run;
    }

    public void deplacerBoule(ArrayList<Boule> sesBoules){
        //TODO : parcoure le tableau de boules et les déplace lorsque leur vitesse n'est pas nulle
    }

    /*TODO : fonction lorsque deux boules se percutent (ou vont elles ? perdent elles leur NRJ cinétique ?)
    TODO : faire des tests du thread (c'est la boucle de jeu qui fera les vérifs de colision, de vitesse, et les affichages graphiques en permanance)
    TODO : fonction qui fait décélérer une boule lorsqu'elle a de la vitesse
    TODO : fonction qui donne le bon angle lorsqu'une boule percute un mur (ça fait un doublon avec la première fonction)
     */


    // démarrage du thread
    @Override
    public void run()
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




            // Calcul du temps de pause, et pause si nécessaire
            // afin de ne réaliser le travail ci-dessus que X fois par secondes
            sleepTime = SKIP_TICKS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime >= 0) {sleep(sleepTime);}
            }
            catch (Exception e) {}
        }
    }

}
