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

    Graph userNetwork; //graph representation of friend network
    private String centralUser; //current central user
    private File file; //log file to be exported
 
    /**
     * default no-argument constructor
     * initializes the graph log file
     */
    public Controller() {
        userNetwork = new Graph();
        this.file = new File("log.txt");
        file.delete(); //ensures that file won't be reused
        file = new File("log.txt");
    }

    /**
     * creates a Controller object with a user-specified 
     * file name & initializes the graph
     * @param fileName - name of log file
     */
    public Controller(String fileName) {
        userNetwork = new Graph();
        this.file = new File(fileName);
    }

    /**
     * this method changes the central user and logs 
     * action in log file
     * @param user - new central user
     */
    public void setCentralUser(String user) {
        //set central user
        this.centralUser = user;
        
        //write action to log file
        String str = "s " + user + "\n";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(str);
            writer.close();
        } catch (IOException e) {
            // ERROR
        }
    }

    /**
     * method to access central user
     * @return central user
     */
    public String getCentralUser() {
        return centralUser;
    }

    /**
     * this method prints the friends of the specified user
     * i.e. any user that this user (vertex) shares an edge with
     * @param user - name of user whose friends should be displayed
     */
    public void printCtrlNetwork(String user) {
        //create a list of the specified user's friends
        List friends = userNetwork.getAdjacentVerticesOf(user);
        
        //display list to user
        RunApplication.primaryStage.setScene(RunApplication.Network(friends));
    }

    /**
     * this method adds a friendship (edge on graph) between two specified users
     * and logs this action in the log file
     * @param user1
     * @param user2
     * @return true if friendship successfully added, false otherwise
     */
    public boolean addFriend(String user1, String user2) {
        //since the implemented graph is directed, an edge must be added both ways
        boolean result = userNetwork.addEdge(user1, user2);
        userNetwork.addEdge(user2, user1);
        
        if(this.centralUser == null) {
          centralUser = user1;
        }
        
        //write action to log file
        String str = "a " + user1 + " " + user2 + "\n";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(str);
            writer.close();
        }catch (IOException e) {
            //error
        }

        return result;
    }

    /**
     * this method removes a friendship (edge on graph) between two 
     * specified users and logs this action in the log file
     * @param user1
     * @param user2
     * @return true is friendship successfully removed, false otherwise
     */
    public boolean removeFriend(String user1, String user2) {
        //remove the undirected edge from between the two users.
        boolean result = userNetwork.removeEdge(user1, user2);
        userNetwork.removeEdge(user2, user1);
        
        //write action to log file
        String str = "r " + user1 + " " + user2 + "\n";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(str);
            writer.close();
        }catch (IOException e) {
            //error
        }
        return result;
    }

    /**
     * this method reads the contents of an input file and calls 
     * addFriend(), addUser(), removeFriend(), removeUser(), and
     * setCentralUser() accordingly. if there is an error reading the file,
     * this method displays an error message to the user
     * @param filePath - path to file to read
     * @param primaryStage
     */
    public void importFile(String filePath, Stage primaryStage){
        try {
            Scanner readFile = new Scanner(new File(filePath));
            //each element of the outer arraylist will represent a line of the file
            //each element of the inner array will represent a word within the line
            ArrayList<String[]> listCommands = new ArrayList<String[]>();
            
            //parse file & store contents in listCommands
            while (readFile.hasNextLine()) {
                String line = readFile.nextLine();
                String[] commands = line.split(" ");
                listCommands.add(commands);
            }
            readFile.close();
            
            //iterate thru each element of listCommands
            for (int i = 0; i < listCommands.size(); i++) {
                String error = "ERROR: The input file was not formatted correctly. Look at line " + i
                        + " of the input file.";
                String[] command = listCommands.get(i);
                
                //check for invalid command line
                if (command.length < 2 || command.length > 3) {
                    primaryStage.setScene(RunApplication.errorMessage(RunApplication.uploadNetworkFile(), error));
                    break;
                }
                
                //use the first char of the line to determine which action to take
                //use other words in line as parameters for whichever method is called
                char check = command[0].charAt(0);
                if (check == 'a') {
                    if (command.length == 3) { //add friendship between two users
                        addFriend(command[1], command[2]);
                    } else { //add user to network
                        addUser(command[1]);
                    }
                } else if (check == 'r') {
                    if (command.length == 3) { //remove friendship between two users
                        removeFriend(command[1], command[2]);
                    } else { //remove user from network
                        removeUser(command[1]);
                    }
                } else if (check == 's') { //set central user
                    setCentralUser(command[1]);
                }
            }
        } catch (FileNotFoundException e) {
            //display error message
            primaryStage.setScene(RunApplication.errorMessage(RunApplication.uploadNetworkFile(),
                    "ERROR: The input file was not found"));
            return;
        }
        //display message upon success
        primaryStage.setScene(
                RunApplication.successMessage(RunApplication.uploadNetworkFile(), "SUCCESS: Network File Uploaded!"));
        return;
    }

    /**
     * this method exports a .txt file that represents the network to a 
     * desired location on the user's computer
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
            
            //copy the contents of the log file to a new file at the desired path
            while (readFile.hasNextLine()) {
                String line = readFile.nextLine();
                output.append(line + "\n");
            }
            output.close();
            readFile.close();
        } catch (Exception e) {
            //display error message
            RunApplication.primaryStage
                    .setScene(RunApplication.errorMessage(RunApplication.ExportFile(), "ERROR: Could Not Write File."));
        }
        //display message upon success
        RunApplication.primaryStage
                .setScene(RunApplication.successMessage(RunApplication.ExportFile(), "SUCCESS: File was written!"));
    }

    /**
     * this method adds a user (vertex in graph) to the social network and 
     * logs this action in the log file
     * @param name - name of user to be added
     * @return true if user successfully added, false otherwise
     */
    public boolean addUser(String name) {
        //add user to userNetwork (add vertex)
        //result is true if add is successful, false otherwise
        boolean result = userNetwork.addVertex(name);
        if(this.centralUser == null) {
            centralUser = name;
          }
        
        //write action to log file
        String str = "a " + name + "\n";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(str);
            writer.close();
        }catch (IOException e) {
            //error
        }
        return result;
    }

    /**
     * this method removes a user (vertex in graph) from the social network and 
     * logs this action in the log file
     * @param name - name of user to be removed
     * @return true if user successfully removed, false otherwise
     */
    public boolean removeUser(String name) {
        //remove user from userNetwork (remove vertex)
        //result is true if remove is successful, false otherwise
        boolean result = userNetwork.removeVertex(name);
        
        //write action to log file
        String str = "r " + name + "\n";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(str);
            writer.close();
        }catch (IOException e) {
            //error
        }
        return result;
    }
    
    /**
     * this method returns a list of the mutual friends between two
     * specified user i.e. any user (vertex) who is friends with 
     * (has an edge between) both users 
     * @param user1
     * @param user2
     * @return a list of mutual friends
     */
    List<String> getMutualFriends(String user1, String user2) {
        //list to store mutual friends
        List<String> mutualFriends;
        //set of all users in the network
        Set<String> allUsers = userNetwork.getAllVertices();
        //check that given users are in the userNetwork
        if (!allUsers.contains(user1) || !allUsers.contains(user2)) {
            return null;
        }
        //list that stores the friends of user1
        List<String> user1Friends = userNetwork.getAdjacentVerticesOf(user1);
        //list that stores the friends of user2
        List<String> user2Friends = userNetwork.getAdjacentVerticesOf(user2);
        //delete friends from user1 list that are not in user2 list
        user1Friends.retainAll(user2Friends);
        //assign new contents of user1 list to mutual friends list
        mutualFriends = user1Friends;

        return mutualFriends;
    }

    /**
     * this method clears the userNetwork and deletes the log file
     */
    public void clearNetwork() {
        userNetwork = new Graph();
        file.delete();
    }


}
