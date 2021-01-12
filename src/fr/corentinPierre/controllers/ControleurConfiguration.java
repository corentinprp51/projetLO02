package fr.corentinPierre.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import fr.corentinPierre.models.Configuration;
import fr.corentinPierre.models.JoueurHumain;
import fr.corentinPierre.models.JoueurVirtuel;
import fr.corentinPierre.models.JoueurVirtuelDebutant;
import fr.corentinPierre.models.Partie;
import fr.corentinPierre.models.Plateau;
import fr.corentinPierre.models.Variante1;

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
					System.out.println("Avancé");
				}
				case "Refill": {
					System.out.println("Refill");
				}
				default:
					//throw new IllegalArgumentException("Unexpected value: " + config.getTypePartie());
				}
				partie.initialisation();
			}
		});
	}
}
