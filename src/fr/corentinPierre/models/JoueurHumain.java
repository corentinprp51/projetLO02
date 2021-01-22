package fr.corentinPierre.models;

/**
 * Représente un joueur humain dans le jeu Shape Up
 * <br>Hérite de la classe Joueur
 * 
 * @author Corentin
 * @author Pierre
 * @see fr.corentinPierre.models.Joueur
 **/

public class JoueurHumain extends Joueur {

	private static final long serialVersionUID = 1L;
	
	public JoueurHumain(int id, String nom) {
		super(id, nom);
	}

}
