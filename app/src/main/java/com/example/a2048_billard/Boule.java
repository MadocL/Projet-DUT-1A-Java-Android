package com.example.a2048_billard;

import android.graphics.Point;

class Boule {

	private static final int CONSTANTE_CALC_RAYON = 1000; // modifier cette constante si les boules apparaissent trop petites

	private int valeur;
	private String couleur;
	private double rayon;
	private Point position;
	private double[] vecteurVit;
	private static String[] couleurs = {"rouge","orange","jaune","vert","bleuC","bleuF","violet","rose","gris","noir","blanc"};
	private boolean isMoving;

	Boule(int valeur, Point position){ //constructeur pour les boules à tirer
		this.valeur = valeur;
		this.position = position;
		couleur = valeurToCouleur(valeur);
		rayon = valeurToRayon(valeur);
		isMoving = false;
	}

	Boule(int valeur, Point position, double[] vecteurVit){ //constructeur pour les boules résultant d'une fusion
		this.valeur = valeur;
		this.position = position;
		couleur = valeurToCouleur(valeur);
		this.vecteurVit = vecteurVit;
		rayon = valeurToRayon(valeur);
		isMoving = true;
	}

	//calcul du rayon à l'aide de la valeur de boule. Je considère la valeur de la boule comme un volume.
	// J'ai pris la formule pour calculer le volume d'une boule à l'aide du rayon, j'ai isolé le volume et maintenant je peux calculer le rayon à partir du volume
	private int valeurToRayon(int valeur){
		return (int) Math.pow((3 * valeur * CONSTANTE_CALC_RAYON)/(4 * Math.PI), 1.0/3);
	}

	private String valeurToCouleur(int valeur){
		switch (valeur){
			case 2:
				return couleurs[0];
			case 4:
				return couleurs[1];
			case 8:
				return couleurs[2];
			case 16:
				return couleurs[3];
			case 32:
				return couleurs[4];
			case 64:
				return couleurs[5];
			case 128:
				return couleurs[6];
			case 256:
				return couleurs[7];
			case 512:
				return couleurs[8];
			case 1024:
				return couleurs[9];
			case 2048:
				return couleurs[10];
			default:
				return "";
		}
	}

	void update(){ //met à jour la position et la vitesse d'une boule
		updatePosition();
		vecteurVit[0] *= 0.98;
		vecteurVit[1] *= 0.98;

		if (isMoving && Math.abs(vecteurVit[0]) < 1 && Math.abs(vecteurVit[1]) < 1){
			stop();
		}
	}

	private void stop(){ //arrête la boule
		isMoving = false;
		vecteurVit[0] = 0;
		vecteurVit[1] = 0;
	}

	private void updatePosition(){
		if (isMoving){
			Point newPos = new Point(0,0);
			newPos.x = (int) (position.x + vecteurVit[0]*0.01);
			newPos.y = (int) (position.y + vecteurVit[1]*0.01);

			boolean collision = handleCollision(newPos);

			if(collision){
				vecteurVit[0] *= 0.95;
				vecteurVit[1] *= 0.95;
			}
			else{
				position = newPos;
			}
		}
	}

	private boolean handleCollision(Point newPos) {
		boolean collision = false;

		if (newPos.x < 0) {
			vecteurVit[0] = -vecteurVit[0];
			position.x = 0;
			collision = true;
		} else if (newPos.x > Terrain.getTailleX()) {
			vecteurVit[0] = -vecteurVit[0];
			position.x = Terrain.getTailleX();
			collision = true;
		}

		if (newPos.y < 0) {
			vecteurVit[1] = -vecteurVit[1];
			position.y = 0;
			collision = true;
		} else if (newPos.y > Terrain.getTailleY()) {
			vecteurVit[1] = -vecteurVit[1];
			position.y = Terrain.getTailleY();
			collision = true;
		}
		return collision;
	}

	void tirer(double[] puissEtAngle) {
		double puissance = puissEtAngle[0];
		double angle = puissEtAngle[1];

		if (puissance > 0) {
			vecteurVit[0] = 100*Math.cos(angle)*puissance;
			vecteurVit[1] = 100*Math.sin(angle)*puissance;
			isMoving = true;
		}
	}

	void dessiner(){
		//TODO : fonction pour dessiner graphiquement les boules
	}

	//getters
	int getValeur() {
		return valeur;
	}
	Point getPosition(){
		return position;
	}
	double getRayon() {
		return rayon;
	}
	double[] getVecteurVit() {
		return vecteurVit;
	}
	boolean isMoving() {
		return isMoving;
	}

	//setters
	void setMoving() {
		isMoving = true;
	}
	void setVecteurVit(double[] vecteurVit) {
		this.vecteurVit = vecteurVit;
	}
}