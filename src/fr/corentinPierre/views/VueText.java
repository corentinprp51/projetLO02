package fr.corentinPierre.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import fr.corentinPierre.models.Carte;
import fr.corentinPierre.models.JoueurHumain;
import fr.corentinPierre.models.JoueurVirtuel;
import fr.corentinPierre.models.JoueurVirtuelDebutant;
import fr.corentinPierre.models.Partie;
/**
 * Classe VueText
 * TEST COMMIT OHO
 * Représente une partie de ShapeUp en console
 * @author Coco
 *
 */
@SuppressWarnings("deprecation")
public class VueText implements Observer, Runnable{
	public static String PROMPT = "> ";
	public static String START = "S";
	public static String POSER = "P";
	public static String DEPLACER = "D";
	public static String FINTOUR = "F";
	
	private Partie partie;
	
	
	public VueText(Partie partie) {
		this.partie=partie;
		partie.addObserver(this);
		// On crée un Thread sur this qui est la VueTexte
		Thread t  = new Thread(this);
		// On démarre le Thread
		t.start();
	}

	@Override
	public void run() {
		// Logique du jeu
		Boolean exit = false;
		String saisie = null;
		do{
			System.out.println(this.messageConsole());
			saisie = this.lireChaine();
			if (saisie.equalsIgnoreCase("S") && partie.getEtat() == "") {
				JoueurHumain p1 = new JoueurHumain(0, "Corentin");
				JoueurHumain p2 = new JoueurHumain(1, "Pierre");
				JoueurHumain p3 = new JoueurHumain(2, "Dorian");
				JoueurVirtuel v1 = new JoueurVirtuelDebutant(0, "Virtuel 1");
				JoueurVirtuel v2 = new JoueurVirtuelDebutant(1, "Virtuel 2");
				JoueurVirtuel v3 = new JoueurVirtuelDebutant(2, "Virtuel 3");
				partie.ajouterJoueur(p1);
				partie.ajouterJoueur(p2);
				partie.ajouterJoueur(p3);
				partie.initialisation();
			} else if (partie.getRound() == 0 && (partie.getEtat() == "initialisation" || partie.getEtat() == "attentePoser" || partie.getEtat() == "erreurPoser")) {
				if(saisie.equalsIgnoreCase(VueText.POSER)) {
					partie.setEtat("attentePoser");
				}
				if(partie.getEtat() == "attentePoser" || partie.getEtat() == "erreurPoser") {
					System.out.println("Saisissez coordonnée x: ");
					int x = Integer.parseInt(this.lireChaine());
					System.out.println("Saisissez coordonnée y: ");
					int y = Integer.parseInt(this.lireChaine());
					boolean isPosee = partie.poserCarte(x, y);
					if(isPosee == false) {
						partie.setEtat("erreurPoser");
					}
					
				}
			} else if (saisie.equalsIgnoreCase(VueText.POSER) && !partie.getEtat().equalsIgnoreCase("attentePoser") && partie.getRound() != 0) {
				//Déplacer une carte
				partie.setEtat("attentePoser");
			} else if (saisie.equalsIgnoreCase(VueText.DEPLACER) && !partie.getEtat().equalsIgnoreCase("attentePoser") && partie.getRound() != 0) {
				//Déplacer une carte
				partie.setEtat("attenteDeplacer");
			} else if(partie.getEtat() == "attentePoser" || partie.getEtat() == "erreurPoser") {
				System.out.println("Saisissez coordonnée x: ");
				int x = Integer.parseInt(this.lireChaine());
				System.out.println("Saisissez coordonnée y: ");
				int y = Integer.parseInt(this.lireChaine());
				boolean isPosee = partie.poserCarte(x, y);
				if(isPosee == false) {
					partie.setEtat("erreurPoser");
				}
				} else if (partie.getEtat() == "attenteDeplacer" || partie.getEtat() == "erreurChoixDeplacer" || partie.getEtat() == "erreurDeplacer" || partie.getEtat().indexOf("carteADeplacer") != -1) {
				if(partie.getEtat() == "attenteDeplacer" || partie.getEtat() == "erreurChoixDeplacer" ) {
					System.out.println("Saisissez coordonnée x de la carte à déplacer: ");
					int x = Integer.parseInt(this.lireChaine());
					System.out.println("Saisissez coordonnée y de la carte à déplacer: ");
					int y = Integer.parseInt(this.lireChaine());
					Carte c = partie.prendreCarteADeplacer(x, y);
					if(c == null) {
						partie.setEtat("erreurChoixDeplacer");
					}
				} 
				 else {
					System.out.println("Saisissez coordonnée x du nouvel emplacement: ");
					int x = Integer.parseInt(this.lireChaine());
					System.out.println("Saisissez coordonnée y du nouvel emplacement: ");
					int y = Integer.parseInt(this.lireChaine());
					boolean deplacee = partie.deplacerCarte(partie.getCarteADeplacer(), x, y);
					if(!deplacee) {
						partie.setEtat("carteADeplacer");
					}
				}
				
			} else if((saisie.equalsIgnoreCase(VueText.FINTOUR) && partie.getEtat().indexOf("poser") != -1) || (saisie.equalsIgnoreCase(VueText.FINTOUR) && partie.getEtat().indexOf("deplacer") != -1 && partie.getCartePiochee() == null)) {
				System.out.println("Etat (dans if) : " + partie.getEtat());
				if(partie.getDeck().size() > 0 ) {
					partie.piocher();
					partie.nouveauRound();
				} else {
					partie.calculerScore();
				}
			}
			
			
			
			if (saisie.equalsIgnoreCase("Q")) {
				exit = true;
			}
		}while(exit==false);
		System.exit(0);
	}
	
