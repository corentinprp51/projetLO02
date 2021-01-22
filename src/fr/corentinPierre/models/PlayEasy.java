package fr.corentinPierre.models;

import java.io.Serializable;
import java.util.Random;

public class PlayEasy implements ModeJeuStrategy, Serializable{

	private static final long serialVersionUID = 1L;

	public int[] placement() {
		Random rX = new Random();
		int low = 0;
		int high = 3;
		int resultX = rX.nextInt(high-low) + low;
		Random rY = new Random();
		int lowY = 0;
		int highY = 5;
		int resultY = rY.nextInt(highY-lowY) + lowY;
		int[] tab = new int[2];
		tab[0] = resultX;
		tab[1] = resultY;
		return tab;
	}

}
