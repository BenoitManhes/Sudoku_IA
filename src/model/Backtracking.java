package model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Vector;

public class Backtracking {
	public static void solve(Sudoku sudoku){
		// actualisation des valeurs possibles pour chaque cases
		sudoku.actualize(); 

		backtrack(sudoku, sudoku.getOrdreTraitement());
		//sudoku.actualize();
	}

	public static boolean backtrack(Sudoku sudoku , PriorityQueue<Case> caseNontestes) {

		// mise a jour des heuristiques pour chaque cases
		Heuristiques.updateHeuristiques(sudoku);

		boolean solutionTrouve = false;
		for (int k = 0; k < caseNontestes.size(); k++) {
			System.out.println(k);

			// choix de la case a traiter selon leurs heuristiques
			Case c = pollCaseAt(caseNontestes, k);
			int i = c.getI();
			int j = c.getJ();
			//System.out.println("Case en test : "+i+","+j);

			// a changer pour adapter avec une ArrayList et la methode qui renvoie les valeur possibles
			ArrayList<Integer> valeurNonTestes = new ArrayList<Integer>(c.getValeursPossibles());
			

			while(!valeurNonTestes.isEmpty() && !solutionTrouve) {
				// choix de la valeur a teste 
				int valeur = valeurNonTestes.get(0);
				valeurNonTestes.remove(0);
				// affectation de la valeur
				sudoku.putValeur(i, j, valeur);
				// mise a jour des valeurs possibles pour les cases concernees
				sudoku.basicForwardChecking();
				sudoku.arcConsistency();

				if(!sudoku.finish()) {
					if(!sudoku.bloquer()) {
						solutionTrouve = backtrack(sudoku,caseNontestes);
					}else {
						sudoku.putValeur(i, j, 0);
						sudoku.basicForwardChecking();
					}
				}else {
					solutionTrouve = true;
					System.out.println("solution trouve !!!!!!!!!!!");
				}

			}
			if(!solutionTrouve) {
				caseNontestes.add(c);
				sudoku.putValeur(i, j, 0);
				sudoku.basicForwardChecking();
			}
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
