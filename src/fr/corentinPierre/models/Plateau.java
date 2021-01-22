package fr.corentinPierre.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Représente le plateau du Shape Up
 * @author Corentin
 * @author Pierre
 **/
public class Plateau implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<Integer, Map<Integer, Carte>> cartesPosees;
	/**
	 * Instancie les cartesPosees.   
	 */
	
	public Plateau() {
		this.cartesPosees = new TreeMap<Integer, Map<Integer, Carte>>();
	}
	public Map<Integer, Map<Integer, Carte>> getCartesPosees(){
		return this.cartesPosees;
	}
	/**
	 * Calcule le nombre de cartes posées sur le plateau
	 * @return int Nombre de cartes actuellement posées
	 */
	
	public int getNbCartesPosees() {
		int count = 0;
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getCartesPosees().entrySet() ) {
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Indique si le plateau est complet ou non
	 * @return boolean Vrai si le plateau est complet, faux sinon
	 * @see fr.corentinPierre.models.Plateau#getNbCartesPosees()
	 */
	public boolean isFull() {
		return this.getNbCartesPosees() == 15;
	}
	
	/**
	 * Vérifie si la règle d'adjacence pour un emplacement donné est respectée (voir règles du jeu)
	 * @param x Coordonnée x de l'emplacement
	 * @param y Coordonnée y de l'emplacement
	 * @return boolean Vrai si l'emplacement respecte la règle d'adjacence, faux sinon
	 */
	public boolean checkAdjacence(int x, int y) {
		if(this.cartesPosees.containsKey(y+1)) {
			if(this.cartesPosees.get(y+1).containsKey(x)) {
				return true;
			}
		}
		if(this.cartesPosees.containsKey(y-1)) {
			if(this.cartesPosees.get(y-1).containsKey(x)) {
				return true;
			}
		}

		if(this.cartesPosees.containsKey(y)) {
			if(this.cartesPosees.get(y).containsKey(x+1)) {
				return true;
			} 
			if(this.cartesPosees.get(y).containsKey(x-1)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Vérifie si la règle de base est respectée pour un emplacement donné ( Carte posée dans les limites du plateau ) 
	 *@param x Coordonnée x de l'emplacement
	 *@param y Coordonnée y de l'emplacement
	 *@return boolean Vrai si la règle est respectée, faux sinon
	 */
	public boolean checkBaseRule(int x, int y) {
		int nbYMax = this.cartesPosees.size();
		ArrayList<Integer> xMaxRange = new ArrayList<>();
		int nbXRange = 0;
		int xMin = 999;
		int xMax = 0;
		int nbXMax = 0;
		int indiceNbXMax = 0;
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getCartesPosees().entrySet() ) {
			int test = this.getCartesPosees().get(entry.getKey()).size();
			if(test > nbXMax) {
				nbXMax = test;
				indiceNbXMax = entry.getKey();
			}
		}
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getCartesPosees().entrySet() ) {
			xMax = 0;
			xMin = 999;
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				if(xMin > entry2.getKey()) {
					xMin = entry2.getKey();
				}
				if(xMax < entry2.getKey()) {
					xMax = entry2.getKey();
				}
			}
			nbXRange = (xMax - xMin) + 1;
			xMaxRange.add(nbXRange);
		}
		if(nbYMax == 5 && this.cartesPosees.containsKey(y)) {
				ArrayList<Integer> coords = new ArrayList<>();
				for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getCartesPosees().entrySet() ) {
					coords.add(entry.getKey());
				}
				if((xMaxRange.get(y) < 3 && nbYMax <= 5)){
					if(coords.contains(x)) {
						return true;
					} else {
						return false;
					}
				} else if (xMaxRange.get(y) == 3) {
					if(this.cartesPosees.get(y).containsKey(x-1) && this.cartesPosees.get(y).containsKey(x+1)) {
						return true;
					} else {
						return false;
					}
				}
				return false;
			
		}
		
		else if (nbXMax == 5) {
			ArrayList<Integer> coords = new ArrayList<>();
			for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getCartesPosees().entrySet() ) {
				for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
					if(entry.getKey() == indiceNbXMax) {
						coords.add(entry2.getKey());
					}
				}
			}
			if(this.cartesPosees.containsKey(y)) {
				if((xMaxRange.get(y) < 5 && nbYMax <= 3)){
					if(coords.contains(x)) {
						return true;
					} else {
						return false;
					}
				} else if (xMaxRange.get(y) == 5) {
					if(this.cartesPosees.get(y).containsKey(x-1) && this.cartesPosees.get(y).containsKey(x+1)) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				if((Collections.max(xMaxRange) <= 5 && nbYMax < 3)) {
					this.cartesPosees.put(y, new TreeMap<Integer, Carte>());
					return true;
				} else {
					return false;
				}
			}
			
		}
		
		else if(this.cartesPosees.containsKey(y)) {
			if(xMaxRange.size() <= y){
				return true;
			}
			if((xMaxRange.get(y) < 5 && nbYMax <= 3) || (xMaxRange.get(y) < 3 && nbYMax <= 5)) {
				return true;
			} else if (xMaxRange.get(y) == 5) {
				if(this.cartesPosees.get(y).containsKey(x-1) && this.cartesPosees.get(y).containsKey(x+1)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
			
		}
		else {
			if((Collections.max(xMaxRange) <= 5 && nbYMax < 3) || (Collections.max(xMaxRange) <= 3 && nbYMax < 5)) {
				this.cartesPosees.put(y, new TreeMap<Integer, Carte>());
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	/**
	 * Pose une carte sur le plateau
	 *@param x Coordonnée x de la carte
	 *@param y Coordonnée y de la carte
	 *@param carte Carte à poser sur le plateau
	 */
	public void setCartesPosees(int x, int y, Carte carte) {
		if(this.cartesPosees.containsKey(y)) {
			this.cartesPosees.get(y).put(x, carte);
		} else {
			this.cartesPosees.put(y, new TreeMap<Integer, Carte>());
			this.cartesPosees.get(y).put(x, carte);
		}

	}
	

}
