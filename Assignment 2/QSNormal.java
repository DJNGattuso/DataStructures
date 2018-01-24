
public class QSNormal 
{
	public static void main(String[] args)
	{
		long[] sequence = new long [args.length]; //create an array of size of the elements array
		System.out.print("The inputs are: ");
		for (int i = 0; i <= args.length - 1; i++) //convert each string in elements to int and store in the new array + prints sequence
		{
			sequence[i] = Integer.parseInt(args[i]);
			System.out.print(sequence[i] + " ");
		}
		
		sort(sequence); //calls sort function
		
		System.out.print("\nThe sorted inputs are: ");
		for (int i = 0; i <= sequence.length - 1; i++)
		{
			System.out.print(sequence[i] + " ");
		}
	}
	
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
		
		//initial found as false
		boolean found_greater = false;
		boolean found_less = false;
		
		long temp;
		
		//new top and bottom values for recursion
		int new_top = top_element;
		int new_bottom = bottom_element;

				
		//loop to sort
		while (bottom_element < pivot_element && pivot_element < top_element)
		{
			if (found_greater == true)
			{}
			else if (array[bottom_element] > array[pivot_element]) //checks for greater value than pivot below pivot
			{
				found_greater = true;
			}
			else { ++bottom_element;}

			if (found_less == true)
			{}
			else if (array[top_element] <= array[pivot_element]) //checks smaller value than pivot above pivot
			{
				found_less = true;
			}
			else { --top_element;}
			
			//condition to swap
			if (found_greater == true && found_less == true)
			{
				temp = array[bottom_element];
				array[bottom_element] = array[top_element];
				array[top_element] = temp;
						
				found_greater = false;
				found_less = false;
				++bottom_element;
				--top_element;
			}
			
			//safety swap
			if (bottom_element >= pivot_element && pivot_element < top_element) //case that bottom is done and top isn't
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
			else if (top_element <= pivot_element && bottom_element < pivot_element) //case that top is done and bottom isn't
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
		
		//partition array for recursion
		bottom_element = pivot_element + 1;
		top_element = pivot_element -1;
		
		//recursion
		if ((new_top - bottom_element) > 1)
		{
			quicksorting(array,bottom_element,new_top);
		}
		if ((top_element - new_bottom) > 1)
		{
			quicksorting(array,new_bottom,top_element);
		}	
	}
}
