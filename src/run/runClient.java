package run;

import javax.swing.JOptionPane;

import client.Client;

public class runClient {

	public static void main(String[] args) {
		
		String IPServer = JOptionPane.showInputDialog(null, "Entre l'adresse IP du serveur : ", "Info Serveur", JOptionPane.QUESTION_MESSAGE);
        String[] arguments = new String[] {IPServer};
        new Client().main(arguments);
	}

}
