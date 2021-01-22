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
 * Repr�sente une partie de jeu Shape Up.
 * <br>Une Partie poss�de �galement une Variante (Variante1, Variante2 ou Variante3).
 * <br>Les scores de la partie sont calcul�s � l'aide du patron de conception Visitor.
 * <br>La classe Partie est un observable donc elle h�rite de la classe Observable.
 * @author Corentin
 * @author Pierre
 * @see fr.corentinPierre.models.Visitable
 **/



public class Partie extends Observable implements Serializable, Visitable {
	/**
	 * Nombre de rounds 
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
	 * Plateau sur lequel la partie se d�roule
	 */
	protected Plateau plateau;
	
	/**
	 * Carte qui sera enlev�e du deck en d�but de la partie
	 */
	protected Carte carteSupp;
	/**
	 * Etat de la partie
	 */
	protected String etat = "";
	/**
	 * Carte pioch�e par un joueur
	 */
	protected Carte cartePiochee;
	/**
	 * Carte du plateau choisie par le joueur qui veut la d�placer
	 */
	protected Carte carteADeplacer;
	
	/**
	 * Permet de savoir si un joueur a d�j� d�plac� une carte ou nom pendant ce tour. 
	 */
	protected boolean alreadyDeplacee;
	
	/**
	 * Variante de la  partie
	 */
	protected Variante regle;
	
	/**
	 * Permet de savoir si la partie est une partie sauvegard�e. 
	 */
	protected boolean loaded = false;
	
	/**
	 * Constructeur
	 * <br>Initialise la partie au round 0
	 * <br>Cr�er la liste des cartes
	 * <br>Cr�er la liste des joueurs
	 * @param plateau Plateau de la partie
	 */
	public Partie(Plateau plateau) {
		this.round = 0;
		this.plateau = plateau;
		this.deck = new LinkedList<Carte>();
		this.joueurs = new ArrayList<Joueur>();
	}
	
	public void setVariante(Variante v) {
		this.regle = v;
	}

	public Variante getVariante() {
		return this.regle;
	}
	
	public int getRound() {
		return this.round;
	}

	public boolean getLoaded() {
		return this.loaded;
	}
	/**
	 *Cr��, ajoute puis m�lange les cartes du deck
	 *@see fr.corentinPierre.models.Carte
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
	
	public void ajouterJoueur(Joueur j) {
		this.joueurs.add(j);
	}
		
	public ArrayList<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	public Carte getCarteSupp() {
		return this.carteSupp;
	}

	public Plateau getPlateau() {
		return this.plateau;
	}
	
	/**
	 * Appelle la m�thode de piochage de la variante
	 * @return Carte Carte pioch�e
	 * @see fr.corentinPierre.models.Variante
	 */
	public Carte piocher() {
		return regle.piocher();
		
	}
	/**
	 * Permet de passer au round suivant.
	 * <br>En fonction de la variante :
	 * <br>(Variante3) Refill : la fonction va reg�n�rer des cartes, remplir et rem�langer le deck au milieu de la partie<br>
	 * (Variante1 & Variante2) Classique et Avanc� : Incr�mente le round et l&apos;indique � ses observateurs
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
	
	public LinkedList<Carte> getDeck(){
		return this.deck;
	}
	
	public String getEtat() {
		return this.etat;
	}
	/**
	 * Change l'�tat de la partie et en informe ses observateurs
	 * @param etat Nouvel �tat de la partie
	 */
	public void setEtat(String etat) {
		this.etat = etat;
		this.setChanged();
		this.notifyObservers();
	}
	
	public Carte getCartePiochee() {
		return this.cartePiochee;
	}

	public Carte getCarteADeplacer() {
		return this.carteADeplacer;
	}

	public boolean getAlreadyDeplacee() {
		return this.alreadyDeplacee;
	}
	
	/**
	 * D�termine le Vainqueur de la partie. 
	 * <br>En cas d'�galit�, le premier joueur qui poss�de le score est d�clar� vainqueur.
	 * @return Joueur Joueur vainqueur de la partie.
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
	 * Retire une carte du deck de mani�re al�atoire
	 */
	protected void retirerCarteCachee() {
		Random rand = new Random();
		int randIndex = rand.nextInt(this.deck.size());
		this.carteSupp = this.deck.get(randIndex);
		this.deck.remove(randIndex);
	}
	
