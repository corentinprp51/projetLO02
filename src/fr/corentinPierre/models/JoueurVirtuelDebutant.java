package fr.corentinPierre.models;
/**
 * Représente un joueur virtuel débutant dans le jeu Shape Up
 * <br>Hérite de la classe JoueurVirtuel
 * 
 * @author Corentin
 * @author Pierre
 * @see fr.corentinPierre.models.JoueurVirtuel
 **/
public class JoueurVirtuelDebutant extends JoueurVirtuel{

	private static final long serialVersionUID = 1L;
	

	/**
	 * Passe la stratégie de jeu en PlayEasy pour un joueur débutant 
	 * @param id
	 * @param nom
	 * @see fr.corentinPierre.models.PlayEasy
	 */
	
	public JoueurVirtuelDebutant(int id, String nom) {
		super(id, nom);
		this.modeJeu = new PlayEasy();
	}

}
