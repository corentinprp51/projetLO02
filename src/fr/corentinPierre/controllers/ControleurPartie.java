package fr.corentinPierre.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;

import fr.corentinPierre.models.Carte;
import fr.corentinPierre.models.Partie;
import fr.corentinPierre.views.JButtonCustom;
import fr.corentinPierre.views.JButtonIndex;
import fr.corentinPierre.views.Scores;


/**
 * Contrôleur responsable du déroulement de la partie. 
 * <br>Permet de gérer l'interface graphique d'une partie.
 * <br>Il fait le lien entre les objets du modèle et la vue. 
 * 
 * @author Corentin Parpette
 * @author Pierre Treuchot
 * 
 * @see fr.corentinPierre.views.MonInterface
 * @see fr.corentinPierre.models.Partie
 *
 */

public class ControleurPartie {
	/**
	 * Partie en cours
	 */
	private Partie partie;
	public ControleurPartie( final Partie partie) {
		this.partie = partie;
	}
	
	/**
	 * Met fin au tour du joueur en cours.
	 * <br>Lors du clic sur un bouton, c'est au tour du joueur suivant de jouer.
	 * <br>En fonction de la variante, la partie peut se terminer si le deck est vide ou si les joueurs n'ont plus qu'une carte en main.
	 * @param btn Bouton de type JButton où l'évènement de clic lui est associé
	 */
	public void finTour(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
			}
		});
	}
	
	/**
	 * Met la partie en attente pour permettre au joueur de poser une carte
	 * <br>Lors du clic sur le bouton, la partie se met en attente afin que le joueur puisse poser une carte.
	 * @param btn Bouton de type JButton où l'évènement de clic lui est associé
	 */
	public void askPoser(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				partie.setEtat("attentePoser");
			}
		});
	}
	
	/**
	 * Met la partie en attente pour permettre au joueur de déplacer une carte
	 * <br>Lors du clic sur le bouton, la partie se met en attente afin que le joueur puisse déplacer une carte.
	 * @param btn Bouton de type JButton où l'évènement de clic lui est associé
	 */
	public void askDeplacer(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				partie.setEtat("attenteDeplacer");
			}
		});
	}
	
	/**
	 * Permet au joueur de poser une carte sur le plateau.
	 * <br>Lors du clique sur une des cases du plateau, la méthode est appelée et va permettre au joueur de poser une carte si la règle d'adjacence est respectée.
	 * <br>S'il est impossible de la poser, la partie se met dans un état erreurPoser ou impossiblePoser
	 * @param grille La grille du plateau de type ArrayList et non nulle. 
	 * @see fr.corentinPierre.views.JButtonCustom
	 * @see fr.corentinPierre.controllers.ControleurPartie#carteAPoser(ArrayList)
	 */
	public void poserCarte(ArrayList<JButtonCustom> grille) {
		grille.forEach(btn -> {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(partie.getEtat() == "attentePoser" || partie.getEtat() == "erreurPoser" || partie.getEtat().indexOf("carteAPoser") != -1) {
							boolean isPosee;
							if(partie.getCartePiochee() != null || partie.getVariante().getCarteAPoser() != null) {
								isPosee = partie.poserCarte(btn.getXGrille(), btn.getYGrille());
							} else {
								isPosee = false;
							}
							
							if(isPosee == false) {
								partie.setEtat("erreurPoser");
							}
					} else if(partie.getEtat() == "attenteDeplacer" || partie.getEtat() == "erreurChoixDeplacer") {
						Carte c = partie.prendreCarteADeplacer(btn.getXGrille(), btn.getYGrille());
						if(c == null) {
							partie.setEtat("erreurChoixDeplacer");
						}
					} else if (partie.getEtat().indexOf("carteADeplacer") != -1) {
						Carte c = partie.getCarteADeplacer();
						boolean isDeplacee = partie.deplacerCarte(c, btn.getXGrille(), btn.getYGrille());
						if(!isDeplacee) {
							partie.setEtat("carteADeplacer" + btn.getXGrille() + btn.getYGrille());
						}
					} else {
						partie.setEtat("impossiblePoser");
					}
				}
			});
		});
	}
	
	/**
	 * (Variante2) Permet au joueur de choisir dans sa main la carte qu'il souhaite poser.
	 * <br>Lors du clic sur un des boutons correspondant à sa main, la carte (si elle est non nulle), la partie va sauvegarder cette Carte pour la poser à l'aide de la méthode poserCarte. 
	 * @param main La main du joueur dans la Variante Avancée de type ArrayList et non nulle
	 * @see fr.corentinPierre.views.JButtonIndex
	 */
	public void carteAPoser(ArrayList<JButtonIndex> main) {
		main.forEach(btn -> {
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
						Carte c = partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()).removeCarteFromMainByIndex(btn.getIndex());
						partie.getVariante().setCarteAPoser(c);
						partie.setEtat("carteAPoser" + btn.getIndex());
					}
					
				}
			});
		});
	}
	
	/**
	 * Affiche la règle des scores lors du clic sur un bouton
	 * @param btn Bouton de type JButton où l'évènement clic lui est associé
	 */
	public void displayScores(JButton btn) {
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Scores dialog = new Scores();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

}
