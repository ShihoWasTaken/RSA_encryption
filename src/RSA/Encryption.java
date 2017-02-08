package RSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import communication.SocketClient;

public class Encryption {

	// Logger
	static protected Logger logger =  Logger.getLogger(SocketClient.class);
	
	
	/**
	 * Decrypt message
	 * @param privateKey
	 * @param cryptedMessage
	 * @return message
	 */
	public static String decrypt(PrivateKey privateKey, String cryptedMessage)
	{
		List<BigInteger> newList 	= new ArrayList<BigInteger>();
		String[] tabCrypted 		= cryptedMessage.split("-");
		
        for(int i = 0; i < tabCrypted.length; i++)
        {
        	if(!tabCrypted[i].equals("-") && tabCrypted[i] != null){
        		//TODO: erreur si c'est autre que des INT
        		BigInteger letter = new BigInteger(tabCrypted[i]);
	        	BigInteger temp = letter.modPow(privateKey.getU(),  privateKey.getPublicKey().getN());
	        	newList.add(temp);
        	}
        }

		StringBuilder sb = new StringBuilder();
        for(int i = 0; i < newList.size(); i++)
        {
        	sb.append((char) newList.get(i).intValue());
        }
		String str = sb.toString();
        return str;
	}
	
	
	/**
	 * Encrypt message
	 * @param publicKey
	 * @param message
	 * @return
	 */
	public static String encrypt(PublicKey publicKey, String message)
	{
		List<Integer> raw = StringToASCII(message);
		String str_encry = "";
		
        for(int i = 0; i < raw.size(); i++)
        {
        	BigInteger letter = new BigInteger(raw.get(i).toString());
        	BigInteger temp = letter.modPow(publicKey.getE(),  publicKey.getN());
        	str_encry += temp.toString() + "-";
        }
        return str_encry;
        
	}
	
	/**
	 * Convert String to ASCII
	 * @param str
	 * @return
	 */
	public static List<Integer> StringToASCII(String str)
	{
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < str.length(); i++)
		{
		    char c = str.charAt(i);
		    list.add(new Integer(c));
		    //Process char
		}
		return list;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		List<Integer> list = StringToASCII("Bonjour !");
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("\n");
		PublicKey publicKey = new PublicKey();
		String list2 = encrypt(publicKey, "Bonjour !");
        System.out.println("List2  " + list2);
        
		PrivateKey privateKey = new PrivateKey(publicKey);
        String decrypted = decrypt(privateKey, list2);
        System.out.println("Decrypted = " + decrypted);
	}

}
