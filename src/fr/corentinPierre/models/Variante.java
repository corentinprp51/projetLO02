package fr.corentinPierre.models;

import java.io.Serializable;

public abstract class Variante implements Serializable{
	private static final long serialVersionUID = 1L;
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
	abstract public void setCarteAPoser(Carte c);
	abstract public Carte getCarteAPoser();
	abstract public Carte piocher();
	
	

}
