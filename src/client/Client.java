package client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;


import RSA.PrivateKey;
import RSA.PublicKey;
import communication.InterfaceUI;
import communication.Message;
import communication.SocketClient;

public class Client extends SocketClient {
	
    
    /**
     * Initialisation du socket 
     * 
     * @param server
     * @param port
     * @throws IOException
     */
	public void InitSocketClient(String server, int port) throws IOException {
		clientSocket 	= new Socket(server, port);
        outputStream 	= new ObjectOutputStream(clientSocket.getOutputStream());
    	inputStream 	= new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                	frame.addLog("<strong color=red>MY IP > </strong>" + myIpNetwork());
                	
                	Message line;
                    send(key_public);
                    while (clientSocket.isConnected()) {
                    	line = (Message) inputStream.readObject();
                    	receive(line);
                    }
                } catch (Exception ex) {
                	logger.error(ex);
        			//ex.printStackTrace();
                }
            }
        };
        myThread.start();
    }
	
	
	public void main(String[] args) {
		// Info Server
        String server 		= args[0];
        name				= "Alice";
        
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
            InitSocketClient(server, port);
        } catch (IOException ex) {
        	logger.error("Cannot connect to " + server + ":" + port);
            //ex.printStackTrace();
            System.exit(0);
        }
	}
}
