package fr.corentinPierre.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import fr.corentinPierre.models.Configuration;
import fr.corentinPierre.models.JoueurHumain;
import fr.corentinPierre.models.JoueurVirtuel;
import fr.corentinPierre.models.JoueurVirtuelDebutant;
import fr.corentinPierre.models.Partie;
import fr.corentinPierre.models.Plateau;
import fr.corentinPierre.models.Variante1;
import fr.corentinPierre.models.Variante2;
import fr.corentinPierre.models.Variante3;
import fr.corentinPierre.views.Regles;

public class ControleurConfiguration {
	private Partie partie;
	private Configuration config;
	
	public ControleurConfiguration(Configuration config, Partie p) {
		this.partie = p;
		this.config = config;
	}
	
	public void changeNbJoueurs(JComboBox<String> select) {
		select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setNombreJoueursAttendu(Integer.parseInt((String) select.getSelectedItem()));
				
			}
		});
	}
	
	public void changeType(JComboBox<String> select) {
		select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setTypePartie((String) select.getSelectedItem());
				
			}
		});
	}
	
	public void displayRules(JButton btn) {
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Regles dialog = new Regles();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void startPartie(JButton btn, ArrayList<JTextField> inputs, ArrayList<JCheckBox> checkboxes) {
		//Plateau p = new Plateau();
		//this.partie = new Variante1("Normal", p);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(config.getNombreJoueursAttendu() == 2) {
					for(int i = 0; i < 2; i++) {
						if(checkboxes.get(i).isSelected()) {
							JoueurVirtuelDebutant jv = new JoueurVirtuelDebutant(i, "Joueur " + i+1);
							partie.ajouterJoueur(jv);
						} else {
							JoueurHumain jh = new JoueurHumain(i, inputs.get(i).getText());
							partie.ajouterJoueur(jh);
						}
					}
				} else {
					for(int i = 0; i < 3; i++) {
						if(checkboxes.get(i).isSelected()) {
							JoueurVirtuelDebutant jv = new JoueurVirtuelDebutant(i, "Joueur " + i+1);
							partie.ajouterJoueur(jv);
						} else {
							JoueurHumain jh = new JoueurHumain(i, inputs.get(i).getText());
							partie.ajouterJoueur(jh);
						}
					}
				}
				switch (config.getTypePartie()) {
				case "Classique": {
					Variante1 v = new Variante1("Normal", partie);
					partie.setVariante(v);
					break;
				}
				case "Avancé": {
					Variante2 v = new Variante2("Avance", partie);
					partie.setVariante(v);
					break;
				}
				case "Refill": {
					Variante1 v = new Variante1("Refill", partie);
					partie.setVariante(v);
					break;
				}
				default:
					//throw new IllegalArgumentException("Unexpected value: " + config.getTypePartie());
				}
				partie.initialisation();
			}
		});
	}
}
