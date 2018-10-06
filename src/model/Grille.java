package model;

public class Grille {

	private static final int TAILLE_CARRE = 3;
	private static final int TAILLE = TAILLE_CARRE*TAILLE_CARRE;
	private Case[][] groupeCase;

	public Grille() {
		this.groupeCase = new Case[TAILLE][TAILLE];
		for (int i = 0; i < TAILLE; i++) {
			for (int j = 0; j < TAILLE; j++) {
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
			int X = x/TAILLE_CARRE;
			int Y = y/TAILLE_CARRE;

			for (int i = Y; i < Y+TAILLE_CARRE; i++) {
				for (int j = X; j < X+TAILLE_CARRE; j++) {
					if(this.groupeCase[i][j].getValue() == value) check = false;
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
			for (int i = 0; i < this.groupeCase.length; i++) {
				//check ligne
				if(this.groupeCase[y][i].getValue() == value) check = false;
				//check colonne
				if(this.groupeCase[i][x].getValue() == value) check = false;
			}
		}

		return check;
	}

	// check que la case soit dans la grille et vide
	public boolean checkCaseDispo(int x, int y) {
		boolean check = false;
		// check si coordonne dans la grille
		if( x>=0 && y>=0 && y<this.groupeCase.length && x<this.groupeCase.length) {
			//check si case vide
			if(this.groupeCase[y][x].getValue() == 0) {
				check = true;
			}
		}

		return check;
	}


	/** ================================================= getter et setter =====================================================================*/
	public Case[][] getGroupeCase() {
		return groupeCase;
	}

}
