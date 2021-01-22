package fr.corentinPierre.models;

import java.util.ArrayList;
import java.util.Observable;


/**
 *Classe Configuration qui représente les variables necessaire a 
 *la configuration de la partie
 * 
 * @author Corentin Parpette
 * @author Pierre Treuchot
 * 
 * @see fr.corentinPierre.views.Configuration
 * @see fr.corentinPierre.models.Configuration
 * @see fr.corentinPierre.models.Partie
 *
 **/
public class Configuration extends Observable{
	/**
	 * Type de la partie configurer
	 */
	private String typePartie = "Classique";
	
	/**
	 * Listes des joueurs
	 */
	private ArrayList<Joueur> joueurs;
	
	/**
	 * Nombre de joueur attendu
	 */
	private int nombreJoueurAttendu;
	/**
	 * Etat de la partie
	 */
	
	private String etat;
	
	
	/**
	 * Constructeur
	 * Genère this.joueurs
	 */
	
	public Configuration() {
		this.joueurs = new ArrayList<>();
		this.nombreJoueurAttendu = 2;
	}
	
	/**
	 * Setter du type de la partie
	 * Changement de l'état de la partie
	 * @param String
	 */
	
	public void setTypePartie(String tp) {
		this.typePartie = tp;
		this.etat = "config.changementTypePartie";
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Ajout d'un joueur a la partie
	 * Changement de l'état de la partie
	 * @param Joueur
	 */
	
	public void addJoueur(Joueur j) {
		this.joueurs.add(j);
		this.etat = "config.ajoutJoueur";
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Setter de l'état de la partie
	 * @param String
	 */
	
	
	public void setEtat(String e) {
		this.etat = e;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Setter du nombre de joueur attendu
	 * Changement de l'état de la partie
	 * @param int
	 */
	
	public void setNombreJoueursAttendu(int nbja) {
		this.nombreJoueurAttendu = nbja;
		this.etat = "config." + nbja + "joueurs";
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Getter de tout les attributs
	 * de la classe configuration
	 * @return String
	 * @return ArrayList
	 * @return int
	 */
	
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
	/**
	 * Ajoute un joueur a la la listes des joueurs 
	 * @param Joueur
	 */
	
	
	public void ajouterJoueur(Joueur j) {
		this.joueurs.add(j);
	}

}
