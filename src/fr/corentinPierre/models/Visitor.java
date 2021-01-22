package fr.corentinPierre.models;
/**
 * Permet de visiter la classe Partie pour y calculer les scores
 * @author Corentin
 * @author Pierre
 **/
public interface Visitor {
	/**
	 * Permet de calculer le score d'un joueur
	 * @param p Partie à visiter
	 * @param id Identifiant du Joueur
	 * @return int Score du joueur
	 */
	abstract public int visit(Partie p, int id);

}