	//Méthodes pour le lancement de la partie
	
	private String messageConsole() {
		if(partie.getEtat() == "") {
			return VueText.START + " pour lancer une partie !";
		} else if(partie.getEtat() == "initialisation") {
			return VueText.POSER + " pour poser une carte !";
		} else if (partie.getEtat() == "attentePoser") {
			return "Sélectionnez les coordonnées de la carte à poser";
		}else if(partie.getEtat().indexOf("poser") != -1){
			return VueText.FINTOUR + " pour terminer son tour";
		}
		return "";
	}

	@Override
	public void update(Observable o, Object arg1) {
		// Lorsque l'état de la partie est modifié
		if(o instanceof Partie) {
			switch (((Partie) o).getEtat()) {
			case "initialisation" : {
				System.out.println(o);
				System.out.println();
				System.out.println("Tour(s): " + ((Partie)o).getRound());
				System.out.println("Deck: " + (((Partie)o).getDeck().size()));
				System.out.println("C'est au tour de " + ((Partie)o).getJoueurs().get(0).getNom() + " de jouer");
				System.out.println("Carte piochée " + ((Partie)o).getCartePiochee());
				break;
			}
			case "attentePoser" : {
				System.out.println("En attente de poser une carte...");
				break;
			}
			case "erreurPoser" : {
				System.out.println("Re-posez la carte, impossible de la poser actuellement");
				break;
			}
			case "attenteDeplacer": {
				System.out.println("En attente de déplacer une carte...");
				break;
			}
			case "erreurChoixDeplacer": {
				System.out.println("Rechoisir votre carte à déplacer");
				break;
			}
			case "finTour" : {
				int idJoueur = ((Partie) o).getRound()%((Partie)o).getJoueurs().size();
				System.out.println("Tour: " + (((Partie) o).getRound() + 1));
				System.out.println("Deck: " + (((Partie)o).getDeck().size()));
				System.out.println("C'est au tour de " + ((Partie)o).getJoueurs().get(idJoueur).getNom() + " de jouer");
				System.out.println("Carte piochée " + ((Partie)o).getCartePiochee());
				break;
			}
			case "finPartie" : {
				System.out.println("******************************");
				System.out.println("Fin de la partie");
				System.out.println("******************************");
				System.out.println("Joueur Vainqueur : " + partie.getVainqueur().getNom());
				System.out.println();
				System.out.println("Liste des scores: ");
				partie.getJoueurs().forEach(joueur -> {
					System.out.println(joueur.getNom() + ": " + joueur.getScore());
				});
				break;
			}
			default:
				//throw new IllegalArgumentException("Unexpected value: " + ((Partie) o).getEtat());
			}
			if(((Partie) o).getEtat().indexOf("poser") != -1) {
				//Alors on récupère les coordonnées
				int x, y;
				x = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 2));
				y = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 1));
				System.out.println("Placement de la carte en " + x + ", " + y);
			} else if (((Partie) o).getEtat().indexOf("carteADeplacer") != -1) {
				System.out.println("Carte à déplacer: ");
				System.out.println(partie.getCarteADeplacer());
			} else if (((Partie) o).getEtat().indexOf("deplacerCarte") != -1) {
				int x, y;
				x = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 2));
				y = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 1));
				//Carte c = partie.getPlateau().getCartesPosees().get(y).get(x);
				System.out.println("Carte déplacée en " + x + ", " + y);
			}
		}
	}
	
	public String lireChaine(){
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in ));
		String resultat=null;
		try {
				System.out.println(VueText.PROMPT);
				resultat=br.readLine();
			} catch (IOException e) {}
		return resultat;
	}

}
