package fr.corentinPierre.models;
/**
 * Repr�sente un joueur virtuel dans le jeu Shape Up
 * <br>H�rite de la classe Joueur
 * 
 * @author Corentin
 * @author Pierre
 * @see fr.corentinPierre.models.Joueur
 **/

abstract public class JoueurVirtuel extends Joueur{

	private static final long serialVersionUID = 1L;
	/**
	 *Le mode de jeu du joueur virtuel
	 **/
	protected ModeJeuStrategy modeJeu;

	public JoueurVirtuel(int id, String nom) {
		super(id, nom);
	}
	
	/**
	 * D�termine les coordonn�es x,y o� le joueur virtuel peut poser sa carte
	 * @return int[] Un tableau d'entier sous la forme [x,y] correspondant aux coordon�es x et y du plateau
	 */

	public int[] choisirEmplacement() {
		return modeJeu.placement();
	}

}
