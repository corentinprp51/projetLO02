package fr.corentinPierre.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import fr.corentinPierre.models.Carte;
import fr.corentinPierre.models.JoueurHumain;
import fr.corentinPierre.models.JoueurVirtuel;
import fr.corentinPierre.models.JoueurVirtuelDebutant;
import fr.corentinPierre.models.Partie;
import fr.corentinPierre.views.JButtonCustom;


public class ControleurPartie {
	private Partie partie;
	public ControleurPartie( final Partie partie) {
		this.partie = partie;
	}
	
	public void startEvent(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JoueurHumain p1 = new JoueurHumain(0, "Corentin");
				JoueurHumain p2 = new JoueurHumain(1, "Pierre");
				JoueurHumain p3 = new JoueurHumain(2, "Dorian");
				JoueurVirtuel v1 = new JoueurVirtuelDebutant(0, "Virtuel 1");
				JoueurVirtuel v2 = new JoueurVirtuelDebutant(1, "Virtuel 2");
				JoueurVirtuel v3 = new JoueurVirtuelDebutant(2, "Virtuel 3");
				partie.ajouterJoueur(p1);
				partie.ajouterJoueur(v1);
				//partie.ajouterJoueur(p3);
				partie.initialisation();
				btn.setEnabled(false);
				//System.out.println(partie);
			}
		});
	}
	
	public void finTour(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(partie.getDeck().size() > 0 ) {
					partie.piocher();
					partie.nouveauRound();
				} else {
					partie.calculerScore();
				}
				//System.out.println(partie);
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
					if(partie.getEtat() == "attentePoser" || partie.getEtat() == "erreurPoser") {
						boolean isPosee = partie.poserCarte(btn.getXGrille(), btn.getYGrille());
						if(isPosee == false) {
							partie.setEtat("erreurPoser");
						}
					} else if(partie.getEtat() == "attenteDeplacer" || partie.getEtat() == "erreurChoixDeplacer") {
						//Choix d'une carte à déplacer
						Carte c = partie.prendreCarteADeplacer(btn.getXGrille(), btn.getYGrille());
						//btn.setIcon(null);
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

}
