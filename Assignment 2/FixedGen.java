
public class FixedGen 
{
	public static void main(String[] args)
	{
		int sizing = Integer.parseInt(args[0]);
	
		long [] newrand = fixrand(sizing);
		for (int i = 0; i <= newrand.length - 1; i++)
		{
			System.out.print(newrand[i] + " ");
		}
	}
	
	//fixed gen function
	public static long[] fixrand(int size)
	{
		//condition to check if size is appropriate
		if (size != 10 && size != 100 && size != 10000 && size != 1000000)
		{
			System.out.print("Improper value for size. Please try again with either 10, 100, 10000 or, 1000000");
			System.exit(1);
		}
		
		long[] n = new long [size];
		for (int i = 0; i <= size - 1; i++)
		{
			n[i] = i;
		}
		
		return n;
	}

}

