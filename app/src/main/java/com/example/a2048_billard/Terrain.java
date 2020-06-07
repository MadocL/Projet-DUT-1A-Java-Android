package com.example.a2048_billard;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.*;

class Terrain {

	private static int TAILLE_X; //tailles du plateau
	private static int TAILLE_Y;
	private static Point POSITION_DEPART;
	private ArrayList<Boule> sesBoules = new ArrayList<>();
	private boolean bouleTiree = true;
	private Boule bouleATirer;
	private boolean win = false;
	private int score = 0;


	void dessiner(Canvas canvas){
		for (Boule b : sesBoules){
			b.dessiner(canvas);
		}
		Paint p = new Paint();
		p.setTextSize(50);
		canvas.drawText("Score : " + score, 100, TAILLE_Y - 100, p);
	}

	private void fusion(Boule b1, Boule b2) { //fusion est appelée seulement si b1.getValeur() == b2.getValeur() && EcartBoules(b1, b2) <= b1.getRayon() * 2
		Point newBoulePos = new Point(0,0);
		int newBouleValeur = b1.getValeur() * 2;

		if (b1.getPosition().x < b2.getPosition().x) {
			newBoulePos.x = (int) b1.getRayon() + b1.getPosition().x;
		}
		else {
			newBoulePos.x = (int) b1.getRayon() + b2.getPosition().x;
		}
		if (b1.getPosition().y < b2.getPosition().y)
			newBoulePos.y = (int) b1.getRayon() + b1.getPosition().y;
		else
			newBoulePos.y = (int) b1.getRayon() + b2.getPosition().y;

		double[] newBouleVit = new double[]{(b1.getVecteurVit()[0] + b2.getVecteurVit()[0])/2, (b1.getVecteurVit()[1] + b2.getVecteurVit()[1])/2};

		sesBoules.remove(b1);
		sesBoules.remove(b2);
		Boule b3 = new Boule(newBouleValeur, newBoulePos, newBouleVit);
		sesBoules.add(b3);
		score += b3.getValeur();
	}

	void nouvelleBoule(){ //fonction pour créer une nouvelle Boule à tirer
		int random = (int)(Math.random() * (8));
		int valeur;
		if (random == 0)
			valeur = 4;
		else
			valeur = 2;
		Boule b = new Boule(valeur, POSITION_DEPART, new double[]{0, 0});
		b.setMoving(false);
		sesBoules.add(b);
		bouleTiree = false;
		bouleATirer = b;
	}

	public void tirerNouvelleBoule(int posX, int posY) {
		double[] puissEtAngle = positionToPuissEtAngle(posX, posY);
		if (puissEtAngle[0] != 0 && bouleATirer != null) {
			bouleATirer.tirer(puissEtAngle);
			bouleTiree = true;
			bouleATirer = null;
		}
	}

	double[] positionToPuissEtAngle(int posX, int posY) {
		int ecartX = posX - POSITION_DEPART.x;
		int ecartY =posY - POSITION_DEPART.y;

		double puissance = 7.5 * Math.sqrt(ecartX * ecartX + ecartY * ecartY);
		double angle = Math.atan2(ecartY, ecartX);

		return new double[]{puissance, angle};
	}

	private int EcartBoulesPosX(Boule b1, Boule b2){
		return Math.abs(b1.getPosition().x - b2.getPosition().x);
	}

	private int EcartBoulesPosY(Boule b1, Boule b2){
		return Math.abs(b1.getPosition().y - b2.getPosition().y);
	}

	private double EcartBoules(Boule b1, Boule b2){ //calcul la distance entre deux boules à l'aide de Pythagore
		return Math.sqrt(Math.pow(EcartBoulesPosX(b1,b2), 2) + Math.pow(EcartBoulesPosY(b1,b2), 2));
	}

	void update(){ //fonction pour capter les collisions, et faire bouger les boules
		for (int i = 0 ; i < sesBoules.size() ; i++){
			for(int j = i + 1 ; j < sesBoules.size() ; j++){
				handleCollision(sesBoules.get(i), sesBoules.get(j));
			}
		}
		for(Boule b : sesBoules) {
			b.update();
		}
		win = handleWin();
	}

	public boolean handleWin(){
		for (Boule b  : sesBoules){
			if (b.getValeur() == 2048) {
				return true;
			}
		}
		return false;
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

			double distance = EcartBoules(b1, b2);
			 if (distance <= b1.getRayon() + b2.getRayon() && b1.getValeur() == b2.getValeur()) {
			 	fusion(b1, b2);
			 }
			 else if(distance <= b1.getRayon() + b2.getRayon()) {
			 	double power = (Math.abs(b1.getVecteurVit()[0]) + Math.abs(b1.getVecteurVit()[1])) + (Math.abs(b2.getVecteurVit()[0]) + Math.abs(b2.getVecteurVit()[1]));
			 	power *= 0.00482;

			 	int oppose = b1.getPosition().y - b2.getPosition().y;
			 	int adjacent = b1.getPosition().x - b2.getPosition().x;
			 	double rotation = Math.atan2(oppose, adjacent);

			 	b1.setMoving(true);
			 	b2.setMoving(true);

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
	public boolean isWin() {
		return win;
	}
	public Boule getBouleATirer() {
		return bouleATirer;
	}

	//setters
	public static void setTailleX(int tailleX) {
		TAILLE_X = tailleX;
	}
	public static void setTailleY(int tailleY) {
		TAILLE_Y = tailleY;
	}
	public static void setPositionDepart(Point positionDepart) {
		POSITION_DEPART = positionDepart;
	}

	public void debugRecentrerBoules() {
		for (Boule b :sesBoules){
			b.setPosition(POSITION_DEPART);
		}
	}
}