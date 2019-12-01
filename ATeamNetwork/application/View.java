package application;

import java.io.FileInputStream;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View  {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 600;
	private static String APP_TITLE = "Welcome!";


	/**
	 * Creates the mainSceneBorderPane with functional buttons, etc
	 */
	private static Scene getFirstScene() {

		// Main BorderPane for the login screen
		BorderPane root = new BorderPane();

		// Horizontal box to be but at the top of the application
		HBox top = new HBox();
		top.setPrefHeight(WINDOW_HEIGHT / 4);

		// Creates a Button that goes in the top center of the application for Central
		// User Options
		Button ctrlUsrOp = new Button("Central User Options");

		// sets the position of the button ctrlUsrOp
		ctrlUsrOp.setTranslateX(WINDOW_WIDTH*5/12);
		ctrlUsrOp.setTranslateY(WINDOW_HEIGHT / 12);
		top.getChildren().add(ctrlUsrOp);

		// sets the Hbox "top" to the top of the main BoderPane
		root.setTop(top);

		// creates a Vbox for both the left and right sides of the screen
		VBox left = new VBox();
		VBox right = new VBox();
		left.setPrefHeight(WINDOW_HEIGHT / 2);
		left.setPrefWidth(WINDOW_WIDTH / 2);
		right.setPrefHeight(WINDOW_HEIGHT / 2);
		right.setPrefWidth(WINDOW_WIDTH / 2);

		// Buttons to be added to the left side of the scene
		Button uploadNtwrkFile = new Button("Upload Network File");// used to upload network files
		Button addNewUser = new Button("Add New User"); // used to add a new user

		// TextField to be added to the left side of the scene.
		TextField user1 = new TextField("Enter User 1...");

		// Adds all the above nodes to the VBox "left" and also positions them to their
		// correct areas on the screen
		//Translates the uploadNtwrkFile button to its position in the screen
		uploadNtwrkFile.setTranslateX(WINDOW_WIDTH/5);
		
		//Translates the addNewUser button to its correct position on the screen
		addNewUser.setTranslateX(WINDOW_WIDTH/5);
		addNewUser.setTranslateY(WINDOW_HEIGHT/10);
		
		//Translates the user1 TextField to its correct position on the screen
		user1.setTranslateY(WINDOW_HEIGHT/4);
		left.getChildren().addAll(uploadNtwrkFile, addNewUser, user1);
		
		

		// Buttons to be added to the right side of the scene.
		Button exportNtwrkFile = new Button("Export Network File");
		Button viewNetwork = new Button("View Network");

		// TextField to be added to the right side of the scene. This input and the
		// input from "user1" will have the option of either adding a friendship
		// between the two or removing a friendship between the two (If they exist of
		// course).
		TextField user2 = new TextField("Enter User 2...");
		
		
		
		//Positions the above buttons to their correct positions on the screen
		
		//Positions the exportNtwrkFile button
		exportNtwrkFile.setTranslateX(WINDOW_WIDTH/8);
		
		//Positons the viewNetwork button
		viewNetwork.setTranslateX(WINDOW_WIDTH/8);
		viewNetwork.setTranslateY(WINDOW_HEIGHT/10);
		
		//positions the user2 TextField to its correct position
		user2.setTranslateY(WINDOW_HEIGHT/4);
		
		// adds correct nodes to each vbox.
		right.getChildren().addAll(exportNtwrkFile, viewNetwork, user2);
		
		

		// The box to be set as the bottom of the screen.
		VBox bottom = new VBox();
		bottom.setPrefHeight(WINDOW_HEIGHT/4);
		bottom.setSpacing(30);
		
		// Buttons to be added to the HBox "bottom"
		Button addFriend = new Button("Add Friendship");// When functional, this should add an edge between both
														// friends input in the textFields user1 and user2
		Button removeFriend = new Button("Remove Friendship");// ^^ but remove the friendship

		//Positions the bottom buttons correctly onto the scene
		
		//Sets the addFriend button to the center of the screen
		addFriend.setTranslateX(WINDOW_WIDTH*5/12);
		
		//sets the removeFriend button to the center of the screen but below the addFriend button
		removeFriend.setTranslateX(WINDOW_WIDTH*5/12);
		//removeFriend.setTranslateY(WINDOW_HEIGHT/4);
		
		// adds the buttons to the HBox bottom.
		bottom.getChildren().addAll(addFriend, removeFriend);

		// Aligns all the boxes to their specified regions in the Main BorderPane
		// "root".
		root.setTop(top);
		root.setLeft(left);
		root.setRight(right);
		root.setBottom(bottom);

		return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	static Scene centralUserOptions() {
	  
	  RunApplication.APP_TITLE = "Welcome to Central User Options!";
	  
	  BorderPane root = new BorderPane();
	  
	  Button newCntrlUsr = new Button ("Add this central user");
	  
	  TextField txtFld = new TextField("  Enter the User you'd like to make the Central User");
	    
	  Button display = new Button("Display Network from the View of the Central User");
	  
	  HBox top = new HBox();
	  top.setPrefHeight(WINDOW_HEIGHT/2);
	  
	  txtFld.setTranslateX(WINDOW_WIDTH*3/8 -75);
	  txtFld.setTranslateY(WINDOW_HEIGHT/4 );
	  txtFld.setPrefWidth(WINDOW_WIDTH/2);
	  newCntrlUsr.setTranslateX(WINDOW_WIDTH*3/8 -300);
	  newCntrlUsr.setTranslateY(WINDOW_HEIGHT/4 + 45);
	  
	  top.getChildren().addAll(txtFld, newCntrlUsr);
	  
	  
	  root.setTop(top);
	  
	 HBox bottom = new HBox();
	 
	 bottom.setPrefHeight(WINDOW_HEIGHT/2);
	 
	 display.setTranslateX(WINDOW_WIDTH/2-140);
	 
	 
	 bottom.getChildren().add(display);
	 root.setBottom(bottom);
	  
	  return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
	}


	
}
