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
/**
 * Repr�sente un <pre>Panel</pre> d'une partie "Avanc�e" de Shape Up
 * <br>H�rite de JPanel
 * <br>Observateur d'une Partie
 * @author Corentin
 * @author Pierre
 *
 */
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
	 * Cr�e le panel.
	 * <br>Cr�e �galement le contr�leur de partie et appel de ses m�thodes pour �couter les �v�nements li�s.
	 * <br>Cr�e chaque composant graphique du Panel.
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
	/**
	 * Effectue des actions sur les composants graphiques � chaque modification de l'�tat de l'objet observ�.
	 * <br>La m�thode est appel�e � chaque modification apport�e sur l'objet Partie. 
	 * <br>Des modifications graphiques sont effectu�es en fonction de l'�tat de la partie apr�s notification des observateurs. 
	 * @param o Objet observ� qui subit des modifications
	 * @see fr.corentinPierre.models.Partie
	 * 
	 */
	public void update(Observable o, Object arg1) {
		if (o instanceof Partie) {
			if(((Partie) o).getVariante() != null) {
				if(((Partie) o).getVariante().getNom().equalsIgnoreCase("Avance")) {
					switch (((Partie) o).getEtat()) {
					
					case "initialisation": {
						buttonPoser.setEnabled(true);
						this.setMainEnabled(false);
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
						buttonFinTour.setEnabled(false);
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
						this.setMainEnabled(false);
						buttonDeplacer.setEnabled(false);
						buttonFinTour.setEnabled(false);
						buttonPoser.setEnabled(false);
						labelInfos.setText("D�placer une carte");
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
							//Affichage Main du Joueur actualis�e
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
						this.setMainEnabled(false);
						break;
						
					}
					case "erreurPoser": {
						labelInfos.setText("Re-posez votre carte � un autre endroit");
						break;
					}
					
					case "erreurChoixDeplacer": {
						labelInfos.setText("Re-choisir votre carte � d�placer");
						break;
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
					}
					if(((Partie) o).getEtat().indexOf("poser") != -1) {
						labelInfos.setText("Carte pos�e");
						this.setMainEnabled(false);
						buttonFinTour.setEnabled(true);
						if(this.isJoueurVirtuel()) {
							buttonFinTour.doClick();
						}
						if(partie.getRound() != 0 && !partie.getAlreadyDeplacee() && !partie.getPlateau().isFull()) {
							buttonDeplacer.setEnabled(true);
						} 
					} else if (((Partie) o).getEtat().indexOf("deplacerCarte") != -1) {
						labelInfos.setText("Carte d�plac�e");
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
	
	/**
	 * Cr�e une ImageIcon redimensionn�e en fonction des tailles pass�es en param�tre.
	 * @param name Nom de l'Image
	 * @param w Longueur de l'image
	 * @param h Hauteur de l'image
	 * @return ImageIcon Image redimensionn�e
	 */
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
	
	/**
	 * Active ou d�sactive les boutons de la main du joueur
	 * @param b Si b vaut vrai, les boutons s'activent, ils se d�sactivent sinon. 
	 */
	private void setMainEnabled(boolean b) {
		this.mainJ.forEach(btn -> {
			btn.setEnabled(b);
		});
	}
	
	/**
	 * Retourne si le joueur courant est un joueur virtuel.
	 * @return boolean Vrai si le joueur est virtuel, faux sinon. 
	 */
	private boolean isJoueurVirtuel() {
		return partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size()) instanceof JoueurVirtuel;
	}
	private JoueurVirtuel getJoueurVirtuel() {
		return (JoueurVirtuel) partie.getJoueurs().get(partie.getRound() % partie.getJoueurs().size());
	}
	
	/**
	 * Effectue un clic sur le bouton poser.
	 */
	public void clickPoser() {
		this.buttonPoser.doClick();
	}
	
	/**
	 * Effectue un clic sur un des boutons de la main du joueur. 
	 * @param id Id de du bouton sur lequel cliquer. 
	 */
	public void clickMain(int id) {
		this.mainJ.get(id).doClick();
	}
	
	/**
	 * D�sactive tous les composants graphiques sauf la main du joueur. 
	 */
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
