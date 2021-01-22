package fr.corentinPierre.views;

import javax.swing.JButton;

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
