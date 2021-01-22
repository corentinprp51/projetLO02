package fr.corentinPierre.models;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Joueur implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected int id;
	protected String nom;
	protected int score;
	protected Carte carteVictoire;
	protected ArrayList<Carte> main;
	
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
	
	public void createMain() {
		this.main = new ArrayList<Carte>();
	}
	
	public void addCarteToMain(Carte c) {
		if(this.main.size() < 3) {
			this.main.add(c);
		} else {
			if(this.main.size() == 3) {
				for(int i = 0; i < this.main.size(); i++) {
					if(this.main.get(i) == null) {
						this.main.add(i, c);
						return;
					}
				}
			}
			}
		}
	
	public boolean removeCarteFromMainBool(Carte c) {
		return this.main.remove(c);
	}
	
	public Carte removeCarteFromMainByIndex(int c) {
		return this.main.remove(c);
	}
	
	public ArrayList<Carte> getMain(){
		return this.main;
	}
	
	public void afficherMain() {
		for(int i = 0; i < this.getMain().size(); i++) {
			System.out.println(this.getMain().get(i));
		}
	}
	
}
