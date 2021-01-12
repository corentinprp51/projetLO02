package fr.corentinPierre.models;

import java.util.ArrayList;
import java.util.Observable;

public class Configuration extends Observable{
	
	private String typePartie = "Classique";
	private ArrayList<Joueur> joueurs;
	private int nombreJoueurAttendu;
	private String etat;
	
	public Configuration() {
		this.joueurs = new ArrayList<>();
		this.nombreJoueurAttendu = 2;
	}
	
	public void setTypePartie(String tp) {
		this.typePartie = tp;
		this.etat = "config.changementTypePartie";
		this.setChanged();
		this.notifyObservers();
	}
	
	public void addJoueur(Joueur j) {
		this.joueurs.add(j);
		this.etat = "config.ajoutJoueur";
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setEtat(String e) {
		this.etat = e;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setNombreJoueursAttendu(int nbja) {
		this.nombreJoueurAttendu = nbja;
		this.etat = "config." + nbja + "joueurs";
		this.setChanged();
		this.notifyObservers();
	}
	
	public String getEtat() {
		return this.etat;
	}
	
	public String getTypePartie() {
		return this.typePartie;
	}
	
	public ArrayList<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	public int getNombreJoueursAttendu() {
		return nombreJoueurAttendu;
	}
	
	public void ajouterJoueur(Joueur j) {
		this.joueurs.add(j);
	}

}
