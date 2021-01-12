package fr.corentinPierre.models;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import fr.corentinPierre.enums.Couleur;
import fr.corentinPierre.enums.Forme;

public class Variante1 extends Variante{
	
	public Variante1(String nom, Partie p) {
		super(nom, p);
	}
	
	public void initialisation() {
		this.partie.melangerCartes();
		this.partie.retirercarteCachee();
		this.partie.attribuerCartesVictoires();
		//Rajout vis à vis de l'interface
		this.partie.piocher();
		//
	}

	/*public void tourJoueur(int id) {
		Carte c = this.piocher();
		Joueur j = this.joueurs.get(id);
		System.out.println("C'est au tour de " + j.getNom() + " de jouer");
		if(this.round == 0) {
			System.out.println(j.getNom() + " pioche" + c);
			//int [] coordonnees = j.choisirEmplacement();
			int [] coordonnees = {0,0};
			this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], c);
			this.nouveauRound();
		} else {
			String choice = j.choisirAction();
			if(choice.equalsIgnoreCase("P")) {
				System.out.println(j.getNom() + " pioche" + c);
				//Si l'utilisateur souhaite placer une carte
				int [] coordonnees = j.choisirEmplacement();
				//Checker si la ligne existe déjà
					boolean poser = false;
					while(poser == false) {
						if(this.getPlateau().getCartesPosees().containsKey(coordonnees[1]) && this.getPlateau().getCartesPosees().get(coordonnees[1]).containsKey(coordonnees[0])) {
							System.out.println("Il y a déjà une carte en: " + coordonnees[0] + ", " + coordonnees[1]);
							coordonnees = j.choisirEmplacement();
						} else if (!this.getPlateau().checkAdjacence(coordonnees[0], coordonnees[1])) {
							System.out.println("Impossible de placer votre carte en " + coordonnees[0] + ", " + coordonnees[1]);
							System.out.println("La règle d'adjacence n'est pas respectée");
							coordonnees = j.choisirEmplacement();
						} else if (!this.getPlateau().checkBaseRule(coordonnees[0], coordonnees[1])) {
							System.out.println("Impossible de placer votre carte en " + coordonnees[0] + ", " + coordonnees[1]);
							System.out.println("La règle de base n'est pas respectée");
							coordonnees = j.choisirEmplacement();
						} else {
							poser = true;
							System.out.println("Coordonées pour placement : " + coordonnees[0] + " - " + coordonnees[1]);
						}
					}
					/*
					while(this.getPlateau().getCartesPosees().containsKey(coordonnees[1]) && this.getPlateau().getCartesPosees().get(coordonnees[1]).containsKey(coordonnees[0])) {
						System.out.println("Il y a déjà une carte en: " + coordonnees[0] + ", " + coordonnees[1]);
						coordonnees = j.choisirEmplacement();
					}
					while(!this.getPlateau().checkAdjacence(coordonnees[0], coordonnees[1]) || !this.getPlateau().checkBaseRule(coordonnees[0], coordonnees[1])) {
						System.out.println("Impossible de placer votre carte en " + coordonnees[0] + ", " + coordonnees[1]);
						System.out.println("La règle d'adjacence ou de base n'est pas respectée");
						coordonnees = j.choisirEmplacement();
					}
					
				System.out.println("Placement de la carte en " + coordonnees[0] + "," + coordonnees[1]);
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], c);
				//Proposer de déplacer une carte
				
				boolean poserCarte = j.demandePoserCarte();
				if(poserCarte) {
					int [] emplacement = j.choisirEmplacement();
					while(!this.getPlateau().getCartesPosees().containsKey(emplacement[1])) {
						System.out.println("Pas de cartes à cet emplacement");
						emplacement = j.choisirEmplacement();
					}
					while(!this.getPlateau().getCartesPosees().get(emplacement[1]).containsKey(emplacement[0])) {
						System.out.println("Pas de cartes à cet emplacement");
						emplacement = j.choisirEmplacement();
					}
					Carte carte = this.getPlateau().getCartesPosees().get(emplacement[1]).remove(emplacement[0]);
					System.out.println("Carte à déplacer: " + carte);
					int [] newCoordonnees = j.choisirEmplacement();
					boolean deplacer = false;
					while(deplacer == false) {
						if(this.getPlateau().getCartesPosees().containsKey(newCoordonnees[1]) && this.getPlateau().getCartesPosees().get(newCoordonnees[1]).containsKey(newCoordonnees[0])) {
							System.out.println("Il y a déjà une carte en: " + newCoordonnees[0] + ", " + newCoordonnees[1]);
							newCoordonnees = j.choisirEmplacement();
						} else if (!this.getPlateau().checkAdjacence(newCoordonnees[0], newCoordonnees[1])) {
							System.out.println("Impossible de placer votre carte en " + newCoordonnees[0] + ", " + newCoordonnees[1]);
							System.out.println("La règle d'adjacence n'est pas respectée");
							newCoordonnees = j.choisirEmplacement();
						} else if (!this.getPlateau().checkBaseRule(newCoordonnees[0], newCoordonnees[1])) {
							System.out.println("Impossible de placer votre carte en " + newCoordonnees[0] + ", " + newCoordonnees[1]);
							System.out.println("La règle de base n'est pas respectée");
							newCoordonnees = j.choisirEmplacement();
						} else {
							deplacer = true;
							System.out.println("Coordonées pour déplacement : " + newCoordonnees[0] + " - " + newCoordonnees[1]);
						}
					}
					System.out.println("Déplacement de la carte en " + newCoordonnees[0] + ", " + newCoordonnees[1]);
					this.getPlateau().setCartesPosees(newCoordonnees[0], newCoordonnees[1], carte);
					this.nouveauRound();
				} else {
					this.nouveauRound();
				}
				
				//Si l'utilisateur souhaite déplacer une carte
			} else {
				
				int [] emplacement = j.choisirEmplacement();
				while(!this.getPlateau().getCartesPosees().containsKey(emplacement[1])) {
					System.out.println("Pas de cartes à cet emplacement");
					emplacement = j.choisirEmplacement();
				}
				while(!this.getPlateau().getCartesPosees().get(emplacement[1]).containsKey(emplacement[0])) {
					System.out.println("Pas de cartes à cet emplacement");
					emplacement = j.choisirEmplacement();
				}
				Carte carte = this.getPlateau().getCartesPosees().get(emplacement[1]).remove(emplacement[0]);
				System.out.println("Carte à déplacer: " + carte);
				int [] newCoordonnees = j.choisirEmplacement();
				boolean deplacer = false;
				while(deplacer == false) {
					if(this.getPlateau().getCartesPosees().containsKey(newCoordonnees[1]) && this.getPlateau().getCartesPosees().get(newCoordonnees[1]).containsKey(newCoordonnees[0])) {
						System.out.println("Il y a déjà une carte en: " + newCoordonnees[0] + ", " + newCoordonnees[1]);
						newCoordonnees = j.choisirEmplacement();
					} else if (!this.getPlateau().checkAdjacence(newCoordonnees[0], newCoordonnees[1])) {
						System.out.println("Impossible de placer votre carte en " + newCoordonnees[0] + ", " + newCoordonnees[1]);
						System.out.println("La règle d'adjacence n'est pas respectée");
						newCoordonnees = j.choisirEmplacement();
					} else if (!this.getPlateau().checkBaseRule(newCoordonnees[0], newCoordonnees[1])) {
						System.out.println("Impossible de placer votre carte en " + newCoordonnees[0] + ", " + newCoordonnees[1]);
						System.out.println("La règle de base n'est pas respectée");
						newCoordonnees = j.choisirEmplacement();
					} else {
						deplacer = true;
						System.out.println("Coordonées pour déplacement : " + newCoordonnees[0] + " - " + newCoordonnees[1]);
					}
				}
				
				System.out.println("Déplacement de la carte en " + newCoordonnees[0] + ", " + newCoordonnees[1]);
				this.getPlateau().setCartesPosees(newCoordonnees[0], newCoordonnees[1], carte);

				//Poser carte (OBLIGATOIRE)
				System.out.println(j.getNom() + " pioche" + c);
				//Si l'utilisateur souhaite placer une carte
				int [] coordonnees = j.choisirEmplacement();
				//Checker si la ligne existe déjà
				boolean poser = false;
				while(poser == false) {
					if(this.getPlateau().getCartesPosees().containsKey(coordonnees[1]) && this.getPlateau().getCartesPosees().get(coordonnees[1]).containsKey(coordonnees[0])) {
						System.out.println("Il y a déjà une carte en: " + coordonnees[0] + ", " + coordonnees[1]);
						coordonnees = j.choisirEmplacement();
					} else if (!this.getPlateau().checkAdjacence(coordonnees[0], coordonnees[1])) {
						System.out.println("Impossible de placer votre carte en " + coordonnees[0] + ", " + coordonnees[1]);
						System.out.println("La règle d'adjacence n'est pas respectée");
						coordonnees = j.choisirEmplacement();
					} else if (!this.getPlateau().checkBaseRule(coordonnees[0], coordonnees[1])) {
						System.out.println("Impossible de placer votre carte en " + coordonnees[0] + ", " + coordonnees[1]);
						System.out.println("La règle de base n'est pas respectée");
						coordonnees = j.choisirEmplacement();
					} else {
						poser = true;
						System.out.println("Coordonées pour placement : " + coordonnees[0] + " - " + coordonnees[1]);
					}
				}
				System.out.println("Placement de la carte en " + coordonnees[0] + "," + coordonnees[1]);
				this.getPlateau().setCartesPosees(coordonnees[0], coordonnees[1], c);
				this.nouveauRound();
			}
		}
		
		
	}*/
	
