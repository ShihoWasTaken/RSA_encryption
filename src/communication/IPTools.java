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
            
            // On récupère le sous réseau à scanner
            Process first_arp = rt.exec("arp -a");
            first_arp.waitFor();
            String IPAddress_sub_with_star = null;
            BufferedReader input_first = new BufferedReader(new InputStreamReader(first_arp.getInputStream()));

            String line_first = input_first.readLine();
            boolean first_line_passed = false;
            while(!first_line_passed)
            {
                int parantheseOuvrante = line_first.indexOf('(');
                int parantheseFermante = line_first.indexOf(')');
                String IPAddress_full = line_first.substring(parantheseOuvrante + 1, parantheseFermante);
                
                int lastDot = IPAddress_full.lastIndexOf('.');
                IPAddress_sub_with_star = IPAddress_full.substring(0, lastDot + 1) + "*";
                first_line_passed = true;
            }
            
            Process pr_nmap = rt.exec("nmap -sP " + IPAddress_sub_with_star);
            pr_nmap.waitFor();
            Process second_arp = rt.exec("arp -a");
            second_arp.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(second_arp.getInputStream()));

            String line=null;

            while((line=input.readLine()) != null)
            {
                int parantheseOuvrante = line.indexOf('(');
                int parantheseFermante = line.indexOf(')');
                String IPAddress = line.substring(parantheseOuvrante + 1, parantheseFermante);
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
        	JOptionPane.showMessageDialog(null, "Le programme nmap doit être installé sur la machine du client", "Erreur", JOptionPane.ERROR_MESSAGE);
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
