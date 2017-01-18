package RSA;

import java.math.BigInteger;
import java.util.Random;

public class PrimeNumber
{

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	int value;
	
	private boolean isPrime(int n) {
	    if(n < 2) return false;
	    if(n == 2 || n == 3) return true;
	    if(n%2 == 0 || n%3 == 0) return false;
	    long sqrtN = (long)Math.sqrt(n)+1;
	    for(long i = 6L; i <= sqrtN; i += 6) {
	        if(n%(i-1) == 0 || n%(i+1) == 0) return false;
	    }
	    return true;
	}
	
	public PrimeNumber()
	{
		boolean isPrime = false;
		Random rand = new Random();
		int  n = 0;
		while(!isPrime)
		{
			n = rand.nextInt(1000) + 2;
			if(isPrime(n))
			{
				isPrime = true;
			}
		}
		this.value = n;
	}
	
	public PrimeNumber(int number) throws Exception
	{
		if(!isPrime(number))
		{
			throw new Exception("Nombre non premier");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < 10; ++i)
		{
			PrimeNumber prime = new PrimeNumber();
			System.out.println("Le nombre premier est " + prime.getValue());
		}
	}

}
