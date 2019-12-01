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

public class View extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "Welcome!";

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		// Create a vertical box with Hello labels for each args
		VBox vbox = new VBox();
		for (String arg : args) {
			vbox.getChildren().add(new Label("hello " + arg));
		}

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(getFirstScene());
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Creates the mainSceneBorderPane with functional buttons, etc
	 */
	private static Scene getFirstScene() {

		// Main BorderPane for the login screen
		BorderPane root = new BorderPane();

		// Horizontal box to be but at the top of the application
		HBox top = new HBox();
		top.setPrefHeight(WINDOW_HEIGHT/3);

		// Creates a Button that goes in the top center of the application for Central
		// User Options
		Button ctrlUsrOp = new Button("Central User Options");
		
		//sets the position of the button ctrlUsrOp
		ctrlUsrOp.setTranslateX(WINDOW_WIDTH*3/8);
		ctrlUsrOp.setTranslateY(WINDOW_HEIGHT/12);
		top.getChildren().add(ctrlUsrOp);

		// sets the Hbox "top" to the top of the main BoderPane
		root.setTop(top);

		// creates a Vbox for both the left and right sides of the screen
		VBox left = new VBox();
		VBox right = new VBox();
		left.setPrefHeight(WINDOW_HEIGHT/3);
		left.setPrefWidth(WINDOW_WIDTH/2);
		right.setPrefHeight(WINDOW_HEIGHT/3);
		right.setPrefWidth(WINDOW_WIDTH/2);

		// Buttons to be added to the left side of the scene
		Button uploadNtwrkFile = new Button("Upload Network File");// used to upload network files
		Button addNewUser = new Button("Add New User"); // used to add a new user

		// TextField to be added to the left side of the scene.
		TextField user1 = new TextField("Enter User 1...");

		// Adds all the above nodes to the VBox "left"
		left.getChildren().addAll(uploadNtwrkFile, addNewUser, user1);

		// Buttons to be added to the right side of the scene.
		Button exportNtwrkFile = new Button("Export Network File");
		Button viewNetwork = new Button("View Network");

		// TextField to be added to the right side of the scene. This input and the
		// input from "user1" will have the option of either adding a friendship
		// between the two or removing a friendship between the two (If they exist of
		// course).
		TextField user2 = new TextField("Enter User 2...");

		// adds correct nodes to each vbox.
		right.getChildren().addAll(exportNtwrkFile, viewNetwork, user2);

		// The box to be set as the bottom of the screen.
		HBox bottom = new HBox();
		// Buttons to be added to the HBox "bottom"
		Button addFriend = new Button("Add Friendship");// When functional, this should add an edge between both
														// friends input in the textFields user1 and user2
		Button removeFriend = new Button("Remove Friendship");// ^^ but remove the friendship

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
}
