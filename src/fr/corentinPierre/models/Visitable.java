package fr.corentinPierre.models;
/**
 *Interface Visitable impémenté par Partie
 * @author Corentin
 * @author Pierre
 **/
public interface Visitable {
	/**
	 *Méthode abstraite qui devra etre implémenter dans Partie
	 *@param Visitor
	 *@param int
	 **/
	public abstract int accept(Visitor visitor, int id);
}
