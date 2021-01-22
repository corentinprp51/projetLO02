package fr.corentinPierre.models;
/**
 *Interface implement� par PlayEasy
 * @author Corentin
 * @author Pierre
 **/
public interface ModeJeuStrategy {
	
	/**
	 *M�thode abstraite qui devra etre d�finie dans PlayEasy
	 *@return int[]
	 **/
	abstract public int[] placement();

}

