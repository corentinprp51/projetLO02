package fr.corentinPierre.models;
/**
 * Strat�gie du joueur virtuel
 * @author Corentin
 * @author Pierre
 **/
public interface ModeJeuStrategy {
	
	/**
	 * D�termine les coordonn�es x,y o� le joueur virtuel peut poser sa carte.
	 * <br>En fonction de la difficult�, la m�thode de recherche de coordonn�es diff�re
	 * @return int[] Un tableau d'entier sous la forme [x,y] correspondant aux coordon�es x et y du plateau
	 */
	abstract public int[] placement();

}

