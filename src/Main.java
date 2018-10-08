import java.io.File;
import model.Backtracking;
import model.Sudoku;

public class Main {

	public static void main(String[] args) {
		File fichier = new File("blabla.txt");
		Sudoku sudoku = new Sudoku(fichier);
		Backtracking.solve(sudoku);

	}

}
