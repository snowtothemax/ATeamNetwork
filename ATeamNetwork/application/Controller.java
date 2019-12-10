package application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;

import application.Graph;
import application.RunApplication;
import application.Model;


public class Controller extends Model{

    private Graph userNetwork;
    private String centralUser;
    private File file;

    public Controller(String fileName) {
        userNetwork = new Graph();
        this.file = new File(fileName);
    }

    public void setCentralUser(String user) {
        this.centralUser = user;

        // TODO
    }

    public void printNetwork() {
        // TODO
    }

    public void printCtrlNetwork(String user) {
        // TODO
    }

    public void addFriend(String user1, String user2) {
        //Since the implemented graph is directed, an edge must be added both ways
        userNetwork.addEdge(user1, user2);
        userNetwork.addEdge(user2, user1);

        String str = "a " + user1 + " " + user2 + "\n";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(str);
        catch (IOException e) {



    }

    public void removeFriend(String user1, String user2) {
        //Remove the undirected edge from between the two users.
        userNetwork.removeEdge(user1,user2);
        userNetwork.removeEdge(user2, user1);
    }

    public void importFile(String filePath) {
        try {
            Scanner readFile = new Scanner(new File(filePath));
            ArrayList<String[]> commands = new ArrayList<String[]>();
            while (readFile.hasNextLine()) {
                String line = readFile.nextLine();
                commands.add(line.split(" "));
            }
            for (int i = 0; i < commands.size(); i++) {
                String[] command = commands.get(i);
                if (command.length < 2 || command.length > 3) {
                    //TODO
                    //ERROR

                }
                switch (command[0].charAt(0)) {
                    case 'a':
                        if (command.length == 3) {
                            addFriend(command[1], command[2]);
                        }else {
                            addUser(command[1]);
                        }
                        break;
                    case 'r':
                        if (command.length == 3) {
                            removeFriend(command[1], command[2]);
                        }else {
                            removeUser(command[1]);
                        }
                        break;
                    case 's':
                        setCentralUser(command[1]);
                        break;
                    default:
                        //TODO
                        //ERROR
                        break;

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * Export a JSON file that represents the network to a desired location on the users computer
     * @return
     */
    public File exportFile() {

        return null;
    }

    public void addUser(String name) {
        // TODO
    }

    public void removeUser(String name) {

    }
}
