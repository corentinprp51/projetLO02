package fr.corentinPierre.models;

public class JoueurVirtuel extends Joueur{
	
	protected ModeJeuStrategy modeJeu;

	public JoueurVirtuel(int id, String nom) {
		super(id, nom);
	}


	@Override
	public int[] choisirEmplacement() {
		// TODO Auto-generated method stub
		return modeJeu.placement();
	}

	@Override
	public String choisirAction() {
		// TODO Auto-generated method stub
		return modeJeu.action();
	}

	@Override
	public boolean demandePoserCarte() {
		// TODO Auto-generated method stub
		return modeJeu.demanderPoserCarte();
	}


}
