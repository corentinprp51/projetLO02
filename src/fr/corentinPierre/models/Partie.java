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

public class Partie extends Observable implements Serializable, Visitable {

	protected int round;
	protected LinkedList<Carte> deck;
	protected ArrayList<Joueur> joueurs;
	protected Plateau plateau;
	protected Carte carteSupp;
	protected String etat = "";
	protected Carte cartePiochee;
	protected Carte carteADeplacer;
	protected boolean alreadyDeplacee;
	protected Variante regle;
	protected boolean loaded = false;
	
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
	 * Générer toutes les cartes et effectuer le mélange
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
	
	public ArrayList<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	public Carte getCarteSupp() {
		return this.carteSupp;
	}
	
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public Carte piocher() {
		return regle.piocher();
		
	}
	
	public void nouveauRound() {
		//System.out.println("Fin du tour...");
		if(this.getVariante().getNom().equalsIgnoreCase("Refill")) {
			if(this.getPlateau().getNbCartesPosees() == 7) {
				//On refill le packet et on mélange
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
	
	public Joueur getVainqueur() {
		Joueur vainqueur = null;
		for(int i = 0; i < this.getJoueurs().size(); i++) {
			if(vainqueur == null || this.getJoueurs().get(i).getScore() > vainqueur.getScore()) {
				vainqueur = this.getJoueurs().get(i);
			}
		}
		return vainqueur;
	}
	
	protected void retirercarteCachee() {
		Random rand = new Random();
		int randIndex = rand.nextInt(this.deck.size());
		this.carteSupp = this.deck.get(randIndex);
		this.deck.remove(randIndex);
	}
	
	public String toString() {
		StringBuffer bf = new StringBuffer();
		//bf.append("\nPartie " + this.getNom());
		bf.append("\nPartie ");
		bf.append("\nNombre de cartes restant: " + this.deck.size());
		bf.append("\nJoueurs");
		Iterator<Joueur> it = this.joueurs.iterator();
		while(it.hasNext()) {
			bf.append("\n" + it.next());
		}
		return bf.toString();
	}
	

	public void initialisation() {
		this.regle.initialisation();
		this.etat = "initialisation";
		this.setChanged();
		this.notifyObservers();
	}
	public boolean poserCarte(int x, int y) {
		int [] coordonnees = {x,y};
		if(this.round == 0) {
			//System.out.println(j.getNom() + " pioche" + c);
			if(this.getVariante().getNom().equalsIgnoreCase("Avance")) {
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.getVariante().getCarteAPoser());
				this.getVariante().setCarteAPoser(null);
			} else {
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.cartePiochee);	
			}
		} else {
			if(this.getPlateau().getCartesPosees().containsKey(coordonnees[1]) && this.getPlateau().getCartesPosees().get(coordonnees[1]).containsKey(coordonnees[0])) {
				//System.out.println("Il y a déjà une carte en: " + coordonnees[0] + ", " + coordonnees[1]);
				return false;
			} else if (!this.getPlateau().checkAdjacence(coordonnees[0], coordonnees[1])) {
				//System.out.println("Impossible de placer votre carte en " + coordonnees[0] + ", " + coordonnees[1]);
				//System.out.println("La règle d'adjacence n'est pas respectée");
				return false;
			} else if (!this.getPlateau().checkBaseRule(coordonnees[0], coordonnees[1])) {
				//System.out.println("Impossible de placer votre carte en " + coordonnees[0] + ", " + coordonnees[1]);
				//System.out.println("La règle de base n'est pas respectée");
				return false;
			}
			if(this.getVariante().getNom().equalsIgnoreCase("Avance")) {
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.getVariante().getCarteAPoser());
				this.getVariante().setCarteAPoser(null);
			} else {
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.cartePiochee);
			}
		}
		//System.out.println("Placement de la carte en " + coordonnees[0] + "," + coordonnees[1]);
		this.cartePiochee = null;
		this.etat = "poser" + coordonnees[0] + coordonnees[1];
		this.setChanged();
		this.notifyObservers();
		return true;
	}
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

	public boolean deplacerCarte(Carte carte, int x, int y) {
		//System.out.println("Carte à déplacer: " + carte);
		int [] newCoordonnees = {x,y};
			if(this.getPlateau().getCartesPosees().containsKey(newCoordonnees[1]) && this.getPlateau().getCartesPosees().get(newCoordonnees[1]).containsKey(newCoordonnees[0])) {
				//System.out.println("Il y a déjà une carte en: " + newCoordonnees[0] + ", " + newCoordonnees[1]);
				return false;
			} else if (!this.getPlateau().checkAdjacence(newCoordonnees[0], newCoordonnees[1])) {
				//System.out.println("Impossible de placer votre carte en " + newCoordonnees[0] + ", " + newCoordonnees[1]);
				//System.out.println("La règle d'adjacence n'est pas respectée");
				return false;
			} else if (!this.getPlateau().checkBaseRule(newCoordonnees[0], newCoordonnees[1])) {
				//System.out.println("Impossible de placer votre carte en " + newCoordonnees[0] + ", " + newCoordonnees[1]);
				//System.out.println("La règle de base n'est pas respectée");
				return false;
			}
		//System.out.println("Déplacement de la carte en " + newCoordonnees[0] + ", " + newCoordonnees[1]);
		this.getPlateau().setCartesPosees(newCoordonnees[0], newCoordonnees[1], carte);
		this.alreadyDeplacee = true;
		this.etat = "deplacerCarte" + newCoordonnees[0] + newCoordonnees[1];
		this.setChanged();
		this.notifyObservers();
		return true;
	}
	
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
	
	public void calculerScore() {
		for(int i = 0; i<this.joueurs.size(); i++) {
			/*int scoreForme = this.calculerForme(this.joueurs.get(i).getCarteVictoire());
			int scoreCouleur = this.calculerCouleur(this.joueurs.get(i).getCarteVictoire());
			int scoreFillable = this.calculerFillable(this.joueurs.get(i).getCarteVictoire());*/
			this.joueurs.get(i).setScore(this.accept(new ScoreVisitor(), i));
		}
		this.setEtat("finPartie");
	}
	
	public static void savePartie(Partie p, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(p);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void setLoaded(boolean l) {
		this.loaded = l;
	}
	
	public static Partie loadPartie(String fileName) {
		Partie p = new Partie(new Plateau());
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			p = (Partie) ois.readObject();
			p.setLoaded(true);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Pas de dernière sauvegarde de parties");
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Pas de dernière sauvegarde de parties");
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Pas de dernière sauvegarde de parties");
			//e.printStackTrace();
		}
		return p;
		
	}

	@Override
	public int accept(Visitor visitor, int id) {
		// TODO Auto-generated method stub
		return visitor.visit(this, id);
	}
}
