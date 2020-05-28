package com.example.a2048_billard;

import android.graphics.Point;

import java.util.*;

class Terrain {

	private static final int TAILLE_X = 800; //tailles du plateau
	private static final int TAILLE_Y = 1500;
	private static final Point POSITION_DEPART = new Point((int) (0.5*TAILLE_X), (int) (0.5*TAILLE_Y));
	private ArrayList<Boule> sesBoules = new ArrayList<>();
	private boolean bouleTiree = false;
	private Boule bouleATirer;
	private Queue q = new Queue(POSITION_DEPART);

	void dessiner(){
		//TODO : dessiner image background
		//TODO : dessiner scores
		for (Boule b : sesBoules){
			b.dessiner();
		}
		if (! bouleTiree) {
			q.dessiner();
		}
	}

	private void fusion(Boule b1, Boule b2) { //fusion est appelée seulement si b1.getValeur() == b2.getValeur() && EcartBoules(b1, b2) <= b1.getRayon() * 2
		Point newBoulePos = new Point(0,0);
		int newBouleValeur = b1.getValeur() * 2;

		if (b1.getPosition().x < b2.getPosition().x) {
			newBoulePos.x = EcartBoulesPosX(b1, b2) / 2 + b1.getPosition().x;
		}
		else {
			newBoulePos.x = EcartBoulesPosX(b1, b2) / 2 + b2.getPosition().x;
		}
		if (b1.getPosition().y < b2.getPosition().y)
			newBoulePos.x = EcartBoulesPosY(b1,b2)/2 + b1.getPosition().y;
		else
			newBoulePos.x = EcartBoulesPosY(b1,b2)/2 + b2.getPosition().y;

		double[] newBouleVit = new double[]{(b1.getVecteurVit()[0] + b2.getVecteurVit()[0])/2, (b1.getVecteurVit()[1] + b2.getVecteurVit()[1])/2};

		sesBoules.remove(b1);
		sesBoules.remove(b2);
		Boule b3 = new Boule(newBouleValeur, newBoulePos, newBouleVit);
		sesBoules.add(b3);
	}

	void nouvelleBoule(){ //fonction pour créer une nouvelle Boule à tirer
		int random = (int)(Math.random() * (8));
		int valeur;
		if (random == 0)
			valeur = 4;
		else
			valeur = 2;
		Boule b = new Boule(valeur, POSITION_DEPART);
		sesBoules.add(b);
		bouleTiree = false;
		bouleATirer = b;
	}

	void handleInput(){
		if (! bouleTiree) {
			bouleATirer.tirer(q.handleInput());
		}
	}

	private int EcartBoulesPosX(Boule b1, Boule b2){
		return b1.getPosition().y - b2.getPosition().y;
	}

	private int EcartBoulesPosY(Boule b1, Boule b2){
		return b1.getPosition().y - b2.getPosition().y;
	}

	private int EcartBoules(Boule b1, Boule b2){ //calcul la distance entre deux boules à l'aide de Pythagore
		return (int) Math.sqrt(Math.pow(EcartBoulesPosX(b1,b2), 2) + Math.pow(EcartBoulesPosY(b1,b2), 2));
	}

	void update(){ //fonction pour update la queue, capter les collisions, et faire bouger les boules
		for (int i = 0 ; i < sesBoules.size() ; i++){
			for(int j = i + 1 ; j < sesBoules.size() ; j++){
				handleCollision(sesBoules.get(i), sesBoules.get(j));
			}
		}
		for(Boule b : sesBoules) {
			b.update();
		}
	}

	boolean BallsAreMoving(){ //fonction renvoyant un boolean indiquant si une ou des boules sont en mouvement
		for(Boule b : sesBoules) {
			if (b.isMoving()){
				return true;
			}
		}
		return false;
	}

	private void handleCollision(Boule b1, Boule b2){ //fonction verifiant s'il y a des collisions et calcule les nouvelles trajectoires
		if(b1.isMoving() || b2.isMoving()){

			Point b1NewPos = new Point(0,0);
			Point b2NewPos = new Point(0,0);

			b1NewPos.x = (int) (b1.getPosition().x + b1.getVecteurVit()[0]*0.01);
			b1NewPos.y = (int) (b1.getPosition().y + b1.getVecteurVit()[1]*0.01);

			b2NewPos.x = (int) (b2.getPosition().x + b2.getVecteurVit()[0]*0.01);
			b2NewPos.y = (int) (b2.getPosition().y + b2.getVecteurVit()[1]*0.01);

			int distance = EcartBoules(b1, b2);
			 if (distance < b1.getRayon() + b2.getRayon() && b1.getValeur() == b2.getValeur()) {
			 	fusion(b1, b2);
			 }
			 else if(distance < b1.getRayon() + b2.getRayon()) {
			 	double power = (Math.abs(b1.getVecteurVit()[0]) + Math.abs(b1.getVecteurVit()[1])) + (Math.abs(b2.getVecteurVit()[0]) + Math.abs(b2.getVecteurVit()[1]));
			 	power *= 0.00482;

			 	int oppose = b1.getPosition().y - b2.getPosition().y;
			 	int adjacent = b1.getPosition().x - b2.getPosition().x;
			 	double rotation = Math.atan2(oppose, adjacent);

			 	b1.setMoving();
			 	b2.setMoving();

				double[] vecteurVit1 = {90*Math.cos(rotation)*power, 90*Math.sin(rotation)*power};
				b1.setVecteurVit(new double[]{(b1.getVecteurVit()[0] + vecteurVit1[0])*0.97, (b1.getVecteurVit()[1] + vecteurVit1[1])*0.97});

				double[] vecteurVit2 = {90*Math.cos(rotation + Math.PI)*power, 90*Math.sin(rotation + Math.PI)*power};
				b2.setVecteurVit(new double[]{(b2.getVecteurVit()[0] + vecteurVit2[0])*0.97, (b2.getVecteurVit()[1] + vecteurVit2[1])*0.97});
			 }
		}
	}

	//getters
	static int getTailleX() {
		return TAILLE_X;
	}
	static int getTailleY() {
		return TAILLE_Y;
	}
	boolean isBouleTiree() {
		return bouleTiree;
	}
}