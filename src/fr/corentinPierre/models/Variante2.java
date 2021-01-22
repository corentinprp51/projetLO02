package fr.corentinPierre.models;
/**
 *Classe qui represente la Variante2  du jeu de SHapeUp
 * @author Corentin
 * @author Pierre
 **/


public class Variante2 extends Variante {

	
	private static final long serialVersionUID = 1L;
	/**
	 * Attribut contenant la carte qui sera poser 
	 */
	private Carte carteAPoser;
	
	/**
	 * Constructeur
	 * @param String
	 * @param Partie
	 */
	public Variante2(String nom, Partie p) {
		super(nom, p);
	}
	/**
	 *Initilisatiion de la partie,
	 *mélange des cartes,retrait de la carte Cachée
	 *attribution des carte de la main
	 */
	@Override
	public void initialisation() {
		this.partie.melangerCartes();
		this.partie.retirerCarteCachee();
		this.attribuerMain();
	}
	/**
	 * Méthode attribuant les cartes que chaque joueur aura en main 
	 */
	public void attribuerMain() {
		this.getPartie().getJoueurs().forEach(joueur -> {
			joueur.createMain();
			for(int i = 0; i < 3; i++) {
				joueur.addCarteToMain(this.partie.getDeck().removeFirst());
			}
		});
	}
	/**
	 * Setter de la carte a poser
	 * @param Carte
	 */
	
	public void setCarteAPoser(Carte c) {
		this.carteAPoser = c;
	}
	/**
	 * Getter de la carte a poser
	 * @return  Carte
	 */
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

	/**
	 *Une carte est piocher et enlever du deck 
	 *elle est ensuite ajouter a la main du joueur 
	 *@return Carte
	 */
	@Override
	public Carte piocher() {
		Carte c = this.partie.deck.removeFirst();
		partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()).addCarteToMain(c);
		return c;
	}
	

}
