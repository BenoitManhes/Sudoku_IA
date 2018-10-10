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
		for(int k=1; k<=9; k++){
			this.valeursPossibles.add(k);
		}
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
		this.valeursPossibles.remove(Integer.valueOf(valeur)); //enleve la valeur actuelle des case possible
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

}
