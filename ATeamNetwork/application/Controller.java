package application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Graph;
import application.RunApplication;
import application.Model;

public class Controller extends Model{
	
	private Controller() {
		super();
	}
	
	public static void setCentralUser(String user) {
		// TODO
	}

	public static void printNetwork() {
		// TODO
	}

	public static void printCtrlNetwork(String user) {
		// TODO
	}

	public static void addFriend(String user1, String user2) {
		//Since the implemented graph is directed, an edge must be added both ways
		userNetwork.addEdge(user1, user2);
		userNetwork.addEdge(user2, user1);
	}
	
	public static void removeFriend(String user1, String user2) {
		//Remove the undirected edge from between the two users.
		userNetwork.removeEdge(user1,user2);
		userNetwork.removeEdge(user2, user1);
	}

	public static void importFile(String filePath) {
		try {
			FileInputStream input = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Export a JSON file that represents the network to a desired location on the users computer
	 * @return
	 */
	public static String exportFile() {
		
		return null;
	}

	public static void addUser() {
		// TODO
	}
}
