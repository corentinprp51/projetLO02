package fr.corentinPierre.views;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;

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

public class MonInterface implements Observer {

	private JFrame frame;
	private Partie partie;
	private JButton buttonStart;
	private JLabel labelJoueur;
	private JLabel labelTour;
	private JLabel labelDeck;
	private JButton buttonCarteVictoire;
	private JButton buttonFinTour;
	private JButton buttonPoser;
	private JButton buttonCartePiochee;
	private JLabel labelInfos;
	private JButton buttonDeplacer;
	private ArrayList<JButtonCustom> grille;
	private JPanel panel_1;
	private JPanel panel_2;
	private ArrayList<JPanel> scores;
	private JLabel lblVainqueur;
	private Configuration configurationPanel;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			Plateau p = new Plateau();
			Partie partie = new Partie(p);
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
		ControleurPartie cp = new ControleurPartie(partie);
		cp.startEvent(buttonStart);
		cp.finTour(buttonFinTour);
		cp.askPoser(buttonPoser);
		cp.askDeplacer(buttonDeplacer);
		
		cp.poserCarte(grille);
		
		partie.addObserver(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 796, 442);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		this.configurationPanel = new Configuration(new fr.corentinPierre.models.Configuration(), partie);
		frame.getContentPane().add(this.configurationPanel);
		
		panel = new JPanel();
		//frame.getContentPane().add(panel);
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
		
		panel_1 = new JPanel();
		panel_1.setSize(796, 100);
		//frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		buttonPoser = new JButton("Poser Carte");
		buttonPoser.setBounds(199, 144, 89, 23);
		buttonPoser.setEnabled(false);
		panel_1.add(buttonPoser);
		
		buttonDeplacer = new JButton("D\u00E9placer Carte");
		buttonDeplacer.setBounds(300, 144, 105, 23);
		buttonDeplacer.setEnabled(false);
		panel_1.add(buttonDeplacer);
		
		buttonFinTour = new JButton("Fin Tour");
		buttonFinTour.setBounds(419, 144, 89, 23);
		buttonFinTour.setEnabled(false);
		panel_1.add(buttonFinTour);
		
		buttonCartePiochee = new JButton("");
		buttonCartePiochee.setBounds(0, 38, 114, 129);
		panel_1.add(buttonCartePiochee);
		
		buttonCarteVictoire = new JButton("");
		buttonCarteVictoire.setBounds(665, 38, 105, 129);
		panel_1.add(buttonCarteVictoire);
		
		JLabel labelCartePiochée = new JLabel("Carte Pioch\u00E9e");
		labelCartePiochée.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelCartePiochée.setBounds(10, 11, 89, 23);
		panel_1.add(labelCartePiochée);
		
		JLabel labelCarteVictoire = new JLabel("Carte Victoire");
		labelCarteVictoire.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelCarteVictoire.setBounds(681, 11, 89, 23);
		panel_1.add(labelCarteVictoire);
		
		labelJoueur = new JLabel("");
		labelJoueur.setFont(new Font("Tahoma", Font.PLAIN, 25));
		labelJoueur.setBounds(300, 11, 105, 42);
		panel_1.add(labelJoueur);
		
		labelDeck = new JLabel("Deck: /");
		labelDeck.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelDeck.setBounds(321, 48, 56, 23);
		panel_1.add(labelDeck);
		
		labelInfos = new JLabel("Messages d'information ici");
		labelInfos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelInfos.setBounds(199, 95, 309, 38);
		panel_1.add(labelInfos);
		
		buttonStart = new JButton("Start");
		buttonStart.setBounds(310, 178, 89, 23);
		panel_1.add(buttonStart);
		
		labelTour = new JLabel("Tour: /");
		labelTour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelTour.setBounds(321, 76, 56, 23);
		panel_1.add(labelTour);
		
		
		panel_2 = new JPanel();
		panel_2.setSize(796, 100);
		panel_2.setLayout(null);
		
		lblVainqueur = new JLabel("");
		lblVainqueur.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVainqueur.setBounds(0, 11, 796, 36);
		panel_2.add(lblVainqueur);
		
		JPanel panel_Score_1 = new JPanel();
		panel_Score_1.setBounds(20, 42, 120, 148);
		panel_Score_1.setEnabled(false);
		panel_2.add(panel_Score_1);
		this.scores.add(panel_Score_1);
		panel_Score_1.setLayout(null);
		
