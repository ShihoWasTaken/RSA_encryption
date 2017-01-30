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
	        	frame.addLog("<strong color=green>RECEIVE KEY PUBLIC > </strong>" + key_public_server.toString());
	        	logger.info("KEY :" + key_public_server);
	        	frame.enabledButton();
	    	}
	        // add message
	        else if(parts.length == 2 && parts[0].equals("message")){
	        	String text_decryp = Encryption.decrypt(key_private ,parts[1]);

	        	// LOG
	        	frame.addMessage("<div style='text-align:right;color: white;background-color:#82B6EA;padding:5px;'>" + text_decryp + "</div>");
	        	frame.addLog("<strong color=green>RECEIVE MESSAGE (crypt) > </strong>" + parts[1]);
	        	frame.addLog("<strong color=green>RECEIVE MESSAGE (decrypt) > </strong>" + text_decryp);
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
        		String text_encrypt =  Encryption.encrypt(key_public_server ,text);
        		outputStream.write((type + "|" + text_encrypt + "\r\n").getBytes());
        		
        		// LOG
            	frame.addMessage("<div style='background-color:#507191;color: white;padding:5px;'>" + text + "</div>");
            	frame.addLog("<strong color=red>SEND MESSAGE (decrypt) > </strong>" + text);
        		frame.addLog("<strong color=red>SEND MESSAGE (crypt) > </strong>" + text_encrypt);

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
