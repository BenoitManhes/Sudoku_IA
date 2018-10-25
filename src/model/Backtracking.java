package model;
import java.util.ArrayList;
import java.util.Collections;

public class Backtracking {
	
	public static void solve(Sudoku sudoku){

		sudoku.actualisationAffichage(); //Actualisation de l'affichage de la grille

		sudoku.elagageInitial(); //Assignation des valeurs déja determinees (voir description dans classe Sudoku)
		sudoku.basicForwardChecking(); //Supression des valeurs possibles des cases en fonction des cases assignes

		//Appel de la fonction de retour sur trace :
		System.out.println("Resolution en cours...");
		backtrack(sudoku, sudoku.getListeCasesAtraiter());

	}

	public static boolean backtrack(Sudoku sudoku, ArrayList<Case> caseAtraiter){
		
		//On actualise les valeurs caracteristique des heuristiques de chaque case:
		Heuristiques.updateHeuristiques(sudoku);  
		
		//on trie la liste des valeur a traiter en fonction des valeurs caracteristiques precedemment calculees
		Collections.sort(caseAtraiter, new CaseComparator()); 
		
		//Si il n'y a plus de case a traiter, le sudoku est complete
		if(caseAtraiter.isEmpty())
		{
			System.out.println("Sudoku resolu!\n----------");
			return true;
		}

		Case c = caseAtraiter.remove(0); //on prend la premiere case de la liste a traiter
		int i = c.getI();
		int j = c.getJ();	
		
		ArrayList<Integer> valeursPossiblesOrdonnee = Heuristiques.leastConstrainingValue(sudoku.getGrille(), c);
		//On stocke dans une liste les valeurs possible pour la case selectionnee en fonction de l'heuristique LCV
		
		for(int value : valeursPossiblesOrdonnee){
			//Test de l'Arc Consitency:
			if(!sudoku.arcConsistency(value, i, j)) {
				continue;
			}
			//la valeur est admissible selon l'AC : 
			//on assigne la valeur puis on actualise les variable legales restantes
			sudoku.addValueInGrid(i, j, value);
			sudoku.basicForwardChecking();

			//Recursivite
			if(backtrack(sudoku, caseAtraiter))
			{
				return true;
			}
			//Le precedent appel renvoie false : on supprime la valeur de la case
			sudoku.addValueInGrid(i, j, 0);
			//On rajoute les variables legales restantes precedement supprimees:
			sudoku.addPossibleValue(i, j, value);
			sudoku.basicForwardChecking();
			//puis on essaye avec la valeur suivante
		}
		//Aucune valeur ne correspond pour ladite case :
		//On reinitialise la liste des valeurs possibles de la case
		c.getValeursPossibles().clear();
		c.getValeursPossibles().addAll(valeursPossiblesOrdonnee);
		//puis on la rajoute a la liste des cases pour la traiter ulterieurement
		caseAtraiter.add(c);
		return false;
	}
}
