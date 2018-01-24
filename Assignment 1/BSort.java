//Nicholas Gattuso 40007087
//COEN 352 Assignment 1

//Question 1 (2 and 3 follow below the code)
//create public class called BSort
public class BSort 
{
	//create public static void main that will take arguments of type string from command prompt
	public static void main(String[] args)
	{
		//sequence of numbers comes from first element on the args array
		String sequence = args[0];
		String[] elements = sequence.split(","); //split the numbers in the sequence based off the comma
		int[] elements_int = new int [elements.length]; //create an array of size of the elements array
		for (int i = 1; i <= elements.length - 1; i++) //convert each string in elements to int and store in the new array
		{
			elements_int[i] = Integer.parseInt(elements[i]);
		}
		
		int key_number = Integer.parseInt(args[1]); //convert the string value of arg[1] (the key value) to int 
		
		//begin sorting with initializing bottom, top and middle
		int bottom_element = 0;
		int top_element = elements_int.length - 1;
		int middle_element = (bottom_element + top_element)/2;
		
		//loop to find the key number
		while (bottom_element <= top_element)
		{
			//recalculate the middle element after each iteration
			middle_element = (bottom_element + top_element)/2;
			
			if (elements_int[middle_element] == key_number) //found the key number
			{
				System.out.print(elements_int[middle_element] + "=");
				break;
			}
			else if (elements_int[middle_element] < key_number) //key number is greater than current middle value
			{
				System.out.print(elements_int[middle_element] + "< ");
				bottom_element = middle_element + 1; //recalculates the new bottom 
			}
			else //key number is less than the current middle value
			{ 
				System.out.print(elements_int[middle_element] + "> ");
				top_element = middle_element - 1; //recalculates the new top
			}		
		}
		if (elements_int[middle_element] != key_number) //key number has not been found
		{
			System.out.print("!");
		}
	}
}

/* Answer to question 2 and 3

2) In question 1, the algorithm used to sort the sequence is called Binary Sort. It is a sorting method containing theta(log(n)) time that we've seen in class and in the tutorial. 
This method works by taking the middle element in the sequence and compares it to the target value, slowly halving the sequence until the target is found or until the sequence can't be shortened.
In other words, it begins by taking the bottom to be the first element, the top to be the last element as seen in lines 23 and 24. The algorithm then checks the middle value. When the target is
above the middle value, we cut the bottom half by changing the bottom to the element just above the middle (as seen in line 41). When the target is below the middle value, we cut the top half
by changing the top to the element just below the middle (as seen in line 46). This process is repeated until the target value is found (in which we break out of the loop - as seen in line 36)
or until the bottom element becomes greater than the top element (while loop condition - show on line 28).

3) When using a string, mathematical operations such as addition and division won't be possible for the elements in the sequence. Therefore, we would need to modify the code in question 1 to
the functionality of a string variable (the major change will be in the sorting process as we won't be able to use '==','<' nor, '>'). To begin, when the program is called, an array of strings
is created. In question 1, we took the arguments and converted them into an array of integers; in this case, we would keep them in an array of strings: lines 14-18 can be removed. In line 20,
we converted the key value stored in args[1] to an integer; in this case, we can replace that line with String key_number = args[1]. We will then continue the rest of the program using 
elements and key_number. The operations for bottom, top and, middle elements don't change because they are indexes in the array. We now come to the major change for the program: the comparison
must be modified because string elements can't be compared using operators < > and ==. Instead, we can use the Comparable function: elements[middle_element].compareTo(key_number). This will be
written just under line 31 (after middle element has been calculated) and assigned to an integer variable. When the middle element is less than key number, a negative number will be assigned 
to the integer variable. When the middle element is equal, 0 will be assigned to the integer variable. When the middle element is bigger, a positive number will be assigned to the integer variable.
From here, we can modify the if statements to look at the value of the integer variable and proceed accordingly: first case (line 33) will look for the variable == 0, the second case (line 38)
will look for the variable < 0 and, the third case (line 43) will look for the variable > 0. With these changes, we will be able to do the Binary Sort using string instead of int. Below is the
modified version as just described. 

public class BSort 
{
	public static void main(String[] args)
	{
		String sequence = args[0];
		String[] elements = sequence.split(","); 
		
		String key_number = args[1];
		
		int bottom_element = 0;
		int top_element = elements.length - 1;
		int middle_element = (bottom_element + top_element)/2;
		
		while (bottom_element <= top_element)
		{
			middle_element = (bottom_element + top_element)/2;
			int compare = elements[middle_element].compareTo(key_number);
			         
			if (compare == 0) //found the key number
			{
				System.out.print(elements[middle_element] + "=");
				break;
			}
			else if (compare < 0) //key number is greater than current middle value
			{
				System.out.print(elements[middle_element] + "< ");
				bottom_element = middle_element + 1; 
			}
			else 
			{ 
				System.out.print(elements[middle_element] + "> ");
				top_element = middle_element - 1; 
			}		
		}
		if (elements[middle_element] != key_number) 
		{
			System.out.print("!");
		}
	}
}
*/