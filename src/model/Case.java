package model;
import java.util.ArrayList;

public class Case {
	private int valeur;
	private ArrayList<Integer> valeursPossibles;
	private int priorityMrv; // Valeur caracteristique pour Minimal Remaining Value
	private int priorityDh; // Valeur caracteristique pour Degre Heuristic
	private int i;	// ligne
	private int j;	// colonne

	public Case(int a, int b) {
		this.i = a;
		this.j = b;
		this.valeur = 0;
		this.valeursPossibles = new ArrayList<Integer>();
		for(int k=1; k<=9; k++){
			//De base, toutes les valeurs peuvent etre assignees a une case vide
			this.valeursPossibles.add(k);
		}
	}

	//Mutateurs et accesseurs :

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
		if(valeur != 0)
			// Si une valeur a ete assignee, on vide la liste des valeurs possibles
			this.valeursPossibles.clear(); 
	}

	public int getPriorityMrv() {
		return priorityMrv;
	}

	public void setPriorityMrv(int priorityMrv) {
		this.priorityMrv = priorityMrv;
	}

	public int getPriorityDh() {
		return priorityDh;
	}

	public void setPriorityDh(int priorityDh) {
		this.priorityDh = priorityDh;
	}

	public ArrayList<Integer> getValeursPossibles(){
		return valeursPossibles;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

}
