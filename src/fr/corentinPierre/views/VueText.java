package fr.corentinPierre.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import fr.corentinPierre.models.Carte;
import fr.corentinPierre.models.Configuration;
import fr.corentinPierre.models.JoueurHumain;
import fr.corentinPierre.models.JoueurVirtuel;
import fr.corentinPierre.models.JoueurVirtuelDebutant;
import fr.corentinPierre.models.Partie;
import fr.corentinPierre.models.Variante1;
import fr.corentinPierre.models.Variante2;
/**
 * Vue Console d'une partie de Jeu Shape Up.
 * <br>La Vue console est concurrente avec la Vue Graphique. 
 * <br>Elle est donc observateur des objets observables Configuration et Partie
 * 
 * @author Corentin
 * @author Pierre
 * @see fr.corentinPierre.models.Configuration
 * @see fr.corentinPierre.models.Partie
 */
@SuppressWarnings("deprecation")
public class VueText implements Observer, Runnable{
	
	public static String PROMPT = "> ";
	public static String START = "C";
	public static String POSER = "P";
	public static String DEPLACER = "D";
	public static String FINTOUR = "F";
	
	private Partie partie;
	
	
	public VueText(Partie partie) {
		this.partie=partie;
		partie.addObserver(this);
		//Cr�ation du Thread
		Thread t  = new Thread(this);
		// On d�marre le Thread
		t.start();
	}

