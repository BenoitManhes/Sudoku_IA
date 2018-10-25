package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Sudoku extends java.util.Observable {

	private Case[][] grille;
	private static Case[][] grilleInitiale;
	private ArrayList<Case> listeCasesAtraiter;

	//Constructeur a partir d'un fichier 
	public Sudoku(File fichier) {
		this.grille = new Case[9][9];
		Sudoku.grilleInitiale = new Case[9][9];
		this.listeCasesAtraiter = new ArrayList<Case>(); 

		int[][] valeurFichier = this.load(fichier); //Chargement du fichier
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//On assigne chaque valeur du fichier à la grille en instanciant
				//des objets cases
				this.grille[i][j] = new Case(i,j);
				this.grille[i][j].setValeur(valeurFichier[i][j]);
				
				//On fait de meme pour une grille dite Initiale permetant de differencier
				//les chiffres de bases pour l'affichage
				Sudoku.grilleInitiale[i][j] = new Case(i,j);
				Sudoku.grilleInitiale[i][j].setValeur(valeurFichier[i][j]);
			}
		}
		actualisationAffichage(); //Actualisation de l'affichage
		basicForwardChecking(); //On procède a une premiere elimination des valeurs legales restantes
		initialisationListeCase(); //On initialise la liste des cases a traiter
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

	public void initialisationListeCase() {
		for (Case[] cases : this.grille) {
			for (Case cas : cases) {
				if(cas.getValeur() == 0) this.listeCasesAtraiter.add(cas);
			}
		}
		Collections.sort(this.listeCasesAtraiter, new CaseComparator());
	}

	public void elagageInitial(){
		//Apres l'initialisation de la grille et avant le debut de la resolution, on assigne a toute les cases
		//qui ne possede qu'une valeur possible ladite valeur.
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(this.getGrille()[i][j].getValeursPossibles().size()==1){
					this.getGrille()[i][j].setValeur(this.getGrille()[i][j].getValeursPossibles().get(0));
					this.getListeCasesAtraiter().remove(this.getGrille()[i][j]);
				}
			}
		}
	}

	public void addValueInGrid(int i, int j, int val) {
		//Methode regroupant l'actualisation d'une valeur, l'affichage et le delai d'attente pour ce dernier.
		this.grille[i][j].setValeur(val);
		this.actualisationAffichage();
		try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
	}

	/*------------- Fonctions necessaires a l'affichage :-----------------*/
	
	public void actualisationAffichage(){
		setChanged();
		notifyObservers(this.grille);
	}
		
	public static boolean isInValeursInitiale(Case caseAnalyse) {
		if(grilleInitiale[caseAnalyse.getI()][caseAnalyse.getJ()].getValeur() != 0) {
			return true;
		}
		return false;
	}

	/*----------- Methodes de suppressions des valeurs illegales dans la grille : ---------*/
	
	public void basicForwardChecking(){
		//Cette methode supprime les valeurs illegales pour chaque case du plateau
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				deletePossibleValue(this.grille[i][j].getValeur(), i, j);
			}
		}
	}
	
	public void deletePossibleValue(int val, int i, int j) {
		//appelee dans la methode precedente : 
		//Pour une case et une valeur donnee, supprime la valeur de la liste des cases
		//en contrainte avec cette derniere
		deleteInCol(val,j);
		deleteInRow(val,i);
		deleteInSquare(val,i,j);
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
	
	/*----------- Methodes d'ajout des valeurs legales dans la grille : ---------*/

	public void addPossibleValue(int i, int j, int val){
		//Cette methode permet d'ajouter une valeur comme valeur legale dans la liste des valeurs possibles
		//des cases en contrainte avec la case(i,j)
		addPossibleValueInCol(val, j);
		addPossibleValueInRow(val, i);
		addPossibleValueInSquare(val, i, j);
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

	/*------------ Arc Consistency ---------------*/
	
	public boolean arcConsistency(int valeur, int i, int j){
		//Cette methode permet de prevoir un eventuel blocage lors de l'agent d'une 
		//valeur dans la case (i,j). Si elle detecte un conflit elle renvoie false
		if(isConflictInAC(valeur, i, j)) {
			return false;
		}
		return true;
	}

	public boolean isConflictInAC(int valeur, int i, int j) {
		if((isConflictInRow(valeur, i, j) == true 
				|| isConflictInCol(valeur, i, j) == true
				|| isConflictInSquare(valeur, i,j) == true)) {
			return true;
		}
			return false;
	}

	public boolean isConflictInRow(int valeur, int i, int j) {
		for(int k = 0 ; k<9 ; k++) {
			if(this.grille[i][k].getValeursPossibles().size() == 1) {
				if(this.grille[i][k].getValeursPossibles().get(0) == valeur && k!=j) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isConflictInCol(int valeur, int i, int j) {
		for(int k = 0 ; k<9 ; k++) {
			if(this.grille[k][j].getValeursPossibles().size() == 1) {
				if(this.grille[k][j].getValeursPossibles().get(0) == valeur && k!=i) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isConflictInSquare(int valeur, int i, int j) {
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


	/*-----------------Accesseurs et Mutateurs-------------------*/
	
	public Case[][] getGrille() {
		return grille;
	}

	public ArrayList<Case> getListeCasesAtraiter() {
		return listeCasesAtraiter;
	}

	public Case[][] getGrilleInitiale() {
		return grilleInitiale;
	}

}
