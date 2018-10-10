package model;

import java.util.Vector;

public class Heuristiques {
	
	//Les m�thodes suivantes agissent sur les attributs
	//int priorityMrv, priorityDh et priorityLcv
	
	public static void minimalRemainingValue(Case caseAComparer){
		caseAComparer.setPriorityMrv(caseAComparer.getValeursPossibles().size());
	}
	
	public static void degreeHeuristic(Case[][] grille, Case caseAComparer){
		caseAComparer.setPriorityDh(getNombreCasesVidesEnvirons(grille, caseAComparer));
	}
	
	public static int leastConstrainingValue(Case C){
		int v =0;	// valeur par default
		return v;
	}
	
	public static void arcConsistency(Case[][] grille){
		
	}
	
	public static void updateHeuristiques(Sudoku sudoku) {
		// mise a jour des heuristiques de toutes les cases non nulles
		for (int i = 0; i < sudoku.getGrille().length; i++) {
			for (int j = 0; j < sudoku.getGrille()[i].length; j++) {
				// calcul des heuristiques
			}
		}
	}
	
	private static int getNombreCasesVidesEnvirons(Case[][] grille, Case caseAComparer) {
		return getCasesVidesLigne(grille, caseAComparer).size()
				+ getCasesVidesColonne(grille, caseAComparer).size()
				+ getCasesVidesCarre(grille, caseAComparer).size();
	}

	private static Vector<Case> getCasesVidesLigne(Case[][] grille, Case caseAComparer) {
		Vector<Case> liste = new Vector<Case>();
		int ligne = caseAComparer.getI();
		for(int j = 0 ; j<grille.length ; j++) {
			if(grille[ligne][j].getValeur()==0) {
				liste.add(grille[ligne][j]);
			}
		}
		return liste;
	}
	
	private static Vector<Case> getCasesVidesColonne(Case[][] grille, Case caseAComparer) {
		Vector<Case> liste = new Vector<Case>();
		int colonne = caseAComparer.getJ();
		for(int i = 0 ; i<grille[1].length ; i++) {
			if(grille[i][colonne].getValeur()==0) {
				liste.add(grille[i][colonne]);
			}
		}
		return liste;
	}
	
	private static Vector<Case> getCasesVidesCarre(Case[][] grille, Case caseAComparer) {
		Vector<Case> liste = new Vector<Case>();
		int iDebutCarre = 3*(caseAComparer.getI()/3);
		int jDebutCarre = 3*(caseAComparer.getJ()/3);
		for(int i = 0 ; i<3 ; i++) {
			for(int j = 0 ; j<3 ; j++) {
				if(grille[iDebutCarre+i][jDebutCarre+j].getValeur()==0 && i!=caseAComparer.getI() && j!=caseAComparer.getJ()) {
					liste.add(grille[iDebutCarre+i][jDebutCarre+j]);
				}
			}
		}
		return liste;
	}
	
	
	
}
