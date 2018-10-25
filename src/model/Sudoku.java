package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Sudoku extends java.util.Observable {

	private Case[][] grille; //sudoku taille fixe, pas besoin de constante
	private static Case[][] grilleInitiale;

	private ArrayList<Case> ordreTraitement;

	public Sudoku(File fichier) {
		this.grille = new Case[9][9];
		Sudoku.grilleInitiale = new Case[9][9];
		this.ordreTraitement = new ArrayList<Case>(); 

		int[][] valeurFichier = this.load(fichier);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.grille[i][j] = new Case(i,j);
				this.grille[i][j].setValeur(valeurFichier[i][j]);
				Sudoku.grilleInitiale[i][j] = new Case(i,j);
				Sudoku.grilleInitiale[i][j].setValeur(valeurFichier[i][j]);
			}
		}
		actualize();
		basicForwardChecking();
		initOrdreTraitement();
	}


	/** ================================================ methode de calcul =====================================================================
	 * @throws FileNotFoundException */
	public int[][] load(File fichier) {

		int[][] valeurFichier = new int[9][9];

		// Initialisation avec un fichier text
		FileReader lectureFile = null;
		try {
			lectureFile = new FileReader (fichier);
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier est illisible");
			e.printStackTrace();
		}

		BufferedReader lecture = new BufferedReader (lectureFile);

		// Lecture de tout le fichier et remplissage de la matrice
		int valeur = 0;
		String ligne = null;

		for(int i = 0 ; i<9 ; i++) {
			try {
				ligne = lecture.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for(int j = 0 ; j<9 ; j++) {
				valeur = Character.getNumericValue(ligne.charAt(j));
				valeurFichier[i][j] = valeur;
			}

		}

		// Liberation des ressources
		try {
			lectureFile.close();
			lecture.close();
		} catch (IOException e) {
			System.out.println("Erreur dans la fermeture du fichier apres lecture");
			e.printStackTrace();
		}

		return valeurFichier;
	}

	public void actualize(){
		// actualisation de la grille au pres de la vue
		//Case[][] grilleSansValeursInitales = getGrilleSansValeursInitiales(grilleInitiale, grille);
		setChanged();
		notifyObservers(this.grille);
	}

	public static boolean isInValeursInitiale(Case caseAnalyse) {

		if(grilleInitiale[caseAnalyse.getI()][caseAnalyse.getJ()].getValeur() != 0) {
			return true;
		}
		return false;
	}

	public void putValeur(int i, int j, int val) {
		this.grille[i][j].setValeur(val);
		this.actualize();
		try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
	}

	public void deleteInCol(int valeur, int j){
		for(int i=0; i<9; i++){
			if(this.grille[i][j].getValeursPossibles().contains(valeur)){
				this.grille[i][j].getValeursPossibles().remove(Integer.valueOf(valeur));
			}
		}
	}

	public void deleteInRow(int valeur, int i){
		for(int j=0; j<9; j++){
			if(this.grille[i][j].getValeursPossibles().contains(valeur)){
				this.grille[i][j].getValeursPossibles().remove(Integer.valueOf(valeur));
			}
		}
	}

	public void deleteInSquare(int valeur, int i, int j){
		int i_min = 3*(i/3); 
		int j_min = 3*(j/3);
		for(i=i_min; i<i_min+3;i++){
			for(j=j_min; j<j_min+3; j++){
				if(this.grille[i][j].getValeursPossibles().contains(valeur)){
					this.grille[i][j].getValeursPossibles().remove(Integer.valueOf(valeur));
				}
			}
		}
	}

	public void addPossibleValueInCol(int valeur, int j){
		for(int i=0; i<9; i++){
			if(!this.grille[i][j].getValeursPossibles().contains(valeur)&&this.grille[i][j].getValeur()==0){
				this.grille[i][j].getValeursPossibles().add(valeur);
			}
		}
	}

	public void addPossibleValueInRow(int valeur, int i){
		for(int j=0; j<9; j++){
			if(!this.grille[i][j].getValeursPossibles().contains(valeur)&&this.grille[i][j].getValeur()==0){
				this.grille[i][j].getValeursPossibles().add(valeur);
			}
		}
	}

	public void addPossibleValueInSquare(int valeur, int i, int j){
		int i_min = 3*(i/3); 
		int j_min = 3*(j/3);
		for(i=i_min; i<i_min+3;i++){
			for(j=j_min; j<j_min+3; j++){
				if(!this.grille[i][j].getValeursPossibles().contains(valeur)&&this.grille[i][j].getValeur()==0){
					this.grille[i][j].getValeursPossibles().add(valeur);
				}
			}
		}
	}

	public void basicForwardChecking(){
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				deletePossibleValue(this.grille[i][j].getValeur(), i, j);
			}
		}
	}

	public void addPossibleValue(int i, int j, int val){
		addPossibleValueInCol(val, j);
		addPossibleValueInRow(val, i);
		addPossibleValueInSquare(val, i, j);
	}

	public void deletePossibleValue(int val, int i, int j) {
		deleteInCol(val,j);
		deleteInRow(val,i);
		deleteInSquare(val,i,j);
	}

	public boolean arcConsistency(int valeur, int i, int j){
		if(isDeleteNeeded(valeur, i, j)) {
			return false;
			//deletePossibleValue(valeur, i, j);
		}
		return true;
	}

	public boolean isDeleteNeeded(int valeur, int i, int j) {
		if((isDeleteNeededValueInRow(valeur, i, j) == true 
				|| isDeleteNeededValueInCol(valeur, i, j) == true
				|| isDeleteNeededValueInSquare(valeur, i,j) == true)) {
			return true;
		}
			return false;
	}

	public boolean isDeleteNeededValueInRow(int valeur, int i, int j) {
		for(int k = 0 ; k<9 ; k++) {
			if(this.grille[i][k].getValeursPossibles().size() == 1) {
				if(this.grille[i][k].getValeursPossibles().get(0) == valeur && k!=j) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isDeleteNeededValueInCol(int valeur, int i, int j) {
		for(int k = 0 ; k<9 ; k++) {
			if(this.grille[k][j].getValeursPossibles().size() == 1) {
				if(this.grille[k][j].getValeursPossibles().get(0) == valeur && k!=i) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isDeleteNeededValueInSquare(int valeur, int i, int j) {
		int i_min = 3*(i/3); 
		int j_min = 3*(j/3);
		for(int k=i_min; k<i_min+3; k++){
			for(int l=j_min; l<j_min+3; l++){
				if(this.grille[k][l].getValeursPossibles().size() == 1) {
					if(this.grille[k][l].getValeursPossibles().get(0) == valeur && k != i && l != j) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean finish() {
		boolean finish = true;
		for (Case[] cases : grille) {
			for (Case cas : cases) {
				if(cas.getValeur() == 0) finish = false;
			}
		}
		return finish;
	}

	public boolean bloquer() {
		boolean bloc = false;
		for (Case[] cases : grille) {
			for (Case cas : cases) {
				if(cas.getValeur() == 0 && cas.getValeursPossibles().isEmpty()) bloc = true;
			}
		}
		return bloc;
	}

	public void initOrdreTraitement() {
		for (Case[] cases : this.grille) {
			for (Case cas : cases) {
				if(cas.getValeur() == 0) this.ordreTraitement.add(cas);
			}
		}
		Collections.sort(this.ordreTraitement, new CaseComparator());
	}

	public Case[][] getGrille() {
		return grille;
	}

	public ArrayList<Case> getOrdreTraitement() {
		return ordreTraitement;
	}

	public Case[][] getGrilleInitiale() {
		return grilleInitiale;
	}







}
