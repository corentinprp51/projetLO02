package fr.corentinPierre.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import fr.corentinPierre.enums.FormePlateau;

public class Plateau implements Serializable {
	
	private FormePlateau formePlateau;
	private int x;
	private int y;
	private Map<Integer, Map<Integer, Carte>> cartesPosees;
	public void poserCarte(Carte carte, int x, int y) {
		
	}
	public FormePlateau getFormePlateau() {
		return this.formePlateau;
	}
	
	public Map<Integer, Map<Integer, Carte>> getCartesPosees(){
		return this.cartesPosees;
	}
	
	public boolean checkAdjacence(int x, int y) {
		if(this.cartesPosees.containsKey(y+1)) {
			if(this.cartesPosees.get(y+1).containsKey(x)) {
				//on peut set la carte
				return true;
			}
		}
		if(this.cartesPosees.containsKey(y-1)) {
			if(this.cartesPosees.get(y-1).containsKey(x)) {
				//on peut set la carte
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
	
	public boolean checkBaseRuleTest(int x, int y) {
		
		if(this.cartesPosees.containsKey(y)) {
			int iX = 1;
			int jX = 1;
			while(this.cartesPosees.get(y).containsKey(x-iX)) {
				iX++;
			}
			while(this.cartesPosees.get(y).containsKey(x+jX)) {
				jX++;
			}
			int nbTotalX = (iX-1) + (jX-1);
			int iY = 1;
			int i = 1;
			int j = 1;
			int jY = 1;
			while(this.cartesPosees.containsKey(y-i)) {
				if(this.cartesPosees.get(y-iY).containsKey(x)) {
					iY++;
				}
				i++;
			}
			while(this.cartesPosees.containsKey(y+j)) {
				if(this.cartesPosees.get(y+jY).containsKey(x)) {
					jY++;
				}
				j++;
			}
			int nbTotalY = (iY-1) + (jY-1);
			if((nbTotalX < 5 && nbTotalY < 3) || (nbTotalX < 3 && nbTotalY < 5)) 
			{
				return true;
			}
			return false;
		} else {
			this.cartesPosees.put(y, new TreeMap<Integer, Carte>());
			if(!this.cartesPosees.get(y).containsKey(x)) {
				int iY = 1;
				int jY = 1;
				int i = 1;
				int j = 1;
				while(this.cartesPosees.containsKey(y-i)) {
					if(this.cartesPosees.get(y-iY).containsKey(x)) {
						iY++;
					}	
					i++;
				}
				while(this.cartesPosees.containsKey(y+j)) {
					if(this.cartesPosees.get(y+jY).containsKey(x)) {
						jY++;
					}
					j++;
				}
				int nbTotalY = (iY-1) + (jY-1);
				if(nbTotalY < 5) {
					return true;
				} 
			} 
		}
		return false;
	}
	
public boolean checkBaseRule(int x, int y) {
		//Calculer rangées max de y & x
		ArrayList<Carte> yMaxRange = new ArrayList<>();
		int nbYMax = this.cartesPosees.size();
		ArrayList<Integer> xMaxRange = new ArrayList<>();
		int nbXRange = 0;
		int xMin = 999;
		int xMax = 0;
		int nbXMax = 0;
		int indiceNbXMax = 0;
		//Tableau pour obtenir le x maximum
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getCartesPosees().entrySet() ) {
			int test = this.getCartesPosees().get(entry.getKey()).size();
			if(test > nbXMax) {
				nbXMax = test;
				indiceNbXMax = entry.getKey();
			}
		}
		//Tableau des ranges de X
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
		//Lorsque Y a atteint 5 cartes de largeur
		if(nbYMax == 5 && this.cartesPosees.containsKey(y)) {
			System.out.println("5 Cartes en largeur");
				/*int indiceMaxRange = 0;
				for (int i = 0; i < xMaxRange.size(); i++) {
					if(xMaxRange.get(i) == 3) {
						indiceMaxRange = i;
						break;
					}
				}*/
				ArrayList<Integer> coords = new ArrayList<>();
				for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getCartesPosees().entrySet() ) {
					coords.add(entry.getKey());
				}
				//Vérifier que la nouvelle coordonnée soit inclu dans celle des coords
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
			System.out.println("5 cartes en longueur");
			ArrayList<Integer> coords = new ArrayList<>();
			for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getCartesPosees().entrySet() ) {
				for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
					if(entry.getKey() == indiceNbXMax) {
						coords.add(entry2.getKey());
					}
				}
			}
			//Vérifier que la nouvelle coordonnée soit inclu dans celle des coords
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
		/*for(Map.Entry<Integer, Map<Integer, Carte>> entry: this.getCartesPosees().entrySet() ) {
			if((this.cartesPosees.get(entry.getKey()).size() == 5 && nbYMax <= 3) || (this.cartesPosees.get(entry.getKey()).size() <= 3 && nbYMax <= 5)) {
				nbXMax = this.cartesPosees.get(entry.getKey()).size();
				break;
			}
		}*/
		
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
	
	public void setCartesPosees(int x, int y, Carte carte) {
		if(this.cartesPosees.containsKey(y)) {
			this.cartesPosees.get(y).put(x, carte);
		} else {
			this.cartesPosees.put(y, new TreeMap<Integer, Carte>());
			this.cartesPosees.get(y).put(x, carte);
		}

		}
			
		
	
	public Plateau() {
		this.x = 5;
		this.y = 3;
		this.formePlateau = FormePlateau.RECTANGLE;
		this.cartesPosees = new TreeMap<Integer, Map<Integer, Carte>>();
	}

}
