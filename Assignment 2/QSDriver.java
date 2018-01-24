
public class QSDriver 
{
	public static void main(String[] args)
	{	
		//get the current time
		long begin = System.currentTimeMillis();
		System.out.print("Program starting at time: " + begin + " milliseconds\n"); //print start time
		
		int sortType = args[0].compareTo("QSNormal"); //check which sort method to use
		int gen = args[1].compareTo("RandomGen"); //check which generating method to use
		int length = Integer.parseInt(args[2]); //fetch and convert the length from args
		
		//check length 
		if (length != 10 && length != 100 && length != 10000 && length != 1000000)
		{
			System.out.print("Improper value for size. Please try again with either 10, 100, 10000 or, 1000000");
			System.exit(1);
		}
		
		long[] numberToBeSorted; //variable to store sequence from generator
		if (gen == 0) //RandomGen
		{
			long seed = System.currentTimeMillis(); //defining the seed
			
			if (args.length == 4) //if seed is passed
			{
				seed = Integer.parseInt(args[3]); //seed takes value from args[3]
			}
			
			numberToBeSorted = RandomGen.getRandom(length,seed); //calls random gen function 
		}
		else 
		{
			numberToBeSorted = FixedGen.fixrand(length); //calls fixed gen function
		}
		
		//print the test sequence
		System.out.print("The " + args[1] + " test inputs are: ");
		for (int i = 0; i <= numberToBeSorted.length - 1; i++)
		{
			System.out.print(numberToBeSorted[i] + " ");
		}
		
		//calls the sorting
		if (sortType == 0)
		{
			QSNormal.sort(numberToBeSorted);
		}
		else { QSInsertion.sort(numberToBeSorted);}

		//prints the sorted sequence
		System.out.print("\nThe sorted inputs are: ");
		for (int i = 0; i <= numberToBeSorted.length - 1; i++)
		{
			System.out.print(numberToBeSorted[i] + " ");
		}
		
		//get end time
		long end = System.currentTimeMillis();
		System.out.print("\nProgram ending at time: " + end + " milliseconds");
		
		//get the total time of the program
		long total_time = (end - begin)*1000;
		System.out.print("\nThe total time is: " + total_time + " microseconds");
		
	}
}
