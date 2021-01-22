package fr.corentinPierre.models;
/**
 *Classe qui represente un joueur VIRTUEL débutant  dans le jeu SHapeUp
 * @author Corentin
 * @author Pierre
 **/
public class JoueurVirtuelDebutant extends JoueurVirtuel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur
	 * Le modejeu du joueur virtuel est configurer en PlayEasy
	 * @param id
	 * @param  nom
	 */
	
	public JoueurVirtuelDebutant(int id, String nom) {
		super(id, nom);
		this.modeJeu = new PlayEasy();
	}

}
