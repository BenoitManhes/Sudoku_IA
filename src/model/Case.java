package model;

import java.util.ArrayList;

public class Case {
	private int valeur;
	private ArrayList<Integer> valeursPossibles;
	private int priorityMrv;
	private int priorityDh;
	
	public Case() {
		this.valeur = 0;
		this.valeursPossibles = new ArrayList<Integer>();
		for(int i=1; i<=9; i++){
			this.valeursPossibles.add(i);
		}
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
		this.valeursPossibles.remove(valeur);
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
	
}
