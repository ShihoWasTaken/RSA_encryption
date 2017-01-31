package run;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import client.Client;
import communication.IPTools;
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
			List<String> servers = IPTools.getAllServersAvailable();
			// Si aucun serveur n'a été trouvé
			if(servers.size() == 0)
			{
				JOptionPane.showMessageDialog(null, "Impossible de trouver des serveurs disponibles", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			// Si au moins un serveur a été trouvé
			else
			{
				
			    String IPServer = (String) JOptionPane.showInputDialog(null, 
			            "Choisissez un serveur disponible : ",
			            "Choix du serveur pour communiquer",
			            JOptionPane.QUESTION_MESSAGE, 
			            null, 
			            servers.toArray(), 
			            servers.toArray()[0]);
			    System.out.println(IPServer);
			    
			        // IPServer will be null if the user clicks Cancel
				String[] arguments = new String[] {IPServer};
				new Client().main(arguments);
			}
		}
	}

}
