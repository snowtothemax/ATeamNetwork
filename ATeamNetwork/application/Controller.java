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

public class Controller {

	Graph userNetwork;
	private String centralUser;
	private File file;

	public Controller() {
		userNetwork = new Graph();
		this.file = new File("log.txt");
	}

	public Controller(String fileName) {
		userNetwork = new Graph();
		this.file = new File(fileName);
	}

	public void setCentralUser(String user) {
		this.centralUser = user;

		String str = "s " + user + "\n";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
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
		userNetwork.addEdge(user2, user1);

		String str = "a " + user1 + " " + user2 + "\n";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
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
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
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
					primaryStage.setScene(RunApplication.errorMessage(RunApplication.uploadNetworkFile(), error));
					break;
				}
				char check = command[0].charAt(0);
				if(check == 'a') {
					if(command.length ==3) {
						addFriend(command[1],command[2]);
					}else {
						addUser(command[1]);
					}
				}else if(check == 'r') {
					if (command.length == 3) {
						removeFriend(command[1], command[2]);
					} else {
						RemoveUser(command[1]);
					}
				}else if(check == 's') {
					setCentralUser(command[1]);
				}else {
					
				}
			}
		} catch (FileNotFoundException e) {
			primaryStage.setScene(RunApplication.errorMessage(RunApplication.uploadNetworkFile(),
					"ERROR: The input file was not found"));
			return;
		}
		System.out.println(userNetwork.getAllVertices().toString());
		return;
	}

	/**
	 * Export a JSON file that represents the network to a desired location on the
	 * users computer
	 * 
	 * @return
	 */
	public File exportFile() {
		return file;
	}

	public boolean addUser(String name) {

		boolean result = userNetwork.addVertex(name);
		
		if(centralUser == null)
		this.setCentralUser(name);
		
		

		String str = "a " + name + "\n";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
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
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
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
	}

}
