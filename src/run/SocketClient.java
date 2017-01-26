package run;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import RSA.Encryption;
import RSA.PrivateKey;
import RSA.PublicKey;

public class SocketClient {
	
	protected String name;
	
	// Logger
	static protected Logger logger =  Logger.getLogger(SocketClient.class);

	// The client socket.
	protected Socket clientSocket = null;

	// The client socket.
	protected ServerSocket serverSocket = null;
	  
	// Output Stream
	protected OutputStream outputStream = null;
	  
	// Input Stream
	protected BufferedReader inputStream = null;
	  
	// Interface UI
	protected ClientUI frame;
	  
	// My public key
	protected PublicKey key_public;
	
	// My private key
	protected PrivateKey key_private;
	
	// public key other client
	protected PublicKey key_public_server;
	
	// The client socket.
	protected final int port = 2222;
    
    
    /**
     * Receive message
     * @param text
     */
    public void receive(String text){
    	logger.info("receive (Other > "+ name +" ) > " +  text);

    	if( text != null ){
	    	
	        // Create a Pattern object
	        String[] parts 		= text.split("\\|");
	        
	        
	        // Add Key Public 
	        if(parts.length == 3 && parts[0].equals("keypublic")){
	        	key_public_server = new PublicKey(new BigInteger(parts[1]), new BigInteger(parts[2]));
	        	logger.info("KEY :" + key_public_server);
	        	frame.enabledButton();
	    	}
	        // add message
	        else if(parts.length == 2 && parts[0].equals("message")){
	        	String text_decryp = Encryption.decrypt(key_private ,parts[1]);
	        	frame.addMessage("> " + text_decryp);
	    	}
    	}
    }
    
    /**
     * Send message
     * @param text
     */
    public void send(String type, String text) {
    	logger.info("Send ("+ name +" > Other ) > " + (type + "|" + text));
    	
        try {
        	if( type.equals("message")){
        		outputStream.write((type + "|" + Encryption.encrypt(key_public_server ,text) + "\r\n").getBytes());
        	}
        	else {
    			outputStream.write((type + "|" + text + "\r\n").getBytes());
        	}
        	
	        outputStream.flush();
	        
		} catch (IOException e) {
			logger.error("Error > " + e);
		}
    }
    
    
}
