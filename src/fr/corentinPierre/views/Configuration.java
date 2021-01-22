package fr.corentinPierre.views;

import javax.swing.JPanel;

import fr.corentinPierre.controllers.ControleurConfiguration;
import fr.corentinPierre.models.Partie;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class Configuration extends JPanel implements Observer {

	private JTextField nomJoueur1;
	private JTextField nomJoueur2;
	private JTextField nomJoueur3;
	
	private fr.corentinPierre.models.Configuration config;
	private Partie partie;
	
	JComboBox<String> typePartie;
	JComboBox<String> nbJoueurs;
	JButton btnStart;
	JPanel panelJoueur1;
	JPanel panelJoueur2;
	JPanel panelJoueur3;
	JPanel panelJoueurs;
	JCheckBox virtuel1;
	JCheckBox virtuel2;
	JCheckBox virtuel3;
	ArrayList<JCheckBox> checkboxes;
	ArrayList<JTextField> inputs;
	JLabel wIcon;
	JButton btnRegles;

	/**
	 * Create the panel.
	 */
	public Configuration(fr.corentinPierre.models.Configuration c, Partie p) {
		this.partie = p;
		this.config = c;
		this.setSize(796, 435);
		this.setLayout(null);
		this.initialize();
		ControleurConfiguration cc = new ControleurConfiguration(this.config, this.partie);
		cc.changeNbJoueurs(nbJoueurs);
		cc.startPartie(btnStart, inputs, checkboxes);
		cc.changeType(typePartie);
		cc.displayRules(btnRegles);
		config.addObserver(this);
		
		//Option
		checkboxes.get(0).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkboxes.get(0).isSelected()) {
					inputs.get(0).setEnabled(false);
				} else {
					inputs.get(0).setEnabled(true);
				}
				
			}
		});
		checkboxes.get(1).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkboxes.get(1).isSelected()) {
					inputs.get(1).setEnabled(false);
				} else {
					inputs.get(1).setEnabled(true);
				}
				
			}
		});
		checkboxes.get(2).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkboxes.get(2).isSelected()) {
					inputs.get(2).setEnabled(false);
				} else {
					inputs.get(2).setEnabled(true);
				}
				
			}
		});
	}
	
	private void initialize() {
		JLabel lblNewLabel = new JLabel("Configuration de la partie");
		lblNewLabel.setBounds(244, 11, 291, 67);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		this.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 83, 381, 309);
		panel.setToolTipText("Type de Partie");
		this.add(panel);
		panel.setLayout(null);
		
		typePartie = new JComboBox();
		typePartie.setBounds(155, 34, 71, 20);
		typePartie.setModel(new DefaultComboBoxModel(new String[] {"Classique", "Avanc\u00E9", "Refill"}));
		typePartie.setToolTipText("Type de Partie");
		panel.add(typePartie);
		
		JLabel lblModeDeJeu = new JLabel("Mode de jeu");
		lblModeDeJeu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblModeDeJeu.setBounds(137, 0, 106, 34);
		panel.add(lblModeDeJeu);
		
		JLabel lblNombreDeJoueurs = new JLabel("Nombre de joueurs");
		lblNombreDeJoueurs.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombreDeJoueurs.setBounds(112, 65, 156, 34);
		panel.add(lblNombreDeJoueurs);
		
		nbJoueurs = new JComboBox<String>();
		nbJoueurs.setModel(new DefaultComboBoxModel(new String[] {"2", "3"}));
		nbJoueurs.setToolTipText("Type de Partie");
		nbJoueurs.setBounds(155, 99, 71, 20);
		panel.add(nbJoueurs);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(50, 264, 106, 34);
		panel.add(btnStart);
		
		panelJoueurs = new JPanel();
		panelJoueurs.setBounds(10, 128, 361, 120);
		panel.add(panelJoueurs);
		panelJoueurs.setLayout(null);
		
		panelJoueur1 = new JPanel();
		panelJoueur1.setBounds(0, 0, 361, 41);
		panelJoueurs.add(panelJoueur1);
		panelJoueur1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Joueur 1");
		panelJoueur1.add(lblNewLabel_1);
		
		nomJoueur1 = new JTextField();
		panelJoueur1.add(nomJoueur1);
		nomJoueur1.setColumns(10);
		
		virtuel1 = new JCheckBox("Virtuel");
		panelJoueur1.add(virtuel1);
		
		panelJoueur2 = new JPanel();
		panelJoueur2.setBounds(0, 39, 361, 41);
		panelJoueurs.add(panelJoueur2);
		panelJoueur2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_1_1 = new JLabel("Joueur 2");
		panelJoueur2.add(lblNewLabel_1_1);
		
		nomJoueur2 = new JTextField();
		nomJoueur2.setColumns(10);
		panelJoueur2.add(nomJoueur2);
		
		virtuel2 = new JCheckBox("Virtuel");
		panelJoueur2.add(virtuel2);
		
		panelJoueur3 = new JPanel();
		panelJoueur3.setBounds(0, 79, 361, 41);
		panelJoueurs.add(panelJoueur3);
		panelJoueur3.setVisible(false);
		panelJoueur3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_1_2 = new JLabel("Joueur 3");
		panelJoueur3.add(lblNewLabel_1_2);
		
		nomJoueur3 = new JTextField();
		nomJoueur3.setColumns(10);
		panelJoueur3.add(nomJoueur3);
		
		virtuel3 = new JCheckBox("Virtuel");
		panelJoueur3.add(virtuel3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(389, 83, 381, 309);
		this.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		wIcon = new JLabel("");
		wIcon.setBounds(389, 83, 381, 309);
		panel_1.add(wIcon);
		
		BufferedImage wPic;
		try {
			wPic = ImageIO.read(this.getClass().getResource("/logo.PNG"));
			wIcon.setIcon(new ImageIcon(wPic));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		inputs = new ArrayList<>();
		inputs.add(nomJoueur1);
		inputs.add(nomJoueur2);
		inputs.add(nomJoueur3);
		checkboxes = new ArrayList<>();
		checkboxes.add(virtuel1);
		checkboxes.add(virtuel2);
		checkboxes.add(virtuel3);
		btnRegles = new JButton("R\u00E8gles");
		btnRegles.setBounds(212, 264, 106, 34);
		panel.add(btnRegles);
	}

	@Override
	public void update(Observable o, Object arg1) {
		if(o instanceof fr.corentinPierre.models.Configuration) {
			switch (((fr.corentinPierre.models.Configuration) o).getEtat()) {
				case "config.2joueurs": {
					System.out.println("2 joueurs");
					panelJoueur3.setVisible(false);
					break;
				}
				case "config.3joueurs": {
					System.out.println("3 joueurs");
					panelJoueur3.setVisible(true);
					break;
				}
			}	
		}
	}
}
