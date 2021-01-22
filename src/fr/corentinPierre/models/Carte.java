package fr.corentinPierre.models;

import java.io.Serializable;

import fr.corentinPierre.enums.Couleur;
import fr.corentinPierre.enums.Forme;

/**
 * Classe Carte qui représente une carte du jeu ShapeUp
 * @author Corentin
 * @author Pierre
 *
 */
public class Carte implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Forme forme;
	private Couleur couleur;
	private Boolean fillable;
	
	/**
	 * Constructeur
	 * @param forme
	 * @param couleur
	 * @param fillable
	 */
	public Carte(Forme forme, Couleur couleur, Boolean fillable) {
		this.forme = forme;
		this.couleur = couleur;
		this.fillable = fillable;
	}
	
	/**
	 * Getter sur l'attribut forme
	 * Retourne la forme de la carte
	 * @return Forme
	 */
	public Forme getForme() {
		return this.forme;
	}
	
	/**
	 * Getter sur l'attribut couleur
	 * Retourne la couleur de la carte
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
