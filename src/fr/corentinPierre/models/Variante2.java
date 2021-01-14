package fr.corentinPierre.models;

public class Variante2 extends Variante {

	private Carte carteAPoser;
	public Variante2(String nom, Partie p) {
		super(nom, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialisation() {
		// TODO Auto-generated method stub
		this.partie.melangerCartes();
		this.partie.retirercarteCachee();
		this.attribuerMain();
	}
	
	public void attribuerMain() {
		this.getPartie().getJoueurs().forEach(joueur -> {
			joueur.createMain();
			for(int i = 0; i < 3; i++) {
				joueur.addCarteToMain(this.partie.getDeck().removeFirst());
			}
		});
	}
	
	public void setCarteAPoser(Carte c) {
		this.carteAPoser = c;
	}
	
	public Carte getCarteAPoser() {
		return this.carteAPoser;
	}
	
	public static void main(String[] args) {
		Partie p = new Partie(new Plateau());
		p.ajouterJoueur(new JoueurHumain(0, "Corentin"));
		p.ajouterJoueur(new JoueurHumain(1, "Coco"));
		p.ajouterJoueur(new JoueurHumain(2, "Cocorentin"));
		Variante2 v = new Variante2("Avancé", p);
		v.initialisation();
	}

	@Override
	public Carte piocher() {
		Carte c = this.partie.deck.removeFirst();
		partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()).addCarteToMain(c);
		return c;
	}
	

}
