package fr.corentinPierre.models;

import java.util.Map;
import java.util.TreeMap;

/**
 * Représentation du Visitor dans le patron de conception Visitor pour calculer les scores.
 * @author Corentin
 * @author Pierre
 * @see fr.corentinPierre.models.Visitor
 **/

public class ScoreVisitor implements Visitor{
	/**
	 * Calcule le score d'un joueur 
	 * @param p Partie à visiter
	 * @param id Id du Joueur
	 * @return int Score du Joueur
	 * @see fr.corentinPierre.models.ScoreVisitor#calculerCouleur(Partie, Carte)
	 * @see fr.corentinPierre.models.ScoreVisitor#calculerForme(Partie, Carte)
	 * @see fr.corentinPierre.models.ScoreVisitor#calculerFillable(Partie, Carte)
	 */
	@Override
	public int visit(Partie p, int id) {
		int scoreForme = this.calculerForme(p, p.getJoueurs().get(id).getCarteVictoire());
		int scoreCouleur = this.calculerCouleur(p, p.getJoueurs().get(id).getCarteVictoire());
		int scoreFillable = this.calculerFillable(p, p.getJoueurs().get(id).getCarteVictoire());
		return scoreForme + scoreCouleur + scoreFillable;
	}
	
	/**
	 * Détermine le nombre de points obtenu par le joueur en fonction du remplissage de sa carte victoire
	 * @param p Partie à visiter
	 * @param victoire Carte victoire du joueur
	 * @return int Score obtenu en fonction du remplissage de la carte victoire
	 */
	