		JLabel lbjJoueur1 = new JLabel("Corentin");
		lbjJoueur1.setBounds(39, 5, 41, 14);
		lbjJoueur1.setEnabled(false);
		panel_Score_1.add(lbjJoueur1);
		
		JButton btnScore1 = new JButton("");
		btnScore1.setBounds(23, 49, 74, 99);
		btnScore1.setEnabled(false);
		panel_Score_1.add(btnScore1);
		
		JLabel labelScore1 = new JLabel("8");
		labelScore1.setBounds(52, 30, 15, 14);
		labelScore1.setEnabled(false);
		panel_Score_1.add(labelScore1);
		
		JPanel panel_Score_2 = new JPanel();
		panel_Score_2.setLayout(null);
		panel_Score_2.setBounds(330, 42, 120, 148);
		panel_Score_2.setEnabled(false);
		panel_2.add(panel_Score_2);
		this.scores.add(panel_Score_2);
		
		JLabel lbjJoueur2 = new JLabel("Corentin");
		lbjJoueur2.setBounds(39, 5, 41, 14);
		lbjJoueur2.setEnabled(false);
		panel_Score_2.add(lbjJoueur2);
		
		JButton btnScore2 = new JButton("");
		btnScore2.setBounds(23, 49, 74, 99);
		btnScore2.setEnabled(false);
		panel_Score_2.add(btnScore2);
		
		JLabel labelScore2 = new JLabel("8");
		labelScore2.setBounds(52, 30, 15, 14);
		labelScore2.setEnabled(false);
		panel_Score_2.add(labelScore2);
		
		JPanel panel_Score_3 = new JPanel();
		panel_Score_3.setLayout(null);
		panel_Score_3.setBounds(635, 42, 120, 148);
		panel_Score_3.setEnabled(false);
		panel_2.add(panel_Score_3);
		this.scores.add(panel_Score_3);
		
		JLabel lbjJoueur3 = new JLabel("Corentin");
		lbjJoueur3.setBounds(39, 5, 41, 14);
		lbjJoueur3.setEnabled(false);
		lbjJoueur3.setVisible(false);
		panel_Score_3.add(lbjJoueur3);
		
		JButton btnScore3 = new JButton("");
		btnScore3.setBounds(23, 49, 74, 99);
		btnScore3.setEnabled(false);
		btnScore3.setVisible(false);
		panel_Score_3.add(btnScore3);
		
		JLabel labelScore3 = new JLabel("8");
		labelScore3.setBounds(52, 30, 15, 14);
		labelScore3.setEnabled(false);
		labelScore3.setVisible(false);
		panel_Score_3.add(labelScore3);
		
