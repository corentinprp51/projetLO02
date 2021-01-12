package fr.corentinPierre.models;

public abstract class Variante {
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
	

}
