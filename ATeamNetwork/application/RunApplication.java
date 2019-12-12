package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RunApplication extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args;

    /** instance fields */
    //window width and height
    private static int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 700;
    //First scene app title
    static String APP_TITLE = "Welcome!";
    static Stage primaryStage; //the primare stage
    static Controller controller = new Controller();//the main controller

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Application.setUserAgentStylesheet(getClass().getResource("stylesheet.css").toExternalForm());
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(RunApplication.firstScene());
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

        Button displayAll = new Button("Display All Users");

        displayAll.setOnAction(e -> primaryStage.setScene(RunApplication.displayAllUsers()));
        ctrlUsrOp.setOnAction(e -> primaryStage.setScene(RunApplication.centralUserOptions()));

        // sets the position of the button ctrlUsrOp
        ctrlUsrOp.setTranslateX(WINDOW_WIDTH / 8);
        ctrlUsrOp.setTranslateY(WINDOW_HEIGHT / 12);
        displayAll.setTranslateX(WINDOW_WIDTH / 5);
        displayAll.setTranslateY(WINDOW_HEIGHT / 12);
        top.getChildren().addAll(ctrlUsrOp, displayAll);

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
        Button addNewUser = new Button("Add/Remove User");
        addNewUser.setOnAction(e -> primaryStage.setScene(RunApplication.addUser()));

        // Adds all the above nodes to the VBox "left" and also positions them to their
        // correct areas on the screen

        // Translates the uploadNtwrkFile button to its position in the screen
        uploadNtwrkFile.setTranslateX(WINDOW_WIDTH / 8);

        // Translates the addNewUser button to its correct position on the screen
        addNewUser.setTranslateX(WINDOW_WIDTH / 8);
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
        exportNtwrkFile.setTranslateX(WINDOW_WIDTH / 8 - 42); // Positions the exportNtwrkFile button
        viewNetwork.setTranslateX(WINDOW_WIDTH / 8 - 42);
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
            if (checktestBoxes(u1, u2))
                if (controller.addFriend(u1, u2))
                    primaryStage.setScene(successMessage(RunApplication.firstScene(),
                            "Successfully added friendship between " + u1 + " and " + u2));
                else
                    primaryStage.setScene(errorMessage(RunApplication.firstScene(),
                            "Error! Couldn't add friendship between " + u1 + " and " + u2));
            else
                errorMessage(RunApplication.firstScene(), "Error! Something must be inserted in both the text boxes");
        });
        Button removeFriend = new Button("Remove Friendship");
        removeFriend.setOnAction(e -> {
            String u1 = user1.getText();
            String u2 = user2.getText();
            if (checktestBoxes(u1, u2))
                if (controller.removeFriend(u1, u2))
                    primaryStage.setScene(successMessage(RunApplication.firstScene(),
                            "Successfully removed friendship between " + u1 + " and " + u2));
                else
                    primaryStage.setScene(errorMessage(RunApplication.firstScene(),
                            "Error! Couldn't  remove friendship between " + u1 + " and " + u2));
            else
                primaryStage.setScene(errorMessage(RunApplication.firstScene(),
                        "Error! Something must be inserted in both the text boxes"));
        });

        Button mutualButton = new Button("Get Mutual Friends");
        mutualButton.setOnAction(e -> {
            String u1 = user1.getText();
            String u2 = user2.getText();
            if (checktestBoxes(u1, u2))
                primaryStage.setScene(displayMutualFriends(controller.getMutualFriends(u1, u2), u1, u2));
            else
                primaryStage.setScene(errorMessage(RunApplication.firstScene(),
                        "Error! Something must be inserted in both the text boxes"));
        });

        Button clearNetwork = new Button("Clear Network");
        clearNetwork.setOnAction(e -> {
            controller = new Controller();
            primaryStage.setScene(successMessage(firstScene(), "SUCCESS: Network Cleared!"));
        });

        // Positions the bottom buttons correctly onto the scene
        addFriend.setTranslateX(WINDOW_WIDTH / 3);// Sets the addFriend button to the center of the
                                                    // screen
        removeFriend.setTranslateX(WINDOW_WIDTH / 3); // sets the removeFriend button below the
                                                        // addFriend button
        mutualButton.setTranslateX(WINDOW_WIDTH / 3);

        clearNetwork.setTranslateX(WINDOW_WIDTH / 3);
        // adds the buttons to the HBox bottom.
        bottom.getChildren().addAll(addFriend, removeFriend, mutualButton, clearNetwork);

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

        TextField txtFld = new TextField(" Enter the User you'd like to make the Central User");

        ObservableList<String> users = FXCollections.observableArrayList(controller.userNetwork.getAllVertices());

        if (users.size() == 0) {
            return errorMessage(centralUserOptions(), "ERROR: The Social Network doesn't have any users. Please add users to contiue");
        }

        users.remove(controller.getCentralUser());

        if (users.size() == 0) {
            return errorMessage(centralUserOptions(), "ERROR: The Social Network has only one user which is the Central User");
        }

        Button newCntrlUsr = new Button("Add Central User");
        newCntrlUsr.setOnAction(e -> {
            String user = txtFld.getText();
            controller.setCentralUser(user);
            if(controller.userNetwork.addVertex(user)) {
                primaryStage.setScene(errorMessage(centralUserOptions(), "ERROR: input user does not exist."));
                controller.userNetwork.removeVertex(user);
            }else {
            primaryStage.setScene(
                    successMessage(centralUserOptions(), "SUCCESS: " + user + " was set as the central user."));
            }
        });

        Button display = new Button("Display Network");
        display.setOnAction(e -> {
            if (controller.getCentralUser() != null) {
                controller.printCtrlNetwork(controller.getCentralUser());
            } else {
                primaryStage.setScene(
                        errorMessage(centralUserOptions(), "ERROR: There is no central user to display from."));
            }
        });

        HBox top = new HBox();
        top.setPrefHeight(WINDOW_HEIGHT / 2);

        txtFld.setTranslateX(WINDOW_WIDTH * 3 / 8 - 75);
        txtFld.setTranslateY(WINDOW_HEIGHT / 4);
        txtFld.setPrefWidth(WINDOW_WIDTH / 2);
        newCntrlUsr.setTranslateX(WINDOW_WIDTH / 8 - 140);
        newCntrlUsr.setTranslateY(WINDOW_HEIGHT / 4 + 45);
        display.setTranslateX(WINDOW_WIDTH / 8 - 310);
        display.setTranslateY(WINDOW_HEIGHT / 4 + 80);

        top.getChildren().addAll(txtFld, newCntrlUsr, display);
        root.setTop(top);

        HBox bottom = new HBox();
        bottom.setPrefHeight(WINDOW_HEIGHT / 2);

        Button back = new Button("Back");
        back.setTranslateX(WINDOW_WIDTH / 3);
        back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));

        root.setBottom(back);

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    static Scene ExportFile() {

        APP_TITLE = "Welcome to Export File!";
        primaryStage.setTitle(APP_TITLE);
        Button exp = new Button("Export to File");

        TextField txt = new TextField(
                "Enter the exact path of the file you'd like to export to (Please insert a valid location for Windows Users).");

        BorderPane root = new BorderPane();

        HBox h = new HBox();
        h.setPrefHeight(WINDOW_HEIGHT / 2);

        txt.setPrefWidth(450);
        txt.setTranslateX(WINDOW_WIDTH / 2 - 225);
        txt.setTranslateY(WINDOW_HEIGHT / 2 - 30);

        exp.setTranslateY(WINDOW_HEIGHT / 2);
        exp.setTranslateX(WINDOW_WIDTH / 8 - 300);

        h.getChildren().addAll(txt, exp);

        root.setTop(h);

        exp.setOnAction(e -> controller.exportFile(txt.getText()));

        Button back = new Button("Back");
        back.setTranslateX(WINDOW_WIDTH / 3 - 20);
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
        Label instruc = new Label("Please type in the address of the network file to upload.");
        instruc.setTranslateX(WINDOW_WIDTH / 4);

        // Creates a new TextField to be used input the address of the network file.
        TextField address = new TextField("Insert Address Here...");
        address.setMaxWidth(WINDOW_WIDTH / 3);
        address.setTranslateX(WINDOW_WIDTH / 3);

        // creates the button to finish the task of uploading the network file.
        Button upload = new Button("Upload File");
        upload.setOnAction(e -> controller.importFile(address.getText(), primaryStage));
        upload.setTranslateX(WINDOW_WIDTH * 3 / 8 - 30);

        box.getChildren().addAll(instruc, address, upload);

        // creates a button that allows the user to return to the first scene
        Button back = new Button("Back");
        back.setTranslateX(WINDOW_WIDTH / 3);
        back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));
        root.setBottom(back);

        return new Scene(root, WINDOW_HEIGHT, WINDOW_WIDTH);
    }

    /**
     * This scene allows the user to add/remove users from their
     * social network
     * @return
     */
    static Scene addUser() {

        //Sets the title of the scene to a new string
        APP_TITLE = "Welcome to Adding New Users!";
        primaryStage.setTitle(APP_TITLE);
        
        //Creates text field for user to enter the name of a user
        TextField txt = new TextField("Enter the name of the User you'd like to Add");
        //Creates a label that explains parameters for user input
        Label label = new Label(
                "The names may contain any letters {A-Z}, space, digits {0-9}, underscore {_}, or apostrophe {'}");

        BorderPane root = new BorderPane();

        //Creates button that will call Controller.addUser() when pressed
        Button add = new Button("Add");
        add.setOnAction(e -> {
            String user = txt.getText();
            if (controller.addUser(user) && checktestBoxes(user))
                primaryStage.setScene(successMessage(addUser(), "Successfully added user:" + "\"" + user + "\""));
            else
                primaryStage.setScene(errorMessage(addUser(), "Error! Couldn't add user:" + "\"" + user + "\""));
        });

        //Creates button that will call Controller.removeUser() when pressed
        Button remove = new Button("Remove");
        remove.setOnAction(e -> {
            String user = txt.getText();
            if (controller.removeUser(user) && checktestBoxes(user))
                primaryStage.setScene(successMessage(addUser(), "Successfully removed user:" + "\"" + user + "\""));
            else
                primaryStage.setScene(errorMessage(addUser(), "Error! Couldn't remove user:" + "\"" + user + "\""));
        });
        
        //Creates button that allows the user to return to the first scene
        Button back = new Button("Back");
        back.setTranslateX(WINDOW_WIDTH / 3);
        back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));

        //Creates HBox & VBox
        HBox h = new HBox();
        VBox V = new VBox();
        
        //Sets dimensions of VBox & HBox
        h.setPrefHeight(WINDOW_HEIGHT / 4);
        h.setPrefWidth(WINDOW_WIDTH);
        V.setPrefWidth(WINDOW_WIDTH / 3);
        V.setPrefHeight(WINDOW_HEIGHT / 4);

        //Sets the width & position of the label
        label.setPrefWidth(WINDOW_WIDTH);
        label.setTranslateY(WINDOW_HEIGHT / 6);
        label.setTranslateX(0);

        //Sets the width & position of the text field
        txt.setPrefWidth(WINDOW_WIDTH / 8);
        txt.setTranslateX(0);
        txt.setTranslateY(WINDOW_HEIGHT / 8);

        //Sets the position of the "add" button
        add.setTranslateY(WINDOW_HEIGHT / 8);
        add.setTranslateX(WINDOW_WIDTH / 3);

        //Sets the position of the "remove" button
        remove.setTranslateX(WINDOW_WIDTH / 3);
        remove.setTranslateY(WINDOW_HEIGHT * 2 / 8);

        //Adds the label to the HBox
        //Adds the text field & buttons to the VBox
        h.getChildren().addAll(label);
        V.getChildren().addAll(txt, add, remove);

        //Aligns the boxes to their specified regions in the Main BorderPane
        root.setTop(h);
        root.setCenter(V);
        root.setBottom(back);

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    }

    /**
     * This scene allows the user to view the user network from 
     * the viewpoint of a central user
     * @param friends
     * @return
     */
    static Scene Network(List<String> friends) {
        try {
            String User = controller.getCentralUser(); // current central user
            
            //Resets String APP_TITLE
            APP_TITLE = "Welcome to Friend Network of " + User;
            //Sets window width
            WINDOW_WIDTH = 590;
            //Sets the title of the scene to APP_TITLE
            primaryStage.setTitle(APP_TITLE);
            BorderPane root = new BorderPane();
            //Creates listview of friends of the central user
            ListView<String> network = new ListView<>();
            ObservableList<String> items = FXCollections.observableArrayList(friends);
            network.setItems(items);
            //Check if user is right-clicking on a name or left-clicking
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
            
            // Creates a VBox for the top of the Screen that adds a network picture, and the
            // FriendsList
            VBox top = new VBox();
            
            //Sets dimensions of top VBox
            top.setPrefWidth(WINDOW_WIDTH);
            top.setPrefHeight(123);
            
            //Imports facebook gif image and displays it in the top region 
            //of the main BorderPane
            FileInputStream topImg = new FileInputStream("facebookStrip.gif");
            Image topStrip = new Image(topImg);
            ImageView topLabel = new ImageView();
            topLabel.setImage(topStrip);
            top.getChildren().add(topLabel);
            root.setTop(top);

            //Imports profile picture and creates an ImageView
            FileInputStream input = new FileInputStream("profilePicture.png");
            Image img = new Image(input);
            ImageView imgView = new ImageView();
            
            //Creates VBox & label with the name of the central user
            VBox leftSide = new VBox();
            Label userName = new Label(User);
            
            //Sets style of the label
            userName.setStyle("-fx-background-color: white; " + "-fx-text-fill: black; " + "-fx-font-size: 10; "
                    + "-fx-font-family: courier");
            
            //Sets components of the main BorderPane 
            //Aligns & resizes labels & buttons
            imgView.setImage(img);
            leftSide.getChildren().addAll(imgView, userName);
            leftSide.setPrefWidth(WINDOW_WIDTH / 2);
            userName.setPrefWidth(200);
            leftSide.setPrefHeight(WINDOW_HEIGHT);
            root.setLeft(leftSide);

            //Creates new VBox & sets its dimensions
            VBox right = new VBox();
            right.setPrefWidth(WINDOW_WIDTH / 2);
            right.setPrefHeight(WINDOW_HEIGHT);
            
            //Creates & styles new label for friends list
            Label friendsLabel = new Label("Friends List");
            friendsLabel.setStyle("-fx-background-color: white; " + "-fx-text-fill: black; " + "-fx-font-size: 25; "
                    + "-fx-font-family: courier");
            network.setStyle("-fx-background-color: white; " + "-fx-text-fill: black; " + "-fx-font-size: 20; "
                    + "-fx-font-family: courier");
            
            //Adds label and listview of network to the right VBox
            right.getChildren().addAll(friendsLabel, network);
            
            //Positions VBox in center of main BorderPane
            root.setCenter(right);
            
            //Creates button that allows the user to return to central user options
            Button back = new Button("Back");
            back.setOnAction(e -> primaryStage.setScene(centralUserOptions()));
            back.setTranslateX(WINDOW_WIDTH / 2);
            root.setBottom(back);
            root.setStyle("-fx-background-color: white; ");

            return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (FileNotFoundException e) { //image file not found
            return (errorMessage(Network(null), "ERROR: File for Image not Found."));
        }
    }

    /**
     * 
     * this scene displays two users' mutual friends
     * @param mutualFriends
     * @param user1
     * @param user2
     * @return
     */
    static Scene displayMutualFriends(List<String> mutualFriends, String user1, String user2) {
        //Sets app title
        APP_TITLE = "Mutual Friends between " + user1 + " and " + user2 + ".";

        //Creates button that allows the user to return to the first scene
        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));
        back.setTranslateX(WINDOW_WIDTH / 3);

        //Checks if mutualFriends is null
        if (mutualFriends == null) {
            return errorMessage(RunApplication.firstScene(), "Error one of the users entered do not exist");
        }

        //Checks if mutualFriends is an empty list
        //Users have no mutual friends
        if (mutualFriends.size() == 0) {
            //Sets new app title
            primaryStage.setTitle(APP_TITLE);
            BorderPane root = new BorderPane();  
            //Creates new HBox
            HBox box = new HBox();
            //Creates a label with a message
            Label message = new Label(
                    "Oops! Doesn't look like " + user1 + " and " + user2 + " have any mutual friends");
            //Sets position of message label
            message.setTranslateX(WINDOW_WIDTH / 4);
            message.setTranslateY(WINDOW_HEIGHT / 2);
            //Adds label to HBox and positions HBox in main BorderPane
            box.getChildren().add(message);
            root.setCenter(box);
            //Creates new HBox, sets its width, and positions it at the bottom
            //of the main BorderPane
            HBox box2 = new HBox();
            box2.setTranslateX(WINDOW_WIDTH * 4 / 9);
            box2.getChildren().add(back);
            root.setBottom(box2);

            return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        }

        //Resets title
        primaryStage.setTitle(APP_TITLE);
        BorderPane root = new BorderPane();

        //Displays the list of mutual friends in the center of the main BorderPane
        ObservableList<String> friends = FXCollections.observableArrayList(mutualFriends);
        ListView<String> listView = new ListView<String>(friends);
        listView.setItems(friends);
        root.setCenter(listView);
        //Adds back button to the bottom of the main BorderPane
        root.setBottom(back);

        return new Scene(root, WINDOW_WIDTH * 2 / 3, WINDOW_HEIGHT);

    }

    /**
     * 
     * @param txt1
     * @param txt2
     * @return
     */
    static boolean checktestBoxes(String txt1, String txt2) {

        txt1 = txt1.trim();

        if (txt1 == null || txt1.equals("") || txt1.isEmpty()) {
            return false;
        }

        txt2 = txt2.trim();

        if (txt2 == null || txt2.equals("") || txt2.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * 
     * @param txt1
     * @return
     */
    static boolean checktestBoxes(String txt1) {

        txt1 = txt1.trim();

        if (txt1 == null || txt1.equals("") || txt1.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @param currScene
     * @param message
     * @return
     */
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
        back.setTranslateX(WINDOW_WIDTH / 3);
        errorMessage.setTranslateY(WINDOW_HEIGHT / 2);
        errorMessage.setTranslateX(WINDOW_WIDTH * 3 / 10);

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    }

    /**
     * 
     * @param currScene
     * @param message
     * @return
     */
    static Scene successMessage(Scene currScene, String message) {
        APP_TITLE = "SUCCESS!";
        primaryStage.setTitle(APP_TITLE);
        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(currScene));
        BorderPane root = new BorderPane();
        HBox box = new HBox();
        Label errorMessage = new Label(message);
        box.getChildren().add(errorMessage);
        root.setCenter(box);
        root.setBottom(back);
        back.setTranslateX(WINDOW_WIDTH / 3);
        errorMessage.setTranslateY(WINDOW_HEIGHT / 2);
        errorMessage.setTranslateX(WINDOW_WIDTH * 3 / 10);

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    /**
     * 
     * @return
     */
    static Scene displayAllUsers() {

        APP_TITLE = "All users";

        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(RunApplication.firstScene()));
        back.setTranslateX(WINDOW_WIDTH / 2);

        ArrayList<String> users = new ArrayList<String>();

        users.addAll(controller.userNetwork.getAllVertices());

        if (users == null) {
            return errorMessage(RunApplication.firstScene(), "Nothing to Display");
        }

        if (users.size() == 0) {
            return errorMessage(firstScene(), "Oops! Looks like there are no users present in the network.");
        }

        primaryStage.setTitle(APP_TITLE);

        BorderPane root = new BorderPane();

        ObservableList<String> allUsers = FXCollections.observableArrayList(users);

        ListView<String> listView = new ListView<String>(allUsers);
        listView.setTranslateX(WINDOW_WIDTH / 2);
        listView.setStyle("-fx-text-fill: black; " + "-fx-font-size: 25; ");

        listView.setItems(allUsers);
        root.setCenter(listView);
        root.setBottom(back);

        root.setStyle("-fx-background-color: white; ");
        listView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String user = (String) listView.getSelectionModel().getSelectedItem(); // selected user
                if (event.getButton() == MouseButton.PRIMARY) { // left click

                    controller.setCentralUser(user);
                    controller.printCtrlNetwork(user);
                } else if (event.getButton() == MouseButton.SECONDARY) { // right click
                    controller.removeUser(user);
                    primaryStage.setScene(displayAllUsers());
                }
            }

        });

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    }

}