	public int accept(Visitor visitor) {
		return 0;
		
	}
		
	
	
	/*
	public static void main(String[] args) {
		
		Plateau p = new Plateau();
		Variante1 partie = new Variante1("Normal", p);
		JoueurHumain p1 = new JoueurHumain(0, "Corentin");
		JoueurHumain p2 = new JoueurHumain(1, "Pierre");
		JoueurHumain p3 = new JoueurHumain(2, "Dorian");
		JoueurVirtuel v1 = new JoueurVirtuelDebutant(0, "Virtuel 1");
		JoueurVirtuel v2 = new JoueurVirtuelDebutant(1, "Virtuel 2");
		JoueurVirtuel v3 = new JoueurVirtuelDebutant(2, "Virtuel 3");
		partie.ajouterJoueur(v1);
		partie.ajouterJoueur(v2);
		//partie.ajouterJoueur(p3);
		partie.initialisation();
		System.out.println(partie);
		int i = 0;
		while(partie.deck.size() > 0) {
			System.out.println("DECK : " + partie.getDeck().size());
			partie.tourJoueur(i%partie.getJoueurs().size());
			i++;
			
		}
		System.out.println("**********Fin de la partie********");
		//Affichage du tapis final: 
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: partie.getPlateau().getCartesPosees().entrySet() ) {
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				System.out.println("Coordonnées: " + entry2.getKey() + ", " + entry.getKey());
				System.out.println(entry2.getValue());
			}
		}

		partie.calculerScore();
		System.out.println(partie);

		
		
	}
		*/

}
