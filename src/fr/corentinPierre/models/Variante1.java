package fr.corentinPierre.models;

import java.util.Random;
/**
 *Classe qui represente la Variante1  du jeu de SHapeUp
 * @author Corentin
 * @author Pierre
 **/

public class Variante1 extends Variante{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Constructeur
	 * @param String
	 * @param Partie
	 */
	
	public Variante1(String nom, Partie p) {
		super(nom, p);
	}
	/**
	 *Initilisatiion de la partie,
	 *mélange des cartes,retrait de la carte Cachée
	 *et attribution des cartes victoires
	 *La premiere carte est piocher
	 */
	public void initialisation() {
		this.partie.melangerCartes();
		this.partie.retirerCarteCachee();
		this.attribuerCartesVictoires();
		this.partie.piocher();
	}
	/**
	 *Une carte victoire est attribuée a tout les joueurs puis  enlever du deck
	 */
	private void attribuerCartesVictoires() {
		for(int i = 0; i<this.partie.joueurs.size(); i++) {
			Random rand = new Random();
			int randIndex = rand.nextInt(this.partie.deck.size());
			this.partie.joueurs.get(i).setCarteVictoire(this.partie.deck.get(randIndex));
			this.partie.deck.remove(randIndex);
		}
	}
	
	/**
	 Setter de la carteAPoser
	 *@param Carte
	 */
	@Override
	public void setCarteAPoser(Carte c) {}

	
	/**
	 * Getter de la carte a Poser
	 * @return Carte
	 */
	@Override
	public Carte getCarteAPoser() {
		return null;
	}
	/**
	 *Une carte est piocher et enlever du deck 
	 *@return Carte
	 */
	@Override
	public Carte piocher() {
		this.partie.cartePiochee = this.partie.deck.remove(0);
		return this.partie.cartePiochee;
	}

}
