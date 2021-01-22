package fr.corentinPierre.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *Classe qui represente un joueur dans le jeu SHapeUp
 * @author Corentin
 * @author Pierre
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
	 * Carte dans la main du joueur
	 */
	protected ArrayList<Carte> main;
	
	
	/**
	 * Constructeur
	 * @param id
	 * @param  nom
	 * Met le score du joueur a 0
	 */
	
	public Joueur(int id, String nom) {
		this.id = id;
		this.nom = nom;
		this.score = 0;
	}
	/**
	 * Getter du nom du joueur
	 * @return String
	 */
	public String getNom() {
		return this.nom;
	}	
	/**
	 * Setter de la carte victoire du joueur
	 * @param Carte
	 */
	public void setCarteVictoire(Carte c) {
		this.carteVictoire = c;
	}
	
	/**
	 * Getter de la carte victoire  du joueur
	 * @return Carte
	 */
	public Carte getCarteVictoire() {
		return this.carteVictoire;
	}
	
	/**
	 * Getter du score du joueur
	 * @return int
	 */
	public int getScore() {
		return this.score;
	}
	/**
	 * Setter du score du joueur
	 * @param int
	 */
	
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Méthode toString permettant l'affichage des attributs d'un joueur
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
	 *Création de la main du joueur
	 */
	
	public void createMain() {
		this.main = new ArrayList<Carte>();
	}
	
	/**
	 *Ajout d'une carte du plateau dans la main du joueur
	 *La taille de la main ne peut exceder trois 
	 *on controle donc sa taille et les emplacement vide 
	 *@param Carte
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
	 *Enlevement d'une carte de la main par Carte
	 *@param Carte
	 *@return boolean
	 */
	public boolean removeCarteFromMainBool(Carte c) {
		return this.main.remove(c);
	}
	/**
	 *Enlevement d'une carte de la main par Index
	 *@param int
	 *@return Carte
	 */
	public Carte removeCarteFromMainByIndex(int c) {
		return this.main.remove(c);
	}
	/**
	 *Getter de la main du joueur
	 *@return ArrayList
	 */
	public ArrayList<Carte> getMain(){
		return this.main;
	}
	/**
	 *Affichage de la main du joueur
	 */
	public void afficherMain() {
		for(int i = 0; i < this.getMain().size(); i++) {
			System.out.println(this.getMain().get(i));
		}
	}
	
}
