package RSA;

import java.math.BigInteger;
import java.util.Random;

public class PublicKey 
{
	private int p;	
	private int q;
	private int e;	
	
	public PublicKey()
	{
		
	}
	
	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public int getE() {
		return e;
	}

	public void setE(int e) {
		this.e = e;
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

	
	public String toString()
	{
		//TODO: corriger
		return "Clé publique (" + this.e + "," + this.e + ")";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
	}

}
