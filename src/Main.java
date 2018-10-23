import java.io.File;
import model.Backtracking;
import model.Sudoku;
import view.ViewSudoku;

public class Main {

	public static void main(String[] args) {
		File fichier = new File("exemple1.txt");
		Sudoku sudoku = new Sudoku(fichier);
		ViewSudoku viewSudoku = new ViewSudoku();
		sudoku.addObserver(viewSudoku);
		
		sudoku.actualize();
		//Backtracking.solve(sudoku);

	}

}
