package model;

import java.util.ArrayList;

public class Case {
	private int X;
	private int Y;
	private int value;
	private ArrayList<Integer> valeurPossible;

	public Case(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setValue(0);
		this.valeurPossible = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			this.valeurPossible.add(i+1);
		}
	}
	
	

	/** ============================================== getter et setter ========================================================================*/
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public ArrayList<Integer> getValeurPossible() {
		return this.valeurPossible;
	}
}
