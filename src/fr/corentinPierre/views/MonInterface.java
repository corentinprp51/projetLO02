package fr.corentinPierre.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fr.corentinPierre.controllers.ControleurPartie;
import fr.corentinPierre.models.Carte;
import fr.corentinPierre.models.JoueurHumain;
import fr.corentinPierre.models.JoueurVirtuel;
import fr.corentinPierre.models.JoueurVirtuelDebutant;
import fr.corentinPierre.models.Partie;
import fr.corentinPierre.models.Plateau;
import fr.corentinPierre.models.Variante1;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MonInterface implements Observer {

	private JFrame frame;
	private Partie partie;
	private ArrayList<JButtonCustom> grille;
	private JPanel panel_2;
	private ArrayList<JPanel> scores;
	private JLabel lblVainqueur;
	private Configuration configurationPanel;
	private JPanel panel;
	private Variante2 panelVariante2;
	private fr.corentinPierre.views.Variante1 panelVariante1;
	private JButton btnDisplayScore;
	private JDialog dialog;
	private JPanel contentPanel;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			//Plateau p = new Plateau();
			Partie partie = Partie.loadPartie("src/save.ser");
			public void run() {
				try {
					MonInterface window = new MonInterface(partie);
					window.frame.setVisible(true);
					new VueText(partie);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MonInterface(Partie partie) {
		this.partie = partie;
		this.grille = new ArrayList<>();
		this.scores = new ArrayList<>();
		initialize();
		partie.addObserver(this);
		partie.setEtat(partie.getEtat());
		ControleurPartie cp = new ControleurPartie(partie);
		cp.displayScores(btnDisplayScore);
		cp.poserCarte(grille);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.initializeSauvegardeDialog();
		frame = new JFrame();
		frame.setBounds(100, 100, 796, 542);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				if(partie.getEtat() == "finTour") {
					try {
						dialog.setVisible(true);
						okButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								Partie.savePartie(partie, "src/save.ser");
								dialog.dispose();
								System.exit(0);
							}
						});
						cancelButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								File file = new File("src/save.ser");
								if(file != null) {
									file.delete();
								}
								System.exit(0);
							}
						});
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if(partie.getEtat().equalsIgnoreCase("finPartie") || partie.getEtat().equalsIgnoreCase("")) {
					File file = new File("save.ser");
					if(file != null) {
						file.delete();
					}
					System.exit(0);
				} else {
					File file = new File("save.ser");
					if(file != null) {
						file.delete();
					}
					System.exit(0);
				}
			}
		});
		
		this.configurationPanel = new Configuration(new fr.corentinPierre.models.Configuration(), partie);
		frame.getContentPane().add(this.configurationPanel);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(5,3));
		JButtonCustom button0_4 = new JButtonCustom("",0,4);
		panel.add(button0_4);
		grille.add(button0_4);
		
		JButtonCustom button1_4 = new JButtonCustom("",1,4);
		panel.add(button1_4);
		grille.add(button1_4);
		
		JButtonCustom button2_4 = new JButtonCustom("",2,4);
		panel.add(button2_4);
		grille.add(button2_4);
		
		JButtonCustom button0_3 = new JButtonCustom("",0,3);
		panel.add(button0_3);
		grille.add(button0_3);
		
		JButtonCustom button1_3 = new JButtonCustom("",1,3);
		panel.add(button1_3);
		grille.add(button1_3);
		
		JButtonCustom button2_3 = new JButtonCustom("",2,3);
		panel.add(button2_3);
		grille.add(button2_3);
		
		JButtonCustom button0_2 = new JButtonCustom("",0,2);
		panel.add(button0_2);
		grille.add(button0_2);
		
		JButtonCustom button1_2 = new JButtonCustom("",1,2);
		panel.add(button1_2);
		grille.add(button1_2);
		
		JButtonCustom button2_2 = new JButtonCustom("",2,2);
		panel.add(button2_2);
		grille.add(button2_2);
		
		JButtonCustom button0_1 = new JButtonCustom("",0,1);
		panel.add(button0_1);
		grille.add(button0_1);
		
		JButtonCustom button1_1 = new JButtonCustom("",1,1);
		panel.add(button1_1);
		grille.add(button1_1);
		JButtonCustom button2_1 = new JButtonCustom("",2,1);
		panel.add(button2_1);
		grille.add(button2_1);
		
		JButtonCustom button0_0 = new JButtonCustom("",0,0);
		panel.add(button0_0);
		grille.add(button0_0);
		
		JButtonCustom button1_0 = new JButtonCustom("",1,0);
		panel.add(button1_0);
		grille.add(button1_0);
		
		JButtonCustom button2_0 = new JButtonCustom("",2,0);
		panel.add(button2_0);
		grille.add(button2_0);
		
		panel_2 = new JPanel();
		panel_2.setSize(796, 100);
		panel_2.setLayout(null);
		
		lblVainqueur = new JLabel("");
		lblVainqueur.setHorizontalAlignment(SwingConstants.CENTER);
		lblVainqueur.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVainqueur.setBounds(246, 11, 288, 36);
		panel_2.add(lblVainqueur);
		
		JPanel panel_Score_1 = new JPanel();
		panel_Score_1.setBounds(20, 42, 120, 148);
		panel_Score_1.setEnabled(false);
		panel_2.add(panel_Score_1);
		this.scores.add(panel_Score_1);
		panel_Score_1.setLayout(null);
		
		JLabel lbjJoueur1 = new JLabel("Corentin");
		lbjJoueur1.setHorizontalAlignment(SwingConstants.CENTER);
		lbjJoueur1.setBounds(10, 5, 100, 14);
		lbjJoueur1.setEnabled(false);
		panel_Score_1.add(lbjJoueur1);
		
		JButton btnScore1 = new JButton("");
		btnScore1.setBounds(23, 49, 74, 99);
		btnScore1.setEnabled(false);
		panel_Score_1.add(btnScore1);
		
		JLabel labelScore1 = new JLabel("8");
		labelScore1.setHorizontalAlignment(SwingConstants.CENTER);
		labelScore1.setBounds(10, 30, 100, 14);
		labelScore1.setEnabled(false);
		panel_Score_1.add(labelScore1);
		
		JPanel panel_Score_2 = new JPanel();
		panel_Score_2.setLayout(null);
		panel_Score_2.setBounds(330, 42, 120, 148);
		panel_Score_2.setEnabled(false);
		panel_2.add(panel_Score_2);
		this.scores.add(panel_Score_2);
		
		JLabel lbjJoueur2 = new JLabel("Corentin");
		lbjJoueur2.setHorizontalAlignment(SwingConstants.CENTER);
		lbjJoueur2.setBounds(10, 5, 100, 14);
		lbjJoueur2.setEnabled(false);
		panel_Score_2.add(lbjJoueur2);
		
		JButton btnScore2 = new JButton("");
		btnScore2.setBounds(23, 49, 74, 99);
		btnScore2.setEnabled(false);
		panel_Score_2.add(btnScore2);
		
		JLabel labelScore2 = new JLabel("8");
		labelScore2.setHorizontalAlignment(SwingConstants.CENTER);
		labelScore2.setBounds(10, 30, 100, 14);
		labelScore2.setEnabled(false);
		panel_Score_2.add(labelScore2);
		
		JPanel panel_Score_3 = new JPanel();
		panel_Score_3.setLayout(null);
		panel_Score_3.setBounds(635, 42, 120, 148);
		panel_Score_3.setEnabled(false);
		panel_2.add(panel_Score_3);
		this.scores.add(panel_Score_3);
		
		JLabel lbjJoueur3 = new JLabel("Corentin");
		lbjJoueur3.setHorizontalAlignment(SwingConstants.CENTER);
		lbjJoueur3.setBounds(10, 5, 100, 14);
		lbjJoueur3.setEnabled(false);
		lbjJoueur3.setVisible(false);
		panel_Score_3.add(lbjJoueur3);
		
		JButton btnScore3 = new JButton("");
		btnScore3.setBounds(23, 49, 74, 99);
		btnScore3.setEnabled(false);
		btnScore3.setVisible(false);
		panel_Score_3.add(btnScore3);
		
		JLabel labelScore3 = new JLabel("8");
		labelScore3.setHorizontalAlignment(SwingConstants.CENTER);
		labelScore3.setBounds(10, 30, 100, 14);
		labelScore3.setEnabled(false);
		labelScore3.setVisible(false);
		panel_Score_3.add(labelScore3);
		
		//Désactivation des boutons de la grille
		this.setGrilleEnabled(false);
		
		panelVariante1 = new fr.corentinPierre.views.Variante1(partie);
		panelVariante2 = new Variante2(partie);
		
		btnDisplayScore = new JButton("R\u00E8gle Score");
		btnDisplayScore.setBounds(544, 11, 150, 36);
		panel_2.add(btnDisplayScore);
	}

	@Override
	public void update(Observable o, Object arg1) {
		if(o instanceof Partie) {
			switch (((Partie) o).getEtat()) {
			case "initialisation": {
				frame.getContentPane().remove(configurationPanel);
				frame.getContentPane().add(panel);
				if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
					 frame.getContentPane().add(panelVariante2);
					 panelVariante1 = null;
				} else {
					panelVariante2 = null;
					frame.getContentPane().add(panelVariante1);
				}
				this.setGrilleEnabled(true);
				if(this.isJoueurVirtuel()) {
					JoueurVirtuel jv = this.getJoueurVirtuel();
					int[] coords = jv.choisirEmplacement();
					if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
						int idJoueur = ((Partie) o).getRound()%((Partie)o).getJoueurs().size();
						panelVariante2.clickPoser();
					} else {
						System.out.println("Coordonnées random: " + coords[0] + ", " + coords[1]);
						panelVariante1.clickPoser();
					}
					
					this.findButton(coords[0], coords[1]).doClick();
				}
				break;
			} 
			case "attentePoser": {
				if(this.isJoueurVirtuel()) {
					JoueurVirtuel jv = this.getJoueurVirtuel();
					int[] coords = jv.choisirEmplacement();
					if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
						panelVariante2.attentePoser();
						panelVariante2.clickMain(0);
					}
					this.findButton(coords[0], coords[1]).doClick();
				}
				break;
			}
			case "finTour": {
				if(partie.getLoaded()) {
					frame.getContentPane().remove(configurationPanel);
					frame.getContentPane().add(panel);
					this.setGrilleEnabled(true);
					this.retablirGrille();
					if(partie.getVariante().getNom().equalsIgnoreCase("Normal") || partie.getVariante().getNom().equalsIgnoreCase("Refill")) {
						frame.getContentPane().add(panelVariante1);
					} else {
						frame.getContentPane().add(panelVariante2);
					}
				}
				
				break;
				
			}
			case "erreurPoser": {
				if(this.isJoueurVirtuel()) {
					JoueurVirtuel jv = this.getJoueurVirtuel();
					int[] coords = jv.choisirEmplacement();
					System.out.println("Coordonnées random: " + coords[0] + ", " + coords[1]);
					if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
						panelVariante2.clickMain(0);
					}
					this.findButton(coords[0], coords[1]).doClick();
				}
				break;
			}
			case "finPartie": {
				if(partie.getVariante().getNom().equalsIgnoreCase("Avance")) {
					frame.getContentPane().remove(panelVariante2);
				} else {
					frame.getContentPane().remove(panelVariante1);
				}
				
				frame.getContentPane().add(panel_2);
				//Affichage du vainqueur
				lblVainqueur.setText("Vainqueur: " + ((Partie)o).getVainqueur().getNom());
				for(int i = 0; i < partie.getJoueurs().size(); i++) {
					this.scores.get(i).setEnabled(true);
					Component[] components = this.scores.get(i).getComponents();
					components[0].setEnabled(true);
					components[1].setEnabled(true);
					components[2].setEnabled(true);
					components[0].setVisible(true);
					components[1].setVisible(true);
					components[2].setVisible(true);
					((JLabel) components[0]).setText(((Partie) o).getJoueurs().get(i).getNom());
					((JLabel) components[2]).setText(String.valueOf(((Partie) o).getJoueurs().get(i).getScore()));
					ImageIcon victoryImg = this.resizeImage(((Partie) o).getJoueurs().get(i).getCarteVictoire().getImageName(), 100, 100);
					((JButton) components[1]).setIcon(victoryImg);
					
				}
				
				break;
			}
		}
			if(((Partie) o).getEtat().indexOf("poser") != -1) {
				int x, y;
				x = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 2));
				y = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 1));
				ImageIcon imgPosee = this.resizeImage(((Partie) o).getPlateau().getCartesPosees().get(y).get(x).getImageName(), 100, 100);
				this.findButton(x, y).setIcon(imgPosee);
			} else if (((Partie) o).getEtat().indexOf("deplacerCarte") != -1) {
				int x, y;
				x = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 2));
				y = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 1));
				Carte c = partie.getPlateau().getCartesPosees().get(y).get(x);
				ImageIcon imgPosee = this.resizeImage(c.getImageName(), 100, 100);
				this.findButton(x, y).setIcon(imgPosee);
			} else if (((Partie) o).getEtat().indexOf("carteADeplacer") != -1) {
				//Supprimer l'image correspondant à la carte choisie
				int x, y;
				x = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 2));
				y = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 1));
				if(this.partie.getPlateau().getCartesPosees().get(y) != null) {
					if(!this.partie.getPlateau().getCartesPosees().get(y).containsKey(x)) {
						this.findButton(x, y).setIcon(null);
					}
				}
			}
			
		}
	}
	private ImageIcon resizeImage(String name, int w, int h) {

		BufferedImage img = null;
		try {
			img = ImageIO.read(this.getClass().getResource("/" + name + ".jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(w, h,Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}
	
	private JButtonCustom findButton(int x, int y) {
		ArrayList <JButtonCustom> optionalVariable = this.grille.stream().filter(element -> element.getXGrille() == x && element.getYGrille() == y).collect(Collectors.toCollection(ArrayList::new));
		return optionalVariable.get(0);
	}
	
	private void setGrilleEnabled(boolean state) {
		this.grille.forEach(btn -> btn.setEnabled(state));
	}
	
	private boolean isJoueurVirtuel() {
		return partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()) instanceof JoueurVirtuel;
	}
	private JoueurVirtuel getJoueurVirtuel() {
		return (JoueurVirtuel) partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size());
	}
	
	private void retablirGrille() {
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: partie.getPlateau().getCartesPosees().entrySet() ) {
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				
				this.findButton(entry2.getKey(), entry.getKey()).setIcon(this.resizeImage(entry2.getValue().getImageName(), 100, 100));
			}
		}
	}
	
	private void initializeSauvegardeDialog() {
		//Création du JDialog de Sauvegarde
				dialog = new JDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setBounds(100, 100, 450, 300);
				dialog.getContentPane().setLayout(new BorderLayout());
				contentPanel = new JPanel();
				contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
				contentPanel.setLayout(null);
				
				JLabel lblNewLabel = new JLabel("Sauvegarder la partie ?");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setBounds(10, 67, 414, 93);
				contentPanel.add(lblNewLabel);
				{
					JPanel buttonPane = new JPanel();
					buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
					dialog.getContentPane().add(buttonPane, BorderLayout.SOUTH);
					{
						okButton = new JButton("Oui");
						okButton.setActionCommand("Oui");
						buttonPane.add(okButton);
						dialog.getRootPane().setDefaultButton(okButton);
					}
					{
						cancelButton = new JButton("Non");
						cancelButton.setActionCommand("Non");
						buttonPane.add(cancelButton);
					}
				}	
	}
	
}
