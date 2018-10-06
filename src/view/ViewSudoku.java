package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene; 
import javafx.scene.layout.Pane; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.Rectangle; 
import javafx.stage.Stage; 

import java.awt.event.ActionListener;
import java.util.Observable;

public class ViewSudoku extends Application implements java.util.Observer {

	public static void main(String[] args) { 
		launch(ViewSudoku.class, args); 
	} 
  
	private final int HEIGHT = 600;
	private final int WIDTH = 600;
	
	@Override 
	public void start(Stage primaryStage) { 
		Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
        primaryStage.setScene(scene);
        
        primaryStage.show();
        
	} 
	
	public void update(Observable obs, Object obj) {
    	// mise a jour de view
    }

	public void addController(ActionListener controller){
		// ajout d action listener si besoin (bouton)
	}
}
