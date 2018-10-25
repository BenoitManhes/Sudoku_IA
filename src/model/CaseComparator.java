package model;
import java.util.Comparator;

public class CaseComparator implements Comparator<Case>{

	//Ce comparateur permet de classer les cases dans la liste selon 
	//les valeurs caracteristiques des heuristiques perecedemment calculees
	
	@Override
	public int compare(Case A, Case B) {
		if(A.getPriorityMrv() == B.getPriorityMrv()){ 
			//En cas d'egalite de la MRV, on classe selon le DH
			if(A.getPriorityDh() >= B.getPriorityDh()){
				return 1;
			}
			else{
				return -1;
			}
		}
		else if(A.getPriorityMrv() > B.getPriorityMrv()){
			return 1;
		}
		else{
			return -1;
		}
	}
}