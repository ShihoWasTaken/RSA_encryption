package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class IPTools 
{
	public static void main(String[] args)
	{
		boolean exists = false;

		try {
		    SocketAddress sockaddr = new InetSocketAddress("192.168.43.178", 2222);
		    // Create an unbound socket
		    Socket sock = new Socket();

		    // This method will block no more than timeoutMs.
		    // If the timeout occurs, SocketTimeoutException is thrown.
		    int timeoutMs = 2000;   // 2 seconds
		    sock.connect(sockaddr, timeoutMs);
		    exists = true;
		}catch(Exception e){
		}
		System.out.println(exists);
        
	}

}
