import java.io.File;
import java.io.IOException;
import java.net.URL;

import model.*;
import view.*;

public class Main {

	public static void main(String[] args) throws IOException {
		URL url = Main.class.getResource("/resources/exemple3.txt");
		File fichier = new File(url.toString().substring(5));
		Sudoku sudoku = new Sudoku(fichier);
		sudoku.addObserver(ViewSudoku.viewSudoku);
		sudoku.actualize();
	}

}
