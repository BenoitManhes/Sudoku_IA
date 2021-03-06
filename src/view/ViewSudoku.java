package view;
import model.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Arrays;
import java.util.Observable;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ViewSudoku implements java.util.Observer {

	JFrame frame;
	Canvas canvas;

	BufferStrategy bufferStrategy;

	private int WIDTH = 700;
	private int HEIGHT = 700;
	private Case[][] grille = new Case[9][9];
	private Case[][] grilleInitiale = new Case[9][9];
	private static Sudoku currentSudoku;
	public static ViewSudoku viewSudoku;

	private final int UL = (int) (WIDTH/9.1);

	public ViewSudoku(){
		//Makes a new window, with the name " Basic game  ".
		frame = new JFrame("Sudoku");
		//this will create the menu
		JMenuBar menu = createMenu();
		frame.setJMenuBar(menu);
		int tailleMenu = menu.getHeight();
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT-tailleMenu));
		panel.setLayout(null);

		canvas = new Canvas();
		canvas.setBounds(0, tailleMenu+2, WIDTH, HEIGHT-tailleMenu);
		canvas.setIgnoreRepaint(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		//this will make the frame not re-sizable
		frame.setResizable(false);
		frame.setVisible(true);
		//this will add the canvas to our frame
		panel.add(canvas);
		panel.isOpaque();
		panel.repaint();
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		//This will make sure the canvas has focus, so that it can take input from mouse/keyboard
		canvas.requestFocus();
		canvas.setBackground(java.awt.Color.WHITE);
		this.render();
	}
	void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		render(g);
		g.dispose();
		bufferStrategy.show();
	}
	protected void render(Graphics2D g){
		for (int i = 0; i < grille.length+1; i++) {
			int epaisseur = UL/12;
			if(i%3 == 0) epaisseur = UL/6;
			g.fillRect(i*UL, 0, epaisseur, UL*9+epaisseur);
			g.fillRect(0, i*UL, UL*9+epaisseur, epaisseur);
		}

		try {
			for (int i = 0; i < grille.length; i++) {
				for (int j = 0; j < grille.length; j++) {
					if(grille[j][i].getValeur() != 0) {
						if(Sudoku.isInValeursInitiale(grille[j][i])) {
							g.setFont(new Font("Tahoma",Font.BOLD, UL/2));
							g.setColor(Color.BLACK);
						}
						else {
							g.setFont(new Font("Tahoma",Font.PLAIN, UL/2));
							g.setColor(Color.GRAY);
						}
						g.drawString(grille[j][i].getValeur()+"", i*UL+UL/3, j*UL+UL*3/4);
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}

	}


	public void update(Observable obs, Object obj) {
		this.grille = (Case[][]) (obj);
		this.render();
		System.out.println("update");
	}

	/*public void init(Case[][] grilleInit) {
		this.grilleInitiale = new CAs;
	}*/

	public void addController(ActionListener controller){

	}

	public static JMenuBar createMenu() {

		JMenuBar menuBar;
		JMenu menuChoixGrille;
		JMenuItem menuItemSolve;

		//Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.repaint();

		//Rechercher dans les fichiers du workspace avec condition de finir par .txt
		String workingDir = System.getProperty("user.dir");
		File rep = new File(workingDir+"/bin/resources");
		File[] fichiersTexte = rep.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		String listeGrilles[] = Arrays.stream(fichiersTexte).map(File::getName).toArray(String[]::new);

		menuChoixGrille = new JMenu("Choisir une grille");

		for(int choix = 0 ; choix<listeGrilles.length ; choix++) {
			JMenuItem choixGrille = new JMenuItem(listeGrilles[choix]);
			File fichierTexte = fichiersTexte[choix];
			
			choixGrille.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setCurrentSudoku(new Sudoku(fichierTexte));
					getCurrentSudoku().addObserver(viewSudoku);
					getCurrentSudoku().actualize();
				}
			});
			menuChoixGrille.add(choixGrille);
		}
		menuBar.add(menuChoixGrille);

		//Resoudre le Sudoku
		menuItemSolve = new JMenuItem("Solve");
		menuItemSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Backtracking.solve(getCurrentSudoku());
				System.out.println("Resolution de la grille");
			}
		});
		menuBar.add(menuItemSolve);


		return menuBar;
	}
	
	public static Sudoku getCurrentSudoku() {
		return currentSudoku;
	}
	public static void setCurrentSudoku(Sudoku currentNewSudoku) {
		currentSudoku = currentNewSudoku;
	}
	public static ViewSudoku getViewSudoku() {
		return viewSudoku;
	}
	public static void setViewSudoku(ViewSudoku newViewSudoku) {
		viewSudoku = newViewSudoku;
	}


}