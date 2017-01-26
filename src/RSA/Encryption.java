package RSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import run.SocketClient;

public class Encryption {

	// Logger
	static protected Logger logger =  Logger.getLogger(SocketClient.class);
	
	public static String decrypt(PrivateKey privateKey, String cryptedMessage)
	{
		List<BigInteger> newList 	= new ArrayList<BigInteger>();
		String[] tabCrypted 		= cryptedMessage.split("-");
		
        for(int i = 0; i < tabCrypted.length; i++)
        {
        	if(!tabCrypted[i].equals("-") && tabCrypted[i] != null){
        		BigInteger letter = new BigInteger(tabCrypted[i]);
	        	BigInteger temp = letter.modPow(privateKey.getU(),  privateKey.getPublicKey().getN());
	        	newList.add(temp);
        	}
        }

		StringBuilder sb = new StringBuilder();
        for(int i = 0; i < newList.size(); i++)
        {
        	sb.append((char) newList.get(i).byteValue());
        }
		String str = sb.toString();
        return str;
	}
	
	public static String encrypt(PublicKey publicKey, String message)
	{
		List<Integer> raw = StringToASCII(message);
		String str_encry = "";
		
        for(int i = 0; i < raw.size(); i++)
        {
        	BigInteger letter = new BigInteger(raw.get(i).toString());
        	BigInteger temp = letter.modPow(publicKey.getE(),  publicKey.getN());
        	str_encry += temp.toString()+"-";
        }
        return str_encry;
	}
	
	public static int CharToASCII(final char character)
	{
		return (int)character;
	}
	
	
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

	public static char ASCIIToChar(final int ascii)
	{
		return (char)ascii;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		/*List<Integer> list = StringToASCII("Bonjour !");
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("\n");
		PublicKey publicKey = new PublicKey(new BigInteger("53"), new BigInteger("97"));
		List<BigInteger> list2 = encrypt(publicKey, "Bonjour !");
        for(int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }
		PrivateKey privateKey = new PrivateKey(publicKey);
        String decrypted = decrypt(privateKey, list2);
        System.out.println("Decrypted = " + decrypted);*/
	}

}
