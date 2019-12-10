package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import application.Graph;
import application.RunApplication;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import application.Model;

public class Controller extends Model {

	public Controller() {
		super();
	}

	public void setCentralUser(String user) {
		Model.centralUser = user;
	}

	public void printCtrlNetwork(String user) {
		List friends = userNetwork.getAdjacentVerticesOf(user);
		RunApplication.primaryStage.setScene(RunApplication.Network(friends));
	}

	public void addFriend(String user1, String user2) {
		// Since the implemented graph is directed, an edge must be added both ways
		userNetwork.addEdge(user1, user2);
		userNetwork.addEdge(user2, user1);
	}

	public void removeFriend(String user1, String user2) {
		// Remove the undirected edge from between the two users.
		userNetwork.removeEdge(user1, user2);
		userNetwork.removeEdge(user2, user1);
	}

	public void importFile(String filePath) {
		try {
			FileInputStream input = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Export a JSON file that represents the network to a desired location on the
	 * users computer
	 * 
	 * @return
	 */
	public String exportFile() {

		return null;
	}

	public void addUser(String name) {
		userNetwork.addVertex(name);
	}
}
