import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Huffman extends GeneralTree
{
	//variables to track in huffman tree
	private int numberOccurence = -1;
	private NodeForTree rootPriority = null;
	private int elementsInPriority = 0;
	private String[] allChars = new String[256];
	private int characterNumb;
	//variables for encoding huffman
	private String[] encodingStream = new String[50];
	private int encodingTrack = -1;
	private String bitstream;
	
	//function to create a new char
	private void createChar(char value)
	{
		NodeForTree newNode = new NodeForTree(null, value, null, null, 0, 1, 0, null, null); //create node
		priorityQ(newNode); //add in priority
	}
	
	//function to create priority queue
	private void priorityQ(NodeForTree nextNode)
	{
		if (rootPriority != null) //if there is 1 or more elements in queue
		{
			addInPriority(rootPriority, nextNode); 
		}
		else //no elements yet
		{
			rootPriority = nextNode;
			numberOccurence = 0;
			rootPriority.occurence = numberOccurence;
			elementsInPriority = 1;
		}
	}
	
	//function for priority queue when elements exist
	private void addInPriority(NodeForTree currentNode, NodeForTree newNode)
	{
		if (currentNode.value != newNode.value) //check to see if new element == to current node
		{
			if (currentNode.rightPriority != null){addInPriority(currentNode.rightPriority,newNode);} //if there's another node in the list, go to that
			else //add new node in list
			{
				++numberOccurence;
				newNode.occurence = numberOccurence;
				currentNode.rightPriority = newNode;
				newNode.leftPriority = currentNode;
				++elementsInPriority;
				sortPriority(newNode);
			}	
		}
		else //found a duplicate, increase frequency and sort it
		{
			++currentNode.frequency;
			sortPriority(currentNode);
		}
	}
	
	//function to sort the priority queue
	private void sortPriority(NodeForTree nodeToSort)
	{
		if (nodeToSort.leftPriority == null){sortGreater(nodeToSort.rightPriority,nodeToSort);} //if node to sort is the root
		else if (nodeToSort.rightPriority == null){sortLess(nodeToSort.leftPriority,nodeToSort);} //if node to sort is the end of the queue
		else
		{
			NodeForTree leftNode = nodeToSort.leftPriority;
			NodeForTree rightNode = nodeToSort.rightPriority;
			if (nodeToSort.frequency < leftNode.frequency){sortLess(leftNode,nodeToSort);} //is the node to sort less frequent 
			else if (rightNode.frequency < nodeToSort.frequency){sortGreater(rightNode,nodeToSort);} //is it more frequent
			else //check its occurency
			{
				if (nodeToSort.occurence < leftNode.occurence){sortLess(leftNode,nodeToSort);} //came first
				else if (rightNode.occurence < nodeToSort.occurence){sortGreater(rightNode,nodeToSort);} //came after
			}	
		}
	}
	
	//function to sort towards the root of the list
	private void sortLess(NodeForTree nextNode, NodeForTree sortingNode)
	{
		NodeForTree temp = null;NodeForTree temp2;
		//sort first by frequency
		if (sortingNode.frequency < nextNode.frequency) 
		{
			if (nextNode.leftPriority != null) //is there a left node two nodes down
			{
				if (sortingNode.rightPriority != null) //is there a right node two nodes up
				{
					//perform a switch
					temp = sortingNode.rightPriority;
					temp2 = nextNode.leftPriority;
				
					sortingNode.leftPriority = temp2;
					nextNode.leftPriority = sortingNode;
					temp.leftPriority = nextNode;
					nextNode.rightPriority = temp;
					sortingNode.rightPriority = nextNode;
					temp2.rightPriority = sortingNode;
				
					sortLess(sortingNode.leftPriority,sortingNode); //call sort 
				}
				else //no right on two nodes up
				{
					//perform switch
					temp2 = nextNode.leftPriority;
					
					sortingNode.leftPriority = temp2;
					nextNode.leftPriority = sortingNode;
					nextNode.rightPriority = temp;
					sortingNode.rightPriority = nextNode;
					temp2.rightPriority = sortingNode;
				
					sortLess(sortingNode.leftPriority,sortingNode); //call sort
				}
			}
			else //there is no far left, therefore left of the sorting node is the root
			{	//repeat of above but no recursion 
				if (sortingNode.rightPriority != null)
				{
					temp = sortingNode.rightPriority;
					temp2 = null;
				
					sortingNode.leftPriority = temp2;
					nextNode.leftPriority = sortingNode;
					temp.leftPriority = nextNode;
					nextNode.rightPriority = temp;
					sortingNode.rightPriority = nextNode;
					sortingNode = rootPriority;
				}
				else
				{
					temp2 = null;
					
					sortingNode.leftPriority = temp2;
					nextNode.leftPriority = sortingNode;
					nextNode.rightPriority = temp;
					sortingNode.rightPriority = nextNode;
					sortingNode = rootPriority;
				}
				
			}
		}
		else if (sortingNode.frequency == nextNode.frequency)//when frequency are the same, sort by occurence
		{
			//repeat of above but for occurence
			if (sortingNode.occurence < nextNode.occurence)
			{
				if (nextNode.leftPriority != null)
				{
					if (sortingNode.rightPriority != null)
					{
						temp = sortingNode.rightPriority;
						temp2 = nextNode.leftPriority;
					
						sortingNode.leftPriority = temp2;
						nextNode.leftPriority = sortingNode;
						temp.leftPriority = nextNode;
						nextNode.rightPriority = temp;
						sortingNode.rightPriority = nextNode;
						temp2.rightPriority = sortingNode;
					
						sortLess(sortingNode.leftPriority,sortingNode);
					}
					else
					{
						temp2 = nextNode.leftPriority;
						
						sortingNode.leftPriority = temp2;
						nextNode.leftPriority = sortingNode;
						nextNode.rightPriority = temp;
						sortingNode.rightPriority = nextNode;
						temp2.rightPriority = sortingNode;
					
						sortLess(sortingNode.leftPriority,sortingNode);
					}
				}
				else
				{
					if (sortingNode.rightPriority != null)
					{
						temp = sortingNode.rightPriority;
						temp2 = null;
					
						sortingNode.leftPriority = temp2;
						nextNode.leftPriority = sortingNode;
						temp.leftPriority = nextNode;
						nextNode.rightPriority = temp;
						sortingNode.rightPriority = nextNode;
						sortingNode = rootPriority;
					}
					else
					{
						temp2 = null;
						
						sortingNode.leftPriority = temp2;
						nextNode.leftPriority = sortingNode;
						nextNode.rightPriority = temp;
						sortingNode.rightPriority = nextNode;
						sortingNode = rootPriority;
					}
				}
			}
		}
	}
	
	//function to sort for greater, exactly the same as for smaller but condition checks are opposite
	private void sortGreater(NodeForTree nextNode, NodeForTree sortingNode)
	{
		NodeForTree temp;NodeForTree temp2;
	
		if (nextNode.frequency < sortingNode.frequency) 
		{
			if (nextNode.rightPriority != null) //there's is a right element
			{
				if (sortingNode.leftPriority != null) //sorting isnt the root
				{
					temp = sortingNode.leftPriority;
					temp2 = nextNode.rightPriority;
				
					sortingNode.rightPriority = temp2;
					temp2.leftPriority = sortingNode;
					temp.rightPriority = nextNode;
					nextNode.leftPriority = temp;
					sortingNode.leftPriority = nextNode;
					nextNode.rightPriority = sortingNode;
				
					sortGreater(sortingNode.rightPriority,sortingNode);
				}
				else //it is the root
				{
					temp = null;
					temp2 = nextNode.rightPriority;
				
					sortingNode.rightPriority = temp2;
					temp2.leftPriority = sortingNode;
					nextNode.leftPriority = temp;
					sortingNode.leftPriority = nextNode;
					nextNode.rightPriority = sortingNode;
					
					if (sortingNode == rootPriority)
					{
						rootPriority = nextNode;
					}
					sortGreater(sortingNode.rightPriority,sortingNode);
				}
			}
			else  //there isnt a right element
			{
				if (sortingNode.leftPriority != null) //sorting isnt the root
				{
					temp = sortingNode.leftPriority;
					temp2 = null;
			
					sortingNode.rightPriority = temp2;
					temp.rightPriority = nextNode;
					nextNode.leftPriority = temp;
					sortingNode.leftPriority = nextNode;
					nextNode.rightPriority = sortingNode;
				}
				else //it is the root
				{
					temp = null;
					temp2 = null;
					
					sortingNode.rightPriority = temp2;
					nextNode.leftPriority = temp;
					sortingNode.leftPriority = nextNode;
					nextNode.rightPriority = sortingNode;
						
					if (sortingNode == rootPriority)
					{
						rootPriority = nextNode;
					}
					
				}
			}
		}
		else if (nextNode.frequency == sortingNode.frequency)//when frequency are the same, sort by occurence
		{
			if (nextNode.occurence < sortingNode.occurence)
			{
				if (nextNode.rightPriority != null)
				{
					if (sortingNode.leftPriority != null) //sorting isnt the root
					{
						temp = sortingNode.leftPriority;
						temp2 = nextNode.rightPriority;
					
						sortingNode.rightPriority = temp2;
						temp2.leftPriority = sortingNode;
						temp.rightPriority = nextNode;
						nextNode.leftPriority = temp;
						sortingNode.leftPriority = nextNode;
						nextNode.rightPriority = sortingNode;
					
						sortGreater(sortingNode.rightPriority,sortingNode);
					}
					else //it is the root
					{
						temp = null;
						temp2 = nextNode.rightPriority;
					
						sortingNode.rightPriority = temp2;
						temp2.leftPriority = sortingNode;
						nextNode.leftPriority = temp;
						sortingNode.leftPriority = nextNode;
						nextNode.rightPriority = sortingNode;
						
						if (sortingNode == rootPriority)
						{
							rootPriority = nextNode;
						}
						sortGreater(sortingNode.rightPriority,sortingNode);
					}
				}
				else
				{
					if (sortingNode.leftPriority != null) //sorting isnt the root
					{
						temp = sortingNode.leftPriority;
						temp2 = null;
					
						sortingNode.rightPriority = temp2;
						temp.rightPriority = nextNode;
						nextNode.leftPriority = temp;
						sortingNode.leftPriority = nextNode;
						nextNode.rightPriority = sortingNode;
					}
					else //it is the root
					{
						temp = null;
						temp2 = null;
					
						sortingNode.rightPriority = temp2;
						nextNode.leftPriority = temp;
						sortingNode.leftPriority = nextNode;
						nextNode.rightPriority = sortingNode;
						
						if (sortingNode == rootPriority)
						{
							rootPriority = nextNode;
						}
					}
				}
			}
		}
	}
	
	//function to build huffman tree
	private void buildHuffman()
	{
		NodeForTree node2;
		
		while (elementsInPriority > 1) //loop through priority queue, take first 2 elements and merge, return the merged and sort it to its place in the queue
		{
			node2 = rootPriority.rightPriority;
			mergeNodes(rootPriority,node2);
			--elementsInPriority;
			if (elementsInPriority > 2){sortPriority(rootPriority);}
		}
		
		encodeHuffman(); //encode the characters in the tree
	}
	
	//function to check two nodes in the queue and order them in where they belong between a new meged node in the huffman tree
	private void mergeNodes(NodeForTree temp, NodeForTree temp2)
	{
		int newfrequency = (temp.frequency) + (temp2.frequency);
		
		//check which of the two nodes as a greater frequency, if the same, check which came first
		if (temp.frequency == temp2.frequency)
		{
			if (temp.occurence < temp2.occurence){addHuffElement(temp, temp2, newfrequency);}
			else {addHuffElement(temp2,temp,newfrequency);}
		}
		else if (temp.frequency < temp2.frequency){addHuffElement(temp,temp2,newfrequency);}
		else {addHuffElement(temp2,temp,newfrequency);}		
		
		rootPriority = root; //new merged node becomes root in the priority queue
		if (elementsInPriority > 2)
		{
			rootPriority.rightPriority = temp2.rightPriority;
			temp = temp2.rightPriority;
			
			temp.leftPriority = rootPriority;
			rootPriority.leftPriority = null;
		}
		else
		{
			rootPriority.rightPriority = null;
			rootPriority.leftPriority = null;
		}
	}
	
	//function to add merge node in huffman tree with its left and right child
	private void addHuffElement(NodeForTree left, NodeForTree right, int frequency)
	{	
		++numberOccurence;
		NodeForTree mergedNode = new NodeForTree(left, '0', right, null, 0, frequency, numberOccurence, null, null);
		left.parent = mergedNode;
		right.parent = mergedNode;
		
		root = mergedNode;
	}
	
	//function to encode huffman tree
	private void encodeHuffman()
	{
		for (int i = 0; i <= 255; i++) //initialize all characters with 0
		{
			allChars[i] = "0";
		}
		
		getCode(root); //search in tree for the codes to encode
	}
	
	private void getCode(NodeForTree temp)
	{
		if (temp.leftpoint != null) //theres a left child
		{
			++encodingTrack;
			encodingStream[encodingTrack] = "0"; //add a 0 to encoding array
			getCode(temp.leftpoint); //encode left subtree
		}
		if (temp.rightpoint != null) //there is a right child
		{
			++encodingTrack;
			encodingStream[encodingTrack] = "1"; //add 1 to encoding array
			getCode(temp.rightpoint); //encode right subtree
		}
		if (temp.leftpoint == null && temp.rightpoint == null) //there is no child (ie, leaf node found)
		{
			if (temp.value != '0') //make sure that this isn't a merged node
			{
				NodeForTree parenting = temp.parent;
				
				if (parenting.leftpoint == temp) //is the leaf to the left of its parent
				{
					++encodingTrack;
					encodingStream[encodingTrack] = "0"; //add a 0
					bitstream = getBitstream(); //get the bitstream
					characterNumb = (int) temp.value; //convert character to its asc11 code
					allChars[characterNumb] = bitstream; //store bitstream to array 
					--encodingTrack;
				}
				else if (parenting.rightpoint == temp) //is leaf to right of its parent
				{
					++encodingTrack;
					encodingStream[encodingTrack] = "1"; //add 1
					bitstream = getBitstream();
					characterNumb = (int) temp.value;
					allChars[characterNumb] = bitstream;
					--encodingTrack;
				}
			}
		}
		--encodingTrack;
	}
	
	//function to get the bitstream
	private String getBitstream() 
	{
		String bits = encodingStream[0]; //store first bit in stream
		
		for (int i = 1; i <= encodingTrack - 1; ++i) //loop through array 
		{
			bits = bits + encodingStream[i]; //add the value to the bitstream
		}
		
		return bits;
	}
	
	//function to print the code of a string 
	private void getEncoding(String temp)
	{
		char tempChar;
		
		System.out.print("The code for " + temp + " is: ");
		for (int i = 0; i < temp.length(); ++i)  //go through the string
		{
			tempChar  = temp.charAt(i); //get a character
			characterNumb = (int) tempChar; //convert the character to int
			System.out.print(allChars[characterNumb]); //print its code
		}
	}

	/*private void printPriority(NodeForTree temp)
	{
		System.out.print("Currently: ");
		printp(temp);
		System.out.print("\n");
	}
	
	private void printp(NodeForTree temp)
	{
		System.out.print(temp.value + " ");	
		if (temp.rightPriority != null)
		{			
			printp(temp.rightPriority);
		}
	}*/

	
	public static void main(String[] args) 
	{
		char charOfString;
		String fromUser = null;
		Huffman testing = new Huffman(); //create new object
		
        String fileName = args[0]; //file name comes from first argument
        String lineInText = null;
        
        //get file
        FileReader fileReader = null;
		try {fileReader = new FileReader(fileName);} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		//read file
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try 
        {
			while((lineInText = bufferedReader.readLine()) != null) 
			{
				for (int i = 0; i < lineInText.length(); ++i) 
				{
					charOfString  = lineInText.charAt(i); //get the character
					testing.createChar(charOfString); //create the character
				}
			}
		} 
        catch (IOException e) {e.printStackTrace();}   

        try {bufferedReader.close();} 
        catch (IOException e) {e.printStackTrace();}         

        testing.buildHuffman(); //build huffman tree
		
       BufferedReader findValue = new BufferedReader(new InputStreamReader(System.in)); //create variable to read
       System.out.print("Please enter an input to find its encoding: ");
       
       try {fromUser = findValue.readLine();} //read the string
       catch (IOException e) {e.printStackTrace();}
       
       testing.getEncoding(fromUser); //get the encoding of the string
	}
}
