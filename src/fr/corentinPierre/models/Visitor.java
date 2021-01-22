package fr.corentinPierre.models;
/**
 *Interface Visiteur imp�ment� par ScoreVisitor
 * @author Corentin
 * @author Pierre
 **/
public interface Visitor {
	/**
	 * M�thode abstraite qui devra etre d�finie dans ScoreVisitor
	 **/
	abstract public int visit(Partie p, int id);

}
