package fr.corentinPierre.models;
/**
 *Interface Visitable imp�ment� par Partie
 * @author Corentin
 * @author Pierre
 **/
public interface Visitable {
	/**
	 *M�thode abstraite qui devra etre impl�menter dans Partie
	 *@param Visitor
	 *@param int
	 **/
	public abstract int accept(Visitor visitor, int id);
}
