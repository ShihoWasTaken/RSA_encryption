package RSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Encryption {
	
	
	public static String decrypt(PrivateKey privateKey, List<BigInteger> cryptedMessage)
	{
		List<BigInteger> newList = new ArrayList<BigInteger>();
        for(int i = 0; i < cryptedMessage.size(); i++)
        {
        	BigInteger letter = new BigInteger(cryptedMessage.get(i).toString());
        	BigInteger temp = letter.modPow(new BigInteger(Integer.toString(privateKey.getU())),  privateKey.getPublicKey().getN());
        	newList.add(temp);
        }

		StringBuilder sb = new StringBuilder();
        for(int i = 0; i < newList.size(); i++)
        {
        	String charStr = newList.get(i).toString();
        	int foo = Integer.parseInt(charStr);
        	sb.append((char) foo);
        }
		String str = sb.toString();
        return str;
	}
	
	public static List<BigInteger> encrypt(PublicKey publicKey, String message)
	{
		List<Integer> raw = StringToASCII(message);
		List<BigInteger> newList = new ArrayList<BigInteger>();
        for(int i = 0; i < raw.size(); i++)
        {
        	BigInteger letter = new BigInteger(raw.get(i).toString());
        	BigInteger temp = letter.modPow(publicKey.getE(),  publicKey.getN());
        	newList.add(temp);
        }
        return newList;
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
		List<Integer> list = StringToASCII("Bonjour !");
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
        System.out.println("Decrypted = " + decrypted);
	}

}
