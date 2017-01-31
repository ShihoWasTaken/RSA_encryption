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
	final static int PORT = 2222;
	final static int TIMEOUT = 1000;
	
	public static boolean isServerOnline(String host, int port)
	{
		boolean exists = false;

		try
		{
		    SocketAddress sockaddr = new InetSocketAddress(host, port);
		    // Create an unbound socket
		    Socket sock = new Socket();

		    // This method will block no more than timeoutMs.
		    // If the timeout occurs, SocketTimeoutException is thrown.
		    sock.connect(sockaddr, TIMEOUT);
		    exists = true;
		}
		catch(Exception e)
		{
		}
		return exists;
	}
	
	
	public static void main(String[] args)
	{
		
        try
        {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("arp -a");

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line=null;

            while((line=input.readLine()) != null)
            {
                int parantheseOuvrante = line.indexOf('(');
                int parantheseFermante = line.indexOf(')');
                String IPAddress = line.substring(parantheseOuvrante + 1, parantheseFermante);
                boolean isOnline = isServerOnline(IPAddress, PORT);
                if(isOnline)
                {
                	System.out.println(IPAddress + "'s server is online");
                }
                else
                {
                	System.out.println(IPAddress + "'s server is offline");
                }
            }

            /*
            int exitVal = pr.waitFor();
            System.out.println("Exited with error code "+exitVal);
            */

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        

        
	}

}