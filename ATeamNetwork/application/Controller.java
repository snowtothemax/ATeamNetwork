package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import application.Graph;
import application.RunApplication;
import javafx.stage.Stage;

/**
 * This class represents the current social network of the user.
 * It stores the graph data structure of the network, the current central
 * user, and the log file that can be exported by the user. This class
 * contains methods to add and remove users from the network, add and
 * remove friendships between users, etc. It is utilized by RunApplication
 * to implement the user's actions in their social network.
 *
 * @author Megan de Joya (dejoya@wisc.edu), Abhinav Kaushik (akaushik4@wisc.edu),
 *         Max Johnson (mkjohnson9@wisc.edu), Yuhan Dai (dai45@wisc.edu)
 *
 */

public class Controller {

  Graph userNetwork;
  private String centralUser;
  private File file;

  public Controller() {
    userNetwork = new Graph();
    this.file = new File("log.txt");
    file.delete();
    file = new File("log.txt");
  }

  public Controller(String fileName) {
    userNetwork = new Graph();
    this.file = new File(fileName);
  }

  public void setCentralUser(String user) {
    this.centralUser = user;

    String str = "s " + user + "\n";
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      writer.append(str);
      writer.close();
    } catch (IOException e) {
      // ERROR
    }
    // TODO
  }

  public String getCentralUser() {
    return centralUser;
  }

  public void printCtrlNetwork(String user) {

    List friends = userNetwork.getAdjacentVerticesOf(user);
    RunApplication.primaryStage.setScene(RunApplication.Network(friends));
  }

  public boolean addFriend(String user1, String user2) {
    // Since the implemented graph is directed, an edge must be added both ways
    boolean result = userNetwork.addEdge(user1, user2);
    if(this.centralUser == null) {
      centralUser = user1;
    }

    userNetwork.addEdge(user2, user1);

    String str = "a " + user1 + " " + user2 + "\n";
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      writer.append(str);
      writer.close();
    } catch (IOException e) {
      // ERROR
    }

    return result;
  }

  public boolean removeFriend(String user1, String user2) {
    // Remove the undirected edge from between the two users.

    boolean result = userNetwork.removeEdge(user1, user2);
    userNetwork.removeEdge(user2, user1);

    String str = "r " + user1 + " " + user2 + "\n";
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      writer.append(str);
      writer.close();
    } catch (IOException e) {
      // ERROR
    }

    return result;
  }

  public void importFile(String filePath, Stage primaryStage) {
    try {
      Scanner readFile = new Scanner(new File(filePath));
      ArrayList<String[]> listCommands = new ArrayList<String[]>();
      while (readFile.hasNextLine()) {
        String line = readFile.nextLine();
        String[] commands = line.split(" ");
        listCommands.add(commands);
      }
      readFile.close();
      for (int i = 0; i < listCommands.size(); i++) {
        String error = "ERROR: The input file was not formatted correctly. Look at line " + i
            + " of the input file.";
        String[] command = listCommands.get(i);
        if (command.length < 2 || command.length > 3) {
          primaryStage
              .setScene(RunApplication.errorMessage(RunApplication.uploadNetworkFile(), error));
          break;
        }
        char check = command[0].charAt(0);
        if (check == 'a') {
          if (command.length == 3) {
            addFriend(command[1], command[2]);
          } else {
            addUser(command[1]);
          }
        } else if (check == 'r') {
          if (command.length == 3) {
            removeFriend(command[1], command[2]);
          } else {
            RemoveUser(command[1]);
          }
        } else if (check == 's') {
          setCentralUser(command[1]);
        } else {

        }
      }
    } catch (FileNotFoundException e) {
      primaryStage.setScene(RunApplication.errorMessage(RunApplication.uploadNetworkFile(),
          "ERROR: The input file was not found"));
      return;
    }
    primaryStage.setScene(RunApplication.successMessage(RunApplication.uploadNetworkFile(),
        "SUCCESS: Network File Uploaded!"));
    return;
  }

  /**
   * Export a JSON file that represents the network to a desired location on the users computer
   *
   * @return
   */
  public void exportFile(String filePath) {
    try {
      filePath.replace("'\'", "\\");
      Writer output = null;
      File fileExport = new File(filePath);
      output = new BufferedWriter(new FileWriter(fileExport));

      Scanner readFile = new Scanner(file);
      while (readFile.hasNextLine()) {
        String line = readFile.nextLine();
        output.append(line + "\n");
      }
      output.close();
      readFile.close();
    } catch (Exception e) {
      RunApplication.primaryStage.setScene(
          RunApplication.errorMessage(RunApplication.ExportFile(), "ERROR: Could Not Write File."));
    }
    RunApplication.primaryStage.setScene(
        RunApplication.successMessage(RunApplication.ExportFile(), "SUCCESS: File was written!"));
  }

  public boolean addUser(String name) {

    boolean result = userNetwork.addVertex(name);

    if(centralUser == null) {
      centralUser = name;
    }


    String str = "a " + name + "\n";
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      writer.append(str);
      writer.close();
    } catch (IOException e) {
      // ERROR
    }
    return result;
  }


  public boolean RemoveUser(String name) {

    boolean result = userNetwork.removeVertex(name);

    String str = "r " + name + "\n";
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      writer.append(str);
      writer.close();
    } catch (IOException e) {
      // ERROR
    }
    return result;
  }

  List<String> getMutualFriends(String user1, String user2) {

    List<String> mutualFriends;

    Set<String> allUsers = userNetwork.getAllVertices();

    if (!allUsers.contains(user1) || !allUsers.contains(user2)) {
      return null;
    }

    List<String> user1Friends = userNetwork.getAdjacentVerticesOf(user1);
    List<String> user2Friends = userNetwork.getAdjacentVerticesOf(user2);

    user1Friends.retainAll(user2Friends);

    mutualFriends = user1Friends;

    return mutualFriends;
  }

  public void clearNetwork() {
    userNetwork = new Graph();
    file.delete();
  }


}
