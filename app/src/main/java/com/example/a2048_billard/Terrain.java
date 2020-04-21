package com.example.a2048_billard;

import java.util.*;
import java.lang.Math.*;

public class Terrain {

	protected ArrayList<Boule> sesBoules = new ArrayList<Boule>();
	//protected final static totalX =;
	//protected final static totalY =;

	public void fusion(Boule b1, Boule b2) { //fusion est appelée seulement si b1.getValeur() == b2.getValeur() && EcartBoules(b1, b2) <= b1.getRayon() * 2
		int newBoulePosX = 0, newBoulePosY = 0, newBouleValeur;

		newBouleValeur = b1.getValeur() * 2;

		if (b1.getPosX() < b2.getPosX())
			newBoulePosX = (int) EcartBoulesPosX(b1,b2)/2 + b1.getPosX();
		else
			newBoulePosX = (int) EcartBoulesPosX(b1,b2)/2 + b2.getPosX();

		if (b1.getPosY() < b2.getPosY())
			newBoulePosX = (int) EcartBoulesPosY(b1,b2)/2 + b1.getPosY();
		else
			newBoulePosX = (int) EcartBoulesPosY(b1,b2)/2 + b2.getPosY();

		sesBoules.remove(b1.getNum());
		sesBoules.remove(b2.getNum());
		Boule b3 = new Boule(newBouleValeur, newBoulePosX, newBoulePosY);
		sesBoules.add(b3);
	}

	public int EcartBoulesPosX(Boule b1, Boule b2){
		int delta = b1.getPosX() - b2.getPosX();
		if (delta < 0)
			return delta * -1;
		return delta;
	}

	public int EcartBoulesPosY(Boule b1, Boule b2){
		int delta = b1.getPosY() - b2.getPosY();
		if (delta < 0)
			return delta * -1;
		return delta;
	}

	public int EcartBoules(Boule b1, Boule b2){ //calcul la distance entre deux boules à l'aide de Pythagore
		return (int) Math.sqrt(Math.pow(EcartBoulesPosX(b1,b2), 2) + Math.pow(EcartBoulesPosY(b1,b2), 2));
	}
}