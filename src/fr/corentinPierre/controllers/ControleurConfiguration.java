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
import fr.corentinPierre.models.JoueurVirtuelDebutant;
import fr.corentinPierre.models.Partie;
import fr.corentinPierre.models.Variante1;
import fr.corentinPierre.models.Variante2;
import fr.corentinPierre.views.Regles;


/**
 * Contr�leur de la configuration d'une partie. 
 * Permet de g�rer l'interface graphique de configuration d'une partie.
 * Il fait le lien entre les objets du mod�le et la vue. 
 * 
 * @author Corentin Parpette
 * @author Pierre Treuchot
 * 
 * @see fr.corentinPierre.views.Configuration
 * @see fr.corentinPierre.models.Configuration
 * @see fr.corentinPierre.models.Partie
 *
 */
public class ControleurConfiguration {
	/**
	 * Partie qui sera instanci�e et lanc�e � l'appel de startPartie
	 */
	private Partie partie;
	/**
	 * Configuration qui sera modifi� � chaque action sur les boutons de l'interface. 
	 */
	private Configuration config;
	
	public ControleurConfiguration(Configuration config, Partie p) {
		this.partie = p;
		this.config = config;
	}
	
	/**
	 * Change le nombre de joueurs en fonction de l'affichage du nombre de joueurs sur l'interface.
	 * @param select Bouton select associ� au nombre de joueurs au niveau de la Configuration. Le select doit �tre du type JComboBox<String> et non null
	 */
	public void changeNbJoueurs(JComboBox<String> select) {
		select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setNombreJoueursAttendu(Integer.parseInt((String) select.getSelectedItem()));
				
			}
		});
	}
	
	/**
	 * Change le type de la partie en fonction de l'affichage type de partie sur l'interface.
	 * @param select Bouton select associ� au type de la partie au niveau de la Configuration. Le select doit �tre du type JComboBox<String> et non nul
	 */
	public void changeType(JComboBox<String> select) {
		select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setTypePartie((String) select.getSelectedItem());
				
			}
		});
	}
	
	/**
	 * Affiche les r�gles de la partie dans une nouvelle fen�tre
	 * @param btn Bouton o� l'�v�nement est associ� de type JButton non nul
	 */
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
	
	/**
	 * Lance la partie en fonction de son type et du nombre / type de joueurs attendu.
	 * Instancie une nouvelle partie et y ajoute la variante, le nombre de joueurs et leur type (virtuel / humain) attendus 
	 * @param btn Bouton o� l'�v�nement est associ� de type JButton non nul
	 * @param inputs Champs de texte o� les noms des Joueurs sont saisis, de type ArrayList<JTextField> non nul.
	 * @param checkboxes, Bouton checkBox o� le type du joueur est sp�cifi� (virtuel ou humain) de type ArrayList<JCheckBox> non nul.
	 * 
	 */
	public void startPartie(JButton btn, ArrayList<JTextField> inputs, ArrayList<JCheckBox> checkboxes) {
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
				case "Avanc�": {
					Variante2 v = new Variante2("Avance", partie);
					partie.setVariante(v);
					break;
				}
				case "Refill": {
					Variante1 v = new Variante1("Refill", partie);
					partie.setVariante(v);
					break;
				}
			}
				partie.initialisation();
			}
		});
	}
}
