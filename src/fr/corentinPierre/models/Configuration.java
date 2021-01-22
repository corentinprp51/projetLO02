package fr.corentinPierre.models;

import java.util.ArrayList;
import java.util.Observable;


/**
 * Permet la Configuration d'une partie de jeu Shape Up
 * <br>Objet observable qui va notifier ses observateurs à chaque modification.
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
	 * Etat de la configuration
	 */
	
	private String etat;
	
	
	/**
	 * Instancie une nouvelle ArrayList pour l'attribut joueurs
	 * <br>Définit une valeur par défaut à 2 pour l'attribut nombreJoueurAttendu
	 * 
	 */
	
	public Configuration() {
		this.joueurs = new ArrayList<>();
		this.nombreJoueurAttendu = 2;
	}
	
	/**
	 * Setter du type de la partie
	 * <br>Changement de l'état de la partie
	 * @param tp
	 */
	
	public void setTypePartie(String tp) {
		this.typePartie = tp;
		this.etat = "config.changementTypePartie";
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Ajout d'un joueur à la configuration
	 * <br>Changement de l'état de la partie
	 * @param j
	 * @see fr.corentinPierre.models.Joueur
	 */
	
	public void addJoueur(Joueur j) {
		this.joueurs.add(j);
		this.etat = "config.ajoutJoueur";
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Setter de l'état de la configuration
	 * @param e
	 */
	
	
	public void setEtat(String e) {
		this.etat = e;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Setter du nombre de joueur attendu
	 * <br>Changement de l'état de la partie
	 * @param nbja
	 */
	
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


}
