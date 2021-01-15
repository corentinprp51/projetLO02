package fr.corentinPierre.views;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.corentinPierre.controllers.ControleurPartie;
import fr.corentinPierre.models.JoueurVirtuel;
import fr.corentinPierre.models.Partie;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Variante1 extends JPanel implements Observer{

	private Partie partie;
	private JLabel labelJoueur;
	private JLabel labelDeck;
	private JLabel labelTour;
	private JLabel labelInfos;
	private JButton buttonPoser;
	private JButton buttonDeplacer;
	private JButton buttonFinTour;
	private JButton buttonCarteVictoire;
	private JButton buttonCartePiochee;
	/**
	 * Create the panel.
	 */
	public Variante1(Partie p) {
		this.partie = p;
		this.setSize(796, 221);
		this.setLayout(null);
		initialize();
		ControleurPartie cp = new ControleurPartie(partie);
		cp.askPoser(buttonPoser);
		cp.askDeplacer(buttonDeplacer);
		cp.finTour(buttonFinTour);
		partie.addObserver(this);
		partie.setEtat(partie.getEtat());
		
	}
	
	public void initialize() {
		buttonPoser = new JButton("Poser Carte");
		buttonPoser.setBounds(199, 144, 89, 23);
		buttonPoser.setEnabled(false);
		add(buttonPoser);
		
		buttonDeplacer = new JButton("D\u00E9placer Carte");
		buttonDeplacer.setBounds(300, 144, 105, 23);
		buttonDeplacer.setEnabled(false);
		add(buttonDeplacer);
		
		buttonFinTour = new JButton("Fin Tour");
		buttonFinTour.setBounds(419, 144, 89, 23);
		buttonFinTour.setEnabled(false);
		add(buttonFinTour);
		
		buttonCartePiochee = new JButton("");
		buttonCartePiochee.setBounds(0, 38, 114, 129);
		add(buttonCartePiochee);
		
		buttonCarteVictoire = new JButton("");
		buttonCarteVictoire.setBounds(665, 38, 105, 129);
		add(buttonCarteVictoire);
		
		JLabel labelCartePiochée = new JLabel("Carte Pioch\u00E9e");
		labelCartePiochée.setHorizontalAlignment(SwingConstants.CENTER);
		labelCartePiochée.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelCartePiochée.setBounds(0, 11, 114, 23);
		add(labelCartePiochée);
		
		JLabel labelCarteVictoire = new JLabel("Carte Victoire");
		labelCarteVictoire.setHorizontalAlignment(SwingConstants.CENTER);
		labelCarteVictoire.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelCarteVictoire.setBounds(665, 11, 105, 23);
		add(labelCarteVictoire);
		
		labelJoueur = new JLabel("");
		labelJoueur.setHorizontalAlignment(SwingConstants.CENTER);
		labelJoueur.setFont(new Font("Tahoma", Font.PLAIN, 25));
		labelJoueur.setBounds(124, 11, 531, 42);
		add(labelJoueur);
		
		labelDeck = new JLabel("Deck: /");
		labelDeck.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelDeck.setBounds(362, 48, 56, 23);
		add(labelDeck);
		
		labelInfos = new JLabel("Messages d'information ici");
		labelInfos.setHorizontalAlignment(SwingConstants.CENTER);
		labelInfos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelInfos.setBounds(124, 95, 531, 38);
		add(labelInfos);
		
		
		labelTour = new JLabel("Tour: /");
		labelTour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelTour.setBounds(362, 76, 56, 23);
		add(labelTour);
	}
	@Override
	public void update(Observable o, Object arg1) {
		// TODO Auto-generated method stub
		if (o instanceof Partie) {
			if(((Partie) o).getVariante() != null) {
				if(((Partie) o).getVariante().getNom().equalsIgnoreCase("Normal") || ((Partie) o).getVariante().getNom().equalsIgnoreCase("Refill")) {
					switch (((Partie) o).getEtat()) {
					
					case "initialisation": {
						buttonPoser.setEnabled(true);
						this.labelJoueur.setText(((Partie) o).getJoueurs().get(0).getNom());
						this.labelDeck.setText("Deck: " + (((Partie)o).getDeck().size()));
						this.labelTour.setText("Tour: " + (((Partie)o).getRound() + 1));
						buttonDeplacer.setEnabled(false);
						buttonFinTour.setEnabled(false);
						ImageIcon img = this.resizeImage(((Partie) o).getJoueurs().get(0).getCarteVictoire().getImageName(), 100, 100);
						buttonCarteVictoire.setIcon(img);
						ImageIcon imgPosee = this.resizeImage(((Partie) o).getCartePiochee().getImageName(), 100, 100);
						buttonCartePiochee.setIcon(imgPosee);
						break;
					} 
					case "attentePoser": {
						int idJoueur = ((Partie) o).getRound()%((Partie)o).getJoueurs().size();
						buttonDeplacer.setEnabled(false);
						buttonFinTour.setEnabled(false);
						buttonPoser.setEnabled(false);
						labelInfos.setText("Poser la carte");
						
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
						this.labelDeck.setText("Deck: " + (((Partie)o).getDeck().size()));
						ImageIcon img = this.resizeImage(((Partie) o).getJoueurs().get(idJoueur).getCarteVictoire().getImageName(), 100, 100);
						buttonCarteVictoire.setIcon(img);
						ImageIcon imgPosee = this.resizeImage(((Partie) o).getCartePiochee().getImageName(), 100, 100);
						buttonCartePiochee.setIcon(imgPosee);
						if(this.isJoueurVirtuel()) {
							buttonPoser.doClick();
						}
						break;
						
					}
					case "erreurPoser": {
						labelInfos.setText("Re-posez votre carte à un autre endroit");
						//if(this.isJoueurVirtuel()) {
							//JoueurVirtuel jv = this.getJoueurVirtuel();
							//int[] coords = jv.choisirEmplacement();
							//System.out.println("Coordonnées random: " + coords[0] + ", " + coords[1]);
							//this.findButton(coords[0], coords[1]).doClick();
						//}
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
						
						break;
					}
					default:
						//throw new IllegalArgumentException("Unexpected value: " + ((Partie) o).getEtat());
					}
					//System.out.println(((Partie) o).getEtat());
					if(((Partie) o).getEtat().indexOf("poser") != -1) {
						buttonCartePiochee.setIcon(null);
						labelInfos.setText("Carte posée");
						buttonFinTour.setEnabled(true);
						if(this.isJoueurVirtuel()) {
							buttonFinTour.doClick();
						}
						if(partie.getRound() != 0 && !partie.getAlreadyDeplacee() && !partie.getPlateau().isFull()) {
							buttonDeplacer.setEnabled(true);
						} 
					} else if (((Partie) o).getEtat().indexOf("deplacerCarte") != -1) {
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
		}
	}
	
	private ImageIcon resizeImage(String name, int w, int h) {

		BufferedImage img = null;
		try {
			img = ImageIO.read(this.getClass().getResource(name + ".jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(w, h,Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}
	
	
	private boolean isJoueurVirtuel() {
		return partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()) instanceof JoueurVirtuel;
	}
	private JoueurVirtuel getJoueurVirtuel() {
		return (JoueurVirtuel) partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size());
	}
	
	public void clickPoser() {
		this.buttonPoser.doClick();
	}
	

}
