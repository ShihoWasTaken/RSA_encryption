package RSA;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class PublicKey
{
	private BigInteger p;	
	private BigInteger q;
	private BigInteger m = null;
	private BigInteger e = null;	
	
	public PublicKey()
	{
		Random rand = new Random();		
		this.p = BigInteger.probablePrime(32, rand);
		this.q = BigInteger.probablePrime(32, rand);
		while(this.q.equals(this.p))
		{
			this.q = BigInteger.probablePrime(32, rand);
		}
	}
	
	public PublicKey(BigInteger p, BigInteger q)
	{
		this.p = p;
		this.q = q;
	}
	
	public BigInteger getP() {
		return p;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}

	public BigInteger getQ() {
		return q;
	}

	public void setQ(BigInteger q) {
		this.q = q;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}
	
	public BigInteger getE()
	{
		// TODO: retirer ça une fois les tests finis
		return new BigInteger("7");
		/*
		if(this.e == null)
		{
			boolean condition = false;
			BigInteger e = null;
			Random rdn = new Random();
			while(!condition)
			{
				e = BigInteger.probablePrime(8, rdn);
				if(this.getM().gcd(e).equals(BigInteger.ONE))
				{
					condition = true;
				}
			}
			this.e = e;
			return e;
		}
		else
		{
			return this.e;
		}
		*/
	}

	public BigInteger getN() {
		return this.p.multiply(this.q);
	}
	
	public BigInteger getM()
	{
		if(this.m == null)
		{
			BigInteger pMoins = this.p.subtract(BigInteger.ONE);
			BigInteger qMoins =  this.q.subtract(BigInteger.ONE);
			this.m = pMoins.multiply(qMoins);
			return this.m;
		}
		else
		{
			return this.m;
		}

	}
	
	public String toString()
	{
		return "Clé publique(" + this.getN() + "," + this.getE() + ")" + "\n"
				+ "p = " + this.getP() + "\n"
				+ "q = " + this.getQ() + "\n"
				+ "n = " + this.getN() + "\n"
				+ "m = " + this.getM() + "\n"
				+ "e = " + this.getE();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		PublicKey publicKey = new PublicKey(new BigInteger("53"), new BigInteger("97"));
		System.out.println(publicKey);
		System.out.println("\n\n");
		PublicKey pub_key2 = new PublicKey();
		System.out.println(pub_key2);
	}

}
