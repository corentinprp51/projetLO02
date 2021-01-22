package fr.corentinPierre.models;

import java.io.Serializable;
/**
 *Classe qui represente les Variante que peut prendre le jeu
 * @author Corentin
 * @author Pierre
 **/

public abstract class Variante implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 *nom de la variante
	 **/
	protected String nom;
	/**
	 *Partie dans lequel la variante va etre utilis�e
	 **/
	protected Partie partie;
	
	/**
	 * Constructeur
	 * @param String
	 * @param Partie
	 */
	
	public Variante(String nom, Partie p) {
		this.nom = nom;
		this.partie = p;
	}
	/**
	 *Getter du nom de la variante
	 *@return String
	 */
	public String getNom() {
		return this.nom;
	}
	/**
	 * Getter de la partie
	 * @return Partie
	 */
	public Partie getPartie() {
		return this.partie;
	}

	/**
	 * Ensemble de m�thode abstraite qui devront etre d�finie dans les Classes h�ritant de Variante
	 */
	abstract public void initialisation();
	
	abstract public void setCarteAPoser(Carte c);
	
	abstract public Carte getCarteAPoser();
	
	abstract public Carte piocher();
	
	

}
