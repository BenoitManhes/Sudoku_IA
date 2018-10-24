package model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Vector;

public class Backtracking {
	public static void solve(Sudoku sudoku){
		// actualisation des valeurs possibles pour chaque cases
		sudoku.actualize(); 

		System.out.println(backtrack(sudoku,sudoku.getOrdreTraitement()));
		//sudoku.actualize();
	}

	public static boolean backtrack(Sudoku sudoku, PriorityQueue<Case> caseNoeud) {

		// mise a jour des heuristiques pour chaque cases
		if(caseNoeud.isEmpty()) {
			return true;
		}else {
			if(sudoku.bloquer()) {
				System.out.println("BLOQUER ===================");
				return false;
			}
			Heuristiques.updateHeuristiques(sudoku);

			PriorityQueue<Case> caseNonTestes = new PriorityQueue<>(caseNoeud);

			while(!caseNoeud.isEmpty() ) {
				System.out.println("Noeud numero :"+caseNonTestes.size());
				// choix de la case a traiter selon leurs heuristiques
				Case c = caseNoeud.poll();
				int i = c.getI();
				int j = c.getJ();
				caseNonTestes.remove(c);
				
				ArrayList<Integer> valeurNonTestes = new ArrayList<Integer>(c.getValeursPossibles());
				testValPossible(c);
				
				while(!valeurNonTestes.isEmpty() ) {
					// ancienne valeur de c
					int exValeurCase = c.getValeur();
					// choix de la valeur a teste 
					int valeur = valeurNonTestes.get(0);
					valeurNonTestes.remove(0);
					System.out.println("("+c.getI()+","+c.getJ()+") valeur choisie :"+valeur);
					
					// affectation de la valeur
					sudoku.putValeur(i, j, valeur);
					// mise a jour des valeurs possibles pour les cases concernees
					if(exValeurCase!=0) sudoku.addPossibleValue(i, j, exValeurCase);
					sudoku.basicForwardChecking();
					sudoku.arcConsistency();
					System.out.println();
					if(backtrack(sudoku,caseNonTestes)) {
						return true;						
					}else {
						System.out.println();
					}
				}
				
				int valeurCase = c.getValeur(); 
				sudoku.putValeur(i, j, 0); 
				if(valeurCase!=0) sudoku.addPossibleValue(i, j, valeurCase); 
				sudoku.basicForwardChecking(); 
				
				caseNonTestes.add(c);
			}


			return false;
		}

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

	public static void testValPossible(Case c) {
		String str ="Case ("+c.getI()+","+c.getJ()+") val possible : ";
		for (Integer i : c.getValeursPossibles()) {
			str+=i+", ";
		}
		System.out.println(str);
	}
}
