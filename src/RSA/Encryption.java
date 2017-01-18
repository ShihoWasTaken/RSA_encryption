package RSA;

import java.math.BigInteger;
import java.util.Random;

public class Encryption {
	
	
	public static void decrypt(String message)
	{
		
	}
	
	public static void encrypt(String message)
	{
		
	}
	
	public static int CharToASCII(final char character)
	{
		return (int)character;
	}

	public static char ASCIIToChar(final int ascii)
	{
		return (char)ascii;
	}
	
	public static BigInteger getN(BigInteger p, BigInteger q)
	{
		return p.multiply(q);
	}
	
	public static BigInteger getM(BigInteger p, BigInteger q)
	{
		BigInteger pMoins = p.subtract(BigInteger.ONE);
		BigInteger qMoins =  q.subtract(BigInteger.ONE);
		return pMoins.multiply(qMoins);
	}

	public static BigInteger getE(BigInteger m)
	{
		boolean condition = false;
		BigInteger e = null;
		Random rdn = new Random();
		while(!condition)
		{
			e = BigInteger.probablePrime(8, rdn);
			if(m.gcd(e)  == BigInteger.ONE)
			{
				condition = true;
			}
		}
		return e;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
