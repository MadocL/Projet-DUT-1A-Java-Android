package com.example.a2048_billard;

public class Boule {

	private static final int CONSTANTE_CALC_RAYON = 1000; // modifier cette constante si les boules apparaissent trop petites
	private static int nbBoules = 0;
	private int num;
	private int valeur;
	private double masse;
	private String couleur;
	private double rayon;
	private int posX;
	private int posY;
	private double vitesse;
	private double nrjCinetique;
	private int direction;
	private static String[] couleurs = {"rouge","orange","jaune","vert","bleuC","bleuF","violet","rose","gris","noir","blanc"};

	Boule(int valeur, int posX, int posY){
		this.valeur = valeur;
		this.posX = posX;
		this.posY = posY;
		this.num = nbBoules;
		nbBoules++;
		this.couleur = valeurToCouleur(valeur);
		this.rayon = valeurToRayon(valeur);
	}

	Boule(int valeur, int posX, int posY, double vitesse, int direction){
		this.valeur = valeur;
		this.posX = posX;
		this.posY = posY;
		this.vitesse = vitesse;
		this.direction = direction;
		this.num = nbBoules;
		nbBoules++;
		this.rayon = valeurToRayon(valeur);
	}

	//calcul du rayon à l'aide de la valeur de boule. Je considère la valeur de la boule comme un volume.
	// J'ai pris la formule pour calculer le volume d'une boule à l'aide du rayon, j'ai isolé le volume et maintenant je peux calculer le rayon à partir du volume
	public int valeurToRayon(int valeur){
		return (int) Math.pow((3 * valeur * CONSTANTE_CALC_RAYON)/(4 * Math.PI), 1/3);
	}

	public String valeurToCouleur (int valeur){
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

	public void tirer(double vitesse, int direction) {
		this.vitesse = vitesse;
		this.direction = direction;
	}

	//getters
	public int getNum() {
		return num;
	}
	public int getValeur() {
		return valeur;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public double getRayon() {
		return rayon;
	}
}