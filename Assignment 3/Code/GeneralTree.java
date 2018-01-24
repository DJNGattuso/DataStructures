
public class GeneralTree extends NodeForTree
{
	//variables in general tree that are necessary for tracking operations
	public int numberOfAdds = 0;
	public int numberOfDeletes = 0;
	private int numberOfFailDeletes = 0;
	private boolean deleteSuccess = false;
	public int numberOfCompares = 0;
	private boolean found = false;
	private int findSuccessful = 0;
	private int findFailed = 0;
	
	//function to add a node
	public void addElement(int val)
	{
		NodeForTree temp = new NodeForTree(null, val, null, 0, 0);
		NodeForTree temp2 = new NodeForTree();
		System.out.println("Adding element " + temp.print());
		
		if (root != null) //checks to see if a root already exist
		{
			++numberOfCompares;
			if (temp.value < root.value) //if the new element is less than the root
			{
				if (root.leftpoint != null) //if the root has a left child
				{
					temp2 = root.leftpoint;
				}
				else 
				{ 
					root.leftpoint = temp;
					temp.rightpoint = root;
				}
			}
			else //the new element is greater than the root
			{
				if (root.rightpoint != null) //the root has a right child
				{
					temp2 = root.rightpoint;
				}
				else 
				{ 
					root.rightpoint = temp;
					if (root.depth < 1)
					++root.depth; 
				}
			}
			
			++numberOfCompares;
			if (temp.value < temp2.value) //checks to see if the new value is less than the current node
			{
				if (temp2.leftpoint != null)
				{
					addLeft(temp, temp2);
				}
				else 
				{
					temp2.leftpoint = temp;
					temp.rightpoint = temp2;
				}
			}
			else //new element is greater than current node
			{
				if (temp2.depth != 0)
				{
					addRight(temp,temp2);
				}
				else 
				{
					temp2.rightpoint = temp;
					temp.rightpoint = root;
					++temp2.depth;
				}
			}
		}
		else { root = temp;}
		++numberOfAdds;
	}
	
	//function for add node to add to the left
	private void addLeft(NodeForTree t1, NodeForTree t2)
	{
		NodeForTree t3 = t2;
		t2 = t2.leftpoint;
		++numberOfCompares;
		if (t1.value < t2.value)
		{
			if (t2.leftpoint != null)
			{
				addLeft(t1, t2);
			}
			else 
			{
				t2.leftpoint = t1;
				t1.rightpoint = t2;
			}
		}
		else
		{
			if (t2.depth != 0)
			{
				addRight(t1,t2);
			}
			else 
			{
				t2.rightpoint = t1;
				t1.rightpoint = t3;
				++t2.depth;
			}
		}	
	}
	
	//function to add node to add to the right
	private void addRight(NodeForTree t1, NodeForTree t2)
	{
		NodeForTree t3 = new NodeForTree();
		t2 = t2.rightpoint;
		++numberOfCompares;
		if (t2.depth == 0)
		{
			t3 = t2.rightpoint;
		}
		
		if (t1.value < t2.value) //checks to see if the new value is less than the current node
		{
			if (t2.leftpoint != null)
			{
				addLeft(t1, t2);
			}
			else 
			{
				t2.leftpoint = t1;
				t1.rightpoint = t2;
			}
		}
		else
		{
			if (t2.depth != 0)
			{
				addRight(t1,t2);
			}
			else 
			{
				t2.rightpoint = t1;
				++t2.depth;
				t1.rightpoint = t3;
			}
		}
	}
	
