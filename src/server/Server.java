package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.swing.JFrame;

import RSA.PrivateKey;
import RSA.PublicKey;
import communication.InterfaceUI;
import communication.Message;
import communication.SocketClient;

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
    	frame.addLog("<div><strong color=red>MY IP > </strong>" + InetAddress.getLoopbackAddress() + "</div>");
	    
	    try {
		      clientSocket = serverSocket.accept();
		 

		      outputStream 	= new ObjectOutputStream(clientSocket.getOutputStream());
		      inputStream 	= new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
		      
		      Message line;
              send(key_public);
              while (clientSocket.isConnected()) {
            	  line = (Message) inputStream.readObject();
            	  receive(line);
              }
		     
	    } catch(Exception e) {
			logger.error("Error A > " + e);
			e.printStackTrace();
	    }
	    
    }
	  
	  
	public void main(String args[]) {
		
		name				= "Server";
		
		// Create key
        this.key_public		= new PublicKey();
        this.key_private	= new PrivateKey(this.key_public);
        
        // Init UI
        frame = new InterfaceUI(this, name);
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
