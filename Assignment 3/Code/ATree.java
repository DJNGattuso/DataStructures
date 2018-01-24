import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ATree extends GeneralTree
{	
	//variables to track balance and parents in AVL
	private boolean unbalanced = false;
	private NodeForTree unbalancedNode = null;
	private int unbalancedValue = 0;
	private int parentChanges = 0;
	
	//new add function for AVL to include parent
	@Override
	public void addElement(int value)
	{	
		NodeForTree temp = new NodeForTree(null, value, null, null, 1, 0);
		
		if (root != null) //checks to see if a root already exist
		{
			adding(temp, root);
		}
		else { root = temp;}
		++numberOfAdds;
		
		setDepth(temp); //set the depth starting at the new value
		
		checkDepth(temp); //check the depth for any unbalanced in the new value
		while (unbalanced == true) //if unbalanced
		{
			rotate(unbalancedNode); //rotate at the unbalanced node
			unbalanced = false;
			unbalancedValue = 0;
			checkDepth(unbalancedNode);
		}
		unbalancedNode = null;
	}
	
	//adding elements
	private void adding(NodeForTree newValue, NodeForTree top)
	{
		++numberOfCompares;
		if (newValue.value < top.value) //is the new value less than current
		{
			if (top.leftpoint == null) //if theres no left child
			{
				top.leftpoint = newValue; 
				newValue.parent = top;
			}
			else //theres a left child
			{
				adding(newValue,top.leftpoint);
			}
		}
		else //value is greater than current
		{
			if (top.rightpoint == null) //no right child
			{
				top.rightpoint = newValue;
				newValue.parent = top;
			}
			else {adding(newValue,top.rightpoint);} //there is a right child
		}
	}

	//function to check depth
	public void checkDepth(NodeForTree temp)
	{
		//go up tree using parent and check depth, stop at root
		//if depth == 3, return that node
		//else return null
		if (temp.leftpoint == null && temp.rightpoint == null) {unbalancedValue = 0;}
		else if (temp.leftpoint != null && temp.rightpoint == null) {unbalancedValue = getDepth(temp.leftpoint);}
		else if (temp.leftpoint == null && temp.rightpoint != null) {unbalancedValue = (-1)*getDepth(temp.rightpoint);}
		else {unbalancedValue = (getDepth(temp.leftpoint) - getDepth(temp.rightpoint));}
		
		if (Math.abs(unbalancedValue) == 3)
		{ unbalanced = true; unbalancedNode = temp;}
		else
		{
			if (temp.parent == null){unbalanced = false;}
			else {checkDepth(temp.parent);}
		}
		
	}

	//function to set depth
	public void setDepth(NodeForTree temp)
	{
		//find max height between left and right child using max then add 1
		//repeat for all parents until root
		if (temp.leftpoint == null && temp.rightpoint == null) {temp.depth = 1;}
		else if (temp.leftpoint != null && temp.rightpoint == null) {temp.depth = temp.getDepth(temp.leftpoint) + 1;}
		else if (temp.leftpoint == null && temp.rightpoint != null) {temp.depth = temp.getDepth(temp.rightpoint) + 1;}
		else {temp.depth = Math.max(temp.getDepth(temp.leftpoint), temp.getDepth(temp.rightpoint)) + 1;}
		
		if (temp == root)
		{}
		else if (temp.parent == root){setDepth(temp.parent);}
	}
	
	//function to rotate
	public void rotate(NodeForTree temp)
	{
		NodeForTree temp2;
		if (unbalancedValue < 0) //is it unbalanced on the right
		{
			unbalancedValue = 0;
			
			//check the unbalance factor of the right child
			temp2 = temp.rightpoint;
			if (temp2.leftpoint == null && temp2.rightpoint == null) {unbalancedValue = 0;}
			else if (temp2.leftpoint != null && temp2.rightpoint == null) {unbalancedValue = getDepth(temp2.leftpoint);}
			else if (temp2.leftpoint == null && temp2.rightpoint != null) {unbalancedValue = (-1)*getDepth(temp2.rightpoint);}
			else {unbalancedValue = (getDepth(temp2.leftpoint) - getDepth(temp2.rightpoint));}
			
			if (unbalancedValue <= 0){rotateLeft(temp);} //single rotation if heavy on right
			else {doubleRotationLeft(temp);} //double rotation if right child is left heavy
		}
		else //unbalanced on the left
		{
			unbalancedValue = 0;
			
			//check the unbalance factor of the left child
			temp2 = temp.leftpoint;
			if (temp2.leftpoint == null && temp2.rightpoint == null) {unbalancedValue = 0;}
			else if (temp2.leftpoint != null && temp2.rightpoint == null) {unbalancedValue = getDepth(temp2.leftpoint);}
			else if (temp2.leftpoint == null && temp2.rightpoint != null) {unbalancedValue = (-1)*getDepth(temp2.rightpoint);}
			else {unbalancedValue = (getDepth(temp2.leftpoint) - getDepth(temp2.rightpoint));}
			
			if (unbalancedValue >= 0){rotateRight(temp);} //single rotation if left heavy
			else {doubleRotationRight(temp);} //double rotation if left child is right heavy
		}
	}

	//function for single rotation left
	public void rotateLeft(NodeForTree temp)
	{
		NodeForTree temp2 = temp.rightpoint;
		temp.rightpoint = temp2.leftpoint;
		temp2.leftpoint = temp;
		temp2.parent = temp.parent;
		temp.parent = temp2;
		++parentChanges;
		
		if (temp2.parent == null){root = temp2;}
		//readjust the depths	
		if (temp.leftpoint == null && temp.rightpoint == null) {temp.depth = 1;}
		else if (temp.leftpoint != null && temp.rightpoint == null) {temp.depth = temp.getDepth(temp.leftpoint) + 1;}
		else if (temp.leftpoint == null && temp.rightpoint != null) {temp.depth = temp.getDepth(temp.rightpoint) + 1;}
		else {temp.depth = Math.max(getDepth(temp.leftpoint), getDepth(temp.rightpoint)) + 1;}
		
		if (temp2.leftpoint == null && temp2.rightpoint == null) {temp2.depth = 1;}
		else if (temp2.leftpoint != null && temp2.rightpoint == null) {temp2.depth = temp.depth;}
		else if (temp2.leftpoint == null && temp2.rightpoint != null) {temp2.depth = temp2.getDepth(temp2.rightpoint) + 1;}
		else {temp2.depth = Math.max(getDepth(temp2.rightpoint), temp.depth) + 1;}
	}

	//function for single rotation right
	public void rotateRight(NodeForTree temp)
	{
		 NodeForTree temp2 = temp.leftpoint;
         temp.leftpoint = temp2.rightpoint;
         temp2.rightpoint = temp;
         temp2.parent = temp.parent;
         temp.parent = temp2;
         ++parentChanges;
         
         if (temp2.parent == null){root = temp2;}
         //readjust the depths
         if (temp.leftpoint == null && temp.rightpoint == null) {temp.depth = 1;}
 		 else if (temp.leftpoint != null && temp.rightpoint == null) {temp.depth = temp.getDepth(temp.leftpoint) + 1;}
 		 else if (temp.leftpoint == null && temp.rightpoint != null) {temp.depth = temp.getDepth(temp.rightpoint) + 1;}
 		 else {temp.depth = Math.max(getDepth(temp.leftpoint), getDepth(temp.rightpoint)) + 1;}
 		
 		 if (temp2.leftpoint == null && temp2.rightpoint == null) {temp2.depth = 1;}
 		 else if (temp2.leftpoint != null && temp2.rightpoint == null) {temp2.depth = temp2.getDepth(temp2.leftpoint) + 1;}
 		 else if (temp2.leftpoint == null && temp2.rightpoint != null) {temp2.depth = temp.depth;}
 		 else {temp2.depth = Math.max(getDepth(temp2.leftpoint), temp.depth) + 1;}
	}

	//function for double rotation left
	public void doubleRotationLeft(NodeForTree temp)
	{
		rotateRight(temp.rightpoint);
		rotateLeft(temp);
	}

	//function for double rotation right
	public void doubleRotationRight(NodeForTree temp)
	{
		rotateLeft(temp.leftpoint);
		rotateRight(temp);
	}
	
	//function to print the parent changes
	private void printParentChanges()
	{
		System.out.print("\nThere were " + parentChanges + " parent changes performed");
	}
	
	public static void main(String[] args)
	{
		ATree testing = new ATree(); //create new object of class
		char task; //track the operation
		String elementString; //string in the line
		int element; //number in the line
		int traversing = -1;
		int trackTrav = 0;
		String lineInText = null;
		
        String fileName = args[0]; //filename comes from first argument 
        
        if (args.length == 2){traversing = Integer.parseInt(args[1]);} //if the number of operations before post order is passed, fetch it and store in traversing
        
        //get the file
        FileReader fileReader = null;
		try {fileReader = new FileReader(fileName);} 
		catch (FileNotFoundException e) {e.printStackTrace();}
        
		//read the file
		BufferedReader bufferedReader = new BufferedReader(fileReader);
        try 
        {
			while((lineInText = bufferedReader.readLine()) != null)  //read each line 
			{
				task  = lineInText.charAt(0); //get the task
				elementString = lineInText.substring(1); //get the number
				element = Integer.parseInt(elementString); //convert from string to int
				
				if (trackTrav == traversing){testing.Postorder(); trackTrav = 0;} //do traversal when trackTrav equals to traversing 
				if (task == 'a'){testing.addElement(element); ++trackTrav;} //add when task is a
				else if (task == 'f'){testing.findNodes(element);++trackTrav;} //find when task is f
				else if (task == 'r'){testing.deleteElement(element);++trackTrav;}	//delete when task is r
			}
		}
        catch (NumberFormatException e) {e.printStackTrace();} 
        catch (IOException e) {e.printStackTrace();}   

        try {bufferedReader.close();} 
        catch (IOException e) {e.printStackTrace();}
		
		testing.printAdds();
		testing.printCompares();
		testing.printDeletes();
		testing.printFinds();
		testing.printParentChanges();
	}

}