	/**
	 * Permet l'affichage d'une partie
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
	 * Appel de la m�thode d'initialisation de la Variante
     * <br>Change l'�tat actuel et en informe ses observateurs
	 * @see fr.corentinPierre.models.Variante
	 */
	public void initialisation() {
		this.regle.initialisation();
		this.etat = "initialisation";
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Pose une carte en fonction de la variante et du round actuel
	 * <br>Si la partie en est � son premier round, la carte peut �tre pos�e n'importe o� sur le plateau
	 * <br>Si on joue en Variante normale, la carte ne sera pos�e uniquement si elle v�rifie la r�gle d'adjacence
	 * <br>Si on joue en Variante avanc�e, la carteAPoser sera pos�e uniquement si elle v�rifie la r�gle d'adjacence. La valeur de l'attribut carteAPoser sera nulle apr�s.
	 * <br>La partie informe ses observateurs du placement ou non de la carte. 
	 * @param x Coordonn�e x du plateau 
	 * @param y Coordonn�e y du plateau
	 * @return Boolean Vrai si la carte a �t� pos�e, faux sinon.
	 * @see fr.corentinPierre.models.Variante
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
	 * S�lectionne une carte du plateau � d�placer 
	 * <br>Informe ses observateurs si la carte est valide et non nulle.
	 * @param x Coordonn�e x de la carte
	 * @param y Coordonn�e y de la carte
	 * @return Carte Carte choisie par le joueur | null s'il n'y a pas de carte � cet emplacement
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
	 * D�place la carte � choisie pr�c�demment par le joueur sur le plateau
	 * <br>Si le nouvel emplacement est v�rifie la r�gle d'adjacence, la carte est plac�e et l'attribut alreadyDeplacee passe � vrai
	 * <br>La partie informe ses observateurs du placement ou non de la carte. 
	 * @param x Coordonn�e x du plateau 
	 * @param y Coordonn�e y du plateau
	 * @return Boolean Vrai si la carte a �t� pos�e, faux sinon.
	 * @see fr.corentinPierre.models.Partie#carteADeplacer
	 * @see fr.corentinPierre.models.Partie#prendreCarteADeplacer(int, int)
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
	 * (Variante2) V�rifie si tous les joueurs n'ont plus  qu'une carte en main
	 * @return boolean Vrai si les joueurs n'ont qu'une carte en main, faux sinon
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
	 * Calcule le score de chaque joueur 
	 * <br>Informe ses observateurs de la fin de la partie une fois les scores calcul�s
	 * @see fr.corentinPierre.models.Joueur
	 * @see fr.corentinPierre.models.Partie#accept(Visitor, int)
	 */
	
	public void calculerScore() {
		for(int i = 0; i<this.joueurs.size(); i++) {
			this.joueurs.get(i).setScore(this.accept(new ScoreVisitor(), i));
		}
		this.setEtat("finPartie");
	}
	
	
	/**
	 * Permet de sauvegarder une partie
	 * @param p Partie � sauvegarder
	 * @param fileName Nom du fichier o� la partie sera sauvegard�e de type String et non nul
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
	
	public void setLoaded(boolean l) {
		this.loaded = l;
	}
	
	/**
	 * Permet de charger une partie sauvegard�e
	 * @param fileName Nom du fichier o� la partie �tait sauvegard�e de type String et non nul
	 * @return Partie Partie charg�e
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
			System.out.println("Pas de derni�re sauvegarde de parties");
		} catch (IOException e) {
			System.out.println("Pas de derni�re sauvegarde de parties");
		} catch (ClassNotFoundException e) {
			System.out.println("Pas de derni�re sauvegarde de parties");
		}
		return p;
		
	}

	/**
	 * Permet d'aller visiter la classe Partie pour calculer le score d'un joueur
	 * @param visitor 
	 * @param id Id du joueur
	 * @return int Score du joueur
	 */
	@Override
	public int accept(Visitor visitor, int id) {
		return visitor.visit(this, id);
	}
	
}
