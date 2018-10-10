package model;

public class Heuristiques {
	
	//Les méthodes suivantes agissent sur les attributs
	//int priorityMrv, priorityDh et priorityLcv
	
	public static void minimalRemainingValue(Case[][] grille){
		
	}
	
	public static void degreeHeuristic(Case[][] grille){
		
	}
	
	public static void leastConstrainingValue(Case[][] grille, int i, int j){
		// maj de la priorityQueue
	}
	
	
	
	public static void updateHeuristiques(Sudoku sudoku) {
		// mise a jour des heuristiques de toutes les cases non nulles
		for (int i = 0; i < sudoku.getGrille().length; i++) {
			for (int j = 0; j < sudoku.getGrille()[i].length; j++) {
				// calcul des heuristiques
			}
		}
	}
}
