package com.example.a2048_billard;

public class Boule {

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

	//TODO : surcharger constructeur en ajoutant une vitesse (et une direction) pour conserver cette dernière lors d'une fusion

	Boule(int valeur, int posX, int posY){
		this.valeur = valeur;
		this.posX = posX;
		this.posY = posY;
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