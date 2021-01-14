package fr.corentinPierre.models;

import java.io.Serializable;

public abstract class Variante implements Serializable{
	protected String nom;
	protected Partie partie;
	public Variante(String nom, Partie p) {
		this.nom = nom;
		this.partie = p;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public Partie getPartie() {
		return this.partie;
	}
	
	abstract public void initialisation();

	public abstract void setCarteAPoser(Carte c);
	abstract public Carte getCarteAPoser();
	abstract public Carte piocher();
	
	

}
