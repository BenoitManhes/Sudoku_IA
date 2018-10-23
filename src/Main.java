import java.io.File;
import java.io.IOException;
import java.net.URL;

import model.*;
import view.*;

public class Main {

	public static void main(String[] args) throws IOException {
		//URL url = Main.class.getResource("C:\\Users\\Benoît\\workspace\\Sudoku_IA\\src\\resources\\exemple1.txt");
		File fichier = new File("C:\\Users\\Benoît\\workspace\\Sudoku_IA\\src\\resources\\exemple1.txt");
		Sudoku sudoku = new Sudoku(fichier);
		ViewSudoku viewSudoku = new ViewSudoku();
		sudoku.addObserver(viewSudoku);

		sudoku.actualize();
		Backtracking.solve(sudoku);

	}

}
