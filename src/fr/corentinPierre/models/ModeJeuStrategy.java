package fr.corentinPierre.models;

public interface ModeJeuStrategy {
	
	abstract public int[] placement();
	abstract public String action();
	abstract public boolean demanderPoserCarte();

}

