package RSA;

import java.math.BigInteger;

public class PrivateKey
{
	private BigInteger u;
	private PublicKey publicKey;
	
	
	
	public PrivateKey(PublicKey publicKey)
	{
		BigInteger r = new BigInteger("0");
		BigInteger v = new BigInteger("0");
		// TODO: mieux gérer les cast et regarder s'il peut y avoir overflow
		this.u = this.extendedEuclide(publicKey.getE(), publicKey.getM(), r, this.u, v);
		this.publicKey = publicKey;
	}
	
	/*
	 *  Entrée : a, b entiers (naturels)
	 * Sortie : r entier (naturel) et  u, v entiers relatifs tels que r = pgcd(a, b) et r = a*u+b*v
	 */
	public BigInteger extendedEuclide(BigInteger a, BigInteger b, BigInteger r, BigInteger u, BigInteger v)
	{
		/*
		 * 
		 * Initialisation : r := a, r' := b, u := 1, v := 0, u' := 0, v' := 1
		 * q  quotient entier
		 * rs, us, vs  variables de stockage intermédiaires
		 */
		r = a;
		BigInteger r_ = b;
		u = new BigInteger("1");
		v = new BigInteger("0");
		BigInteger u_ = new BigInteger("0");
		BigInteger v_ = new BigInteger("1");
		
		BigInteger q;
		BigInteger rs;
		BigInteger us;
		BigInteger vs;
		
		/*
		 * tant que (r' ≠ 0) faire
		 * 		q := r÷r'
    	 * 		rs := r, us := u, vs := v,
    	 * 		r := r', u := u', v := v',
    	 * 		r' := rs - q*r', u' = us - q*u', v' = vs - q*v'
   		 * fait
   		 */
		while(r.compareTo(BigInteger.valueOf(0)) != 1)
		{
			q = r.divide(r_);
			rs = r;
			us = u;
			vs = v;
			r = r_;
			u = u_;
			v = v_;
			r_ = rs.subtract(q.multiply(r_));
			u_ = us.subtract(q.multiply(u_));
			v_ = vs.subtract(q.multiply(v_));
		}	
		return u.add(u);
	}
	
	public BigInteger getU()
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