	//function to delete an element
	public void deleteElement(int value)
	{

		finding(root,value); //checks to see if the element exist in the tree
		resetTraversal(root); //reset the traversal
		if (found == true) //element exist
		{
			found = false;  
			root.tagTraversal = 1;
			if (root.value == value) //is the root the value to delete
			{
				if (root.leftpoint == null && root.rightpoint == null) //no child
				{
					root = null;
				}
				else if (root.leftpoint != null) //left child
				{
					root.value = getMax(root.leftpoint); //get the max of the left subtree
					deleteLeft(root.leftpoint, root.value, root); //delete max
					deleteSuccess = true;
				}
				else if (root.rightpoint != null) //right child
				{
					root.value = getMin(root.rightpoint); //get min of right subtree
					deleteRight(root.rightpoint, root.value, root); //delete min
					deleteSuccess = true;
				}
			}
			else if (value < root.value) //if value is less than root
			{
				deleteLeft(root.leftpoint,value,root);
			}
			else //value is greater than
			{
				deleteRight(root.rightpoint,value,root);
			}
		}
		
		if (deleteSuccess == true)
		{
			deleteSuccess = false;
			++numberOfDeletes;
		}
		else {++numberOfFailDeletes;}
		resetTraversal(root);
	}
	
	//function to delete from the left subtree
	private void deleteLeft(NodeForTree temp,int val, NodeForTree temp2)
	{
		NodeForTree tracker = temp.rightpoint;
		
		if (temp.tagTraversal != 1)
		{
			temp.tagTraversal = 1;
			
			if (temp.value == val)
			{
				if (temp.leftpoint == null && temp.rightpoint == null)
				{
					temp2.leftpoint = null;
					deleteSuccess = true;
				}
				else if (temp.leftpoint == null && tracker.tagTraversal == 1)
				{
					temp2.leftpoint = null;
					deleteSuccess = true;
				}
				else if (temp.leftpoint != null)
				{
					temp.value = getMax(temp.leftpoint);
					deleteLeft(temp.leftpoint, temp.value, temp);
					deleteSuccess = true;
				}
				else 
				{
					temp.value = getMin(temp.rightpoint);
					deleteRight(temp.rightpoint, temp.value, temp);
					deleteSuccess = true;
				}
			}
			else if (val < temp.value)
			{
				if (temp.leftpoint != null){deleteLeft(temp.leftpoint,val,temp);}
			}
			else
			{
				if (temp.rightpoint != null){deleteRight(temp.rightpoint,val,temp);}
			}
		}
	}
	
	//function to delete from right subtree
	private void deleteRight(NodeForTree temp,int val, NodeForTree temp2)
	{
		NodeForTree tracker = null;
		if (temp.rightpoint == null){}
		else {tracker = temp.rightpoint;}
		
		if (temp.tagTraversal != 1)
		{
			temp.tagTraversal = 1;
			
			if (temp.value == val)
			{
				if (temp.leftpoint == null && temp.rightpoint == null)
				{
					temp2.rightpoint = null;
					deleteSuccess = true;
				}
				else if (temp.leftpoint == null && tracker.tagTraversal == 1)
				{
					temp2.rightpoint = temp.rightpoint;
					deleteSuccess = true;
				}
				else if (temp.leftpoint != null)
				{
					temp.value = getMax(temp.leftpoint);
					deleteLeft(temp.leftpoint, temp.value, temp);
					deleteSuccess = true;
				}
				else 
				{
					temp.value = getMin(temp.rightpoint);
					deleteRight(temp.rightpoint, temp.value, temp);
					deleteSuccess = true;
				}
			}
			else if (val < temp.value)
			{
				if (temp.leftpoint != null){deleteLeft(temp.leftpoint,val,temp);}
			}
			else
			{
				if (temp.rightpoint != null){deleteRight(temp.rightpoint,val,temp);}
			}
		}
	}
	
	//function to get the min of the right
	public int getMin(NodeForTree temp)
	{
		int min = temp.value;
		
		while (temp.leftpoint != null)
		{
			min = temp.value;
			temp = temp.leftpoint;
		}
		
		return min;
	}
	
	//function to get max of the left
	public int getMax(NodeForTree temp)
	{
		int max = temp.value;

		while (temp.rightpoint != null)
		{
			max = temp.value;
			temp = temp.rightpoint;
		}
		
		return max;
	}
	
	//function to print number of adds
	public void printAdds()
	{
		System.out.print("\nThere were " + numberOfAdds + " adds completed");
	}
	
	//function to print the deletes
	public void printDeletes()
	{
		System.out.print("\nThere were " + numberOfDeletes + " element(s) deleted and " + numberOfFailDeletes + " unsuccessful deletes possibly due to non-existing element");
	}
	
