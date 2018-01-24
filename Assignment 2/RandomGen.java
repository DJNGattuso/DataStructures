import java.util.Random;

public class RandomGen 
{
	public static void main(String[] args)
	{
		int sizing = Integer.parseInt(args[0]);
		long seeding;
		if (args.length == 2) //if seed is passed
		{
			seeding = Integer.parseInt(args[1]);
		}
		else { seeding = System.currentTimeMillis();}
		
		//array to receive the random sequence
		long [] newrand = getRandom(sizing,seeding);
		
		for (int i = 0; i <= newrand.length - 1; i++)
		{
			System.out.print(newrand[i] + " ");
		}
	}
	
	//function to generate random values
	public static long[] getRandom(int size, long seed)
	{
		//condition to check if size is appropriate
		if (size != 10 && size != 100 && size != 10000 && size != 1000000)
		{
			System.out.print("Improper value for size. Please try again with either 10, 100, 10000 or, 1000000");
			System.exit(1);
		}
		
		//define value as random with seed
		Random value = new Random(seed);
		
		long n[] = new long [size]; //array to store value
		
		for (int i = 0; i <= n.length - 1; i++)
		{
			n[i] = value.nextInt(1000) + 1; //generate a random value from 0 to 1000 and store in n
		}
	
		return n;
	}

}
