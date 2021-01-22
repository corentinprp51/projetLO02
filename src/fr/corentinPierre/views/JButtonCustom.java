package fr.corentinPierre.views;


import javax.swing.JButton;

/**
 * Bouton qui poss�de des coordonn�es x,y correspondant au plateau.
 * <br>H�rite de JButton
 * @author Corentin
 * @author Pierre
 *
 */

public class JButtonCustom extends JButton {

	private static final long serialVersionUID = 1L;
	private int xGrille;
	private int yGrille;
	public JButtonCustom (String title, int xGrille, int yGrille) {
		super(title);
		this.xGrille = xGrille;
		this.yGrille = yGrille;
	}
	
	public int getXGrille() {
		return this.xGrille;
	}
	
	public int getYGrille() {
		return this.yGrille;
	}


}
