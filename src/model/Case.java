package model;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Case {
	private int valeur;
	private ArrayList<Integer> valeursPossibles;
	private int priorityMrv;
	private int priorityDh;
	private int i;	// ligne
	private int j;	// colonne
	
	public Case(int a, int b) {
		this.i = a;
		this.j = b;
		this.valeur = 0;
		this.valeursPossibles = new ArrayList<Integer>();
		for(int k=1; k<=9; k++){
			this.valeursPossibles.add(k);
		}
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
		if(valeur != 0)
			this.valeursPossibles.clear(); //enleve la valeur actuelle des case possible
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
