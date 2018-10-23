package model;

public class Backtracking {
	public static void solve(Sudoku sudoku){
		
		Heuristiques.updateHeuristiques(sudoku);
		Case c = sudoku.getOrdreTraitement().poll();
		int valeur = Heuristiques.leastConstrainingValue(c);
		
		//boolean solution = backtrack(sudoku,c.getI(),c.getJ(),valeur);
	}
	
	/*public static boolean backtrack(Sudoku sudoku, Case lastCase, int lastI, int lastJ, int lastValeur) {
		//
		Heuristiques.updateHeuristiques(sudoku);
		Case c = sudoku.getOrdreTraitement().poll();
		int valeur = Heuristiques.leastConstrainingValue(c);
		*/
}
