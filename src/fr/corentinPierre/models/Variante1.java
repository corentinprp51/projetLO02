package fr.corentinPierre.models;

import java.util.Random;


public class Variante1 extends Variante{
	
	private static final long serialVersionUID = 1L;

	public Variante1(String nom, Partie p) {
		super(nom, p);
	}
	
	public void initialisation() {
		this.partie.melangerCartes();
		this.partie.retirerCarteCachee();
		this.attribuerCartesVictoires();
		this.partie.piocher();
	}
	
	private void attribuerCartesVictoires() {
		for(int i = 0; i<this.partie.joueurs.size(); i++) {
			Random rand = new Random();
			int randIndex = rand.nextInt(this.partie.deck.size());
			this.partie.joueurs.get(i).setCarteVictoire(this.partie.deck.get(randIndex));
			this.partie.deck.remove(randIndex);
		}
	}
	

	@Override
	public void setCarteAPoser(Carte c) {}

	@Override
	public Carte getCarteAPoser() {
		return null;
	}

	@Override
	public Carte piocher() {
		this.partie.cartePiochee = this.partie.deck.remove(0);
		return this.partie.cartePiochee;
	}

}
