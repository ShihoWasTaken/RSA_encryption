package RSA;

import java.math.BigInteger;

public class PrivateKey
{
	private int u;
	private PublicKey publicKey;
	
	
	
	public PrivateKey(PublicKey publicKey)
	{
		int r = 0;
		int v = 0;
		// TODO: mieux gérer les cast et regarder s'il peut y avoir overflow
		this.u = this.extendedEuclide(Integer.parseInt(publicKey.getE().toString()), Integer.parseInt(publicKey.getM().toString()), r, this.u, v);
		this.publicKey = publicKey;
	}
	
	/*
	 *  Entrée : a, b entiers (naturels)
	 * Sortie : r entier (naturel) et  u, v entiers relatifs tels que r = pgcd(a, b) et r = a*u+b*v
	 */
	public int extendedEuclide(int a, int b, int r, int u, int v)
	{
		/*
		 * 
		 * Initialisation : r := a, r' := b, u := 1, v := 0, u' := 0, v' := 1
		 * q  quotient entier
		 * rs, us, vs  variables de stockage intermédiaires
		 */
		r = a;
		int r_ = b;
		u = 1;
		v = 0;
		int u_ = 0;
		int v_ = 1;
		
		int q;
		int rs;
		int us;
		int vs;
		
		/*
		 * tant que (r' ≠ 0) faire
		 * 		q := r÷r'
    	 * 		rs := r, us := u, vs := v,
    	 * 		r := r', u := u', v := v',
    	 * 		r' := rs - q*r', u' = us - q*u', v' = vs - q*v'
   		 * fait
   		 */
		while(r_ != 0)
		{
			q = r / r_;
			rs = r;
			us = u;
			vs = v;
			r = r_;
			u = u_;
			v = v_;
			r_ = rs - q*r_;
			u_ = us - q*u_;
			v_ = vs - q*v_;
		}	
		return u_ + u;
	}
	
	public int getU()
	{
		return this.u;
	}
	
	public PublicKey getPublicKey()
	{
		return this.publicKey;
	}
	
	public String toString()
	{
		return "Clé privée(" + this.publicKey.getN() + "," + this.getU() + ")" + "\n"
				+ "p = " + this.getPublicKey().getP() + "\n"
				+ "q = " + this.getPublicKey().getQ() + "\n"
				+ "n = " + this.getPublicKey().getN() + "\n"
				+ "m = " + this.getPublicKey().getM() + "\n"
				+ "e = " + this.getPublicKey().getE();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PublicKey publicKey = new PublicKey(new BigInteger("53"), new BigInteger("97"));
		System.out.println(publicKey);
		
		System.out.println("\n\n");
		
		PrivateKey privateKey = new PrivateKey(publicKey);
		System.out.println(privateKey);
	}

}
