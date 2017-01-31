package communication;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	protected ObjectOutputStream outputStream = null;
	  
	// Input Stream
	protected ObjectInputStream inputStream = null;
	  
	// Interface UI
	protected InterfaceUI frame;
	  
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
    public void receive(Message message){
    	logger.info("receive (Other > "+ name +" ) > " +  message);

    	if( message != null ){
	        
	        // Add Key Public 
	        if(message.getType().equals(TypeAction.public_key)){
	        	key_public_server = message.getKey();
	        	frame.addLog("<div><strong color=green>RECEIVE KEY PUBLIC > </strong>" + key_public_server.toString()+ "</div>");
	        	frame.enabledButton();
	    	}
	        // add message
	        else if(message.getType().equals(TypeAction.message)){
	        	String text_decryp = Encryption.decrypt(key_private , message.getMessage());

	        	// LOG
	        	frame.addMessage("<div style='text-align:right;color: white;background-color:#82B6EA;padding:5px;'>" + text_decryp + "</div>");
	        	frame.addLog("<div><strong color=green>RECEIVE MESSAGE (crypt) > </strong>" + message.getMessage() + "</div>"
	        			+ "<div><strong color=green>RECEIVE MESSAGE (decrypt) > </strong>" + text_decryp + "</div>");
	    	}
    	}
    }
    
    /**
     * Send message
     * @param text
     */
    public void send(String text) {
    	logger.info("Send ("+ name +" > Other ) > " + ("message|" + text));
    	
        try {
        	
    		String text_encrypt =  Encryption.encrypt(key_public_server ,text);
    		outputStream.writeObject(new Message(TypeAction.message, text_encrypt, null));
    		
    		// LOG
        	frame.addMessage("<div style='background-color:#507191;color: white;padding:5px;'>" + text + "</div>");
        	frame.addLog("<div><strong color=red>SEND MESSAGE (decrypt) > </strong>" + text + "</div>"
        			+ "<div><strong color=red>SEND MESSAGE (crypt) > </strong>" + text_encrypt + "</div>");

        
	        outputStream.flush();
	        
		} catch (IOException e) {
			logger.error("Error > " + e);
		}
    }
    
    /**
     * Send PublicKey
     * @param key
     */
    public void send(PublicKey key) {
    	try {
        	logger.info("Send ("+ name +" > Other ) > " + ("key_public|" + key));
    		
			outputStream.writeObject(new Message(TypeAction.public_key, null, key));
	        outputStream.flush();
		} catch (IOException e) {
			logger.error("Error > " + e);
		}
    }
    
    
}
