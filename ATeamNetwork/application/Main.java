package application;

import java.io.FileInputStream;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "CS400 MyFirstJavaFX";

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		// Create a vertical box with Hello labels for each args
		VBox vbox = new VBox();
		for (String arg : args) {
			vbox.getChildren().add(new Label("hello " + arg));
		}

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// the list of strings to be viewed in the ComboBox, first initialized as a
		// list, then converted into an ObservableList
		String[] cbxList = { "Click Me!", "Don't Click Me.", "Really Don't Click Me.", "Actually Please Click Me." };

		// Creates a new ComboBox to be put in the left/center pane of the root
		// BorderPane
		ComboBox cbx = new ComboBox(FXCollections.observableArrayList(cbxList));

		// Creates a FileInputStream to access the image to be placed in the center of
		// the BorderPane.
		FileInputStream input = new FileInputStream(
				"C:\\Users\\front\\Documents\\CompSci\\CS400\\HelloFX\\MyFace.jpg");
		Image myFace = new Image(input);

		// creates an ImageView of my face in the center panel
		ImageView img = new ImageView(myFace);

		// creates a button to be used in the bottom panel with the Label "Done"
		Button doneBtn = new Button("Done");

		// Creates a small grid on the right side of the root BorderPane (A game of tic
		// tac toe)
		GridPane grid = new GridPane();
		// adds components to the fist row
		grid.addRow(1, new Label("X"));
		grid.addRow(1, new Label("O"));
		grid.addRow(1, new Label("X"));
		// adds components to the second row
		grid.addRow(2, new Label("O"));
		grid.addRow(2, new Label("X"));
		grid.addRow(2, new Label("O"));
		// adds components to the third row
		grid.addRow(3, new Label("O"));
		grid.addRow(3, new Label("X"));
		grid.addRow(3, new Label("X"));

		// Add the vertical box to the center of the root pane
		root.setTop(new Label(APP_TITLE));
		root.setCenter(img);
		root.setLeft(cbx);
		root.setBottom(doneBtn);
		root.setRight(grid);
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
