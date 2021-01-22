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
 * Repr�sente un <pre>Panel</pre> d'une partie "Normale" ou "Refill" de Shape Up.
 * <br>H�rite de JPanel.
 * <br>Observateur d'une Partie.
 * @author Corentin
 * @author Pierre
 *
 */
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
	 * Cr�e le panel.
	 * <br>Cr�e �galement le contr�leur de partie et appel de ses m�thodes pour �couter les �v�nements li�s.
	 * <br>Appel la m�thode initialize
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
	
	/**
	 * Cr�e chacun des composants graphiques du Panel
	 */
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
		
		JLabel labelCartePioch�e = new JLabel("Carte Pioch\u00E9e");
		labelCartePioch�e.setHorizontalAlignment(SwingConstants.CENTER);
		labelCartePioch�e.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelCartePioch�e.setBounds(0, 11, 114, 23);
		add(labelCartePioch�e);
		
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
	/**
	 * Effectue des actions sur les composants graphiques � chaque modification de l'�tat de l'objet observ�.
	 * <br>La m�thode est appel�e � chaque modification apport�e sur l'objet Partie. 
	 * <br>Des modifications graphiques sont effectu�es en fonction de l'�tat de la partie apr�s notification des observateurs. 
	 * @param o Objet observ� qui subit des modifications
	 * @see fr.corentinPierre.models.Partie
	 * 
	 */
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
						buttonCartePiochee.setIcon(null);
						labelInfos.setText("Carte pos�e");
						buttonFinTour.setEnabled(true);
						if(this.isJoueurVirtuel()) {
							buttonFinTour.doClick();
						}
						if(partie.getRound() != 0 && !partie.getAlreadyDeplacee() && !partie.getPlateau().isFull()) {
							buttonDeplacer.setEnabled(true);
						} 
					} else if (((Partie) o).getEtat().indexOf("deplacerCarte") != -1) {
						labelInfos.setText("Carte d�plac�e");
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
		this.buttonPoser.setEnabled(true);
		this.buttonPoser.doClick();
	}
	

}
