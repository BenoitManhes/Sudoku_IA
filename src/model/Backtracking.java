package model;

import java.util.PriorityQueue;
import java.util.Vector;

public class Backtracking {
	public static void solve(Sudoku sudoku){
		// actualisation des valeurs possibles pour chaque cases
		sudoku.actualize(); 

		backtrack(sudoku, sudoku.getOrdreTraitement());

	}

	public static boolean backtrack(Sudoku sudoku , PriorityQueue<Case> caseNontestes) {

		// mise a jour des heuristiques pour chaque cases
		Heuristiques.updateHeuristiques(sudoku);

		boolean solutionTrouve = false;

		for (int k = 0; k < caseNontestes.size(); k++) {


			// choix de la case a traiter selon leurs heuristiques
			Case c = pollCaseAt(caseNontestes, k);
			int i = c.getI();
			int j = c.getJ();

			// a changer pour adapter avec une ArrayList et la methode qui renvoie les valeur possibles
			PriorityQueue<Integer> valeurNonTestes = new PriorityQueue<>(c.getValeurNonTestes());

			while(!valeurNonTestes.isEmpty() && !solutionTrouve) {
				// choix de la valeur a teste selon leurs heuristiques
				int valeur = c.getValeurNonTestes().poll();
				sudoku.getGrille()[i][j].setValeur(valeur);

				// mise a jour des valeurs possibles pour les cases concernees
				sudoku.basicForwardChecking();
				sudoku.arcConsistency();

				if(!sudoku.blocked()) {
					solutionTrouve = backtrack(sudoku,caseNontestes);					
				}else {
					solutionTrouve = true;
				}

			}
			caseNontestes.add(c);
		}
		
		return solutionTrouve;

	}

	public static Case pollCaseAt(PriorityQueue<Case> queue, int i) {
		Vector<Case> v = new Vector<>();
		for (int j = 0; j < i; j++) {
			v.add(queue.poll());
		}
		Case caseVoule = queue.poll();
		for (Case case1 : v) {
			queue.add(case1);
		}
		return caseVoule;
	}
}
