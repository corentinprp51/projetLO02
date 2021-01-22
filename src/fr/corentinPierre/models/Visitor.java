package fr.corentinPierre.models;
/**
 *Interface Visiteur impémenté par ScoreVisitor
 * @author Corentin
 * @author Pierre
 **/
public interface Visitor {
	/**
	 * Méthode abstraite qui devra etre définie dans ScoreVisitor
	 **/
	abstract public int visit(Partie p, int id);

}
