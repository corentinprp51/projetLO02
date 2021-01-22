package fr.corentinPierre.models;
/**
 * Stratégie du joueur virtuel
 * @author Corentin
 * @author Pierre
 **/
public interface ModeJeuStrategy {
	
	/**
	 * Détermine les coordonnées x,y où le joueur virtuel peut poser sa carte.
	 * <br>En fonction de la difficulté, la méthode de recherche de coordonnées diffère
	 * @return int[] Un tableau d'entier sous la forme [x,y] correspondant aux coordonées x et y du plateau
	 */
	abstract public int[] placement();

}