	//function to print number of compares
	public void printCompares()
	{
		System.out.print("\nThere were " + numberOfCompares + " compares performed");
	}
	
	//function to print the finds
	public void printFinds()
	{
		System.out.print("\nThe number of successful finds is " + findSuccessful + " the number of failed finds is " + findFailed);
	}
	
	//function to find a node
	public void findNodes(int look)
	{
		found = false;
		root.tagTraversal = 1;
			
		++numberOfCompares;
		if (root.value == look) //is the root the value
		{
			found = true;
		}
		else if (look < root.value) //is the value less than the root
		{
			if (root.leftpoint != null) //is there a left child
			{
				finding(root.leftpoint,look);
			}
		}
		else //is the value greater than the root
		{
			if (root.rightpoint != null) //is there a right child
			{
				finding(root.rightpoint,look);
			}
		}
		
		if (found == true)
		{++findSuccessful;}
		else {++findFailed;}
		
		resetTraversal(root);
		
	}
	
	//function to find
	private void finding(NodeForTree temp, int l)
	{
		if (temp.tagTraversal != 1)
		{
			temp.tagTraversal = 1;
			++numberOfCompares;
			if (temp.value == l) //is the value the same
			{
				found = true;
			}
			else if (l < temp.value) //is it less than 
			{
				if (temp.leftpoint != null)
				{
					finding(temp.leftpoint,l);
				}
			}
			else
			{
				if (temp.rightpoint != null) //is it greater than 
				{
					finding(temp.rightpoint,l);
				}
			}
		}
	}
	
	
	//function to traverse inorder
	public void inorder()
	{         
		System.out.println("\nTraversing inorder...");
		NodeForTree temp = root;
		NodeForTree temp2 = new NodeForTree();
		NodeForTree dummy = new NodeForTree();
		NodeForTree temp3 = new NodeForTree();
		
		while (temp.leftpoint != null)
    	{
    		temp = temp.leftpoint;
    	}
        
        
	    while(temp.tagTraversal < 1)
	    {
	    	while (temp.leftpoint != null)
	    	{
	    		temp3 = temp.leftpoint;
	    		if (temp3.tagTraversal != 0)
	    		{
	    			break;
	    		}
	    		temp = temp.leftpoint;
	    	}
	    	
	    	System.out.print(temp.print() + " ");
	        ++temp.tagTraversal;
	          
	        if (temp.leftpoint != null)
	        {temp2 = temp.leftpoint;}
	        else {temp2 = dummy; temp2.tagTraversal = 1;}
	          
	        if (temp2.tagTraversal == 0)
	        {
	       	  temp = temp2;
	        }
	        else {temp = temp.rightpoint;}   
        }
	    
	    resetTraversal(root);
	}
	
	//function to call postorder traversal from main
	public void Postorder()
    {
		System.out.println("\nTraversing Postorder...");
		postOrder(root);
		resetTraversal(root);
    }
	
	//function to traverse postorder
	private void postOrder(NodeForTree temp)
	{
		if (temp != null && temp.tagTraversal == 0) //if the node exist
		{
			temp.tagTraversal = 1; //mark that you've been here
			postOrder(temp.leftpoint); //traverse its left subtree
			postOrder(temp.rightpoint); //traverse its right subtree
					
			System.out.print(temp.print() + " ");	//print element
		}
	}
	
	//function to reset the traversal tag
	private void resetTraversal(NodeForTree temp)
	{
		if (temp != null && temp.tagTraversal == 1)
		{
			temp.tagTraversal = 0;
			resetTraversal(temp.leftpoint);
			resetTraversal(temp.rightpoint);
		}
	}
	
	
	public static void main(String[] args)
	{
		GeneralTree test = new GeneralTree();
		test.addElement(4);
		test.addElement(3);
		test.addElement(2);
		test.addElement(1);
		test.addElement(5);
		test.addElement(7);
		
	    test.Postorder();
	    test.findNodes(3);
	    test.deleteElement(1);
	    test.inorder();
	    test.findNodes(8);
	    test.printAdds();
		test.printCompares();
		test.printFinds();
		test.printDeletes();
	}
}
