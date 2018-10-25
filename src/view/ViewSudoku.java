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
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Observable;
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
	private static Sudoku currentSudoku;
	public static ViewSudoku viewSudoku;
	private final int UL = (int) (WIDTH/9.1);

	//=========================================== Creation de la view du Sudoku ====================================================================
	
	public ViewSudoku(){
		
		// Construction de la fenetre
		frame = new JFrame("Sudoku");
		
		// Construction du menu et du panel en fonction
		JMenuBar menu = createMenu();
		frame.setJMenuBar(menu);
		int tailleMenu = menu.getHeight();
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT-tailleMenu));
		panel.setLayout(null);

		// Creation du canvas
		canvas = new Canvas();
		canvas.setBounds(0, tailleMenu+2, WIDTH, HEIGHT-tailleMenu);
		canvas.setIgnoreRepaint(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		// Fenetre not re-sizable
		frame.setResizable(false);
		frame.setVisible(true);
		
		// Ajouter le canvas a la fenetre
		panel.add(canvas);
		panel.isOpaque();
		panel.repaint();
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		
		// Focus du canvas
		canvas.requestFocus();
		canvas.setBackground(java.awt.Color.WHITE);
		this.render();
	}
	
	
	//=========================================== Mise a jour de la view du Sudoku ====================================================================
	
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
						
						// Nous differentions ici la grille d'origine et la nouvelle grille creee
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
		}

	}

	public void update(Observable obs, Object obj) {
		this.grille = (Case[][]) (obj);
		this.render();
	}


	
	//==================================================== Creation du menu =================================================================================

	public static JMenuBar createMenu() {

		JMenuBar menuBar;
		JMenu menuChoixGrille;
		JMenuItem menuItemSolve;

		//Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.repaint();

		// Rechercher dans les fichiers du workspace avec condition de finir par .txt
		String workingDir = System.getProperty("user.dir");
		File rep = new File(workingDir+"/bin/resources");
		File[] fichiersTexte = rep.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		String listeGrilles[] = Arrays.stream(fichiersTexte).map(File::getName).toArray(String[]::new);
		
		// Menu du choix de la grille
		menuChoixGrille = new JMenu("Choisir une grille");
		for(int choix = 0 ; choix<listeGrilles.length ; choix++) {
			JMenuItem choixGrille = new JMenuItem(listeGrilles[choix]);
			File fichierTexte = fichiersTexte[choix];
			
			choixGrille.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setCurrentSudoku(new Sudoku(fichierTexte));
					getCurrentSudoku().addObserver(viewSudoku);
					getCurrentSudoku().actualisationAffichage();
				}
			});
			menuChoixGrille.add(choixGrille);
		}
		menuBar.add(menuChoixGrille);

		// Menu de la resolution du Sudoku
		menuItemSolve = new JMenuItem("Resoudre");
		menuItemSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Grille: "+listeGrilles[0]);
				Backtracking.solve(getCurrentSudoku());
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