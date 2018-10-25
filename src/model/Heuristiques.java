package model;

import java.util.ArrayList;
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
	
	public static ArrayList<Integer> leastConstrainingValue(Case[][] grille, Case caseAComparer){
		
		ArrayList<Integer> listeValeurs = new ArrayList<>();
		int [] compteur = new int[caseAComparer.getValeursPossibles().size()];
		int kCompteur = 0;
		
		for(int valeurPossible : caseAComparer.getValeursPossibles()) {
			for(Case caseVL : getCasesVidesLigne(grille, caseAComparer)) {
				if(caseVL.getValeursPossibles().contains(valeurPossible)) {
					compteur[kCompteur]++;
				}
			}
			for(Case caseVC : getCasesVidesColonne(grille, caseAComparer) ) {
				if(caseVC.getValeursPossibles().contains(valeurPossible)) {
					compteur[kCompteur]++;
				}
			}
			for(Case caseVCarre : getCasesVidesCarre(grille, caseAComparer) ) {
				if(caseVCarre.getValeursPossibles().contains(valeurPossible)) {
					compteur[kCompteur]++;
				}
			}
			
			kCompteur++;
		}
		
		for(int k = 0 ; k<compteur.length ; k++) {
			int max = 0;
			int indiceMax = 0;
			for (int i = 0; i < compteur.length; i++) {
				if(compteur[i]>max) {
					indiceMax = i;
					max = compteur[i];
				}
			}
			listeValeurs.add(0, caseAComparer.getValeursPossibles().get(indiceMax));
			compteur[indiceMax]=-1;
		}
		
		return listeValeurs;
	}
	
	
	
	public static void updateHeuristiques(Sudoku sudoku) {
		// mise a jour des heuristiques de toutes les cases non nulles
		for (int i = 0; i < sudoku.getGrille().length; i++) {
			for (int j = 0; j < sudoku.getGrille()[i].length; j++) {
				minimalRemainingValue(sudoku.getGrille()[i][j]);
				degreeHeuristic(sudoku.getGrille(), sudoku.getGrille()[i][j]);
			}
		}
	}
	
	
	//---------------------------------------------------- Methodes Utiles --------------------------------------------------------------------------------
	
	
	private static int getNombreCasesVidesEnvirons(Case[][] grille, Case caseAComparer) {
		return getCasesVidesLigne(grille, caseAComparer).size()
				+ getCasesVidesColonne(grille, caseAComparer).size()
				+ getCasesVidesCarre(grille, caseAComparer).size();
	}

	private static Vector<Case> getCasesVidesLigne(Case[][] grille, Case caseAComparer) {
		Vector<Case> liste = new Vector<Case>();
		int ligne = caseAComparer.getI();
		for(int j = 0 ; j<grille.length ; j++) {
			if(grille[ligne][j].getValeur()==0 && j!=caseAComparer.getJ()) {
				liste.add(grille[ligne][j]);
			}
		}
		return liste;
	}
	
	private static Vector<Case> getCasesVidesColonne(Case[][] grille, Case caseAComparer) {
		Vector<Case> liste = new Vector<Case>();
		int colonne = caseAComparer.getJ();
		for(int i = 0 ; i<grille[1].length ; i++) {
			if(grille[i][colonne].getValeur()==0 && i!=caseAComparer.getJ()) {
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
				if(grille[iDebutCarre+i][jDebutCarre+j].getValeur()==0 && i!=caseAComparer.getI() && j!=caseAComparer.getJ() ) {
					liste.add(grille[iDebutCarre+i][jDebutCarre+j]);
				}
			}
		}
		return liste;
	}
	
	
	
}
