package com.example.a2048_billard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread thread;
    Terrain partie = new Terrain();
    Integer timer = null;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameLoopThread(this);
    }

    public void doDraw(Canvas canvas){
        canvas.drawColor(Color.parseColor("#088A08"));
        partie.dessiner(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(thread.getState()==Thread.State.TERMINATED) {
            thread=new GameLoopThread(this);
        }
        Terrain.setTailleX(holder.getSurfaceFrame().right);
        Terrain.setTailleY(holder.getSurfaceFrame().bottom);
        Terrain.setPositionDepart(new Point((int) (0.5*holder.getSurfaceFrame().right), (int) (0.7*holder.getSurfaceFrame().bottom)));
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //pas besoin
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }

    public void update() {
        if (timer != null) {
            if (partie.isBouleTiree() && timer > 600) {
                partie.debugRecentrerBoules();
            }
        }
        if (!partie.BallsAreMoving() && partie.isBouleTiree() || partie.isBouleTiree() && timer > 600) {
            partie.nouvelleBoule();
            timer = null;
        }
        if (timer == null){
            timer = 1;
        }
        else {
            timer += 1;
        }
        partie.update();
        //if (partie.isWin()) {
         //   surfaceDestroyed(this.getHolder());
        //}
    }

    public boolean onTouchEvent(MotionEvent event) {
        int posX = (int)event.getX();
        int posY = (int)event.getY();

        switch (event.getAction()) {
            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                break;

            // code exécuté lorsque le doight glisse sur l'écran.
            case MotionEvent.ACTION_MOVE:
                break;

            // lorsque le doigt quitte l'écran
            case MotionEvent.ACTION_UP:
                partie.tirerNouvelleBoule(posX, posY);
        }
        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }
}
