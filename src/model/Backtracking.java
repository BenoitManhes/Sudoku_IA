package model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Vector;

public class Backtracking {
	public static int compteur = 0;
	public static void solve(Sudoku sudoku){
		// actualisation des valeurs possibles pour chaque cases
		sudoku.actualize(); 
		//backtrack(sudoku,sudoku.getOrdreTraitement());
		
		/*for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(sudoku.getGrille()[i][j].getValeursPossibles().size()==1){
					sudoku.getGrille()[i][j].setValeur(sudoku.getGrille()[i][j].getValeursPossibles().get(0));
					sudoku.getOrdreTraitement().remove(sudoku.getGrille()[i][j]);
				}
			}
		}
		sudoku.basicForwardChecking();*/
		
		backtrack(sudoku, sudoku.getOrdreTraitement());
		
		//sudoku.actualize();
	}

	
	public static boolean backtrack(Sudoku sudoku, PriorityQueue<Case> caseNoeud){
		Heuristiques.updateHeuristiques(sudoku);
		System.out.println("taille liste caseNoeud : "+caseNoeud.size());
		if(caseNoeud.isEmpty())
		{
			System.out.println("rempli");
			return true;
		}
		
		if(sudoku.bloquer()) return false;
			
		Case c = caseNoeud.poll();
		int i = c.getI();
		int j = c.getJ();		
		System.out.println("noeud "+i+","+j);
		System.out.println("val possible"+c.getValeursPossibles());
		/** Least Constraining Value */
		//ArrayList<Integer> copyValPossible = new ArrayList<Integer>(c.getValeursPossibles());
		ArrayList<Integer> copyValPossible = Heuristiques.leastConstrainingValue(sudoku.getGrille(), c);
		for(int value : copyValPossible){
			System.out.println("compteur:"+compteur);
			if(sudoku.arcConsistency(value, i, j) == false) {
				System.out.println("arc consistency faux");
				continue;
			}
			compteur++;
			sudoku.putValeur(i, j, value);
			sudoku.basicForwardChecking();

			if(backtrack(sudoku, caseNoeud)) {	
				return true;
			}
			
			sudoku.putValeur(i, j, 0);
			sudoku.addPossibleValue(i, j, value);
			sudoku.basicForwardChecking();
		}
		c.getValeursPossibles().clear();
		c.getValeursPossibles().addAll(copyValPossible);
		System.out.println("false");
		System.out.println("taille liste caseNoeud : "+caseNoeud.size());
		caseNoeud.add(c);
		return false;
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
