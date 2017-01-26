package run;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import client.Client;
import server.Server;


public class runService {
	
	// Logger
	static protected Logger logger =  Logger.getLogger(runService.class);
	
	
	public static void main(String[] args) {
		
		// MODAL POUR LE CHOIX SERVEUR OU CLIENT
		Object[] selectioValues = { "Alice (Server)", "Bob (Client)"};
		String initialSection = "Serveur";
		Object selection = JOptionPane.showInputDialog(null, "Type :", "Message Box", JOptionPane.QUESTION_MESSAGE, null, selectioValues, initialSection);
		
		logger.info("->> " + selection);
		
		// Lancement du serveur ou du client
		if(selection.equals("Alice (Server)"))
		{
            String[] arguments = new String[] {};
			new Server().main(arguments);
		}
		else
		{
			String IPServer = JOptionPane.showInputDialog(null, "Entre l'adresse IP du serveur : ", "Info Serveur", JOptionPane.QUESTION_MESSAGE);
            String[] arguments = new String[] {IPServer};
			new Client().main(arguments);
		}
	}

}