	@Override
	/**
	 * Configure, cr�e et joue une partie de Shape Up.
	 * <br>Toutes les saisies sont effectu�es au sein de la m�thode run.
	 * <br>L'utilisateur peut ainsi configurer sa partie et y jouer en mode Texte.
	 */
	public void run() {
		// Logique du jeu
		Boolean exit = false;
		String saisie = null;
		do{
			System.out.println(this.messageConsole());
			saisie = this.lireChaine();
			if (saisie.equalsIgnoreCase("C") && partie.getEtat() == "") {
				fr.corentinPierre.models.Configuration config = new Configuration();
				String modeJeu = "";
				do {
					System.out.println("Mode de jeu de la partie (N : Normal, A : Avanc�, R: refill");
					modeJeu = this.lireChaine();
				} while (!modeJeu.equalsIgnoreCase("N") && !modeJeu.equalsIgnoreCase("A") && !modeJeu.equalsIgnoreCase("R"));
				config.setTypePartie(modeJeu);
				int nbJoueurs = -1;
				do {
					System.out.println("Indiquez le nombre de joueurs (2 ou 3)");
					nbJoueurs = Integer.parseInt(this.lireChaine());
				} while (nbJoueurs != 3 && nbJoueurs != 2);
				config.setNombreJoueursAttendu(nbJoueurs);
				for(int i = 0; i < nbJoueurs; i++) {
					System.out.println("Joueur Virtuel ? (O / N)");
					String virtuel = this.lireChaine();
					boolean isVirtuel = virtuel.equalsIgnoreCase("O") ? true : false;
					String nomJ = "";
					if(!isVirtuel) {
						System.out.println("Saisir nom joueur " + i+1);
						nomJ = this.lireChaine();
					}
					config.addJoueur(isVirtuel ? new JoueurVirtuelDebutant(i, "Joueur 0" + i+1) : new JoueurHumain(i, nomJ));
				}
				config.getJoueurs().forEach(joueur -> {
					partie.ajouterJoueur(joueur);
				});
				switch (config.getTypePartie()) {
				case "N": {
					Variante1 v = new Variante1("Normal", partie);
					partie.setVariante(v);
					break;
				}
				case "A": {
					Variante2 v = new Variante2("Avance", partie);
					partie.setVariante(v);
				}
				case "R": {
					System.out.println("Refill");
					Variante1 v = new Variante1("Refill", partie);
					partie.setVariante(v);
				}
				}
				partie.initialisation();
			} else if (partie.getRound() == 0 && (partie.getEtat() == "initialisation" || partie.getEtat() == "attentePoser" || partie.getEtat() == "erreurPoser")) {
				if(saisie.equalsIgnoreCase(VueText.POSER)) {
					partie.setEtat("attentePoser");
				}
				if(partie.getEtat() == "attentePoser" || partie.getEtat() == "erreurPoser") {
					if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
						int x = -1;
						do {
							System.out.println("Saisissez la carte � poser (entre 0 et 2 inclus)");
							x = Integer.parseInt(this.lireChaine());
						} while (x < 0 || x > 2);
						
						Carte c = partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()).removeCarteFromMainByIndex(x);
						partie.getVariante().setCarteAPoser(c);
						partie.setEtat("carteAPoser" + x);
					} else {
						System.out.println("Saisissez coordonn�e x: ");
						int x = Integer.parseInt(this.lireChaine());
						System.out.println("Saisissez coordonn�e y: ");
						int y = Integer.parseInt(this.lireChaine());
						boolean isPosee = partie.poserCarte(x, y);
						if(isPosee == false) {
							partie.setEtat("erreurPoser");
						}
					}
					
				}
			} else if (saisie.equalsIgnoreCase(VueText.POSER) && !partie.getEtat().equalsIgnoreCase("attentePoser") && partie.getRound() != 0) {
				partie.setEtat("attentePoser");
			} else if (saisie.equalsIgnoreCase(VueText.DEPLACER) && !partie.getEtat().equalsIgnoreCase("attentePoser") && partie.getRound() != 0) {
				partie.setEtat("attenteDeplacer");
			} else if(partie.getEtat() == "attentePoser" || partie.getEtat() == "erreurPoser") {
				if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
					System.out.println("Saisissez la carte � poser (entre 0 et 2 inclus)");
					int x = -1;
					do {
						System.out.println("Saisissez la carte � poser (entre 0 et 2 inclus)");
						x = Integer.parseInt(this.lireChaine());
					} while (x < 0 || x > 2);
					Carte c = partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()).removeCarteFromMainByIndex(x);
					partie.getVariante().setCarteAPoser(c);
					partie.setEtat("carteAPoser" + x);
				} else {
					System.out.println("Saisissez coordonn�e x: ");
					int x = Integer.parseInt(this.lireChaine());
					System.out.println("Saisissez coordonn�e y: ");
					int y = Integer.parseInt(this.lireChaine());
					boolean isPosee = partie.poserCarte(x, y);
					if(isPosee == false) {
						partie.setEtat("erreurPoser");
					}
				}
			} else if (partie.getEtat() == "attenteDeplacer" || partie.getEtat() == "erreurChoixDeplacer" || partie.getEtat() == "erreurDeplacer" || partie.getEtat().indexOf("carteADeplacer") != -1) {
				if(partie.getEtat() == "attenteDeplacer" || partie.getEtat() == "erreurChoixDeplacer" ) {
					System.out.println("Saisissez coordonn�e x de la carte � d�placer: ");
					int x = Integer.parseInt(this.lireChaine());
					System.out.println("Saisissez coordonn�e y de la carte � d�placer: ");
					int y = Integer.parseInt(this.lireChaine());
					Carte c = partie.prendreCarteADeplacer(x, y);
					if(c == null) {
						partie.setEtat("erreurChoixDeplacer");
					}
				} 
				 else {
					System.out.println("Saisissez coordonn�e x du nouvel emplacement: ");
					int x = Integer.parseInt(this.lireChaine());
					System.out.println("Saisissez coordonn�e y du nouvel emplacement: ");
					int y = Integer.parseInt(this.lireChaine());
					boolean deplacee = partie.deplacerCarte(partie.getCarteADeplacer(), x, y);
					if(!deplacee) {
						partie.setEtat("carteADeplacer");
					}
				}
				
			} else if((saisie.equalsIgnoreCase(VueText.FINTOUR) && partie.getEtat().indexOf("poser") != -1) || (saisie.equalsIgnoreCase(VueText.FINTOUR) && partie.getEtat().indexOf("deplacer") != -1 && partie.getCartePiochee() == null)) {
				if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
					if(partie.getDeck().size() == 0) {
						if(partie.allPlayerHaveOneCard()) {
							partie.getJoueurs().forEach(joueur -> {
								joueur.setCarteVictoire(joueur.getMain().get(0));
							});
							partie.calculerScore();
						} else {
							partie.nouveauRound();
						}
						
					} else {
						partie.piocher();
						partie.nouveauRound();
					}
				} else if(partie.getVariante().getNom().equalsIgnoreCase("Normal")) {
					if(partie.getDeck().size() > 0 ) {
						partie.piocher();
						partie.nouveauRound();
					} else {
						partie.calculerScore();
					}
				} else {
					if(partie.getPlateau().isFull()) {
						partie.calculerScore();
					} else {
						partie.piocher();
						partie.nouveauRound();
					}
				}
			} else if (partie.getEtat().indexOf("carteAPoser") != -1) {
				int idCarte = Character.getNumericValue(partie.getEtat().charAt(partie.getEtat().length() - 1));
				System.out.println("Saisissez coordonn�e x: ");
				int x = Integer.parseInt(this.lireChaine());
				System.out.println("Saisissez coordonn�e y: ");
				int y = Integer.parseInt(this.lireChaine());
				boolean isPosee = partie.poserCarte(x, y);
				if(isPosee == false) {
					partie.setEtat("carteAPoser" + idCarte);
				}
			}
			
			
			
			if (saisie.equalsIgnoreCase("Q")) {
				exit = true;
			}
		}while(exit==false);
		System.exit(0);
	}
	
	/**
	 * Retourne un message selon l'�tat de la partie.
	 * @return String Cha�ne de caract�re du message design�
	 */
	
	private String messageConsole() {
		String msg = "";
		if(partie.getEtat() == "") {
			msg = VueText.START + " pour configurer la partie !";
		} else if(partie.getEtat() == "initialisation") {
			msg = VueText.POSER + " pour poser une carte !";
		} else if (partie.getEtat() == "attentePoser" || partie.getEtat() == "erreurPoser") {
			msg = "S�lectionnez les coordonn�es de la carte � poser";
		}else if (partie.getEtat() == "attenteDeplacer" || partie.getEtat() == "erreurChoixDeplacer") {
			msg = "S�lectionnez les coordonn�es de la carte � d�placer";
		}else if(partie.getEtat().indexOf("poser") != -1){
			msg = VueText.FINTOUR + " pour terminer son tour";
		}
		else if(partie.getEtat().indexOf("poser") != -1){
			msg = VueText.FINTOUR + " pour terminer son tour";
		} else if (partie.getEtat() == "finTour") {
			if(partie.getRound() > 1) {
				msg = VueText.POSER + " pour poser une carte / " + VueText.DEPLACER + " pour d�placer une carte";
			} else {
				msg = VueText.POSER + " pour poser une carte !";
			}
		}
		return msg;
	}

	@Override
	/**
	 * Effectue des affichages consoles � chaque modification de l'�tat de l'objet observ�.
	 * <br>La m�thode est appel�e � chaque modification apport�e sur l'objet Partie. 
	 * <br>Des modifications graphiques sont effectu�es en fonction de l'�tat de la partie apr�s notification des observateurs. 
	 * @param o Objet observ� qui subit des modifications
	 * @see fr.corentinPierre.models.Partie
	 * 
	 */
	public void update(Observable o, Object arg1) {
		if(o instanceof Partie) {
			switch (((Partie) o).getEtat()) {
			case "initialisation" : {
				
				System.out.println(o);
				System.out.println();
				System.out.println("Tour(s): " + ((Partie)o).getRound());
				System.out.println("Deck: " + (((Partie)o).getDeck().size()));
				System.out.println("C'est au tour de " + ((Partie)o).getJoueurs().get(0).getNom() + " de jouer");
				if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
					partie.getJoueurs().get(0).afficherMain();
				} else {
					System.out.println("Carte pioch�e " + ((Partie)o).getCartePiochee());	
				}
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
				System.out.println("En attente de d�placer une carte...");
				break;
			}
			case "erreurChoixDeplacer": {
				System.out.println("Rechoisir votre carte � d�placer");
				break;
			}
			case "finTour" : {
				int idJoueur = ((Partie) o).getRound()%((Partie)o).getJoueurs().size();
				System.out.println("Tour: " + (((Partie) o).getRound() + 1));
				System.out.println("Deck: " + (((Partie)o).getDeck().size()));
				System.out.println("C'est au tour de " + ((Partie)o).getJoueurs().get(idJoueur).getNom() + " de jouer");
				if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
					partie.getJoueurs().get(idJoueur).afficherMain();
				} else {
					System.out.println("Carte pioch�e " + ((Partie)o).getCartePiochee());	
				}
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
			}
			if(((Partie) o).getEtat().indexOf("poser") != -1) {
				//Alors on r�cup�re les coordonn�es
				int x, y;
				x = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 2));
				y = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 1));
				System.out.println("Placement de la carte en " + x + ", " + y);
			} else if (((Partie) o).getEtat().indexOf("carteADeplacer") != -1) {
				System.out.println("Carte � d�placer: ");
				System.out.println(partie.getCarteADeplacer());
			} else if (((Partie) o).getEtat().indexOf("deplacerCarte") != -1) {
				int x, y;
				x = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 2));
				y = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 1));
				System.out.println("Carte d�plac�e en " + x + ", " + y);
			}
			else if (((Partie) o).getEtat().indexOf("carteAPoser") != -1) {
				System.out.println("Carte � poser: ");
				System.out.println(((Partie) o).getVariante().getCarteAPoser());
			}
		}
	}
	
	/**
	 * Retourne la saisie de l'utilisateur dans la console
	 * @return String Saisie de l'utilisateur.
	 */
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
