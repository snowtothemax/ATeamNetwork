package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import application.Graph;
import application.RunApplication;
import javafx.stage.Stage;

public class Controller {

	private Graph userNetwork;
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
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
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

	public void addFriend(String user1, String user2) {
		// Since the implemented graph is directed, an edge must be added both ways
		userNetwork.addEdge(user1, user2);
		userNetwork.addEdge(user2, user1);

		String str = "a " + user1 + " " + user2 + "\n";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// ERROR
		}

	}

	public void removeFriend(String user1, String user2) {
		// Remove the undirected edge from between the two users.
		userNetwork.removeEdge(user1, user2);
		userNetwork.removeEdge(user2, user1);

		String str = "r " + user1 + " " + user2 + "\n";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// ERROR
		}
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
				switch (command[0].charAt(0)) {
				case 'a':
					if (command.length == 3) {
						addFriend(command[1], command[2]);
					} else {
						addUser(command[1]);
					}
					break;
				case 'r':
					if (command.length == 3) {
						removeFriend(command[1], command[2]);
					} else {
						removeUser(command[1]);
					}
					break;
				case 's':
					setCentralUser(command[1]);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			primaryStage.setScene(RunApplication.errorMessage(RunApplication.uploadNetworkFile(),
					"ERROR: The input file was not found"));
			return;
		}
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

	public void addUser(String name) {
		userNetwork.addVertex(name);

		String str = "a " + name + "\n";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// ERROR
		}
		// TODO
	}

	public void removeUser(String name) {
		userNetwork.removeVertex(name);

		String str = "r " + name + "\n";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// ERROR
		}
	}

	public void clearNetwork() {
		userNetwork = new Graph();
	}
}