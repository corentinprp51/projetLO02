package fr.corentinPierre.views;


import javax.swing.JButton;

public class JButtonCustom extends JButton {

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
