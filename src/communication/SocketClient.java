package communication;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

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
    			String nomDuClient = "";
	    		if(serverSocket == null)
	    			nomDuClient = "Bob";
	    		else
	    			nomDuClient = "Alice";
	    		frame.addMessage("<div>" + nomDuClient + "</div>");
	        	frame.addMessage("<div style='font-family:\"Roboto\";text-align:left;color: black;background-color:#D8D8D8;padding:5px;margin:5px;border-radius:10px;'>" + text_decryp + "</div>");
	        	frame.addLog("<strong color=green>RECEIVE MESSAGE (crypt) > </strong>" + message.getMessage());
	        	frame.addLog("<strong color=green>RECEIVE MESSAGE (decrypt) > </strong>" + text_decryp);
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
    			String nomDuClient = "";
    		if(serverSocket == null)
    			nomDuClient = "Alice";
    		else
    			nomDuClient = "Bob";
    		frame.addMessage("<div>" + nomDuClient + "</div>");
        	frame.addMessage("<div style='font-family:\"Roboto\";text-align:right;background-color:#0033FF;color: white;padding:5px;margin:5px;border-radius:10px;'>" + text + "</div>");
        	frame.addLog("<strong color=red>SEND MESSAGE (decrypt) > </strong>" + text);
    		frame.addLog("<strong color=red>SEND MESSAGE (crypt) > </strong>" + text_encrypt);

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
    
    /**
     * GET IP 
     * @return
     */
    public String myIpNetwork(){
    	String res = null;
    	try {
	        String localhost = InetAddress.getLocalHost().getHostAddress();
	        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
	        while (e.hasMoreElements()) {
	            NetworkInterface ni = (NetworkInterface) e.nextElement();
	            if(ni.isLoopback())
	                continue;
	            if(ni.isPointToPoint())
	                continue;
	            Enumeration<InetAddress> addresses = ni.getInetAddresses();
	            while(addresses.hasMoreElements()) {
	                InetAddress address = (InetAddress) addresses.nextElement();
	                if(address instanceof Inet4Address) {
	                    String ip2 = address.getHostAddress();
	                    if(!ip2.equals(localhost))
	                    	res = ip2;
	                }
	            }
	        }
    	} catch (IOException e) {
			logger.error("Error > " + e);
		}
        return res;
    }
}
