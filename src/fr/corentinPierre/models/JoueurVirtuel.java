package fr.corentinPierre.models;

abstract public class JoueurVirtuel extends Joueur{

	private static final long serialVersionUID = 1L;
	protected ModeJeuStrategy modeJeu;

	public JoueurVirtuel(int id, String nom) {
		super(id, nom);
	}

	public int[] choisirEmplacement() {
		// TODO Auto-generated method stub
		return modeJeu.placement();
	}

}
