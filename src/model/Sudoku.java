package model;

import java.io.File;
import java.util.PriorityQueue;

public class Sudoku extends java.util.Observable {

	private Case[][] grille; //sudoku taille fixe, pas besoin de constante
	private PriorityQueue<Case> ordreTraitement;

	public Sudoku(File fichier) {
		this.grille = new Case[9][9];
		this.ordreTraitement = new PriorityQueue<Case>(new CaseComparator()); 
		
		int[][] valeurFichier = this.load(fichier);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.grille[i][j] = new Case();
				this.grille[i][j].setValeur(valeurFichier[i][j]);
			}
		}
		// initialisation("nomFichier.txt"); 
	}

	/** ================================================ methode de calcul =====================================================================*/
	public int[][] load(File fichier) {
		int[][] valeurFichier = new int[9][9];
				// initialisation avec un fichier text
				//a faire
		return valeurFichier;
	}
	
	public void actualize(){
		//Actualise la grille en modifiant les valeurs de chaque
		//case et les priorités en focntion des heuristiques
		setChanged();
		notifyObservers(grille);
	}
	
	public boolean notInCol(int valeur, int j){
		for(int i=0; i<9; i++){
			if(this.grille[i][j].getValeur() == valeur)
				return false;
		}
		return true;
	}
	
	public boolean notInRow(int valeur, int i){
		for(int j=0; j<9; j++){
			if(this.grille[i][j].getValeur() == valeur)
				return false;
		}
		return true;
	}
	
	public boolean notInSquare(int valeur, int i, int j){
		int i_min = 3*(i/3); 
		int j_min = 3*(j/3);
		for(i=i_min; i<i_min+3;i++){
			for(j=j_min; j<j_min+3; j++){
				if(this.grille[i][j].getValeur() == valeur)
					return false;
			}
		}
		return true;
	}

}
