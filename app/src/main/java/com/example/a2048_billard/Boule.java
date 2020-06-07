package com.example.a2048_billard;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

class Boule {

	// modifier cette constante si les boules apparaissent trop petites
	private static double DiagonaleEcranPx = Math.sqrt(Terrain.getTailleX() * Terrain.getTailleX() + Terrain.getTailleY() * Terrain.getTailleY());
	private static final int CONSTANTE_CALC_RAYON = 20;

	private int valeur;
	private String couleur;
	private double rayon;
	private Point position;
	private double[] vecteurVit;
	//private static String[] couleurs = {"#FF0000","#FF8000","#FFFF00","#40ff00","#00ffff","#0040ff","#8000ff","#ff00ff","#8c8c8c","#000000","#000000"};
	private static String[] couleurs = {"#eee4da","#eee2ce","#f3b27e","#f6976b","#f77e68","#f76147","#edcf72","#edcc61","#eed278","#eecf6b","#eecd5f","#000000"}; //couleurs originales 2048
	private boolean isMoving;

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
		return (int) Math.pow((3 * valeur * CONSTANTE_CALC_RAYON * DiagonaleEcranPx)/(4 * Math.PI), 1.0/3);
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
				return couleurs[11];
		}
	}

	void update(){ //met à jour la position et la vitesse d'une boule
		updatePosition();
		vecteurVit[0] *= 0.97;
		vecteurVit[1] *= 0.97;

		if (isMoving && Math.abs(vecteurVit[0]) < 30 && Math.abs(vecteurVit[1]) < 30){
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

		if (newPos.x <= (int) rayon) {
			vecteurVit[0] = -vecteurVit[0];
			position.x = (int) rayon;
			collision = true;
		} else if (newPos.x >= Terrain.getTailleX() - (int) rayon) {
			vecteurVit[0] = -vecteurVit[0];
			position.x = Terrain.getTailleX() - (int) rayon;
			collision = true;
		}

		if (newPos.y <= (int) rayon) {
			vecteurVit[1] = -vecteurVit[1];
			position.y = (int) rayon;
			collision = true;
		} else if (newPos.y >= Terrain.getTailleY() - (int) rayon) {
			vecteurVit[1] = -vecteurVit[1];
			position.y = Terrain.getTailleY() - (int) rayon;
			collision = true;
		}
		return collision;
	}

	void tirer(double[] puissEtAngle) {
		double puissance = puissEtAngle[0];
		double angle = puissEtAngle[1];

		if (puissance > 0) {
			vecteurVit[0] = Math.cos(angle)*puissance;
			vecteurVit[1] = Math.sin(angle)*puissance;
			isMoving = true;
		}
	}

	void dessiner(Canvas canvas){
		Paint p = new Paint();
		p.setColor(Color.parseColor(couleur));
		canvas.drawCircle(position.x, position.y, (float) rayon, p);

		Paint p2 = new Paint();
		p2.setTextSize((int) rayon);
		if (valeur == 2 || valeur == 4){
			p2.setColor(Color.BLACK);
		}
		else{
			p2.setColor(Color.WHITE);
		}
		switch (String.valueOf(valeur).length()){
			case 1:
				canvas.drawText(String.valueOf(valeur), position.x - (int) rayon/4, position.y + (int) rayon/4, p2);
				break;
			case 2:
				canvas.drawText(String.valueOf(valeur), position.x - (int) (3* (rayon/5)), position.y + (int) (3* (rayon/8)), p2);
				break;
			case 3:
				canvas.drawText(String.valueOf(valeur), position.x - (int) (4* (rayon/5)), position.y + (int) (3* (rayon/8)), p2);
				break;
			case 4:
				p2.setTextSize((int) (rayon * 0.7));
				canvas.drawText(String.valueOf(valeur), position.x - (int) (4* (rayon/5)), position.y + (int) rayon/4, p2);
				break;
		}
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
	void setMoving(boolean moving) {
		this.isMoving = moving;
	}
	void setVecteurVit(double[] vecteurVit) {
		this.vecteurVit = vecteurVit;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
}