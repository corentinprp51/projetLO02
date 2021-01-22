package fr.corentinPierre.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.TreeMap;

import fr.corentinPierre.enums.Couleur;
import fr.corentinPierre.enums.Forme;

/**
 *Classe qui represente une partie dans le jeu SHapeUp
 * @author Corentin
 * @author Pierre
 **/



public class Partie extends Observable implements Serializable, Visitable {
	/**
	 * nombre de round 
	 */
	protected int round;
	/**
	 * Liste des Cartes encore dans le deck 
	 */
	protected LinkedList<Carte> deck;
	/**
	 * Liste des joueurs jouant la partie
	 */
	protected ArrayList<Joueur> joueurs;
	/**
	 * Plateau sur lequel la partie se deroule
	 */
	protected Plateau plateau;
	
	/**
	 * Carte qui sera enlever du deck en début de la partie
	 */
	protected Carte carteSupp;
	/**
	 * Etat de la partie
	 */
	protected String etat = "";
	/**
	 * Carte piocher par un joueur
	 */
	protected Carte cartePiochee;
	/**
	 * Carte choisie par le joueur qui veut la deplacer
	 */
	protected Carte carteADeplacer;
	
	/**
	 * Attribut qui stock si un joueur a deja deplacer une carte ou non 
	 */
	protected boolean alreadyDeplacee;
	
	/**
	 * Variante de la  partie 
	 */
	protected Variante regle;
	
	/**
	 * La partie a été sauvegarder ou non 
	 */
	protected boolean loaded = false;
	
	/**
	 * Constructeur
	 * Initialise la partie au round 0
	 * Créer une liste ( Liste des cartes)
	 * Créer une autre liste (Liste des joueurs)
	 * @param Plateau
	 */
	public Partie(Plateau plateau) {
		this.round = 0;
		this.plateau = plateau;
		this.deck = new LinkedList<Carte>();
		this.joueurs = new ArrayList<Joueur>();
	}
	
	/**
	 * Setter de la Variante 
	 * @param Variante
	 */
	
	public void setVariante(Variante v) {
		this.regle = v;
	}
	/**
	 * Getter de la Variante 
	 * @return Variante
	 */
	public Variante getVariante() {
		return this.regle;
	}
	
	/**
	 * Getter du round
	 * @return int
	 */
	public int getRound() {
		return this.round;
	}
	/**
	 * Savoir si une partie a été sauvegarder
	 * @return boolean
	 */
	public boolean getLoaded() {
		return this.loaded;
	}
	/**
	 *Création des cartes, ajout des cartes au deck 
	 *puis mélange du deck 
	 */
	public void melangerCartes() {
		for(int j = 0; j < Forme.values().length; j++) {
			for (int k = 0; k < Couleur.values().length; k++) {
				for(int l = 0; l < 2; l++) {
					boolean fillable;
					if(l == 0) fillable = false;
					else fillable = true;
					Carte carte = new Carte(Forme.values()[j], Couleur.values()[k], fillable);
					this.deck.add(carte);
				}
			}
		}
		Collections.shuffle(this.deck);
	}
	
	/**
	 * Ajouter un joueur à la partie
	 * @param Joueur j
	 */
	public void ajouterJoueur(Joueur j) {
		this.joueurs.add(j);
	}
	
	/**
	 * Getter de la listes des joueurs jouant la partie
	 * @return ArrayList
	 */
	
	public ArrayList<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	
	/**
	 * Getter sur la Carte supprimer en début de partie
	 * @return Carte
	 */
	public Carte getCarteSupp() {
		return this.carteSupp;
	}
	
