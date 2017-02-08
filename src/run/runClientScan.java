package run;

import java.util.List;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

import client.Client;
import communication.IPTools;



public class runClientScan {
	
	// Logger
	static protected Logger logger =  Logger.getLogger(runClientScan.class);

	/**
	 * RUN CLIENT WITH SCAN
	 * @param args
	 */
	public static void main(String[] args) {
		
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
		    
		    // If cancel 
		    if( IPServer != null){
			    // IPServer will be null if the user clicks Cancel
				String[] arguments = new String[] {IPServer};
				new Client().main(arguments);
		    }
		}
	}

}
