package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// TODO: add return buttons to all sub-scenes

public class RunApplication extends Application {
  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  private List<String> args;

  private static final int WINDOW_WIDTH = 600;
  private static final int WINDOW_HEIGHT = 600;
  static String APP_TITLE = "Welcome!";
  static Stage primaryStage;
  static Controller controller = new Controller();

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    primaryStage.setTitle(APP_TITLE);
    
    List<String> names = new ArrayList<String>();
  //  names.add("ADAMN");
    primaryStage.setScene(RunApplication.displayMutualFriends(null, "1", "2"));
    primaryStage.show();
  }

  static Scene firstScene() {
    APP_TITLE = "Welcome to the Social Network";
    
    primaryStage.setTitle(APP_TITLE);
    BorderPane root = new BorderPane();

    // Horizontal box to be but at the top of the application
    HBox top = new HBox();
    top.setPrefHeight(WINDOW_HEIGHT / 4);

    // Creates a Button that goes in the top center of the application for Central
    // User Options
    Button ctrlUsrOp = new Button("Central User Options");
    ctrlUsrOp.setOnAction(e -> primaryStage.setScene(RunApplication.centralUserOptions()));

    // sets the position of the button ctrlUsrOp
    ctrlUsrOp.setTranslateX(WINDOW_WIDTH * 5 / 12);
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

    // TextField to be added to the left side of the scene.
    TextField user1 = new TextField("Enter User 1...");

    // Buttons on the left
    Button uploadNtwrkFile = new Button("Upload Network File");
    uploadNtwrkFile.setOnAction(e -> primaryStage.setScene(RunApplication.uploadNetworkFile()));
    Button addNewUser = new Button("Add New User");
    addNewUser.setOnAction(e -> primaryStage.setScene(RunApplication.addUser()));

    // Adds all the above nodes to the VBox "left" and also positions them to their
    // correct areas on the screen

    // Translates the uploadNtwrkFile button to its position in the screen
    uploadNtwrkFile.setTranslateX(WINDOW_WIDTH / 5);

    // Translates the addNewUser button to its correct position on the screen
    addNewUser.setTranslateX(WINDOW_WIDTH / 5);
    addNewUser.setTranslateY(WINDOW_HEIGHT / 10);

    // Translates the user1 TextField to its correct position on the screen
    user1.setTranslateY(WINDOW_HEIGHT / 4);
    left.getChildren().addAll(uploadNtwrkFile, addNewUser, user1);

    // Buttons to be added to the right side of the scene.

    Button exportNtwrkFile = new Button("Export Network File");
    exportNtwrkFile.setOnAction(e -> primaryStage.setScene(RunApplication.ExportFile()));
    Button viewNetwork = new Button("View Network");
    viewNetwork.setOnAction(e -> primaryStage.setScene(RunApplication.centralUserOptions()));

    // TextField to be added to the right side of the scene. This input and the
    // input from "user1" will have the option of either adding a friendship
    // between the two or removing a friendship between the two (If they exist of
    // course).
    TextField user2 = new TextField("Enter User 2...");

    // Positions the above buttons to their correct positions on the screen
    exportNtwrkFile.setTranslateX(WINDOW_WIDTH / 8); // Positions the exportNtwrkFile button
    viewNetwork.setTranslateX(WINDOW_WIDTH / 8);
    viewNetwork.setTranslateY(WINDOW_HEIGHT / 10);// Positons the viewNetwork button
    user2.setTranslateY(WINDOW_HEIGHT / 4);// positions the user2 TextField to its correct position

    // adds nodes to each vbox.
    right.getChildren().addAll(exportNtwrkFile, viewNetwork, user2);

    // The box to be set as the bottom of the screen.
    VBox bottom = new VBox();
    bottom.setPrefHeight(WINDOW_HEIGHT / 4);
    bottom.setSpacing(30);

    // Buttons to be added to the HBox "bottom"
    Button addFriend = new Button("Add Friendship");
    addFriend.setOnAction(e -> {
      String u1 = user1.getText();
      String u2 = user2.getText();
      controller.addFriend(u1, u2);
    });
    Button removeFriend = new Button("Remove Friendship");
    removeFriend.setOnAction(e -> {
      String u1 = user1.getText();
      String u2 = user2.getText();
      controller.removeFriend(u1, u2);
    });

    // Positions the bottom buttons correctly onto the scene
    addFriend.setTranslateX(WINDOW_WIDTH * 5 / 12);// Sets the addFriend button to the center of the
                                                   // screen
    removeFriend.setTranslateX(WINDOW_WIDTH * 5 / 12); // sets the removeFriend button below the
                                                       // addFriend button

    // adds the buttons to the HBox bottom.
    bottom.getChildren().addAll(addFriend, removeFriend);

    // Aligns all the boxes to their specified regions in the Main BorderPane
    root.setTop(top);
    root.setLeft(left);
    root.setRight(right);
    root.setBottom(bottom);

    // Set the main scene and show it on the window
    Scene scene1 = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    return scene1;
  }

  /**
   * The scene that is accessed when the button "Central User Options" is pressed.
   * 
   * @return Scene - The scene for Central User Options
   */
  static Scene centralUserOptions() {

    APP_TITLE = "Welcome to Central User Options!";
    primaryStage.setTitle(APP_TITLE);
    BorderPane root = new BorderPane();

    TextField txtFld = new TextField("  Enter the User you'd like to make the Central User");

    Button newCntrlUsr = new Button("Add this central user");
    newCntrlUsr.setOnAction(e -> {
      String user = txtFld.getText();
      controller.setCentralUser(user);
    });

    Button display = new Button("Display Network");
    display.setOnAction(e -> {
      String user = txtFld.getText();
      controller.printCtrlNetwork(user);
    });

    HBox top = new HBox();
    top.setPrefHeight(WINDOW_HEIGHT / 2);

    txtFld.setTranslateX(WINDOW_WIDTH * 3 / 8 - 75);
    txtFld.setTranslateY(WINDOW_HEIGHT / 4);
    txtFld.setPrefWidth(WINDOW_WIDTH / 2);
    newCntrlUsr.setTranslateX(WINDOW_WIDTH * 3 / 8 - 300);
    newCntrlUsr.setTranslateY(WINDOW_HEIGHT / 4 + 45);
    display.setTranslateX(WINDOW_WIDTH * 3 / 8 - 416);
    display.setTranslateY(WINDOW_HEIGHT / 4 + 80);

    top.getChildren().addAll(txtFld, newCntrlUsr, display);
    root.setTop(top);

    HBox bottom = new HBox();
    bottom.setPrefHeight(WINDOW_HEIGHT / 2);

    Button back = new Button("Back");
    back.setTranslateX(WINDOW_WIDTH / 2);
    back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));

    root.setBottom(back);

    return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
  }

  static Scene ExportFile() {

    APP_TITLE = "Welcome to Export File!";
    primaryStage.setTitle(APP_TITLE);
    Button exp = new Button("Export to File");
    exp.setOnAction(e -> controller.exportFile());

    TextField txt = new TextField("Enter the exact path of the file you'd like to export to");

    BorderPane root = new BorderPane();

    HBox h = new HBox();
    h.setPrefHeight(WINDOW_HEIGHT / 2);

    txt.setPrefWidth(450);
    txt.setTranslateX(WINDOW_WIDTH / 2 - 225);
    txt.setTranslateY(WINDOW_HEIGHT / 2 - 30);

    exp.setTranslateY(WINDOW_HEIGHT / 2);
    exp.setTranslateX(WINDOW_WIDTH / 2 - 475);

    h.getChildren().addAll(txt, exp);

    root.setTop(h);

    Button back = new Button("Back");
    back.setTranslateX(WINDOW_WIDTH / 2);
    back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));

    root.setBottom(back);

    return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
  }

  /**
   * The Scene that is shown when the uploadNetworkFile button is pressed
   * 
   * @return
   */
  static Scene uploadNetworkFile() {
    // Sets the title of the scene to a new string
    APP_TITLE = "Upload Network File";
    primaryStage.setTitle(APP_TITLE);
    // Creates a root BorderPane object to be used as the background of the scene
    BorderPane root = new BorderPane();

    // creates a spacing that centers the center Vbox Pane
    HBox space = new HBox();
    space.setPrefHeight(WINDOW_HEIGHT / 4);

    // creates a new VBox that will be used to stack the nodes of the Scene
    VBox box = new VBox();
    root.setCenter(box);
    root.setTop(space);

    // Sets the spacing of nodes in the VBox
    box.setSpacing(WINDOW_HEIGHT / 8);
    box.setPrefHeight(WINDOW_HEIGHT / 2);

    // Creates a new label explaining the use of the textField and what needs to be
    // put in the textfield in order to upload a file correctly.
    Label instruc = new Label("Please type in the address of the network file (.JSON) to upload.");
    instruc.setTranslateX(WINDOW_WIDTH / 4);

    // Creates a new TextField to be used input the address of the network file.
    TextField address = new TextField("Insert Adress Here...");
    address.setMaxWidth(WINDOW_WIDTH / 3);
    address.setTranslateX(WINDOW_WIDTH / 3);

    // creates the button to finish the task of uploading the network file.
    Button upload = new Button("Upload File");
    upload.setOnAction(e -> controller.importFile("null", primaryStage));
    upload.setTranslateX(WINDOW_WIDTH * 3 / 8);

    box.getChildren().addAll(instruc, address, upload);

    Button back = new Button("Back");
    back.setTranslateX(WINDOW_WIDTH / 2);
    back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));
    root.setBottom(back);

    return new Scene(root, WINDOW_HEIGHT, WINDOW_WIDTH);
  }

  static Scene addUser() {

    APP_TITLE = "Welcome to Adding New Users!";
    primaryStage.setTitle(APP_TITLE);
    TextField txt = new TextField("Enter the name of the User you'd like to Add");
    Label label = new Label(
        "The names may contain any letters {A-Z}, space, digits {0-9}, underscore {_}, or apostrophe {'}");

    BorderPane root = new BorderPane();

    Button done = new Button("ADD");
    done.setOnAction(e -> {
      String user = txt.getText();
      controller.addUser(user);
    });

    Button back = new Button("Back");
    back.setTranslateX(WINDOW_WIDTH / 2);
    back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));

    HBox h = new HBox();
    VBox V = new VBox();

    h.setPrefHeight(WINDOW_HEIGHT / 4);
    h.setPrefWidth(WINDOW_WIDTH);
    V.setPrefWidth(WINDOW_WIDTH / 3);
    V.setPrefHeight(WINDOW_HEIGHT / 4);

    label.setPrefWidth(WINDOW_WIDTH);
    label.setTranslateY(WINDOW_HEIGHT / 6);
    label.setTranslateX(0);

    txt.setPrefWidth(WINDOW_WIDTH / 8);
    txt.setTranslateX(0);
    txt.setTranslateY(WINDOW_HEIGHT / 8);

    done.setTranslateY(WINDOW_HEIGHT / 8);
    done.setTranslateX(WINDOW_WIDTH / 2);

    h.getChildren().addAll(label);
    V.getChildren().addAll(txt, done);

    root.setTop(h);
    root.setCenter(V);
    root.setBottom(back);

    return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

  }

  static Scene Network(List<String> friends) {
    String User = controller.getCentralUser(); // current central user
    APP_TITLE = "Welcome to Friend Network of " + User;

    primaryStage.setTitle(APP_TITLE);
    BorderPane root = new BorderPane();
    ListView<String> network = new ListView<>();
    ObservableList<String> items = FXCollections.observableArrayList(friends);
    network.setItems(items);
    network.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        String user = (String) network.getSelectionModel().getSelectedItem(); // selected user
        if (event.getButton() == MouseButton.PRIMARY) { // left click
          if (user.compareTo(controller.getCentralUser()) != 0) {
            controller.setCentralUser(user);
            controller.printCtrlNetwork(user);
          }
        } else if (event.getButton() == MouseButton.SECONDARY) { // right click
          controller.removeFriend(User, user);
          controller.printCtrlNetwork(User);
        }

      }

    });
    root.setTop(network);

    Button back = new Button("Back");
    back.setOnAction(e -> primaryStage.setScene(centralUserOptions()));
    back.setTranslateX(WINDOW_WIDTH / 2);
    root.setBottom(back);

    return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
  }

  static Scene displayMutualFriends(List<String> mutualFriends, String user1, String user2) {

    APP_TITLE = "Mutual Friends between " + user1 + " and " + user2 + ".";
  
    Button back = new Button("Back");
    back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));
    
    if (mutualFriends == null) {
      return errorMessage(RunApplication.firstScene(), "Error one of the users entered do not exist");
    }
    
    if(mutualFriends.size() == 0) {
      primaryStage.setTitle(APP_TITLE);
      BorderPane root = new BorderPane();
      HBox box = new HBox();
      Label message = new Label("Oops! Doesn't look like "+ user1+" and " +user2+ " have any mutual friends");
      message.setTranslateX(WINDOW_WIDTH/4);
      message.setTranslateY(WINDOW_HEIGHT/2);
      box.getChildren().add(message);
      root.setCenter(box);
      
      HBox box2 = new HBox();
      box2.setTranslateX(WINDOW_WIDTH*4/9);
      box2.getChildren().add(back);
      root.setBottom(box2);
    
      return new Scene(root, WINDOW_WIDTH , WINDOW_HEIGHT);
    }
    
    primaryStage.setTitle(APP_TITLE);
    
    BorderPane root = new BorderPane();
    
    ObservableList<String> friends = FXCollections.observableArrayList(mutualFriends);
    
    ListView<String> listView = new ListView<String>(friends);
    
    listView.setItems(friends);
    
    root.setCenter(listView);
    
    root.setBottom(back);
    
    
    return new Scene(root, WINDOW_WIDTH*2/3, WINDOW_HEIGHT);

  }

  static Scene errorMessage(Scene currScene, String message) {
    APP_TITLE = "ERROR!";
    primaryStage.setTitle(APP_TITLE);
    Button back = new Button("Back");
    back.setOnAction(e -> primaryStage.setScene(currScene));
    BorderPane root = new BorderPane();
    HBox box = new HBox();
    Label errorMessage = new Label(message);
    box.getChildren().add(errorMessage);
    root.setCenter(box);
    root.setBottom(back);
    back.setTranslateX(WINDOW_WIDTH/2 );
    errorMessage.setTranslateY(WINDOW_HEIGHT/2 );
    errorMessage.setTranslateX(WINDOW_WIDTH*3/10);

    return new Scene(root, WINDOW_WIDTH , WINDOW_HEIGHT);

  }

}