	/**
	 * Getter sur le plateau
	 * @return Plateau
	 */
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	/**
	 * Piocher une carte
	 * @return Carte
	 */
	public Carte piocher() {
		return regle.piocher();
		
	}
	/**
	 * En fonction de la variante choisie,la fonction permet de :
	 * Variante Refill : la fonction va regenerer les Cartes, remplir et remelanger le deck
	 * et ensuite elle incrémante le round et change l'état et l'attribut alredy deplacee de la partie
	 * En Variante Classique et expert elle se contente d'incrémanter le round et change l'état et l'attribut already deplacee de la partie
	 *
	 */
	public void nouveauRound() {
		if(this.getVariante().getNom().equalsIgnoreCase("Refill")) {
			if(this.getPlateau().getNbCartesPosees() == 7) {
				LinkedList<Carte> refill = new LinkedList<Carte>();
				for(int j = 0; j < Forme.values().length; j++) {
					for (int k = 0; k < Couleur.values().length; k++) {
						for(int l = 0; l < 2; l++) {
							boolean fillable;
							if(l == 0) fillable = false;
							else fillable = true;
							Carte carte = new Carte(Forme.values()[j], Couleur.values()[k], fillable);
							refill.add(carte);
						}
					}
				}
				for(int i = 0; i < 10; i++) {
					this.deck.add(refill.get(i));
				}
				Collections.shuffle(this.deck);
			}
			this.round++;
			this.alreadyDeplacee = false;
			this.etat = "finTour";
			this.setChanged();
			this.notifyObservers();
		} else {
			this.round++;
			this.alreadyDeplacee = false;
			this.etat = "finTour";
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	/**
	 * Getter du deck
	 * @return LinkedList
	 */
	public LinkedList<Carte> getDeck(){
		return this.deck;
	}
	
	/**
	 * Getter de l'état de la partie
	 * @return String
	 */
	public String getEtat() {
		return this.etat;
	}
	/**
	 * Setter de l'état de la partie
	 * @param String
	 */
	public void setEtat(String etat) {
		this.etat = etat;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Getter de la carte piochée durant se tour
	 * @return Carte
	 */
	public Carte getCartePiochee() {
		return this.cartePiochee;
	}
	/**
	 * Getter de la carte a deplacer
	 * @return Carte
	 */
	public Carte getCarteADeplacer() {
		return this.carteADeplacer;
	}
	/**
	 * Getter l'état de l'attribut AlreadyDeplacee
	 * @return Boolean
	 */
	public boolean getAlreadyDeplacee() {
		return this.alreadyDeplacee;
	}
	
	/**
	 * Méthode qui determine quel joueur a gagner la partie
	 * @return Joueur
	 */
	public Joueur getVainqueur() {
		Joueur vainqueur = null;
		for(int i = 0; i < this.getJoueurs().size(); i++) {
			if(vainqueur == null || this.getJoueurs().get(i).getScore() > vainqueur.getScore()) {
				vainqueur = this.getJoueurs().get(i);
			}
		}
		return vainqueur;
	}
	/**
	 * Méthode qui enleve une carte aléatoire du deck

	 */
	protected void retirerCarteCachee() {
		Random rand = new Random();
		int randIndex = rand.nextInt(this.deck.size());
		this.carteSupp = this.deck.get(randIndex);
		this.deck.remove(randIndex);
	}
	
	/**
	 * Méthode toString permettant l'affichage des attributs de la partie
	 * @return String
	 */
	
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("\nPartie ");
		bf.append("\nNombre de cartes restant: " + this.deck.size());
		bf.append("\nJoueurs");
		Iterator<Joueur> it = this.joueurs.iterator();
		while(it.hasNext()) {
			bf.append("\n" + it.next());
		}
		return bf.toString();
	}
	
	/**
	 * Méthode intialisant la partie en fonction de la variante choisie
	 * et qui change l'état de la partie
	 */
	public void initialisation() {
		this.regle.initialisation();
		this.etat = "initialisation";
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Méthode s'occupant de la pose des cartes en fonction des variantes
	 * 
	 * @param int coordoner x 
	 * @parem int coordoner y 
	 * @return Boolean en fonction de si la pose de carte a été un succès ou non
	 */
	
	
	public boolean poserCarte(int x, int y) {
		int [] coordonnees = {x,y};
		if(this.round == 0) {
			if(this.getVariante().getNom().equalsIgnoreCase("Avance")) {
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.getVariante().getCarteAPoser());
				this.getVariante().setCarteAPoser(null);
			} else {
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.cartePiochee);	
			}
		} else {
			if(this.getPlateau().getCartesPosees().containsKey(coordonnees[1]) && this.getPlateau().getCartesPosees().get(coordonnees[1]).containsKey(coordonnees[0])) {
				return false;
			} else if (!this.getPlateau().checkAdjacence(coordonnees[0], coordonnees[1])) {
				return false;
			} else if (!this.getPlateau().checkBaseRule(coordonnees[0], coordonnees[1])) {
				return false;
			}
			if(this.getVariante().getNom().equalsIgnoreCase("Avance")) {
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.getVariante().getCarteAPoser());
				this.getVariante().setCarteAPoser(null);
			} else {
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.cartePiochee);
			}
		}
		this.cartePiochee = null;
		this.etat = "poser" + coordonnees[0] + coordonnees[1];
		this.setChanged();
		this.notifyObservers();
		return true;
	}
	
	/**
	 * Méthode permettant de selectionner une carte a deplacer 
	 * @param int coordoner x de la carte voulu
	 * @parem int coordoner y de la carte voulu
	 * @return Carte carte choisie par le joueur
	 */
	
	
	public Carte prendreCarteADeplacer(int x, int y) {
		int [] emplacement = {x,y};
		if(!this.getPlateau().getCartesPosees().containsKey(emplacement[1])) {
			return null;
		}
		if(!this.getPlateau().getCartesPosees().get(emplacement[1]).containsKey(emplacement[0])) {
			return null;
		}
		this.carteADeplacer = this.getPlateau().getCartesPosees().get(emplacement[1]).remove(emplacement[0]);
		this.etat = "carteADeplacer" + x + y;
		this.setChanged();
		this.notifyObservers();
		return this.carteADeplacer;
	}
	/**
	 * Méthode permettant de replacer sur le plateau la carte que le joueur souhaite deplacer
	 * @param Carte 
	 * @param int coordoner x 
	 * @parem int coordoner y 
	 * @return boolean
	 */
	

	public boolean deplacerCarte(Carte carte, int x, int y) {
		int [] newCoordonnees = {x,y};
			if(this.getPlateau().getCartesPosees().containsKey(newCoordonnees[1]) && this.getPlateau().getCartesPosees().get(newCoordonnees[1]).containsKey(newCoordonnees[0])) {
				return false;
			} else if (!this.getPlateau().checkAdjacence(newCoordonnees[0], newCoordonnees[1])) {
				return false;
			} else if (!this.getPlateau().checkBaseRule(newCoordonnees[0], newCoordonnees[1])) {
				return false;
			}
		this.getPlateau().setCartesPosees(newCoordonnees[0], newCoordonnees[1], carte);
		this.alreadyDeplacee = true;
		this.etat = "deplacerCarte" + newCoordonnees[0] + newCoordonnees[1];
		this.setChanged();
		this.notifyObservers();
		return true;
	}
	
	/**
	 * Méthode vérifiant si tout les joueurs ont une carte en main
	 * @return boolean
	 */
	
	public boolean allPlayerHaveOneCard() {
		int compteur = 0;
		for(int i = 0; i<this.getJoueurs().size(); i++) {
			if (this.joueurs.get(i).getMain().size() == 1) {
				compteur++;
			}
		}
		if(compteur == this.getJoueurs().size()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Méthode qui affecte son score  a chaque joueur 
	 * et change l'état de la partie en "finPartie"
	 */
	
	public void calculerScore() {
		for(int i = 0; i<this.joueurs.size(); i++) {
			this.joueurs.get(i).setScore(this.accept(new ScoreVisitor(), i));
		}
		this.setEtat("finPartie");
	}
	
	/**
	 * Méthode permettant de sauvegarde la partie
	 */
	
	public static void savePartie(Partie p, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(p);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui indique si la partie a été sauvegardée
	 */
	public void setLoaded(boolean l) {
		this.loaded = l;
	}
	
	/**
	 * Méthode permettant d'importer une partie sauvegardée
	 */
	public static Partie loadPartie(String fileName) {
		Partie p = new Partie(new Plateau());
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			p = (Partie) ois.readObject();
			p.setLoaded(true);
			fis.close();
		} catch (FileNotFoundException e) {
			System.out.println("Pas de dernière sauvegarde de parties");
		} catch (IOException e) {
			System.out.println("Pas de dernière sauvegarde de parties");
		} catch (ClassNotFoundException e) {
			System.out.println("Pas de dernière sauvegarde de parties");
		}
		return p;
		
	}

	@Override
	public int accept(Visitor visitor, int id) {
		return visitor.visit(this, id);
	}
	
}
