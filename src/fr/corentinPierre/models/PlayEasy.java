package fr.corentinPierre.models;

import java.io.Serializable;
import java.util.Random;
/**
 * Repr�sente la strat�gie de jeu d'un joueur virtuel debutant
 * @author Corentin
 * @author Pierre
 * @see fr.corentinPierre.models.JoueurVirtuelDebutant
 * @see fr.corentinPierre.models.ModeJeuStrategy
 **/
public class PlayEasy implements ModeJeuStrategy, Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * D�termine les coordonn�es x,y o� le joueur virtuel peut poser sa carte<br>
	 * Les coordonn�es sont recherch�es � l&apos;aide d&apos;un nombre al�atoire entre 0 & 5 exclu pour y et 0 & 3 exclu pour x
	 * @return int[] Un tableau d&apos;entiers sous la forme [x,y] correspondant aux coordon�es x et y du plateau
	 */
	public int[] placement() {
		Random rX = new Random();
		int low = 0;
		int high = 3;
		int resultX = rX.nextInt(high-low) + low;
		Random rY = new Random();
		int lowY = 0;
		int highY = 5;
		int resultY = rY.nextInt(highY-lowY) + lowY;
		int[] tab = new int[2];
		tab[0] = resultX;
		tab[1] = resultY;
		return tab;
	}

}
