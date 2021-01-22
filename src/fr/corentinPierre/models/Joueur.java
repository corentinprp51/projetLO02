package fr.corentinPierre.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Représente un joueur abstrait dans le jeu Shape Up
 * @author Corentin
 * @author Pierre
 * 
 **/

public abstract class Joueur implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * id du joueur
	 */
	protected int id;
	/**
	 * nom du joueur
	 */
	protected String nom;
	/**
	 * score du joueur
	 */
	protected int score;
	
	/**
	 * carteVictoire du joueur
	 */
	protected Carte carteVictoire;
	
	/**
	 * Main du joueur
	 */
	protected ArrayList<Carte> main;
	
	
	/**
	 * Défini une valeur par défaut du score à 0.
	 */
	
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
	
	/**
	 * Méthode toString permettant l'affichage d'un joueur
	 * @return String
	 */
	
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("\nJoueur " + this.id + ": " + this.nom);
		bf.append("\nCarte victoire: " + this.carteVictoire);
		bf.append("\nPoints: " + this.score);
		return bf.toString();
	}
	
	/**
	 * Création de la main du joueur
	 */
	public void createMain() {
		this.main = new ArrayList<Carte>();
	}
	
	/**
	 * Ajout d'une carte du plateau dans la main du joueur
	 * <br>On ajoute la carte si et seulement si la taille de la main du joueur n'excède pas trois
	 * @param c Carte piochée à ajouter dans la main
	 */
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
	/**
	 * Supprime la carte sélectionnée de la main du joueur
	 * @param c Carte de la main à retirer
	 * @return boolean Vrai si la carte a bien été supprimée, faux sinon
	 */
	public boolean removeCarteFromMainBool(Carte c) {
		return this.main.remove(c);
	}
	/**
	 * Supprime la carte sélectionnée de la main du joueur
	 * @param c Id de la carte de la main à retirer
	 * @return boolean Vrai si la carte a bien été supprimée, faux sinon
	 */
	public Carte removeCarteFromMainByIndex(int c) {
		return this.main.remove(c);
	}
	public ArrayList<Carte> getMain(){
		return this.main;
	}
	/**
	 * (Vue Console) Affiche la main du joueur
	 */
	public void afficherMain() {
		for(int i = 0; i < this.getMain().size(); i++) {
			System.out.println(this.getMain().get(i));
		}
	}
	
}
