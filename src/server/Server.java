package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import javax.swing.JFrame;

import RSA.PrivateKey;
import RSA.PublicKey;
import run.ClientUI;
import run.SocketClient;

public class Server extends SocketClient {
	
	/**
     * Initialisation du socket 
     * 
     * @param server
     * @param port
     * @throws IOException
     */
	public void InitSocketServer(int port) throws IOException {
	    serverSocket = new ServerSocket(port);
	    
	    try {
		      clientSocket = serverSocket.accept();
		 
		      outputStream 	= clientSocket.getOutputStream();
		      inputStream 	= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		      
		      String line;
              send("keypublic", key_public.getP()+ "|" + key_public.getQ());
              while (true) {
            	  line = inputStream.readLine();
            	  receive(line);
              }
		     
	    } catch(Exception e) {
			logger.error("Error A > " + e.getMessage());
			e.printStackTrace();
	    }
	    
    }
	  
	  
	public void main(String args[]) {
		
		name				= "Server";
		
		// Create key
        this.key_public		= new PublicKey();
        this.key_private	= new PrivateKey(this.key_public);
        
        // Init UI
        frame = new ClientUI(this, "Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true); 
        
		try {
			InitSocketServer(port);
        } catch (IOException ex) {
			logger.error("Error > " + ex);
            ex.printStackTrace();
            System.exit(0);
        }
	} 
}
