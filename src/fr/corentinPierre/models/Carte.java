package fr.corentinPierre.models;

import java.io.Serializable;

import fr.corentinPierre.enums.Couleur;
import fr.corentinPierre.enums.Forme;

/**
 * Représente une carte du jeu Shape Up
 * @author Corentin
 * @author Pierre
 *
 */
public class Carte implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Forme forme;
	private Couleur couleur;
	/**
	 * Vrai si la carte est pleine, faux sinon
	 */
	private Boolean fillable;

	
	public Carte(Forme forme, Couleur couleur, Boolean fillable) {
		this.forme = forme;
		this.couleur = couleur;
		this.fillable = fillable;
	}
	
	/**
	 * Getter sur l'attribut forme
	 * <br>Retourne la forme de la carte
	 * @return Forme
	 */
	public Forme getForme() {
		return this.forme;
	}
	
	/**
	 * Getter sur l'attribut couleur
	 * <br>Retourne la couleur de la carte
	 * @return Couleur
	 */
	public Couleur getCouleur() {
		return this.couleur;
	}
	
	/**
	 * Getter sur l'attribut fillable
	 * @return boolean
	 */
	public boolean getFillable() {
		return this.fillable;
	}
	
	/**
	 * Méthode toString permettant l'affichage d'une carte
	 * @return String
	 */
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("\n***************");
		bf.append("\n" + this.forme);
		bf.append("\n" + this.couleur);
		bf.append("\n" + this.fillable);
		bf.append("\n***************");
		return bf.toString();
	}
	
	/**
	 * Retourne le nom de la carte qui correspond au nom de son fichier image associé 
	 * @return String le nom de la carte de type String et non nul
	 */
	public String getImageName() {
		String color = "", shape = "", fillable = "";
		switch (this.couleur) {
			case ROUGE: {
				color = "R";
				break;
			}
			case BLEU: {
				color = "B";
				break;
			}
			case VERT: {
				color = "V";
				break;
			}
		}
		switch (this.forme) {
			case CARRE: {
				shape = "C";
				break;
			}
			case CERCLE: {
				shape = "R";
				break;
			}
			case TRIANGLE: {
				shape = "T";
				break;
			}
		
		}
		if(this.fillable) {
			fillable = "R";
		} else {
			fillable = "V";
		}
		return shape + color + fillable;
	}
	
	
}