		//Désactivation des boutons de la grille
		this.setGrilleEnabled(false);
		
	}

	@Override
	public void update(Observable o, Object arg1) {
		if(o instanceof Partie) {
			switch (((Partie) o).getEtat()) {
			case "initialisation": {
				frame.getContentPane().remove(configurationPanel);
				frame.getContentPane().add(panel);
				frame.getContentPane().add(panel_1);
				buttonPoser.setEnabled(true);
				this.setGrilleEnabled(true);
				this.labelJoueur.setText(((Partie) o).getJoueurs().get(0).getNom());
				//Affichage carte victoire
				ImageIcon img = this.resizeImage(((Partie) o).getJoueurs().get(0).getCarteVictoire().getImageName(), 100, 100);
				buttonCarteVictoire.setIcon(img);
				ImageIcon imgPosee = this.resizeImage(((Partie) o).getCartePiochee().getImageName(), 100, 100);
				buttonCartePiochee.setIcon(imgPosee);
				this.labelDeck.setText("Deck: " + (((Partie)o).getDeck().size()));
				this.labelTour.setText("Tour: " + (((Partie)o).getRound() + 1));
				//C:\\Users\\Coco\\eclipse-workspace\\projetLO02\\src\\fr\\corentinPierre\\views\\CBR.jpg
				break;
			} 
			case "attentePoser": {
				buttonDeplacer.setEnabled(false);
				buttonFinTour.setEnabled(false);
				buttonPoser.setEnabled(false);
				labelInfos.setText("Poser la carte");
				if(this.isJoueurVirtuel()) {
					JoueurVirtuel jv = this.getJoueurVirtuel();
					int[] coords = jv.choisirEmplacement();
					this.findButton(coords[0], coords[1]).doClick();
				}
				break;
			}
			case "attenteDeplacer": {
				buttonDeplacer.setEnabled(false);
				buttonFinTour.setEnabled(false);
				buttonPoser.setEnabled(false);
				labelInfos.setText("Déplacer une carte");
				break;
			}
			case "finTour": {
				labelInfos.setText("");
				buttonPoser.setEnabled(true);
				if(partie.getRound() > 1) {
					buttonDeplacer.setEnabled(true);
				}
				buttonFinTour.setEnabled(false);
				int idJoueur = ((Partie) o).getRound()%((Partie)o).getJoueurs().size();
				this.labelTour.setText("Tour: " + (((Partie) o).getRound() + 1));
				this.labelJoueur.setText(((Partie) o).getJoueurs().get(idJoueur).getNom());
				//Affichage carte victoire
				ImageIcon img = this.resizeImage(((Partie) o).getJoueurs().get(idJoueur).getCarteVictoire().getImageName(), 100, 100);
				buttonCarteVictoire.setIcon(img);
				ImageIcon imgPosee = this.resizeImage(((Partie) o).getCartePiochee().getImageName(), 100, 100);
				buttonCartePiochee.setIcon(imgPosee);
				this.labelDeck.setText("Deck: " + (((Partie)o).getDeck().size()));
				if(this.isJoueurVirtuel()) {
					buttonPoser.doClick();
				}
				//System.out.println(((Partie) o).getDeck().size());
				break;
				
			}
			case "erreurPoser": {
				labelInfos.setText("Re-posez votre carte à un autre endroit");
				if(this.isJoueurVirtuel()) {
					JoueurVirtuel jv = this.getJoueurVirtuel();
					int[] coords = jv.choisirEmplacement();
					System.out.println("Coordonnées random: " + coords[0] + ", " + coords[1]);
					this.findButton(coords[0], coords[1]).doClick();
				}
				break;
			}
			
			case "erreurChoixDeplacer": {
				labelInfos.setText("Re-choisir votre carte à déplacer");
			}
			
			case "impossiblePoser": {
				labelInfos.setText("Action impossible");
				break;
			}
			case "finPartie": {
				labelInfos.setText("Fin de la partie");
				buttonPoser.setEnabled(false);
				buttonFinTour.setEnabled(false);
				buttonDeplacer.setEnabled(false);
				frame.getContentPane().remove(panel_1);
				frame.getContentPane().add(panel_2);
				//Affichage du vainqueur
				lblVainqueur.setText("<html><div style='text-align: center;'>Vainqueur: "+ ((Partie)o).getVainqueur().getNom() + "</div></html>");
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
			default:
				//throw new IllegalArgumentException("Unexpected value: " + ((Partie) o).getEtat());
			}
			//System.out.println(((Partie) o).getEtat());
			if(((Partie) o).getEtat().indexOf("poser") != -1) {
				//Alors on récupère les coordonnées
				int x, y;
				x = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 2));
				y = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 1));
				ImageIcon imgPosee = this.resizeImage(((Partie) o).getPlateau().getCartesPosees().get(y).get(x).getImageName(), 100, 100);
				buttonCartePiochee.setIcon(null);
				this.findButton(x, y).setIcon(imgPosee);
				labelInfos.setText("Carte posée");
				buttonFinTour.setEnabled(true);
				if(this.isJoueurVirtuel()) {
					buttonFinTour.doClick();
				}
				if(partie.getRound() != 0 && !partie.getAlreadyDeplacee()) {
					buttonDeplacer.setEnabled(true);
				} 
			} else if (((Partie) o).getEtat().indexOf("deplacerCarte") != -1) {
				int x, y;
				x = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 2));
				y = Character.getNumericValue(((Partie) o).getEtat().charAt(((Partie) o).getEtat().length() - 1));
				Carte c = partie.getPlateau().getCartesPosees().get(y).get(x);
				ImageIcon imgPosee = this.resizeImage(c.getImageName(), 100, 100);
				this.findButton(x, y).setIcon(imgPosee);
				labelInfos.setText("Carte déplacée");
				if(partie.getCartePiochee() == null) {
					buttonFinTour.setEnabled(true);
				} else {
					buttonPoser.setEnabled(true);
				}
				buttonDeplacer.setEnabled(false);
			} 
			
		}
	}
	private ImageIcon resizeImage(String name, int w, int h) {

		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("C:\\\\Users\\\\Coco\\\\eclipse-workspace\\\\projetLO02\\\\src\\\\fr\\\\corentinPierre\\\\views\\\\" + name + ".jpg"));
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
		return partie.getRound() != 0 && partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()) instanceof JoueurVirtuel;
	}
	private JoueurVirtuel getJoueurVirtuel() {
		return (JoueurVirtuel) partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size());
	}
}
