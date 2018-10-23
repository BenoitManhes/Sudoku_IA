package model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Vector;

public class Backtracking {
	public static void solve(Sudoku sudoku){
		// actualisation des valeurs possibles pour chaque cases
		sudoku.actualize(); 

		backtrack(sudoku);
		//sudoku.actualize();
	}

	public static boolean backtrack(Sudoku sudoku) {

		// mise a jour des heuristiques pour chaque cases
		Heuristiques.updateHeuristiques(sudoku);

		boolean solutionTrouve = false;
		for (int k = 0; k < sudoku.getOrdreTraitement().size(); k++) {
			System.out.println(k);

			// choix de la case a traiter selon leurs heuristiques
			Case c = pollCaseAt(sudoku.getOrdreTraitement(), k);
			int i = c.getI();
			int j = c.getJ();
			//System.out.println("Case en test : "+i+","+j);

			// a changer pour adapter avec une ArrayList et la methode qui renvoie les valeur possibles
			ArrayList<Integer> valeurNonTestes = new ArrayList<Integer>(c.getValeursPossibles());
			

			while(!valeurNonTestes.isEmpty() && !solutionTrouve) {
				int valeurCase = c.getValeur();
				sudoku.putValeur(i, j, 0);
				sudoku.addPossibleValue(i, j, valeurCase);
				sudoku.basicForwardChecking();
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
						solutionTrouve = backtrack(sudoku);
					}else{
						System.out.println("Bloquage en :"+i+","+j);
					}
				}else {
					solutionTrouve = true;
					System.out.println("solution trouve !!!!!!!!!!!");
				}

			}
			if(!solutionTrouve) {
				sudoku.getOrdreTraitement().add(c);
				int valeurCase = c.getValeur();
				sudoku.putValeur(i, j, 0);
				sudoku.addPossibleValue(i, j, valeurCase);
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
