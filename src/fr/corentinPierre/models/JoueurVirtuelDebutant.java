package fr.corentinPierre.models;

public class JoueurVirtuelDebutant extends JoueurVirtuel{

	private static final long serialVersionUID = 1L;

	public JoueurVirtuelDebutant(int id, String nom) {
		super(id, nom);
		this.modeJeu = new PlayEasy();
	}

}
