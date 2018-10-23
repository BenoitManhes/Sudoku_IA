package model;

import java.util.Comparator;

public class CaseComparator implements Comparator<Case>{

	@Override
	public int compare(Case A, Case B) {
		if(A.getPriorityDh() == B.getPriorityDh()){
			if(A.getPriorityMrv() >= B.getPriorityMrv()){
				return 1;
			}
			else{
				return -1;
			}
		}
		else if(A.getPriorityDh() > B.getPriorityDh()){
			return 1;
		}
		else{
			return -1;
		}
	}

}
