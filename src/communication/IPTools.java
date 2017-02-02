package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
		    sock.close();
		    exists = true;
		}
		catch(Exception e)
		{
		}
		return exists;
	}
	
	public static List<String> getAllServersAvailable()
	{
		List<String> servers = new ArrayList<String>();
        try
        {
            Runtime rt = Runtime.getRuntime();
            
            String[] cmd = {"/bin/bash","-c","echo ce1mdpp | sudo -S arp-scan -l"};
            Process arp_scan = Runtime.getRuntime().exec(cmd);
            //Process arp_scan = rt.exec("");
            arp_scan.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(arp_scan.getInputStream()));

            String line=null;

            while((line=input.readLine()) != null)
            {
            	int tab = line.indexOf('\t');
            	if(tab != -1)
            	{
                    String IPAddress = line.substring(0, tab);
                	if(line.startsWith("192"))
                	{
                        boolean isOnline = isServerOnline(IPAddress, PORT);
                        if(isOnline)
                        {
                        	servers.add(IPAddress);
                        	System.out.println(IPAddress + "'s server is online");
                        }
                        else
                        {
                        	System.out.println(IPAddress + "'s server is offline");
                        }
                	}
            	}
            }
            // Test de localhost
            String IPAddress = "localhost";
            boolean isOnline = isServerOnline(IPAddress, PORT);
            if(isOnline)
            {
            	servers.add(IPAddress);
            	System.out.println(IPAddress + "'s server is online");
            }
            else
            {
            	System.out.println(IPAddress + "'s server is offline");
            }
        }
        catch(IOException e)
        {
        	JOptionPane.showMessageDialog(null, "Les programmes arp-scan et gksu doivent être installés sur la machine du client", "Erreur", JOptionPane.ERROR_MESSAGE);
        	System.exit(-1);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return servers;
	}
	
	
	public static void main(String[] args)
	{
		getAllServersAvailable();
		boolean locahost = isServerOnline("localhost", 2222);
		System.out.println("localhost online ? =  " + locahost);
	}

}
