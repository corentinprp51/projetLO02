package fr.corentinPierre.models;

import java.io.Serializable;
/**
 * Repr�sentation abstraite d'une variante de Shape Up
 * @author Corentin
 * @author Pierre
 **/

public abstract class Variante implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 *nom de la variante
	 **/
	protected String nom;
	/**
	 *Partie dans laquelle la variante va �tre utilis�e
	 **/
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


	/**
	 * Initialise une partie
	 */
	abstract public void initialisation();
	
	abstract public void setCarteAPoser(Carte c);
	
	abstract public Carte getCarteAPoser();
	
	/**
	 * Pioche une carte
	 * @return Carte Carte pioch�e
	 */
	abstract public Carte piocher();
	
	

}
