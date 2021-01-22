package fr.corentinPierre.models;
/**
 * Repr�sente un joueur virtuel d�butant dans le jeu Shape Up
 * <br>H�rite de la classe JoueurVirtuel
 * 
 * @author Corentin
 * @author Pierre
 * @see fr.corentinPierre.models.JoueurVirtuel
 **/
public class JoueurVirtuelDebutant extends JoueurVirtuel{

	private static final long serialVersionUID = 1L;
	

	/**
	 * Passe la strat�gie de jeu en PlayEasy pour un joueur d�butant 
	 * @param id
	 * @param nom
	 * @see fr.corentinPierre.models.PlayEasy
	 */
	
	public JoueurVirtuelDebutant(int id, String nom) {
		super(id, nom);
		this.modeJeu = new PlayEasy();
	}

}
