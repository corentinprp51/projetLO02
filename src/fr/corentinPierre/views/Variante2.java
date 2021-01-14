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

public class Variante2 extends JPanel implements Observer{

	private Partie partie;
	private ArrayList<JButtonIndex> mainJ;
	private JLabel labelJoueur;
	private JLabel labelDeck;
	private JLabel labelTour;
	private JLabel labelInfos;
	private JButton buttonPoser;
	private JButton buttonDeplacer;
	private JButton buttonFinTour;
	/**
	 * Create the panel.
	 */
	public Variante2(Partie p) {
		this.partie = p;
		this.setSize(796, 221);
		this.setLayout(null);
		buttonPoser = new JButton("Poser");
		buttonPoser.setBounds(451, 143, 89, 23);
		add(buttonPoser);
		
		buttonDeplacer = new JButton("D\u00E9placer");
		buttonDeplacer.setBounds(550, 143, 89, 23);
		add(buttonDeplacer);
		ControleurPartie cp = new ControleurPartie(partie);
		cp.askPoser(buttonPoser);
		cp.askDeplacer(buttonDeplacer);
		
		JButtonIndex main1 = new JButtonIndex("", 0);
		main1.setBounds(86, 112, 114, 129);
		add(main1);
		
		JButtonIndex main2 = new JButtonIndex("", 1);
		main2.setBounds(197, 112, 114, 129);
		add(main2);
		
		JButtonIndex main3 = new JButtonIndex("", 2);
		main3.setBounds(307, 112, 114, 129);
		add(main3);
		
		this.mainJ = new ArrayList<JButtonIndex>();
		this.mainJ.add(main1);
		this.mainJ.add(main2);
		this.mainJ.add(main3);
		cp.carteAPoser(mainJ);
		
		labelJoueur = new JLabel("a");
		labelJoueur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelJoueur.setHorizontalAlignment(SwingConstants.CENTER);
		labelJoueur.setBounds(10, 11, 776, 23);
		add(labelJoueur);
		
		labelDeck = new JLabel("Deck: 0");
		labelDeck.setHorizontalAlignment(SwingConstants.CENTER);
		labelDeck.setBounds(367, 36, 61, 14);
		add(labelDeck);
		
		labelTour = new JLabel("Tour: 1");
		labelTour.setHorizontalAlignment(SwingConstants.CENTER);
		labelTour.setBounds(375, 54, 46, 14);
		add(labelTour);
		
		labelInfos = new JLabel("Messages d'informations ici");
		labelInfos.setHorizontalAlignment(SwingConstants.CENTER);
		labelInfos.setBounds(230, 87, 335, 14);
		add(labelInfos);
		
		
		buttonFinTour = new JButton("Fin Tour");
		buttonFinTour.setBounds(504, 177, 89, 23);
		add(buttonFinTour);
		cp.finTour(buttonFinTour);
		this.setMainEnabled(false);
		partie.addObserver(this);
		partie.setEtat(partie.getEtat());
	}
	@Override
	public void update(Observable o, Object arg1) {
		// TODO Auto-generated method stub
		if (o instanceof Partie) {
			if(((Partie) o).getVariante() != null) {
				if(((Partie) o).getVariante().getNom().equalsIgnoreCase("Avance")) {
					switch (((Partie) o).getEtat()) {
					
					case "initialisation": {
						buttonPoser.setEnabled(true);
						this.labelJoueur.setText(((Partie) o).getJoueurs().get(0).getNom());
						//Affichage main joueur
						for(int i = 0; i < 3; i++) {
							ImageIcon img = this.resizeImage(((Partie) o).getJoueurs().get(0).getMain().get(i).getImageName(), 100, 100);
							this.mainJ.get(i).setIcon(img);
						}
						//
						this.labelDeck.setText("Deck: " + (((Partie)o).getDeck().size()));
						this.labelTour.setText("Tour: " + (((Partie)o).getRound() + 1));
						buttonDeplacer.setEnabled(false);
						//buttonFinDuTour.setEnabled(true);
						buttonFinTour.setEnabled(false);
						//if(this.isJoueurVirtuel()) {
							//JoueurVirtuel jv = this.getJoueurVirtuel();
							//int[] coords = jv.choisirEmplacement();
							//System.out.println("Coordonnées random: " + coords[0] + ", " + coords[1]);
							//buttonPoser.doClick();
							//this.findButton(coords[0], coords[1]).doClick();
						//}
						break;
					} 
					case "attentePoser": {
						int idJoueur = ((Partie) o).getRound()%((Partie)o).getJoueurs().size();
						for(int i = 0; i < ((Partie)o).getJoueurs().get(idJoueur).getMain().size(); i++) {
							ImageIcon img = this.resizeImage(((Partie) o).getJoueurs().get(idJoueur).getMain().get(i).getImageName(), 100, 100);
							this.mainJ.get(i).setEnabled(true);
						}
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
						this.setMainEnabled(false);
						labelInfos.setText("");
						buttonPoser.setEnabled(true);
						if(partie.getRound() > 1) {
							buttonDeplacer.setEnabled(true);
						}
						buttonFinTour.setEnabled(false);
							int idJoueur = ((Partie) o).getRound()%((Partie)o).getJoueurs().size();
							this.labelTour.setText("Tour: " + (((Partie) o).getRound() + 1));
							this.labelJoueur.setText(((Partie) o).getJoueurs().get(idJoueur).getNom());
							//Affichage Main du Joueur actualisée
							for(int i = 0; i < 3; i++) {
								this.mainJ.get(i).setIcon(null);
							}
							for(int i = 0; i < ((Partie)o).getJoueurs().get(idJoueur).getMain().size(); i++) {
								ImageIcon img = this.resizeImage(((Partie) o).getJoueurs().get(idJoueur).getMain().get(i).getImageName(), 100, 100);
								this.mainJ.get(i).setIcon(img);
								this.mainJ.get(i).setEnabled(true);
							}
							this.labelDeck.setText("Deck: " + (((Partie)o).getDeck().size()));
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
						labelInfos.setText("Carte posée");
						this.setMainEnabled(false);
						buttonFinTour.setEnabled(true);
						if(this.isJoueurVirtuel()) {
							buttonFinTour.doClick();
						}
						if(partie.getRound() != 0 && !partie.getAlreadyDeplacee()) {
							buttonDeplacer.setEnabled(true);
						} 
					} else if (((Partie) o).getEtat().indexOf("deplacerCarte") != -1) {
						labelInfos.setText("Carte déplacée");
							if(partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()).getMain().size() < 3) {
								buttonFinTour.setEnabled(true);
							} else {
								buttonPoser.setEnabled(true);
								buttonFinTour.setEnabled(false);
							}
						buttonDeplacer.setEnabled(false);
					} else if (((Partie) o).getEtat().indexOf("carteAPoser") != -1) {
						int idCarte = Character.getNumericValue(((Partie)o).getEtat().charAt(partie.getEtat().length() - 1));
						this.mainJ.get(idCarte).setIcon(null);
						this.setMainEnabled(false);
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
	
	private void setMainEnabled(boolean b) {
		this.mainJ.forEach(btn -> {
			btn.setEnabled(b);
		});
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
	
	public void clickMain(int id) {
		this.mainJ.get(id).doClick();
	}
	
	public void attentePoser() {
		int idJoueur = partie.getRound()%partie.getJoueurs().size();
		for(int i = 0; i < partie.getJoueurs().get(idJoueur).getMain().size(); i++) {
			ImageIcon img = this.resizeImage(partie.getJoueurs().get(idJoueur).getMain().get(i).getImageName(), 100, 100);
			this.mainJ.get(i).setEnabled(true);
		}
		buttonDeplacer.setEnabled(false);
		buttonFinTour.setEnabled(false);
		buttonPoser.setEnabled(false);
		labelInfos.setText("Poser la carte");
	}
}
