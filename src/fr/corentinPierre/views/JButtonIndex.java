package fr.corentinPierre.views;

import javax.swing.JButton;
/**
 * Bouton qui poss�de un index correspondant � la position des cartes dans la main du joueur.
 * <br>H�rite de JButton
 * @author Corentin
 * @author Pierre
 *
 */

public class JButtonIndex extends JButton {
	private static final long serialVersionUID = 1L;
	private int index;
	public JButtonIndex (String title, int i) {
		super(title);
		this.index = i;
	}
	public int getIndex() {
		return this.index;
	}
}