	public int calculerFillable(Partie p, Carte victoire) {
		int score = 0;
		Map<Integer, Map<Integer, Carte>> cartes = new TreeMap<Integer, Map<Integer, Carte>>(p.getPlateau().getCartesPosees());	
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			int nbCartes = 0;
			int [] combo = new int[2];
			combo[0] = 0;
			combo[1] = 0;
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				if(entry2.getValue().getFillable() == victoire.getFillable()) {
					nbCartes++;
					
				} else {
					if(combo[0] <= 1) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
					nbCartes = 0;
				}
			}
			if(nbCartes > 1) {
				if(combo[0] < 2) {
					combo[0] = nbCartes;
				} else {
					combo[1] = nbCartes;
				}
			}
			if(combo[0] > 2) {
				score += combo[0];
			}
			if(combo[1] > 2) {
				score += combo[1];
			}
		}
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				int i = 0;
				int [] combo = new int[2];
				combo[0] = 0;
				combo[1] = 0;
				int nbCartes = 0;
				while(cartes.containsKey(entry.getKey()+i)) {
					if(cartes.get(entry.getKey() + i).containsKey(entry2.getKey())) {
						if(cartes.get(entry.getKey() + i).get(entry2.getKey()).getFillable() == victoire.getFillable()) {
							nbCartes++;
							
						} else {
							if(combo[0] <= 1) {
								combo[0] = nbCartes;
							} else {
								combo[1] = nbCartes;
							}
							nbCartes = 0;
						}
					}
					i++;
				}
				if(nbCartes > 1) {
					if(combo[0] < 2) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
				}
				if(combo[0] > 2) {
					score += combo[0];
				}
				if(combo[1] > 2) {
					score += combo[1];
				}
			}
			break;
		}
		return score;
	}
	/**
	 * Détermine le nombre de points obtenu par le joueur en fonction de la couleur de sa carte victoire
	 * @param p Partie à visiter
	 * @param victoire Carte victoire du joueur
	 * @return int Score obtenu en fonction de la couleur de la carte victoire
	 */
	public int calculerCouleur(Partie p, Carte victoire) {
		int score = 0;
		Map<Integer, Map<Integer, Carte>> cartes = new TreeMap<Integer, Map<Integer, Carte>>(p.getPlateau().getCartesPosees());	
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			int nbCartes = 0;
			int [] combo = new int[2];
			combo[0] = 0;
			combo[1] = 0;
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				if(entry2.getValue().getCouleur() == victoire.getCouleur()) {
					nbCartes++;
					
				} else {
					if(combo[0] <= 1) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
					nbCartes = 0;
				}
			}
			if(nbCartes > 1) {
				if(combo[0] < 2) {
					combo[0] = nbCartes;
				} else {
					combo[1] = nbCartes;
				}
			}
			if(combo[0] > 2) {
				score += combo[0] + 1;
			}
			if(combo[1] > 2) {
				score += combo[1] + 1;
			}
		}
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				int i = 0;
				int [] combo = new int[2];
				combo[0] = 0;
				combo[1] = 0;
				int nbCartes = 0;
				while(cartes.containsKey(entry.getKey()+i)) {
					if(cartes.get(entry.getKey() + i).containsKey(entry2.getKey())) {
						if(cartes.get(entry.getKey() + i).get(entry2.getKey()).getCouleur() == victoire.getCouleur()) {
							nbCartes++;
							
						} else {
							if(combo[0] <= 1) {
								combo[0] = nbCartes;
							} else {
								combo[1] = nbCartes;
							}
							nbCartes = 0;
						}
					}
					i++;
				}
				if(nbCartes > 1) {
					if(combo[0] < 2) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
				}
				if(combo[0] > 2) {
					score += combo[0] + 1;
				}
				if(combo[1] > 2) {
					score += combo[1] + 1;
				}
			}
			break;
		}
		return score;
	}
	/**
	 * Détermine le nombre de points obtenu par le joueur en fonction de la forme de sa carte victoire
	 * @param p Partie à visiter
	 * @param victoire Carte victoire du joueur
	 * @return int Score obtenu en fonction de la forme de la carte victoire
	 */
	public int calculerForme(Partie p, Carte victoire) {
		int score = 0;
		Map<Integer, Map<Integer, Carte>> cartes = new TreeMap<Integer, Map<Integer, Carte>>(p.getPlateau().getCartesPosees());	
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: cartes.entrySet() ) {
			int nbCartes = 0;
			int [] combo = new int[2];
			combo[0] = 0;
			combo[1] = 0;
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				if(entry2.getValue().getForme() == victoire.getForme()) {
					nbCartes++;
					
				} else {
					if(combo[0] <= 1) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
					nbCartes = 0;
				}
			}
			if(nbCartes > 1) {
				if(combo[0] < 2) {
					combo[0] = nbCartes;
				} else {
					combo[1] = nbCartes;
				}
			}
			if(combo[0] > 1) {
				score += combo[0] - 1;
			}
			if(combo[1] > 1) {
				score += combo[1] - 1;
			}
		}
		for(Map.Entry<Integer, Map<Integer, Carte>> entry: p.getPlateau().getCartesPosees().entrySet() ) {
			for(Map.Entry<Integer, Carte> entry2: entry.getValue().entrySet()) {
				int i = 0;
				int [] combo = new int[2];
				combo[0] = 0;
				combo[1] = 0;
				int nbCartes = 0;
				while(cartes.containsKey(entry.getKey()+i)) {
					if(cartes.get(entry.getKey() + i).containsKey(entry2.getKey())) {
						if(cartes.get(entry.getKey() + i).get(entry2.getKey()).getForme() == victoire.getForme()) {
							nbCartes++;
						} else {
							if(combo[0] <= 1) {
								combo[0] = nbCartes;
							} else {
								combo[1] = nbCartes;
							}
							nbCartes = 0;
						}
					}
					i++;
				}
				if(nbCartes > 1) {
					if(combo[0] < 2) {
						combo[0] = nbCartes;
					} else {
						combo[1] = nbCartes;
					}
				}
				if(combo[0] > 1) {
					score += combo[0] - 1;
				}
				if(combo[1] > 1) {
					score += combo[1] - 1;
				}
			}
			break;
		}
		return score;
	}
	

}
