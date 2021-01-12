package fr.corentinPierre.models;

public abstract class Joueur {
	
	protected int id;
	protected String nom;
	protected int score;
	protected Carte carteVictoire;
	
	public Joueur(int id, String nom) {
		this.id = id;
		this.nom = nom;
		this.score = 0;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setCarteVictoire(Carte c) {
		this.carteVictoire = c;
	}
	
	public Carte getCarteVictoire() {
		return this.carteVictoire;
	}
	
	public int getScore() {
		return this.score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("\nJoueur " + this.id + ": " + this.nom);
		bf.append("\nCarte victoire: " + this.carteVictoire);
		bf.append("\nPoints: " + this.score);
		return bf.toString();
	}
	
	abstract public int[] choisirEmplacement();
	abstract public String choisirAction();
	abstract public boolean demandePoserCarte();
	
	

	
	
}
