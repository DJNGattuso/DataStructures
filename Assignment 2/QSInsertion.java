
public class QSInsertion 
{
	//static values that will be constantly used
	static boolean found_greater = false;
	static boolean found_less = false;
	static long temp;
	
	public static void main(String[] args)
	{
		long[] input = new long [args.length]; //create an array of size of the elements array
		
		System.out.print("The inputs are: ");
		for (int i = 0; i <= args.length - 1; i++) //convert each string in elements to int and store in the new array + print out the sequence
		{
			input[i] = Integer.parseInt(args[i]);
			System.out.print(input[i] + " ");
		}
		
		sort(input); //calls sort function
		
		//prints sorted sequence
		System.out.print("\nThe sorted inputs are: ");
		for (int i = 0; i <= input.length -1; i++)
		{
			System.out.print(input[i] + " ");
		}
	}
	
		//function sort
		public static void sort(long[] input)
		{
			//begin sorting with initializing bottom, top and middle
			int bottom = 0;
			int top = input.length - 1;	
			
			quicksorting(input,bottom,top); //calls quicksort function
			
		}
		
		//quicksort function
		public static void quicksorting(long[] array, int bottom_element, int top_element)
		{
			int pivot_element = (bottom_element + top_element) >> 1; //pivot will be the middle. This prevents from swapping the pivot later
		
			//initialize found values as false
			boolean found_greater = false; 
			boolean found_less = false;
			
			long temp; //temp value
			
			//variables for new top and bottom in recursion
			int new_top = top_element;
			int new_bottom = bottom_element;

					
			//loop to sort
			while (bottom_element < pivot_element && pivot_element < top_element)
			{
				if (found_greater == true)
				{}
				else if (array[bottom_element] > array[pivot_element]) //checks to find a bigger value under the pivot
				{
					found_greater = true;
				}
				else { ++bottom_element;}

				if (found_less == true)
				{}
				else if (array[top_element] <= array[pivot_element]) //checks to find a smaller value above the pivot
				{
					found_less = true;
				}
				else { --top_element;}
				
				if (found_greater == true && found_less == true) //condition to swap
				{
					temp = array[bottom_element];
					array[bottom_element] = array[top_element];
					array[top_element] = temp;
							
					found_greater = false;
					found_less = false;
					++bottom_element;
					--top_element;
				}
				
				//safety conditions
				if (bottom_element >= pivot_element && pivot_element < top_element) //case that bottom is done but top isn't
				{
					while (pivot_element < top_element)
					{
						if (found_less == true)
						{
							temp = array[top_element];
						
							for (int i = top_element; i > pivot_element; i--)
							{
								array[i] = array[i-1];
							}
							
							array[pivot_element] = temp;
							++pivot_element;
							
							found_less = false;
						}
						if (array[top_element] <= array[pivot_element])
						{
							found_less = true;
						}
						else { --top_element;}
					}
					
				}
				else if (top_element <= pivot_element && bottom_element < pivot_element) //case that top is done but bottom isn't
				{
					while (bottom_element < pivot_element)
					{
						if (found_greater == true)
						{
							temp = array[bottom_element];
						
							for (int i = bottom_element; i < pivot_element; i++)
							{
								array[i] = array[i+1];
							}
						
							array[pivot_element] = temp;
							--pivot_element;
							found_greater = false;
						}
						if (array[bottom_element] > array[pivot_element])
						{
							found_greater = true;
						}
						else { ++bottom_element;}
					}
				}
			}
			
			//define new bottom and top
			bottom_element = pivot_element + 1;
			top_element = pivot_element -1;
			
			//begin recursion
			if ((new_top - bottom_element) > 10)
			{
				quicksorting(array,bottom_element,new_top);
			}
			else { insertion(array,bottom_element,new_top);}
		
			
			if ((top_element - new_bottom) > 10)
			{
				quicksorting(array,new_bottom,top_element);
			}
			else { insertion(array,new_bottom,top_element);}
		}
			
		//function for insertion
		public static void insertion(long[] array, int low, int high)
		{
			long temp_value;
			
			//loop condition to go through all values in array
			for (int i = 1; i <= array.length -1; i++)
			{
				//loop condition to go through all values before current i
				for (int n = i; n > 0; n--)
				{
					if (array[n]<array[n-1])
					{
						temp_value = array[n];
						array[n] = array[n-1];
						array[n-1] = temp_value;
					}
				}
			}
		}
}
