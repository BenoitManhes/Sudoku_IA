package view;

import java.awt.event.ActionListener;
import java.util.Observable;

public class ViewSudoku implements java.util.Observer {
	
	// attribut

	ViewSudoku() {
		// Construction de la Frame, utiliser java FX
	}
	
	public void update(Observable obs, Object obj) {
    	// mise a jour de view
    }

	public void addController(ActionListener controller){
		// ajout d action listener si besoin (bouton)
	}
}
