package fr.corentinPierre.models;

import java.util.Scanner;

public class JoueurHumain extends Joueur {

	public JoueurHumain(int id, String nom) {
		super(id, nom);
		// TODO Auto-generated constructor stub
	}

	
	public String choisirAction() {
		try {
			System.out.println("Choisir type d'action (P: poser une carte / D: déplacer une carte) ");
			Scanner choiceInput = new Scanner(System.in);
			String choice = choiceInput.nextLine();
			if(choice.equalsIgnoreCase("P") || choice.equalsIgnoreCase("D")) {
				return choice;
			}
			return this.choisirAction();
		} catch (Exception e) {
			return this.choisirAction();
			
		}
	}

	@Override
	public int[] choisirEmplacement() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Saisissez coordonnée x: ");
			Scanner xInput = new Scanner(System.in);
			int x = xInput.nextInt();
			System.out.println("Saisissez coordonnée y: ");
			Scanner yInput = new Scanner(System.in);
			int y = yInput.nextInt();
			int[] tab = new int[2];
			tab[0] = x;
			tab[1] = y;
			return tab;
		} catch (Exception e) {
			return this.choisirEmplacement();
			
		}
		
		
	}
	
	public boolean demandePoserCarte() {
		try {
			System.out.println("Voulez-vous déplacer une carte ? (O: oui / N: non) ");
			Scanner choiceInput = new Scanner(System.in);
			String choice = choiceInput.nextLine();
			if(choice.equalsIgnoreCase("O") || choice.equalsIgnoreCase("N")) {
				if(choice.equalsIgnoreCase("O")){
					return true;
				} else {
					return false;
				}
			}
			this.demandePoserCarte();
			return false;
		} catch (Exception e) {
			this.demandePoserCarte();
			return false;
		}
	}
	
	public static void main (String[] args ) {
		Joueur j = new JoueurHumain(0, "Corentin");
		j.choisirEmplacement();
	}

}
