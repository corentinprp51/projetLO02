package fr.corentinPierre.models;

import java.io.Serializable;
import java.util.Random;
/**
 *Classe qui represente la stratégie de jeu d'un joueur virtuel debutant
 *elle implément ModeJeuStategy et Serializable
 * @author Corentin
 * @author Pierre
 **/
public class PlayEasy implements ModeJeuStrategy, Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 *Méthode qui va determiner la case sur laquel le joueur virtuel debutant va poser sa carte
	 *(Ici les coordoner sont choisie au hasard)
	 *@return int[]
	 **/
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
