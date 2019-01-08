package dataStructures;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap {
	private HeapList trees;
	private static int totalLinks;
	private static int totalCuts;
	private static int marks;
	private HeapNode min;
	private int size;
	private int numberOfTrees;//Roee: the size of the list trees
	
	public static void main(String[] args){
		System.out.println("project");
		deleteMinTests();
		
		
	}
	public static void deleteMinTests(){
		FibonacciHeap fib = new FibonacciHeap();
		fib.insert(14);
		fib.insert(30);
		fib.insert(5);
		fib.insert(9);
		fib.insert(3);
		fib.insert(21);
		fib.insert(50);
		fib.insert(19);
		fib.printRootKeys();
		fib.deleteMin();
		fib.printRootKeys();
		fib.deleteMin();
		fib.printRootKeys();
		System.out.println("");
		
	}
	public void printRootKeys(){
		for(HeapNode node: this.trees){
			System.out.print(node.key+"("+node.rank+")"+"\t");
		}
		System.out.println("size:"+this.size+" minkey:"+this.min.key);
		//System.out.print("\n");
	}
	public FibonacciHeap(){
		this.trees = new HeapList();
		this.min = new HeapNode(Integer.MAX_VALUE);
	}
	
	/**
	 * increase marks of the Heap
	 * used for potential function
	 * @param i
	 */
	private static void increaseMarks(int i) {
		marks += i;
	}

	/**
	 * public boolean empty()
	 *
	 * precondition: none
	 * 
	 * The method returns true if and only if the heap is empty.
	 * 
	 */
	public boolean empty() {
		return this.trees.size() == 0;
	}

	/**
	 * public HeapNode insert(int key)
	 *
	 * Creates a node (of type HeapNode) which contains the given key, and inserts
	 * it into the heap.
	 */
	public HeapNode insert(int key) {
		HeapNode newNode = new HeapNode(key);
		newNode.rank=0;
		this.trees.add(newNode);
		this.numberOfTrees++;
		this.size++;
		this.min=chooseTheSmallerNode(this.min,newNode);
		
		
		return newNode;
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the node containing the minimum key.
	 *
	 */
	public void deleteMin()
    {
		//##part I
    	if (this.empty()){
    		return;
    	}
    	if(this.size()==1){
    		emptyTheHeap();
    		return;
    	}
    	removeMinNodeAndUpgradeItsChildren();
    	
    	//this.printRootKeys();
    	
    	//##part II
    	double boundingRatio=1.4404;
    	double maxTreeRank = (Math.log(this.size())/Math.log(2))*boundingRatio;
    	//according to slide 84 the maximal tree rank is bounded by maxTreeRank
    	int roundedMaxTreeRank = (int) Math.floor(maxTreeRank)+1;
    	HeapNode[] Bins = new HeapNode[roundedMaxTreeRank];
    	successiveLinking(Bins);
    	
    	//##part III
    	UpdateNewRootsAndMinNode(Bins);
    	
    	
     	return; // should be replaced by student code
     	
    }
	
	public void emptyTheHeap(){
		
		this.trees = new HeapList();
		marks = 0;
		this.size=0;
		min = null;
		this.numberOfTrees=0;
	}
    /**slide 37
     * removing the minimal node from the list trees and upgrading it's list of children
     * to be new roots
     * Notice!: the field min becomes null after calling thing function
     * **/
    public void removeMinNodeAndUpgradeItsChildren(){
    	
    	
    	HeapNode minNode = this.min;
    	
    	int numberOfChildren = minNode.children.size();
    	this.trees.remove(minNode);
    	for(HeapNode child :minNode.children){
    		child.parent=null;
    		this.trees.add(child);
    	}
    	this.min=null;
    	this.size--;
    	this.numberOfTrees=this.numberOfTrees-1+numberOfChildren;
    	
    }
    public void successiveLinking(HeapNode[]Bins){
    	for(HeapNode root:this.trees){
    		continuousAddition(Bins,root,root.rank);
    		
    	}
    }
    public void continuousAddition(HeapNode[]Bins,HeapNode root,int index){
    	if(Bins[index]==null){
    		Bins[index]=root;
    		return;
    	}
    	continuousAddition(Bins,linkTrees(root,Bins[index]),index+1);
    	Bins[index]=null;
    	
    	
    }
    /**slide 12
     * @pre trees contains root1 and root2
     * returns the new root of the new tree**/
    public HeapNode linkTrees(HeapNode root1,HeapNode root2){
    	if(root1.rank!=root2.rank){
    		System.out.print("problem! trying to link unmatched ranks");
    		return null;
    	}
    	HeapNode smallRoot;
		HeapNode bigRoot;
    	if (root1.key<=root2.key){
    		 smallRoot=root1;
    		 bigRoot=root2;
    	}
    	else{
    		 smallRoot=root2;
    		 bigRoot=root1;
    	}
    	/*
    	 * needed only if we link to withing the successive linking proccess:
    	this.trees.remove(bigRoot);
    	this.numberOfTrees--;
    	*/
    	smallRoot.children.add(bigRoot);
    	bigRoot.parent=smallRoot;
    	smallRoot.rank++;
    	totalLinks++;
    	return smallRoot;
    }
    public void UpdateNewRootsAndMinNode(HeapNode[] bins){
    	
    	boolean searchForMin=this.min==null;
    	HeapNode tmpMin = new HeapNode(Integer.MAX_VALUE);
    	
    	this.trees = new HeapList();
    	this.numberOfTrees=0;
    	for(int i=0;i<bins.length;i++){
    		if(bins[i]!=null){
    			bins[i].parent=null;
    			this.trees.add(bins[i]);
    			numberOfTrees++;
    			if(searchForMin){
    				tmpMin=chooseTheSmallerNode(tmpMin,bins[i]);
    			}
    		}
    	}
    	if(searchForMin){
    		this.min=tmpMin;
    	}
    	
    }
    public HeapNode chooseTheSmallerNode(HeapNode a,HeapNode b){
    	if(a.key<=b.key){
    		return a;
    	}
    	return b;
    }

	/**
	 * public HeapNode findMin()
	 *
	 * Return the node of the heap whose key is minimal.
	 *
	 */
	public HeapNode findMin() {
		return min;
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		return; // should be replaced by student code
	}

	/**
	 * public int size()
	 *
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() {
		return size; // should be replaced by student code
	}

	/**
	 * public int[] countersRep()
	 *
	 * Return a counters array, where the value of the i-th entry is the number of
	 * trees of order i in the heap.
	 * 
	 */
	public int[] countersRep() {
		int[] arr = new int[42];
		return arr; // to be replaced by student code
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap.
	 * TODO should probably implement this with decreaseKey+deleteMin
	 */
	public void delete(HeapNode x) {
		this.decreaseKey(x,x.key-(this.min.key-1));
		this.deleteMin();
		return;
	}

	/**
	 * public void decreaseKey(HeapNode x, int delta)
	 *
	 * The function decreases the key of the node x by delta. The structure of the
	 * heap should be updated to reflect this chage (for example, the cascading cuts
	 * procedure should be applied if needed).
	 */
	public void decreaseKey(HeapNode x, int delta) {
		return; // should be replaced by student code
	}

	/**
	 * public int potential()
	 *
	 * This function returns the current potential of the heap, which is: Potential
	 * = #trees + 2*#marked The potential equals to the number of trees in the heap
	 * plus twice the number of marked nodes in the heap.
	 */
	public int potential() {
		return this.numberOfTrees+2*marks;
	}

	/**
	 * public static int totalLinks()
	 *
	 * This static function returns the total number of link operations made during
	 * the run-time of the program. A link operation is the operation which gets as
	 * input two trees of the same rank, and generates a tree of rank bigger by one,
	 * by hanging the tree which has larger value in its root on the tree which has
	 * smaller value in its root.
	 */
	public static int totalLinks() {
		return totalLinks;
	}
	
	/**
	 * 
	 * @param node the node to check
	 * @return true iff node is a root of the heap
	 */
	private boolean isRoot(HeapNode node) {
		return trees.contains(node);
	}
	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made during
	 * the run-time of the program. A cut operation is the operation which
	 * diconnects a subtree from its parent (during decreaseKey/delete methods).
	 */
	public static int totalCuts() {
		return 0; // should be replaced by student code
	}

	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than FibonacciHeap (for example
	 * HeapNode), do it in this file, not in another file
	 * 
	 */
	public class HeapNode {
		private HeapNode parent;
		private int rank=0; // Needs to be updated only if node is the root of a tree
		private HeapList children;
		
		public int key; // TODO Why is this public
		private boolean mark;
		
		public HeapNode(int key, HeapList children, boolean mark) {
			this.key = key;
			this.children = children;
			this.rank = children.size();
		}
		
		public HeapNode(int key) {
			this(key, new HeapList(), false);
		}

		public int getKey() {
			return this.key;
		}
		
		/**
		 * check if node is marked, for cascading cuts
		 * @return
		 */
		public boolean isMarked() {
			return mark;
		}
		
		/**
		 * Mark the current node, if it's not a root node
		 * Assumes node is unmarked 
		 */
		public void mark() {
			if (!this.isMarked() && !FibonacciHeap.this.isRoot(this)){
				this.mark = true;
				FibonacciHeap.increaseMarks(1);
			}
		}
		
		/**
		 * Cuts the child from the parent, so that it can be added to the root list
		 * assumes this == child.parent
		 * @param child
		 * @return child
		 */
		public HeapNode cut(HeapNode child) {
			this.children.remove(child);
			child.removeParent();
			return child;
		}
		
		/**
		 * remove's this node's parent
		 */
		private void removeParent() {
			this.setParent(null);
		}
		
		private void setParent(HeapNode newParent) {
			this.parent = newParent;
		}

		public HeapNode getParent() {
			return parent;
		}

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}
	}
	/**
	 * A self implemented list to contain HeapNodes 
	 *
	 */
	public class HeapList implements Iterable<HeapNode>{
		private int size;
		private HeapNode first;
		private HeapNode last;
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}
		public boolean contains(HeapNode node) {
			// TODO Auto-generated method stub
			return false;
		}
		public void remove(HeapNode minNode) {
			// TODO Auto-generated method stub
			
		}
		public void add(HeapNode newNode) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public Iterator<HeapNode> iterator() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
