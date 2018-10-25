package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Vector;

public class Backtracking {
	public static int compteur = 0;
	public static void solve(Sudoku sudoku){
		// actualisation des valeurs possibles pour chaque cases
		sudoku.actualize(); 
		//backtrack(sudoku,sudoku.getOrdreTraitement());
		
		for (int k = 0; k < 9; k++) {
			for (int l = 0; l < 9; l++) {
				if(sudoku.getGrille()[k][l].getValeursPossibles().size()==1){
					sudoku.getGrille()[k][l].setValeur(sudoku.getGrille()[k][l].getValeursPossibles().get(0));
					sudoku.getOrdreTraitement().remove(sudoku.getGrille()[k][l]);
				}
			}
		}
		sudoku.basicForwardChecking();
		
		backtrack2(sudoku, sudoku.getOrdreTraitement());
		
		//sudoku.actualize();
	}

	public static boolean backtrack2(Sudoku sudoku, ArrayList<Case> caseNoeud){
		Heuristiques.updateHeuristiques(sudoku);
		Collections.sort(caseNoeud, new CaseComparator());
		System.out.println("compteur:"+compteur);
		compteur++;
		System.out.println("taille liste caseNoeud : "+caseNoeud.size());
		if(caseNoeud.isEmpty())
		{
			System.out.println("rempli");
			return true;
		}
		
	/*	if(sudoku.bloquer())
			return false;
		*/
		
		Case c = caseNoeud.remove(0);
		int i = c.getI();
		int j = c.getJ();
		int currentValue = 0;		
		System.out.println("noeud "+i+","+j);
		System.out.println("val possible"+c.getValeursPossibles());
		ArrayList<Integer> copyValPossible = Heuristiques.leastConstrainingValue(sudoku.getGrille(), c);
		//ArrayList<Integer> copyValPossible = new ArrayList<Integer>(c.getValeursPossibles());
		for(int value : copyValPossible){
			currentValue = value;
			sudoku.putValeur(i, j, value);
			sudoku.basicForwardChecking();
			sudoku.arcConsistency();
			
			
			
			if(backtrack2(sudoku, caseNoeud))
			{
				return true;
			}
			
			sudoku.putValeur(i, j, 0);
			sudoku.addPossibleValue(i, j, currentValue);
			sudoku.basicForwardChecking();
			sudoku.arcConsistency();
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
