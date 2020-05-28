package com.example.a2048_billard;

import android.graphics.Point;

class Queue {
    private Point position;
    private double puissance;
    private double sens;

    Queue(Point position) {
        this.position = position;
    }

    void dessiner(){
        //TODO : fonction pour dessiner graphiquement la queue lorsqu'un doigt est posé sur la boule a tirer
    }

    double[] handleInput(){ //TODO : fonction captant les doigts et renvoyant la puissance et le sens
        return new double[]{puissance, sens};
    }
}
