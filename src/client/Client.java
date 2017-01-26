package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import RSA.PrivateKey;
import RSA.PublicKey;
import run.ClientUI;
import run.SocketClient;

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
        outputStream 	= clientSocket.getOutputStream();

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                	inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                	
                    String line;
                    send("keypublic", key_public.getN()+ "|" + key_public.getE());
                    while (true) {
                    	line = inputStream.readLine();
                    	logger.info("-->" + line);
                    	receive(line);
                    }
                } catch (IOException ex) {
                	logger.error(ex);
                }
            }
        };
        myThread.start();
    }
	
	
	public void main(String[] args) {
		// Info Server
        String server 		= args[0];
        name				= "Client";
        
        // Create key
        this.key_public		= new PublicKey();
        this.key_private	= new PrivateKey(this.key_public);
        
        // Init UI
        frame = new ClientUI(this, "Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true); 
        
        try {
            InitSocketClient(server,port);
        } catch (IOException ex) {
        	logger.error("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
	}
}
