package model;

public class Grille {

	private int[][] grille;
	private Case[][] groupeCase;

	public Grille() {
		this.grille = new int[9][9];
		this.groupeCase = new Case[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				groupeCase[i][j] = new Case(j,i);
			}
		}
		// initialisation("nomFichier.txt"); 
	}

	/** ================================================ methode de calcul =====================================================================*/
	public void init(String fichier) {
		// initialisation avec un fichier text
	}

	// check valeur selon le carre ou elle est placee
	public boolean checkCarre(int x, int y, int value) {
		boolean check = true;
		if(this.checkCaseDispo(x, y)) {
			int X = x/3;
			int Y = y/3;

			for (int i = Y; i < Y+3; i++) {
				for (int j = X; j < X+3; j++) {
					if(this.grille[i][j] == value) check = false;
				}
			}
		}

		return check;
	}

	// check valeur selon la ligne et la colonne
	public boolean checkLineaire(int x, int y, int value) {
		boolean check = true;
		if(this.checkCaseDispo(x, y)) {
			//check ligne & colonne
			for (int i = 0; i < grille.length; i++) {
				//check ligne
				if(this.grille[y][i] == value) check = false;
				//check colonne
				if(this.grille[i][x] == value) check = false;
			}
		}

		return check;
	}

	// check que la case soit dans la grille et vide
	public boolean checkCaseDispo(int x, int y) {
		boolean check = false;
		// check si coordonne dans la grille
		if( x>=0 && y>=0 && y<this.grille.length && x<this.grille.length) {
			//check si case vide
			if(this.grille[y][x]==0) {
				check = true;
			}
		}

		return check;
	}


	/** ================================================= getter et setter =====================================================================*/
	public int[][] getGrille() {
		return this.grille;
	}

	public Case[][] getGroupeCase() {
		return groupeCase;
	}

}
