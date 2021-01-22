package fr.corentinPierre.models;
/**
 * Interface Visitable suivant le patron de conception Visitor utilis� pour le calcul des scores
 * @author Corentin
 * @author Pierre
 **/
public interface Visitable {
	/**
	 *M�thode qui retournera le Score du Joueur une fois impl�ment�
	 *@param visitor
	 *@param id Id du Joueur
	 *@return int Score du joueur
	 **/
	public abstract int accept(Visitor visitor, int id);
}
