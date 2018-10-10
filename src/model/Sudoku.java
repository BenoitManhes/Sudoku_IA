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
				basicForwardChecking(i, j, this.grille[i][j].getValeur());
			}
		}
		// initialisation("nomFichier.txt"); 
	}

	/** ================================================ methode de calcul =====================================================================*/
	public int[][] load(File fichier) {
		int[][] valeurFichier = new int[9][9];
				// initialisation avec un fichier text
				//TODO
		return valeurFichier;
	}
	
	public void actualize(){
		//Actualise la grille en modifiant les valeurs de chaque
		//case et les priorités en focntion des heuristiques
		setChanged();
		notifyObservers(grille);
	}
	
	public void deleteInCol(int valeur, int j){
		for(int i=0; i<9; i++){
			this.grille[i][j].getValeursPossibles().remove(Integer.valueOf(valeur));
		}
	}
	
	public void deleteInRow(int valeur, int i){
		for(int j=0; j<9; j++){
			this.grille[i][j].getValeursPossibles().remove(Integer.valueOf(valeur));
		}
	}
	
	public void deleteInSquare(int valeur, int i, int j){
		int i_min = 3*(i/3); 
		int j_min = 3*(j/3);
		for(i=i_min; i<i_min+3;i++){
			for(j=j_min; j<j_min+3; j++){
				this.grille[i][j].getValeursPossibles().remove(Integer.valueOf(valeur));
			}
		}
	}
	
	public void basicForwardChecking(int i, int j, int valeur){
		deleteInCol(valeur, j);
		deleteInRow(valeur, i);
		deleteInSquare(valeur, i, j);
	}

}
