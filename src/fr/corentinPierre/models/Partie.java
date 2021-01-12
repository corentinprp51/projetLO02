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

public class Partie extends Observable implements Serializable {

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
		this.cartePiochee = this.deck.remove(0);
		return this.cartePiochee;
		
	}
	
	public void nouveauRound() {
		//System.out.println("Fin du tour...");
		this.round++;
		this.alreadyDeplacee = false;
		this.etat = "finTour";
		this.setChanged();
		this.notifyObservers();
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
			this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.cartePiochee);
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
		}
		//System.out.println("Placement de la carte en " + coordonnees[0] + "," + coordonnees[1]);
		this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], this.cartePiochee);
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
	
	public void calculerScore() {
		for(int i = 0; i<this.joueurs.size(); i++) {
			int scoreForme = this.calculerForme(this.joueurs.get(i).getCarteVictoire());
			int scoreCouleur = this.calculerCouleur(this.joueurs.get(i).getCarteVictoire());
			int scoreFillable = this.calculerFillable(this.joueurs.get(i).getCarteVictoire());
			this.joueurs.get(i).setScore(scoreCouleur + scoreForme + scoreFillable);
		}
		this.setEtat("finPartie");
	}
	
	protected void attribuerCartesVictoires() {
		for(int i = 0; i<this.joueurs.size(); i++) {
			Random rand = new Random();
			int randIndex = rand.nextInt(this.deck.size());
			this.joueurs.get(i).setCarteVictoire(this.deck.get(randIndex));
			this.deck.remove(randIndex);
		}
	}
	
	public int calculerFillable(Carte victoire) {
		int score = 0;
		//Comptage Horizontal
		Map<Integer, Map<Integer, Carte>> cartes = new TreeMap<Integer, Map<Integer, Carte>>(this.getPlateau().getCartesPosees());	
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			int nbCartes = 0;
			int [] combo = new int[2];
			combo[0] = 0;
			combo[1] = 0;
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				if(entry2.getValue().getFillable() == victoire.getFillable()) {
					nbCartes++;
					
				} else {
					if(combo[0] <= 1) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
					nbCartes = 0;
				}
			}
			if(nbCartes > 1) {
				if(combo[0] < 2) {
					combo[0] = nbCartes;
				} else {
					combo[1] = nbCartes;
				}
			}
			if(combo[0] > 2) {
				score += combo[0];
			}
			if(combo[1] > 2) {
				score += combo[1];
			}
		}
		//Comptage Vertical
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				int i = 0;
				int [] combo = new int[2];
				combo[0] = 0;
				combo[1] = 0;
				int nbCartes = 0;
				while(cartes.containsKey(entry.getKey()+i)) {
					if(cartes.get(entry.getKey() + i).containsKey(entry2.getKey())) {
						if(cartes.get(entry.getKey() + i).get(entry2.getKey()).getFillable() == victoire.getFillable()) {
							nbCartes++;
							//cartes.get(entry.getKey() + i).remove(entry2.getKey());
							
						} else {
							if(combo[0] <= 1) {
								combo[0] = nbCartes;
							} else {
								combo[1] = nbCartes;
							}
							nbCartes = 0;
						}
					}
					i++;
				}
				if(nbCartes > 1) {
					if(combo[0] < 2) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
				}
				if(combo[0] > 2) {
					score += combo[0];
				}
				if(combo[1] > 2) {
					score += combo[1];
				}
			}
			break;
		}
		return score;
	}
	
	public int calculerCouleur(Carte victoire) {
		int score = 0;
		//Comptage Horizontal
		Map<Integer, Map<Integer, Carte>> cartes = new TreeMap<Integer, Map<Integer, Carte>>(this.getPlateau().getCartesPosees());	
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			int nbCartes = 0;
			int [] combo = new int[2];
			combo[0] = 0;
			combo[1] = 0;
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				if(entry2.getValue().getCouleur() == victoire.getCouleur()) {
					nbCartes++;
					
				} else {
					if(combo[0] <= 1) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
					nbCartes = 0;
				}
			}
			if(nbCartes > 1) {
				if(combo[0] < 2) {
					combo[0] = nbCartes;
				} else {
					combo[1] = nbCartes;
				}
			}
			if(combo[0] > 2) {
				score += combo[0] + 1;
			}
			if(combo[1] > 2) {
				score += combo[1] + 1;
			}
		}
		//Comptage Vertical
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				int i = 0;
				int [] combo = new int[2];
				combo[0] = 0;
				combo[1] = 0;
				int nbCartes = 0;
				while(cartes.containsKey(entry.getKey()+i)) {
					if(cartes.get(entry.getKey() + i).containsKey(entry2.getKey())) {
						if(cartes.get(entry.getKey() + i).get(entry2.getKey()).getCouleur() == victoire.getCouleur()) {
							nbCartes++;
							//cartes.get(entry.getKey() + i).remove(entry2.getKey());
							
						} else {
							if(combo[0] <= 1) {
								combo[0] = nbCartes;
							} else {
								combo[1] = nbCartes;
							}
							nbCartes = 0;
						}
					}
					i++;
				}
				if(nbCartes > 1) {
					if(combo[0] < 2) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
				}
				if(combo[0] > 2) {
					score += combo[0] + 1;
				}
				if(combo[1] > 2) {
					score += combo[1] + 1;
				}
			}
			break;
		}
		return score;
	}
	public int calculerForme(Carte victoire) {
		int score = 0;
		//Comptage Horizontal
		Map<Integer, Map<Integer, Carte>> cartes = new TreeMap<Integer, Map<Integer, Carte>>(this.getPlateau().getCartesPosees());	
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			int nbCartes = 0;
			int [] combo = new int[2];
			combo[0] = 0;
			combo[1] = 0;
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				if(entry2.getValue().getForme() == victoire.getForme()) {
					nbCartes++;
					
				} else {
					if(combo[0] <= 1) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
					nbCartes = 0;
				}
			}
			if(nbCartes > 1) {
				if(combo[0] < 2) {
					combo[0] = nbCartes;
				} else {
					combo[1] = nbCartes;
				}
			}
			if(combo[0] > 1) {
				score += combo[0] - 1;
			}
			if(combo[1] > 1) {
				score += combo[1] - 1;
			}
		}
		//Comptage Vertical
		//Affichage du tapis final: 
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getPlateau().getCartesPosees().entrySet() ) {
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				int i = 0;
				int [] combo = new int[2];
				combo[0] = 0;
				combo[1] = 0;
				int nbCartes = 0;
				while(cartes.containsKey(entry.getKey()+i)) {
					if(cartes.get(entry.getKey() + i).containsKey(entry2.getKey())) {
						if(cartes.get(entry.getKey() + i).get(entry2.getKey()).getForme() == victoire.getForme()) {
							nbCartes++;
							//cartes.get(entry.getKey() + i).remove(entry2.getKey());
							
						} else {
							if(combo[0] <= 1) {
								combo[0] = nbCartes;
							} else {
								combo[1] = nbCartes;
							}
							nbCartes = 0;
						}
					}
					i++;
				}
				if(nbCartes > 1) {
					if(combo[0] < 2) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
				}
				if(combo[0] > 1) {
					score += combo[0] - 1;
				}
				if(combo[1] > 1) {
					score += combo[1] - 1;
				}
			}
			break;
		}
		return score;
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
}
