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
 * Permet de gérer l'interface graphique d'une partie.
 * Il fait le lien entre les objets du modèle et la vue. 
 * 
 * @author Corentin Parpette
 * @author Pierre Treuchot
 * 
 * @see fr.corentinPierre.views.MonInterface
 * @see fr.corentinPierre.models.Partie
 *
 */

public class ControleurPartie {
	private Partie partie;
	public ControleurPartie( final Partie partie) {
		this.partie = partie;
	}
	
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
	
	public void askPoser(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				partie.setEtat("attentePoser");
			}
		});
	}
	
	public void askDeplacer(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				partie.setEtat("attenteDeplacer");
			}
		});
	}
	
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
