package fr.corentinPierre.models;

public class JoueurVirtuelDebutant extends JoueurVirtuel{

	public JoueurVirtuelDebutant(int id, String nom) {
		super(id, nom);
		this.modeJeu = new PlayEasy();
	}

}
