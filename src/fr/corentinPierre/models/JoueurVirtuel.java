package fr.corentinPierre.models;
/**
 *Classe qui represente un joueur VIRTUEL dans le jeu SHapeUp
 * @author Corentin
 * @author Pierre
 **/

abstract public class JoueurVirtuel extends Joueur{

	private static final long serialVersionUID = 1L;
	/**
	 *Le mode de jeu du joueur virtuel
	 **/
	protected ModeJeuStrategy modeJeu;
	/**
	 * Constructeur
	 * @param id
	 * @param  nom
	 */
	public JoueurVirtuel(int id, String nom) {
		super(id, nom);
	}
	
	
	/**
	 *Choix des coordonner ou placer la carte
	 *@return int[]
	 */

	public int[] choisirEmplacement() {
		// TODO Auto-generated method stub
		return modeJeu.placement();
	}

}
