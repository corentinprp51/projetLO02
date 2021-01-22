package fr.corentinPierre.models;
/**
 *Interface implementé par PlayEasy
 * @author Corentin
 * @author Pierre
 **/
public interface ModeJeuStrategy {
	
	/**
	 *Méthode abstraite qui devra etre définie dans PlayEasy
	 *@return int[]
	 **/
	abstract public int[] placement();

}

