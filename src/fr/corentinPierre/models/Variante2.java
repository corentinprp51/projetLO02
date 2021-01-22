package fr.corentinPierre.models;
/**
 * Représentation de la variante avancée du Shape Up
 * <br>Hérite de Variante
 * @author Corentin
 * @author Pierre
 * @see fr.corentinPierre.models.Variante
 **/


public class Variante2 extends Variante {

	
	private static final long serialVersionUID = 1L;
	/**
	 * Attribut contenant la carte choisie par le joueur pour le placement sur le plateau 
	 */
	private Carte carteAPoser;
	
	public Variante2(String nom, Partie p) {
		super(nom, p);
	}
	/**
	 * Initialise la partie. 
	 * <br>Mélange les cartes, retire la carte cachée
	 * <br>Constitue les mains des joueurs
	 */
	@Override
	public void initialisation() {
		this.partie.melangerCartes();
		this.partie.retirerCarteCachee();
		this.attribuerMain();
	}
	/**
	 * Remplit la main de chaque joueur de 3 cartes dans le deck.
	 */
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

	/**
	 * Pioche une carte et l'ajoute à la main du joueur
	 * @return Carte Carte piochée
	 */
	@Override
	public Carte piocher() {
		Carte c = this.partie.deck.removeFirst();
		partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()).addCarteToMain(c);
		return c;
	}
	

}
