
public class NodeForTree 
{
	//variables to add in constructor
	public NodeForTree root;
	public int value;
	public NodeForTree leftpoint;
	public NodeForTree rightpoint;
	public NodeForTree parent;
	public int frequency;
	public int occurence;
	public NodeForTree leftPriority;
	public NodeForTree rightPriority;
	public int depth;
	public int tagTraversal;

	
	//constructor
	public NodeForTree()
	{}
	
	//constructor with parameters for general tree
	public NodeForTree(NodeForTree leftpoint, int value, NodeForTree rightpoint, int depth, int tagTraversal)
	{
		this.leftpoint = leftpoint;
		this.value = value;
		this.rightpoint = rightpoint;
		this.depth = depth;
		this.tagTraversal = tagTraversal;
	}
	
	//constructor for AVL Tree
	public NodeForTree(NodeForTree leftpoint, int value, NodeForTree rightpoint, NodeForTree parent, int depth, int tagTraversal)
	{
		this.leftpoint = leftpoint;
		this.value = value;
		this.rightpoint = rightpoint;
		this.parent = parent;
		this.depth = depth;
		this.tagTraversal = tagTraversal;
	}
	
	//constructor for Huffman
	public NodeForTree(NodeForTree leftpoint, char value, NodeForTree rightpoint, NodeForTree parent, int tagTraversal, int frequency, int occurence, NodeForTree leftPri, NodeForTree rightPri)
	{
		this.leftpoint = leftpoint;
		this.value = value;
		this.rightpoint = rightpoint;
		this.parent = parent;
		this.tagTraversal = tagTraversal;
		this.frequency = frequency;
		this.occurence = occurence;
		this.leftPriority = leftPri;
		this.rightPriority = rightPri;
	}
	
	//function to print node value
	public String print()
	{
		return value + "";
	}
	
	//function to get the depth for AVL tree
	public int getDepth(NodeForTree temp)
	{
		return temp.depth;
	}


}
